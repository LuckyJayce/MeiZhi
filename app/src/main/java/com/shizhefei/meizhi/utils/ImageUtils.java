package com.shizhefei.meizhi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageUtils {

    private static DisplayImageOptions userOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    private static DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    private static DisplayImageOptions imageOptions2 = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(300)).build();

    public static void display(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url, imageView, imageOptions);
    }

    public static void display(ImageView imageView, String url, int defualtImageId) {
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(defualtImageId).showImageForEmptyUri(defualtImageId)
                .showImageOnFail(defualtImageId).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoader.getInstance().displayImage(url, imageView, imageOptions);
    }

    public static void displayAutoHeight(final ImageView imageView, String url) {
        displayAutoHeight(imageView, url, null, -1);
    }

    public static void displayAutoHeight(final ImageView imageView, String url, int maxHeight) {
        displayAutoHeight(imageView, url, null, maxHeight);
    }

    public static void displayAutoHeight(final ImageView imageView, String url, final ImageLoadingListener imageLoadingListener, final int maxHeight) {
        display(imageView, url, null, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (imageLoadingListener != null) {
                    imageLoadingListener.onLoadingStarted(imageUri, view);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (imageLoadingListener != null) {
                    imageLoadingListener.onLoadingFailed(imageUri, view, failReason);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                if (imageLoadingListener != null) {
                    imageLoadingListener.onLoadingComplete(imageUri, view, loadedImage);
                }
                if (imageView.getMeasuredWidth() == 0) {
                    imageView.post(new Runnable() {

                        @Override
                        public void run() {
                            show(loadedImage);
                        }
                    });
                } else {
                    show(loadedImage);
                }
            }

            private void show(Bitmap loadedImage) {
                if (loadedImage == null) {
                    return;
                }
                int width = imageView.getMeasuredWidth();
                int w = loadedImage.getWidth();
                int h = loadedImage.getHeight();
                int height = (int) (1.0 * h * width / w);
                if (maxHeight != -1 && height > maxHeight) {
                    height = maxHeight;
                }
                LayoutParams params = imageView.getLayoutParams();
                params.height = height;
                imageView.setLayoutParams(params);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, null);
    }

    public static void displayAnimal(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url, imageView, imageOptions2);
    }

    public static File getImageFilePath(String imageUrl) {
        return ImageLoader.getInstance().getDiskCache().get(imageUrl);
    }

    public static void displayUserIcon(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url, imageView, userOptions);
    }

    public static void cancle(ImageView imageView) {
        ImageLoader.getInstance().cancelDisplayTask(imageView);
    }

    public static void display(ImageView imageView, String url, Drawable defaultDrawable, ImageLoadingListener listener,
                               ImageLoadingProgressListener progressListener) {
        DisplayImageOptions op;
        if (defaultDrawable != null) {
            op = new DisplayImageOptions.Builder().showImageOnLoading(defaultDrawable).showImageForEmptyUri(defaultDrawable)
                    .showImageOnFail(defaultDrawable).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        } else {
            op = imageOptions;
        }
        ImageLoader.getInstance().displayImage(url, imageView, op, listener, progressListener);
    }

    public static ByteArrayOutputStream compressImageToStream(Bitmap image, int maxKBSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxKBSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        return baos;
    }

    public static Bitmap compressImage(Bitmap image, int maxKBSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxKBSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap decodeFromBitmap(Bitmap source, int width, int height) {
        return ThumbnailUtils.extractThumbnail(source, width, height);
    }

    public static ByteArrayOutputStream decodeFromFileToStream(File dst, int width, int height, int maxKBSize) {
        Bitmap bitmap = decodeFromFile(dst, width, height);
        ByteArrayOutputStream outputStream = compressImageToStream(bitmap, maxKBSize);
        bitmap.recycle();
        return outputStream;
    }

    public static Bitmap decodeFromFile(File dst, int width, int height, int maxKBSize) {
        Bitmap bitmap = decodeFromFile(dst, width, height);
        Bitmap newbitmap = compressImage(bitmap, maxKBSize);
        bitmap.recycle();
        return newbitmap;
    }

    public static Bitmap decodeFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options(); // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height); // 这里一定要将其设置回false，因为之前我们将其设置成了true
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
            // roundedSize = initialSize;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static boolean hasCacheImage(String url) {
        File file = ImageLoader.getInstance().getDiskCache().get(url);
        return file != null && file.exists();
    }

    public static void cacheImage(String url) {
        DisplayImageOptions userOptions = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().loadImage(url, userOptions, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
