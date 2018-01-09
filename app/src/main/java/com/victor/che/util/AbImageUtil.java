/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.victor.che.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * © 2012 amsoft.cn 名称：AbImageUtil.java 描述：图片处理类.
 *
 * @author 还如一梦中
 * @version v1.0 @date：2013-01-17 下午11:52:13
 */
public class AbImageUtil {

    private final static String TAG = "AbImageUtil";

    /**
     * 图片处理：裁剪.
     */
    public static final int CUTIMG = 0;

    /**
     * 图片处理：缩放.
     */
    public static final int SCALEIMG = 1;

    /**
     * 图片处理：不处理.
     */
    public static final int ORIGINALIMG = 2;

    /**
     * 图片最大宽度.
     */
    public static final int MAX_WIDTH = 4096 / 2;

    /**
     * 图片最大高度.
     */
    public static final int MAX_HEIGHT = 4096 / 2;

    /**
     * 描述：获取原图.
     *
     * @param file File对象
     * @return Bitmap 图片
     */
    public static Bitmap getBitmap(File file) {
        Bitmap resizeBmp = null;
        try {
            resizeBmp = BitmapFactory.decodeFile(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizeBmp;
    }

    /**
     * 从互联网上获取指定大小的图片.
     *
     * @param url           要下载文件的网络地址
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getBitmap(String url, int desiredWidth, int desiredHeight) {
        Bitmap bitmap = null;
        URLConnection con = null;
        InputStream is = null;
        try {
            URL imageURL = new URL(url);
            con = imageURL.openConnection();
            con.setDoInput(true);
            con.connect();
            is = con.getInputStream();
            bitmap = getBitmap(is, desiredWidth, desiredHeight);
            // 超出的裁掉
            if (bitmap.getWidth() > desiredWidth || bitmap.getHeight() > desiredHeight) {
                bitmap = getCutBitmap(bitmap, desiredWidth, desiredHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 从流中获取指定大小的图片.
     *
     * @param inputStream
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    public static Bitmap getBitmap(InputStream inputStream, int desiredWidth, int desiredHeight) {
        Bitmap bitmap = null;
        try {
            byte[] data = AbStreamUtil.stream2bytes(inputStream);
            bitmap = getBitmap(data, desiredWidth, desiredHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 从流中获取指定大小的图片.
     *
     * @param data
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    public static Bitmap getBitmap(byte[] data, int desiredWidth, int desiredHeight) {
        Bitmap resizeBmp = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, opts);

            // 获取图片的原始宽度高度
            int srcWidth = opts.outWidth;
            int srcHeight = opts.outHeight;
            int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
            desiredWidth = size[0];
            desiredHeight = size[1];

            // 默认为ARGB_8888.
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            // 以下两个字段需一起使用：
            // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
            opts.inPurgeable = true;
            // 位图可以共享一个参考输入数据(inputstream、阵列等)
            opts.inInputShareable = true;
            // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
            int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
            opts.inSampleSize = sampleSize;

            // 创建内存
            opts.inJustDecodeBounds = false;
            // 使图片不抖动
            opts.inDither = false;
            resizeBmp = BitmapFactory.decodeByteArray(data, 0, data.length, opts);

            if (resizeBmp != null) {
                resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizeBmp;
    }

    /**
     * 描述：缩放图片.
     *
     * @param file          File对象
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getScaleBitmap(File file, int desiredWidth, int desiredHeight) {

        Bitmap resizeBmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);

        // 获取图片的原始宽度高度
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;

        // 需要的尺寸重置
        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];

        // 默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        // 以下两个字段需一起使用：
        // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        // 位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        opts.inSampleSize = sampleSize;
        // 创建内存
        opts.inJustDecodeBounds = false;
        // 使图片不抖动
        opts.inDither = false;

        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

        // 缩放的比例
        float scale = getMinScale(resizeBmp.getWidth(), resizeBmp.getHeight(), desiredWidth, desiredHeight);
        if (scale < 1) {
            // 缩小
            resizeBmp = scaleBitmap(resizeBmp, scale);
        }

        // 超出的裁掉
        if (resizeBmp.getWidth() > desiredWidth || resizeBmp.getHeight() > desiredHeight) {
            resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
        }
        return resizeBmp;
    }


    /**
     * 描述：裁剪图片.
     *
     * @param file          File对象
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getCutBitmap(File file, int desiredWidth, int desiredHeight) {

        Bitmap resizeBmp = null;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);

        // 获取图片的原始宽度
        int srcWidth = opts.outWidth;
        // 获取图片原始高度
        int srcHeight = opts.outHeight;

        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];

        // 默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        // 以下两个字段需一起使用：
        // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        // 位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        opts.inSampleSize = sampleSize;
        // 创建内存
        opts.inJustDecodeBounds = false;
        // 使图片不抖动
        opts.inDither = false;
        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

        if (resizeBmp != null) {
            resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
        }
        return resizeBmp;
    }

    /**
     * 描述：裁剪图片.
     *
     * @param bitmap        the bitmap
     * @param desiredWidth  新图片的宽
     * @param desiredHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getCutBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {

        if (!checkBitmap(bitmap)) {
            return null;
        }

        if (!checkSize(desiredWidth, desiredHeight)) {
            return null;
        }

        Bitmap resizeBmp = null;

        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int offsetX = 0;
            int offsetY = 0;

            if (width > desiredWidth) {
                offsetX = (width - desiredWidth) / 2;
            } else {
                desiredWidth = width;
            }

            if (height > desiredHeight) {
                offsetY = (height - desiredHeight) / 2;
            } else {
                desiredHeight = height;
            }

            resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, desiredWidth, desiredHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }

    /**
     * 描述：根据等比例缩放图片.
     *
     * @param bitmap the bitmap
     * @param scale  比例
     * @return Bitmap 新图片
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {

        if (!checkBitmap(bitmap)) {
            return null;
        }

        if (scale == 1) {
            return bitmap;
        }

        Bitmap resizeBmp = null;
        try {
            // 获取Bitmap资源的宽和高
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();

            // 注意这个Matirx是android.graphics底下的那个
            Matrix matrix = new Matrix();
            // 设置缩放系数，分别为原来的0.8和0.8
            matrix.postScale(scale, scale);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }

    /**
     * 获取缩小的比例.
     *
     * @param srcWidth
     * @param srcHeight
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    private static float getMinScale(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
        // 缩放的比例
        float scale = 0;
        // 计算缩放比例，宽高的最小比例
        float scaleWidth = (float) desiredWidth / srcWidth;
        float scaleHeight = (float) desiredHeight / srcHeight;
        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }

        return scale;
    }

    private static int[] resizeToMaxSize(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
        int[] size = new int[2];
        if (desiredWidth <= 0) {
            desiredWidth = srcWidth;
        }
        if (desiredHeight <= 0) {
            desiredHeight = srcHeight;
        }
        if (desiredWidth > MAX_WIDTH) {
            // 重新计算大小
            desiredWidth = MAX_WIDTH;
            float scaleWidth = (float) desiredWidth / srcWidth;
            desiredHeight = (int) (desiredHeight * scaleWidth);
        }

        if (desiredHeight > MAX_HEIGHT) {
            // 重新计算大小
            desiredHeight = MAX_HEIGHT;
            float scaleHeight = (float) desiredHeight / srcHeight;
            desiredWidth = (int) (desiredWidth * scaleHeight);
        }
        size[0] = desiredWidth;
        size[1] = desiredHeight;
        return size;
    }

    private static boolean checkBitmap(Bitmap bitmap) {
        if (bitmap == null) {//原图Bitmap为空了
            return false;
        }

        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {//原图Bitmap大小为0
            return false;
        }
        return true;
    }

    private static boolean checkSize(int desiredWidth, int desiredHeight) {
        if (desiredWidth <= 0 || desiredHeight <= 0) {//请求Bitmap的宽高参数必须大于0
            return false;
        }
        return true;
    }


    /**
     * 找到最合适的SampleSize
     *
     * @param width
     * @param height
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    private static int findBestSampleSize(int width, int height, int desiredWidth, int desiredHeight) {
        double wr = (double) width / desiredWidth;
        double hr = (double) height / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }

}
