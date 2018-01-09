package com.victor.che.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.AbFileUtil;
import com.victor.che.util.AbImageUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 裁剪图片的界面
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月23日 下午2:43:48
 */
public class CropImageActivity extends BaseActivity implements CropImageView.OnCropImageCompleteListener {
    private Bitmap mBitmap;
    private String mPath = "CropImageActivity";
    private int mCropWidth;// 裁剪框的宽度
    private int mCropHeight;// 裁剪框的高度

    @BindView(R.id.cropImageView)
    CropImageView mCropImageView;//裁剪框

    @Override
    public int getContentView() {
        return R.layout.activity_crop_image;
    }

    @Override
    protected void initView() {

        // 获取裁剪框的宽和高(默认是正方形裁剪)
        mCropWidth = getIntent().getIntExtra("mCropWidth", 1);
        mCropHeight = getIntent().getIntExtra("mCropHeight", 1);

        boolean mShowTip = getIntent().getBooleanExtra("mShowTip", false);//是否显示tip提示
        findViewById(R.id.tv_tip).setVisibility(mShowTip ? View.VISIBLE : View.GONE);

        mCropImageView.setAspectRatio(mCropWidth, mCropHeight);//设置宽高比，默认是1:1正方形
        mCropImageView.setFixedAspectRatio(true);//固定宽高比
        mCropImageView.setAutoZoomEnabled(true);//允许缩放
        mCropImageView.setShowProgressBar(true);

        mPath = getIntent().getStringExtra("PATH");
        try {
            // 相册中原来的图片
            File mFile = new File(mPath);
            mBitmap = AbFileUtil.getBitmapFromSD(mFile, AbImageUtil.SCALEIMG, 0, 0);// 图片缩放，防止OOM异常
            if (mBitmap == null) {
                MyApplication.showToast("没有找到图片");
                finish();
            } else {
                // 缩放裁剪框，防止跃出图片边界
                mCropImageView.setImageBitmap(mBitmap);
            }
        } catch (Exception e) {
            MyApplication.showToast("没有找到图片");
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCropImageView.setOnCropImageCompleteListener(null);
        if (mBitmap != null) {
            mBitmap = null;
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.btn_rotate_right, R.id.tv_operate})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_operate:// 完成
                Uri outputUri = getOutputUri();
                mCropImageView.saveCroppedImageAsync(outputUri);//异步保存
                break;
            case R.id.btn_rotate_right://向右旋转90度
                mCropImageView.rotateImage(90);
                break;

        }
    }


    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        Uri outputUri = Uri.EMPTY;
        try {
            String saveFilename = String.valueOf(System.currentTimeMillis());
            String ext = ".jpg";
            outputUri = Uri.fromFile(File.createTempFile(saveFilename, ext, getCacheDir()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file for output image", e);
        }
        return outputUri;
    }

    /**
     * 裁剪完成
     *
     * @param view
     * @param result
     */
    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        Intent intent = new Intent();
        intent.putExtra("PATH", result.getUri().getPath());//裁剪的路径
        setResult(RESULT_OK, intent);
        finish();
    }
}
