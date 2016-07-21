package com.shizhefei.meizhi.controller;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.shizhefei.meizhi.R;
import com.shizhefei.meizhi.controller.common.BaseActivity;
import com.shizhefei.meizhi.modle.datasource.GankRxDataSource;
import com.shizhefei.meizhi.modle.entry.Gank;
import com.shizhefei.meizhi.modle.entry.Meizhi;
import com.shizhefei.meizhi.utils.DisplayUtils;
import com.shizhefei.meizhi.utils.ImageViewAutoHeightListener;
import com.shizhefei.meizhi.view.LoadView;
import com.shizhefei.meizhi.view.adapter.DetailPagerAdapter;
import com.shizhefei.mvc.MVCNormalHelper;
import com.shizhefei.mvc.http.UrlBuilder;
import com.shizhefei.utils.ArrayListMap;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.List;

public class DetailActivity extends BaseActivity {
    public static final String EXTRA_MEIZHI_MEIZHI = "extra_meizhi_meizhi";
    public static final String EXTRA_INT_IMAGEWIDTH = "extra_int_imagewidth";
    public static final String EXTRA_INT_IMAGEHEIGHT = "extra_int_imageheight";
    public static final String TRANSIT_PIC = "TRANSIT_PIC";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private View contentLayout;
    private ScrollIndicatorView indicator;
    private ViewPager viewPager;
    private ImageView meizhiImageView;
    private Toolbar toolbar;
    private Meizhi meizhi;
    private IndicatorViewPager indicatorViewPager;
    private MVCNormalHelper<ArrayListMap<String, List<Gank>>> mvcHelper;
    private DetailPagerAdapter detailPagerAdapter;
    private View mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 19) {
            setStatusBarVisiable(false);
        }
        setContentView(R.layout.activity_detail);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.detail_collapsingToolbarLayout);
        mainContent = findViewById(R.id.main_content);
        appBarLayout = (AppBarLayout) findViewById(R.id.detail_appbarLayout);
        contentLayout = findViewById(R.id.detail_content_layout);
        indicator = (ScrollIndicatorView) findViewById(R.id.detail_indicator);
        viewPager = (ViewPager) findViewById(R.id.detail_viewPager);
        meizhiImageView = (ImageView) findViewById(R.id.detail_meizhi_imageView);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        meizhi = (Meizhi) getIntent().getSerializableExtra(EXTRA_MEIZHI_MEIZHI);
        int maxHeight = getResources().getDisplayMetrics().heightPixels / 3 * 2;
        if (getIntent().hasExtra(EXTRA_INT_IMAGEWIDTH)) {
            int width = getIntent().getIntExtra(EXTRA_INT_IMAGEWIDTH, -1);
            int height = getIntent().getIntExtra(EXTRA_INT_IMAGEHEIGHT, -1);
            int h = (int) (1.0f * getResources().getDisplayMetrics().widthPixels / width * height);
            if (h > maxHeight) {
                h = maxHeight;
            }
            ViewGroup.LayoutParams layoutParams = meizhiImageView.getLayoutParams();
            layoutParams.height = h;
            meizhiImageView.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = meizhiImageView.getLayoutParams();
            layoutParams.height = maxHeight;
            meizhiImageView.setLayoutParams(layoutParams);
        }

        ViewCompat.setTransitionName(meizhiImageView, TRANSIT_PIC);
        Glide.with(this).load(meizhi.url).dontAnimate().listener(new ImageViewAutoHeightListener(meizhiImageView, maxHeight)).into(meizhiImageView);
        collapsingToolbarLayout.setTitle(meizhi.desc);


        indicator.setSplitAuto(false);
        indicator.setScrollBar(new ColorBar(getApplicationContext(), getResources().getColor(R.color.primary), DisplayUtils.dipToPix(
                getApplicationContext(), 2)));
        OnTransitionTextListener onTransitionTextListener = new OnTransitionTextListener();
        onTransitionTextListener.setColorId(getApplicationContext(), R.color.primary, R.color.text_secondary);
        indicator.setOnTransitionListener(onTransitionTextListener);
        viewPager.setOffscreenPageLimit(3);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(detailPagerAdapter = new DetailPagerAdapter());


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadView loadView2 = new LoadView(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dipToPix(getApplicationContext(), 300)));
        mvcHelper = new MVCNormalHelper<ArrayListMap<String, List<Gank>>>(contentLayout, loadView2);
        //2016-07-08T11:58:56.336Z
        String year = meizhi.publishedAt.substring(0, 4);
        String month = meizhi.publishedAt.substring(5, 7);
        String day = meizhi.publishedAt.substring(8, 10);
        mvcHelper.setDataSource(new GankRxDataSource(year, month, day));
//        mvcHelper.setDataSource(new GankDataSource(year, month, day));
        mvcHelper.setAdapter(detailPagerAdapter);
        mvcHelper.refresh();

        ViewPropertyAnimator.animate(mainContent).alpha(1).setDuration(1000);
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
//                http://gank.io/2016/07/12
                String year = meizhi.publishedAt.substring(0, 4);
                String month = meizhi.publishedAt.substring(5, 7);
                String day = meizhi.publishedAt.substring(8, 10);
                String url = new UrlBuilder("http://gank.io").sp(year).sp(month).sp(day).build();
                ShareCompat.IntentBuilder.from(this).setText(url).setType("text/plain").startChooser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvcHelper.destory();
    }
}
