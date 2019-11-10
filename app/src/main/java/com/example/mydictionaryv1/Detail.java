package com.example.mydictionaryv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Detail extends AppCompatActivity {
    private WebView webView;
    private ViewPager viewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        int id=intent.getIntExtra("id",0);
//        String word=intent.getStringExtra("word");
//        String a=intent.getStringExtra("content");
        String oldClass=intent.getStringExtra("fragment");
        viewPager =findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPager(this,id,oldClass));

    }
}
