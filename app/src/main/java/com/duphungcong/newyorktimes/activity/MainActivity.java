package com.duphungcong.newyorktimes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.duphungcong.newyorktimes.R;
import com.duphungcong.newyorktimes.adapter.ArticlesAdapter;
import com.duphungcong.newyorktimes.adapter.EndlessRecyclerViewScrollListener;
import com.duphungcong.newyorktimes.api.NYTClient;
import com.duphungcong.newyorktimes.api.NYTResponse;
import com.duphungcong.newyorktimes.api.NYTService;
import com.duphungcong.newyorktimes.common.Constant;
import com.duphungcong.newyorktimes.fragment.FilterFragment;
import com.duphungcong.newyorktimes.model.Article;
import com.duphungcong.newyorktimes.viewmodel.ArticleFilter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilterFragment.FinishFilterListener {
    private RecyclerView rvArticles;
    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    // Query params
    private String currentQuery = null;
    private int currentPage = 0;
    private ArticleFilter articleFilter = new ArticleFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(MainActivity.this, articles);

        rvArticles.setAdapter(articlesAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvArticles.setLayoutManager(gridLayoutManager);

        fetchArticles(currentQuery, articleFilter, currentPage);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                currentPage = page;
                fetchArticles(currentQuery, articleFilter, currentPage);
            }
        };
        // Add the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(scrollListener);

        articlesAdapter.setOnItemClickListener(new ArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Article article = articles.get(position);
                Uri uri = Uri.parse(article.getWebUrl());
                Intent intent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                intent.putExtra("uri", uri);
                startActivity(intent);

            }
        });
    }

    public void fetchArticles(String query, ArticleFilter filter, int page) {
        NYTService service = NYTClient.getService().create(NYTService.class);
        Call<NYTResponse> call = service.getArticleSearch(Constant.API_KEY,             // api-key
                                                            query,                      // search text
                                                            filter.getSort(),           // sort filter
                                                            filter.getNewsDeskQuery(),  // news_desk filter
                                                            filter.getBeginDateQuery(), // begin_date filter
                                                            page);                      // pagination
        call.enqueue(new Callback<NYTResponse>() {
            @Override
            public void onResponse(Call<NYTResponse> call, Response<NYTResponse> response) {
                if(response.isSuccessful()) {
                    Log.v("MSG", response.body().toString());
                    List<Article> newArticles = response.body().getResponse().getDocs();
                    articlesAdapter.updateList(newArticles);
                }
            }

            @Override
            public void onFailure(Call<NYTResponse> call, Throwable t) {
                // Invalid JSON format, show appropriate error.
                Log.v("MSG", "NOT RESPONSE");
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                articlesAdapter.clearList();
                fetchArticles(currentQuery, articleFilter, currentPage);

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            showFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment fragment = FilterFragment.newInstance(articleFilter);
        fragment.show(fm, "fragment_filter");
    }

    // Pass new filter setting when user click save icon on toolbar
    @Override
    public void onSaveFilter(ArticleFilter savedFilter) {
        articleFilter = savedFilter;
        articlesAdapter.clearList();
        scrollListener.resetState();
        currentPage = 0;
        fetchArticles(currentQuery, articleFilter, currentPage);
    }

    // Restore filter setting of previous session when user click cancel icon on toolbar
    @Override
    public void onCancelFilter(ArticleFilter backupFilter) {
        articleFilter = backupFilter;
    }
}
