package com.dheia.finalyear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsWebview extends AppCompatActivity {

    private ProgressBar progressBar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_webview);

        Intent receiveIntent = getIntent();
        String url = receiveIntent.getStringExtra("url");
        ;

        WebView webView = findViewById(R.id.newsWebview);
        frameLayout = findViewById(R.id.framelayoutnews);

        progressBar = findViewById(R.id.webViewprogressbar);

        progressBar.setMax(100);

        progressBar.setVisibility(View.VISIBLE);


        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                         super.onReceivedError(view, request, error);
                                         Toast.makeText(NewsWebview.this, "Sorry Please try again later", Toast.LENGTH_SHORT).show();
                                     }
                                 }
        );
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                setTitle("Loading...");

                if (newProgress == 100) {
                    frameLayout.setVisibility(View.GONE);
                    setTitle("NewsFeed");
                }
                super.onProgressChanged(view, newProgress);


            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        progressBar.setProgress(0);
        webView.loadUrl(url);

    }


}
