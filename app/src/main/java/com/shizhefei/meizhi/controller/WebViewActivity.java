package com.shizhefei.meizhi.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.controller.common.BaseActivity;
import com.shizhefei.meizhi.utils.UIHelper;

public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private String url;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    public static final String INTENT_STRING_URL = "intent_String_url";
    public static final String INTENT_STRING_TITLE = "intent_string_title";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarVisiable(false);
        setContentView(R.layout.fragment_web);
        url = getIntent().getStringExtra(INTENT_STRING_URL);
        title = getIntent().getStringExtra(INTENT_STRING_TITLE);
        progressBar = (ProgressBar) findViewById(R.id.web_progressBar);
        webView = (WebView) findViewById(R.id.web_webView);
        toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        setSupportActionBar(toolbar);
        if (TextUtils.isEmpty(title)) {
            toolbar.setTitle(url);
        } else {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);

        webView.setWebChromeClient(webChromeClient);

    }

    public static void startWeb(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(INTENT_STRING_URL, url);
        intent.putExtra(INTENT_STRING_TITLE, title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_web_refresh:
                webView.reload();
                return true;
            case R.id.menu_web_copy:
                Snackbar.make(webView, "网址复制成功" + "\n" + webView.getUrl(), 500).show();
                return true;
            case R.id.menu_web_share:
                ShareCompat.IntentBuilder.from(this).setText(webView.getUrl()).setType("text/plain").startChooser();
                return true;
        }
        return false;
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

    };

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (TextUtils.isEmpty(title)) {
                toolbar.setTitle(url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (TextUtils.isEmpty(title)) {
                toolbar.setTitle(url);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIHelper.destroyWebView(webView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
