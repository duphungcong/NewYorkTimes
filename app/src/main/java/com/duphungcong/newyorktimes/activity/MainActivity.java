package com.duphungcong.newyorktimes.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
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

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
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
    }

    private class FetchArticlesTask extends AsyncTask<Call, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(Call... params) {
            try {
                Call<NYTResponse> call = params[0];
                Response<NYTResponse> response = call.execute();
                return response.body().getResponse().getDocs();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            articlesAdapter.refresh(articles);
        }
    }

    public void fetchArticles(String query, ArticleFilter filter, int page) {
        NYTService service = NYTClient.getService().create(NYTService.class);
        final Call<NYTResponse> call = service.getArticleSearch(Constant.API_KEY,       // api-key
                                                            query,                      // search text
                                                            filter.getSort(),           // sort filter
                                                            filter.getNewsDeskQuery(),  // news_desk filter
                                                            filter.getBeginDateQuery(), // begin_date filter
                                                            page);                      // pagination
        new FetchArticlesTask().execute(call);
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
                Toast.makeText(MainActivity.this, currentQuery, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(MainActivity.this, savedFilter.getSort(), Toast.LENGTH_SHORT).show();
    }

    // Restore filter setting of previous session when user click cancel icon on toolbar
    @Override
    public void onCancelFilter(ArticleFilter backupFilter) {
        articleFilter = backupFilter;
    }
}
