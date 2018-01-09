package com.victor.che.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;


import com.victor.che.app.AppConfig;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.finalteam.rxgalleryfinal.utils.FilenameUtils;

/**
 * Created by jia on 2015/12/30.
 */
public class BitmapUtil {


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


    /**
     * 获取小图片
     *
     * @param filePath
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int screenWidth, int screenHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, screenWidth, screenWidth);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getSmallBitmap(String filePath, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = inSampleSize;
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Context context, Bitmap bm, String fileName) {
        if (bm == null) {
            ToastUtils.show(context, "保存出错了...");
            return null;
        }
        File dirFile = new File(AppConfig.ROOT_FILE, "/SaveImage");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File myCaptureFile = new File(dirFile, fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            ToastUtils.show(context, "保存出错了...");
            e.printStackTrace();
        } catch (IOException e) {
            ToastUtils.show(context, "保存出错了...");
            e.printStackTrace();
        } catch (Exception e) {
            ToastUtils.show(context, "保存出错了...");
            e.printStackTrace();
        }
        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), myCaptureFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);

        return myCaptureFile.getAbsolutePath();
    }

    /**
     * 图片压缩并且存储
     *
     * @param targetFile
     *         保存目标文件
     * @param bitmap
     *         图片地址
     * @param scale
     *         图片缩放值
     * @return
     */
    public static void compressAndSaveBitmap(File targetFile, Bitmap bitmap, int scale) {


        FileOutputStream fileOutputStream = null;

        try {
            //1、得到图片的宽、高
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;


            int originalImageWidth = options.outWidth;
            int originalImageHeight = options.outHeight;


            //3、计算图片压缩inSampleSize
            int maxValue = Math.max(originalImageWidth, originalImageHeight);
            if (maxValue > 3000) {
                options.inSampleSize = scale * 5;
            } else if (maxValue > 2000 && maxValue <= 3000) {
                options.inSampleSize = scale * 4;
            } else if (maxValue > 1500 && maxValue <= 2000) {
                options.inSampleSize = (int) (scale * 2.5);
            } else if (maxValue > 1000 && maxValue <= 1500) {
                options.inSampleSize = (int) (scale * 1.3);
                //            } else if (maxValue > 400 && maxValue <= 1000) {
                //                options.inSampleSize = scale * 2;
            } else {
                options.inSampleSize = scale;
            }
            options.inJustDecodeBounds = false;

            targetFile.getParentFile().mkdirs();
            //bitmap = compressImage(bitmap);
            fileOutputStream = new FileOutputStream(targetFile);

            //5、保存图片
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            IOUtils.flush(fileOutputStream);
            IOUtils.close(fileOutputStream);
            if (bitmap != null && bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }


    /**
     * 图片压缩并且存储
     *
     * @param targetFile
     *         保存目标文件
     * @param originalPath
     *         图片地址
     * @param scale
     *         图片缩放值
     * @return
     */
    public static void compressAndSaveImage(File targetFile, String originalPath, int scale) {
        Bitmap bitmap = null;
        FileOutputStream fileOutputStream = null;
        try {
            //1、得到图片的宽、高
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(originalPath,options);
            if (bitmap != null) {
                bitmap.recycle();
                System.gc();
            }

            int originalImageWidth = options.outWidth;
            int originalImageHeight = options.outHeight;

            //2、获取图片方向
            int orientation = getImageOrientation(originalPath);
            int rotate = 0;
            switch (orientation) {//判断是否需要旋转
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = -90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            int sampleSize = 1;

            //3、计算图片压缩inSampleSize
            int maxValue = originalImageWidth;
            if (maxValue < 960) {
                sampleSize = 1;
            } else {
                sampleSize = Math.round((float) maxValue / 960);
            }

            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            //4、图片方向纠正和压缩(生成缩略图)
            bitmap = BitmapFactory.decodeFile(originalPath,options);

            if (bitmap == null) {
                return;
            }
            String extension = FilenameUtils.getExtension(originalPath);
            targetFile.getParentFile().mkdirs();
            fileOutputStream = new FileOutputStream(targetFile);
            if (rotate != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, false);
            }
            //5、保存图片
            if (TextUtils.equals(extension.toLowerCase(), "jpg")
                    || TextUtils.equals(extension.toLowerCase(), "jpeg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
            } else if (TextUtils.equals(extension.toLowerCase(), "webp")
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                bitmap.compress(Bitmap.CompressFormat.WEBP, 75, fileOutputStream);
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.flush(fileOutputStream);
            IOUtils.close(fileOutputStream);
            if (bitmap != null && bitmap.isRecycled()) {
                bitmap.recycle();
            }
            System.gc();
        }
    }


    /**
     * 获取一张图片在手机上的方向值
     *
     * @param uri
     * @return
     * @throws IOException
     */
    public static int getImageOrientation(String uri) {
        if (!new File(uri).exists()) {
            return 0;
        }
        try {
            ExifInterface exif = new ExifInterface(uri);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            return orientation;
        } catch (Exception e) {
        }
        return 0;
    }

    public static File getFiles() {
        File file = new File(AppConfig.ROOT_FILE, "/temp/.nomedia/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return file;
    }


    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }
}
