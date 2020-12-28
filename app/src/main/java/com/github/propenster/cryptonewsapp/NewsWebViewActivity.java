package com.github.propenster.cryptonewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsWebViewActivity extends AppCompatActivity {

    WebView newsWebView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);
        newsWebView = (WebView)findViewById(R.id.newsWebView);

        displayNewsItemWebView();
        //setContentView(newsWebView);

    }

    public void displayNewsItemWebView() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        newsWebView.loadUrl(url);
    }
}