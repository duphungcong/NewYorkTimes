package com.duphungcong.newyorktimes.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.duphungcong.newyorktimes.R;
import com.duphungcong.newyorktimes.adapter.ArticlesAdapter;
import com.duphungcong.newyorktimes.api.NYTClient;
import com.duphungcong.newyorktimes.api.NYTResponse;
import com.duphungcong.newyorktimes.api.NYTService;
import com.duphungcong.newyorktimes.common.Constant;
import com.duphungcong.newyorktimes.model.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvArticles;
    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(MainActivity.this, articles);

        rvArticles.setAdapter(articlesAdapter);

        rvArticles.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fetchArticles(null);
    }

    public void fetchArticles(String query) {
        NYTService service = NYTClient.getService().create(NYTService.class);
        Call<NYTResponse> call = service.getArticleSearch(Constant.API_KEY, null);
        call.enqueue(new Callback<NYTResponse>() {
            @Override
            public void onResponse(Call<NYTResponse> call, Response<NYTResponse> response) {
                List<Article> newArticles = response.body().getResponse().getDocs();
                articles.addAll(newArticles);
                articlesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NYTResponse> call, Throwable t) {
                // Invalid JSON format, show appropriate error.
                t.printStackTrace();
            }
        });
    }
}
