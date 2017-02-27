package com.duphungcong.newyorktimes.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.duphungcong.newyorktimes.R;

/**
 * Created by udcun on 2/27/2017.
 */

public class ArticleDetailActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Uri uri = getIntent().getParcelableExtra("uri");

        webView = (WebView) findViewById(R.id.wvArticleDetail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(uri.toString());
    }
}
