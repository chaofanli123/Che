package com.victor.che.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Driving;
import com.victor.che.event.DrivingEvent;
import com.victor.che.widget.ClipViewLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ClipImageActivity";
    @BindView(R.id.topbar_right)
    ImageView topbarRight;
    private ClipViewLayout clipViewLayout1;
    private ClipViewLayout clipViewLayout2;
    private ImageView back;
    private TextView btnCancel;
    private TextView btnOk;
    //类别 1: qq, 2: weixin
    private int type;
    private File file_update;
    private String file_update_path;//需要裁剪的图片路径
    private Bitmap photoBmp = null;//需要裁剪的图片bitmap
    private int degree=0;

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("裁剪");
        type = getIntent().getIntExtra("type", 1);
        Uri data = getIntent().getData();
        try {
            file_update = new File(new URI(data.toString()));
            file_update_path=file_update.getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (data != null) {
            try {
                photoBmp = MediaStore.Images.Media.getBitmap(ClipImageActivity.this.getContentResolver(), data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initViews();
        Glide.with(mContext).load(R.drawable.icon_xuanzhuan).dontAnimate().into(topbarRight);

    }

    /**
     * 初始化组件
     */
    public void initViews() {
        clipViewLayout1 = (ClipViewLayout) findViewById(R.id.clipViewLayout1);
        clipViewLayout2 = (ClipViewLayout) findViewById(R.id.clipViewLayout2);
//        back = (ImageView) findViewById(R.id.iv_back);
        btnCancel = (TextView) findViewById(R.id.btn_cancel);
        btnOk = (TextView) findViewById(R.id.bt_ok);
        //设置点击事件监听器
      //  back.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_clip_image;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "image uri: " + getIntent().getData());
        if (type == 1) {
            clipViewLayout1.setVisibility(View.VISIBLE);
            clipViewLayout2.setVisibility(View.GONE);
            //设置图片资源
            clipViewLayout1.setImageSrc(getIntent().getData());
        } else {
            clipViewLayout2.setVisibility(View.VISIBLE);
            clipViewLayout1.setVisibility(View.GONE);
            //设置图片资源
            clipViewLayout2.setImageSrc(getIntent().getData());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.bt_ok:
                generateUriAndReturn();
                break;
        }
    }


    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        if (type == 1) {
            zoomedCropBitmap = clipViewLayout1.clip();
        } else {
            zoomedCropBitmap = clipViewLayout2.clip();
        }
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//            Intent intent = new Intent();
//            intent.setData(mSaveUri);
//            setResult(RESULT_OK, intent);
//            finish();
            String realFilePathFromUri = getRealFilePathFromUri(this, mSaveUri);

            final File file = new File(realFilePathFromUri);//file_update 原图片
            MyParams params = new MyParams();
            params.put("key", "a932cfbb0bc6bae2c415102e23ba0df4");//您申请的appkey
            params.put("cardType", "6");//证件类型，参考接口1
            params.put("pic", file);//上传图片,大小限制在3M以内(一般建议200k以下)
            VictorHttpUtil.doPost(this, Define.URL_QUERY_PLN, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            if (element.error_code == 0) {
                                Gson gson = new Gson();
                                Driving driving = gson.fromJson(element.result, Driving.class);
                                if (driving == null) {
                                    MyApplication.showToast("行驶证识别失败，请稍后重试");
                                    return;
                                }
                                if (getIntent().getBooleanExtra("mPostBack", false)) {//需要回传
                                    DrivingEvent event = new DrivingEvent(driving, file);
                                    EventBus.getDefault().post(event);
                                    MyApplication.showToast("识别成功");
//                                    Intent intent=new Intent();
//                                    intent.putExtra("DrivingEvent",event);
                                    setResult(333);
                                    finish();
                                }
//                                else {// 直接点击扫一扫，跳转到收款界面
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("mData", pln);
//                                    MyApplication.openActivity(mContext, ReceiptActivity.class, bundle);
//                                }
                            } else {
                                //                            MyApplication.showToast(element.reason);
                                MyApplication.showToast("识别失败！请稍后重试");
                            }
                        }

                        @Override
                        public void onFaliure(String url, int statusCode, String content, Throwable error) {
                            super.onFaliure(url, statusCode, content, error);
                        }

                        @Override
                        public void callbackError(String url, Element obj) {
                            super.callbackError(url, obj);
                        }

                        @Override
                        public void callbackErrorJSONFormat(String url) {
                            super.callbackErrorJSONFormat(url);
                        }

                        @Override
                        public void callbackNoNetwork(String url) {
                            super.callbackNoNetwork(url);
                        }
                    });

        }
    }

    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 旋转照片
     */
    @OnClick(R.id.topbar_right)
    public void onViewClicked() {
//      int degree = readPicDegree(file_update_path);//旋转的角度
//        degree = 0;//旋转的角度
        if (degree<90){
            degree=90;
        }else if (degree<180){
            degree=180;
        }else if (degree<270){
            degree=270;
        }else {
            degree=0;
        }
        //设置图片资源
        clipViewLayout2.initSrcPic(getIntent().getData(),degree);

    }

    /**
     * 通过ExifInterface类读取图片文件的被旋转角度
     * @param path ： 图片文件的路径
     * @return 图片文件的被旋转角度
     */
    public static int readPicDegree(String path) {
        int degree = 0;

        // 读取图片文件信息的类ExifInterface
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }

        return degree;
    }

    /**
     * 将图片纠正到正确方向
     *
     * @param degree ： 图片被系统旋转的角度
     * @param bitmap ： 需纠正方向的图片
     * @return 纠向后的图片
     */
    public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRotate(degree);

        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }



    File saveBitmap(Bitmap bitmap) {
        File file0 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis() + "_compressed.jpg");
        OutputStream os0 = null;
        try {
            os0 = new FileOutputStream(file0);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os0);
            os0.flush();
            os0.close();
            //                            message.sendToTarget();
        } catch (IOException e) {
        } finally {
            if (os0 != null) {
                try {
                    os0.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
        return file0;
    }
}
