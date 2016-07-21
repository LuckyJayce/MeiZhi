package com.shizhefei.meizhi.controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.controller.common.BaseActivity;
import com.shizhefei.meizhi.modle.datasource.MeizhisAsyncDataSource;
import com.shizhefei.meizhi.modle.entry.Meizhi;
import com.shizhefei.meizhi.view.adapter.MeizhiAdapter;
import com.shizhefei.mvc.MVCSwipeRefreshHelper;
import com.shizhefei.recyclerview.HFAdapter;

import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Toolbar toolBar;
    private MVCSwipeRefreshHelper<List<Meizhi>> mvcHelper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MeizhiAdapter meizhiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarVisiable(false);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.main_toolBar);
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);

        toolBar.setTitle(R.string.app_name);
        setSupportActionBar(toolBar);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipeRefreshLayout);
        mvcHelper = new MVCSwipeRefreshHelper<>(swipeRefreshLayout);
        mvcHelper.setAdapter(meizhiAdapter = new MeizhiAdapter());
        mvcHelper.setDataSource(new MeizhisAsyncDataSource());
        mvcHelper.refresh();

        meizhiAdapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvcHelper.destory();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        String url;
        String title;
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_main_github_login:
                url = getString(R.string.url_login_github);
                title = getString(R.string.menu_github_login);
                WebViewActivity.startWeb(getContext(), title, url);
                return true;
            case R.id.menu_main_github_hot:
                url = getString(R.string.url_github_trending);
                title = getString(R.string.menu_github_hot);
                WebViewActivity.startWeb(getContext(), title, url);
                return true;
            case R.id.menu_main_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public HFAdapter.OnItemClickListener onItemClickListener = new HFAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(HFAdapter adapter, RecyclerView.ViewHolder vh, int position) {

            MeizhiAdapter.ItemViewHolder viewHolder = (MeizhiAdapter.ItemViewHolder) vh;

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Drawable drawable = viewHolder.imageView.getDrawable();
            if (drawable != null) {
                intent.putExtra(DetailActivity.EXTRA_INT_IMAGEHEIGHT, drawable.getBounds().width());
                intent.putExtra(DetailActivity.EXTRA_INT_IMAGEWIDTH, drawable.getBounds().height());
            }
            Meizhi meizhi = meizhiAdapter.getData().get(position);
            intent.putExtra(DetailActivity.EXTRA_MEIZHI_MEIZHI, meizhi);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    MainActivity.this, viewHolder.imageView, DetailActivity.TRANSIT_PIC);
            try {
                ActivityCompat.startActivity(MainActivity.this, intent, optionsCompat.toBundle());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                startActivity(intent);
            }

        }
    };


    private long time;
    private long t;

    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        long cTime = System.currentTimeMillis();
        if (cTime - time < 2000) {
            t++;
            if (t >= 2) {
                finish();
            }
        } else {
            t = 1;
        }
        if (t == 1) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
        time = cTime;
    }
}
