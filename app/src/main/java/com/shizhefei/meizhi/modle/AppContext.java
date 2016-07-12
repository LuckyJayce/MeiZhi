package com.shizhefei.meizhi.modle;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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
        // 初始化图片加载
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .threadPriority(Thread.NORM_PRIORITY - 2).diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCacheSize(50 * 1024 * 1024) // 50
                    // Mb
                    .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
                    // for
                    // release
                    // app
                    .build();
            ImageLoader.getInstance().init(config);
        }
    }
}
