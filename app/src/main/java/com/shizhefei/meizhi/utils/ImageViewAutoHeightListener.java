package com.shizhefei.meizhi.utils;

import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by LuckyJayce on 2016/7/13.
 */
public class ImageViewAutoHeightListener implements RequestListener<String, GlideDrawable> {
    private final int maxHeight;
    private View imageView;

    public ImageViewAutoHeightListener(View imageView) {
        this.imageView = imageView;
        this.maxHeight = -1;
    }

    public ImageViewAutoHeightListener(View imageView, int maxHeight) {
        this.imageView = imageView;
        this.maxHeight = maxHeight;
    }

    private void show(GlideDrawable loadedImage) {
        if (loadedImage == null) {
            return;
        }
        int width = imageView.getMeasuredWidth();
        int w = loadedImage.getIntrinsicWidth();
        int h = loadedImage.getIntrinsicHeight();
        int height = (int) (1.0 * h * width / w);
        if (maxHeight != -1 && height > maxHeight) {
            height = maxHeight;
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = height;
        imageView.setLayoutParams(params);
    }

    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(final GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        if (imageView.getMeasuredWidth() == 0) {
            imageView.post(new Runnable() {

                @Override
                public void run() {
                    show(resource);
                }
            });
        } else {
            show(resource);
        }
        return false;
    }
}
