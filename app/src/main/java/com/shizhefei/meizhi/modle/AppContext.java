package com.shizhefei.meizhi.modle;

import android.app.Application;

import com.shizhefei.meizhi.view.LoadViewFactory;
import com.shizhefei.mvc.MVCHelper;

/**
 * Created by LuckyJayce on 2016/7/12.
 */
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MVCHelper.setLoadViewFractory(new LoadViewFactory());
    }
}
