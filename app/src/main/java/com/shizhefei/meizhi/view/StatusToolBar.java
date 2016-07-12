package com.shizhefei.meizhi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.shizhefei.meizhi.controller.common.BaseActivity;

/**
 * Created by LuckyJayce on 2016/7/12.
 */
public class StatusToolBar extends Toolbar {

    public StatusToolBar(Context context) {
        super(context);
        init();
    }

    public StatusToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPadding(0, BaseActivity.getStatusBarHeight(getContext()), 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int statusHeight = BaseActivity.getStatusBarHeight(getContext());
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, statusHeight + height);
    }
}
