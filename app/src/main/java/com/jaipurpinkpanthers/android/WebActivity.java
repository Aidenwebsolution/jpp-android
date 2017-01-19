package com.jaipurpinkpanthers.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.jaipurpinkpanthers.android.util.WebViewController;

public class WebActivity extends AppCompatActivity {

    WebView webview;
    String webLink;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = this.getIntent();
        webLink = intent.getStringExtra("webLink");
        initilizeViews();
    }

    public void initilizeViews(){

        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewController());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(webLink);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.destroy();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        }  else {
            super.onBackPressed();
            webview.goBack();
            webview.clearHistory();
            webview.destroy();
            Log.d("initilizeViews:1232132","webview");

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        this.overridePendingTransition(R.anim.slide_right_in_slow, R.anim.slide_left_out_slow);
        //Re-register receivers on resume
        //registerReceivers();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("initilizeViews:1231","webview");

    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.destroy();
        webview.goBack();
        webview.clearHistory();
        finish();

    }
}
