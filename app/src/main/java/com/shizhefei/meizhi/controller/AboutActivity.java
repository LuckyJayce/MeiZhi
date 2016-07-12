package com.shizhefei.meizhi.controller;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.controller.common.BaseActivity;

public class AboutActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private FloatingActionButton githubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 19) {
            setStatusBarVisiable(false);
        }

        setContentView(R.layout.activity_about);
        toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.about_collapsingToolbarLayout);
        githubButton = (FloatingActionButton) findViewById(R.id.about_floatingActionButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(getString(R.string.luckyjayce));

        githubButton.setOnClickListener(onClicklistener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_detail_share:
                String url = getString(R.string.url_github_meizhi);
                ShareCompat.IntentBuilder.from(this).setText(url).setType("text/plain").startChooser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == githubButton) {
                String url = getString(R.string.url_github_luckyjayce);
                String name = getString(R.string.luckyjayce);
                WebViewActivity.startWeb(getApplicationContext(), name, url);
            }
        }
    };

}
