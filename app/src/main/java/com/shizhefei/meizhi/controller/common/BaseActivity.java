package com.shizhefei.meizhi.controller.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.shizhefei.meizhi.R;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class BaseActivity extends AppCompatActivity {
    private FrameLayout group;
    private View statusBarView;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            supportRequestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        }
        group = new FrameLayout(getApplicationContext());
        group.addView(statusBarView = createStatusBar());
        setTranslucentStatus(true);
//        group.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                UIHelper.hideSoftKeyboard(group);
//            }
//        });
        super.setContentView(group, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public Context getContext() {
        return this;
    }

    private View createStatusBar() {
        int height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            height = getStatusBarHeight(this);
        }
        View statusBarView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        statusBarView.setBackgroundResource(R.color.primary);
        statusBarView.setLayoutParams(lp);
        return statusBarView;
    }

    public void setStatusBarVisiable(boolean isVisiable) {
        if (isVisiable) {
            statusBarView.setVisibility(View.VISIBLE);
        } else {
            statusBarView.setVisibility(View.GONE);
        }
        if (content != null) {
            content.setFitsSystemWindows(isVisiable);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTranslucentStatus(boolean isTranslucentStatus) {
        if (isTranslucentStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                localLayoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            }
            statusBarView.setVisibility(View.VISIBLE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags |= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                localLayoutParams.flags |= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            }
            statusBarView.setVisibility(View.GONE);
        }
    }

    public void setStatusBarBg(int resid) {
        statusBarView.setBackgroundResource(resid);
    }

    public void setStatusBarColorBg(int color) {
        statusBarView.setBackgroundColor(color);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, group, false);
        content = view;
        group.addView(view, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (statusBarView.getVisibility() == View.VISIBLE) {
                view.setFitsSystemWindows(true);
            } else {
                view.setFitsSystemWindows(false);
            }
        }
    }

    @Override
    public void setContentView(View view) {
        content = view;
        group.addView(view, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (statusBarView.getVisibility() == View.VISIBLE) {
                view.setFitsSystemWindows(true);
            } else {
                view.setFitsSystemWindows(false);
            }
        }
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        content = view;
        group.addView(view, 0, params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (statusBarView.getVisibility() == View.VISIBLE) {
                view.setFitsSystemWindows(true);
            } else {
                view.setFitsSystemWindows(false);
            }
        }
    }

    private View content;

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
