package com.victor.che.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.victor.che.api.Define;

/**
 * 类说明: 加载网络图片专用
 *
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015年12月2日 上午10:12:58
 */
public class ImageLoaderUtil {

    /**
     * 加载网络图片
     *
     * @param context   上下文
     * @param imageView 要展示的ImageView
     * @param url       图片网络地址
     */
    public static void display(Context context, final ImageView imageView, final String url) {
        display(context, imageView, url, 0, 0);
    }

    /**
     * 加载网络图片
     *
     * @param context       上下文
     * @param imageView     要展示的ImageView
     * @param url           图片网络地址
     * @param desiredWidth  -1表示原图片大小 宽度
     * @param desiredHeight -1表示原图片大小 高度
     * @param loadingResId  加载过程中的图片 0表示无图片
     * @param errorResId    加载失败的图片 0表示无图片
     */
    public static void display(Context context, final ImageView imageView, String url, int desiredWidth, int desiredHeight,
                               int loadingResId, int errorResId, boolean original) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(errorResId);
            return;
        }
        String innerurl = (url.startsWith("http://") || url.startsWith("https://")) ? url : Define.API_DOMAIN + url;
        RequestCreator requestCreator = Picasso.with(context).load(innerurl);
        if (desiredWidth > 0 && desiredHeight > 0) {
            requestCreator.resize(desiredWidth, desiredHeight);
        }
        if (loadingResId != 0) {
            requestCreator.placeholder(loadingResId);
        }
        if (errorResId != 0) {
            requestCreator.error(errorResId);
        }
        if (original) {//展示原图
            Transformation transformation = new Transformation() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public Bitmap transform(Bitmap source) {

                    int targetWidth = imageView.getMinimumWidth();

                    if (source.getWidth() == 0) {
                        return source;
                    }

                    //如果图片小于设置的宽度，则返回原图
                    if (source.getWidth() < targetWidth) {
                        return source;
                    } else {
                        //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                        int targetHeight = (int) (targetWidth * aspectRatio);
                        if (targetHeight != 0 && targetWidth != 0) {
                            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                            if (result != source) {
                                // Same bitmap is returned if sizes are the same
                                source.recycle();
                            }
                            return result;
                        } else {
                            return source;
                        }
                    }

                }

                @Override
                public String key() {
                    return "transformation" + " desiredWidth";
                }
            };
            requestCreator.transform(transformation).into(imageView);
        } else {
            requestCreator.fit().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param context      上下文
     * @param imageView    要展示的ImageView
     * @param url          图片网络地址
     * @param loadingResId 加载过程中的图片
     * @param errorResId   加载失败的图片
     */
    public static void display(Context context, final ImageView imageView, final String url, final int loadingResId,
                               final int errorResId) {
        display(context, imageView, url, -1, -1, loadingResId, errorResId, false);
    }

    /**
     * 加载网络图片
     *
     * @param context      上下文
     * @param imageView    要展示的ImageView
     * @param url          图片网络地址
     * @param loadingResId 加载过程中的图片
     * @param errorResId   加载失败的图片
     */
    public static void display(Context context, final ImageView imageView, final String url, final int loadingResId,
                               final int errorResId, boolean original) {
        display(context, imageView, url, -1, -1, loadingResId, errorResId, original);
    }

}
