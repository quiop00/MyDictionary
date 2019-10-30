package com.example.mydictionaryv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Detail extends AppCompatActivity {
    private WebView webView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        String a=intent.getStringExtra("content");
        WebViewClient yourWebClient = new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView  view, String  url)
            {
                // This line we let me load only pages inside  Webpage
                if ( url.contains("") == true )
                    // Load new URL Don't override URL Link
                    return false;

                // Return true to override url loading (In this case do nothing).
                return true;
            }
        };
        webView=findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //viewHolder.webView.loadData("<strong>"+item.getWord()+"</strong>","text/html","utf-8");
       webView.loadDataWithBaseURL("","<div style='font-size:150%'>"+ a+"</div>","text/html","UTF-8", "");
        webView.setWebViewClient(yourWebClient);
    }
}
