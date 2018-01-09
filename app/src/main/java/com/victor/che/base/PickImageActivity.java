package com.victor.che.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.ui.CropImageActivity;
import com.victor.che.util.AbFileUtil;
import com.victor.che.util.CollectionUtil;
import com.victor.che.widget.ChooseImageDialogFragment;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 类说明: 需要从手机的相册或相机中获取相片并裁剪的界面
 *
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015年12月13日 下午4:10:02
 */
public abstract class PickImageActivity extends BaseActivity {
    /**************
     * 裁剪图片所需字段
     *****************/
    protected static final int REQUEST_PICK_FROM_CAMERA = 6708;// 从照相机获取图片
    protected static final int REQUEST_PICK_FROM_ALBUM = 9162;// 从相册获取图片
    protected static final int REQUEST_CROP_IMAGE = 6709;// 裁剪图片
    protected String cameraFilePath;// 拍照后的照片保存全路径
    private String croppedImagePath;// 裁剪后的照片路径
    private static final int CODE_FOR_WRITE_PERMISSION = 0;

    private int mCropWidth = -1;// 裁剪框的宽度
    private int mCropHeight = -1;// 裁剪框的高度
    private boolean mShowTip = false;//是否显示tip提示

    /**************
     * 裁剪图片所需字段
     *****************/

    private ArrayList<String> mSelectPath;

    /**
     * 从相册选择图片
     */
    public void _pickFromAlbum() {
        // 存储权限检查
        new TedPermission(mActivity)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)//存储权限
                .setDeniedMessage(getString(R.string.rationale_storage))
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
                        // 是否显示拍摄图片
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                        // 最大可选择图片数量
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                        // 选择模式
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                        // 默认选择
                        if (mSelectPath != null && mSelectPath.size() > 0) {
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                        }
                        startActivityForResult(intent, REQUEST_PICK_FROM_ALBUM);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    }

                }).check();
    }

    /**
     * 从相册选择图片
     */
    private void _pickFromCamera() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            MyApplication.showToast("没有可用的存储卡");
            return;
        }

        // 相机权限检查
        new TedPermission(mActivity)
                .setPermissions(Manifest.permission.CAMERA)//相机权限
                .setDeniedMessage(getString(R.string.rationale_camera))
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File localFile = new File(AbFileUtil.getFileDownloadDir(mContext),
                                "/tmp_capture_" + System.currentTimeMillis() + ".jpg");
                        cameraFilePath = localFile.getAbsolutePath();
                        try {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localFile));
                            startActivityForResult(intent, REQUEST_PICK_FROM_CAMERA);
                        } catch (Exception e) {
                            e.printStackTrace();
                            MyApplication.showToast("未找到系统相机程序");
                        }
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    }

                }).check();
    }

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PICK_FROM_ALBUM://从相册获取图片
                if (resultCode == RESULT_OK) {
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (CollectionUtil.getSize(mSelectPath) == 1) {
                        String currentFilePath = mSelectPath.get(0);
                        if (!TextUtils.isEmpty(currentFilePath)) {
                            _gotoCropImage(currentFilePath);
                        } else {
                            MyApplication.showToast("未在存储卡中找到这个文件");
                        }
                    }
                }
                break;
            case REQUEST_PICK_FROM_CAMERA:
                // 从照相机获取图片
                _gotoCropImage(cameraFilePath);
                break;
            case REQUEST_CROP_IMAGE:
                // 图片裁剪后
                croppedImagePath = data.getStringExtra("PATH");

                if (listener != null) {//回调
                    listener.onCropImageComplete(croppedImagePath);
                }

                break;
        }
    }

    /**
     * 去裁剪界面
     *
     * @param imagePath
     */
    private void _gotoCropImage(String imagePath) {
        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtra("PATH", imagePath);
        if (mCropWidth > 0) {
            intent.putExtra("mCropWidth", mCropWidth);
        }
        if (mCropHeight > 0) {
            intent.putExtra("mCropHeight", mCropHeight);
        }
        intent.putExtra("mShowTip", mShowTip);
        startActivityForResult(intent, REQUEST_CROP_IMAGE);
    }

    private OnCropImageCompleteListener listener;

    /**
     * 显示选择头像的对话框
     *
     * @param listener 裁剪完成的回调
     */
    protected void showChooseAvatarDialog(OnCropImageCompleteListener listener) {
        showChooseAvatarDialog(0, 0, listener);
    }

    /**
     * 显示选择头像的对话框
     */
    protected void showChooseAvatarDialog(int mCropWidth, int mCropHeight, OnCropImageCompleteListener listener) {
        this.mCropWidth = mCropWidth;
        this.mCropHeight = mCropHeight;

        this.listener = listener;
        ChooseImageDialogFragment.newInstance(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                _pickFromCamera();
            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                _pickFromAlbum();
            }
        }).show(getFragmentManager(), getClass().getSimpleName());
    }


    /**
     * 设置裁剪框的宽和高度
     *
     * @param mCropWidth
     * @param mCropHeight
     */
    protected void setCropRectWidthAndHeight(int mCropWidth, int mCropHeight) {
        this.mCropWidth = mCropWidth;
        this.mCropHeight = mCropHeight;
    }

    /**
     * 设置裁剪框的宽和高度
     *
     * @param mCropWidth
     * @param mCropHeight
     * @param showTip
     */
    protected void setCropRectWidthAndHeight(int mCropWidth, int mCropHeight, boolean showTip) {
        this.mCropWidth = mCropWidth;
        this.mCropHeight = mCropHeight;
        this.mShowTip = showTip;
    }

    /**
     * 图片裁剪完成后的回调
     */
    public static interface OnCropImageCompleteListener {
        /**
         * 图片裁剪后
         *
         * @param croppedImagePath 裁剪后的图片路径
         */
        void onCropImageComplete(String croppedImagePath);
    }
}
