package com.shizhefei.meizhi.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

/**
 * 界面帮助类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年10月10日 下午3:33:36
 */
public class UIHelper {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(View mEt) {
        ((InputMethodManager) mEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEt.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyboard(View mEt) {
        mEt.requestFocus();
        ((InputMethodManager) mEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(mEt, InputMethodManager.SHOW_FORCED);
    }

    public static void destroyWebView(WebView webView) {
        ViewParent parent = webView.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(webView);
            webView.destroy();
        }
    }
}
