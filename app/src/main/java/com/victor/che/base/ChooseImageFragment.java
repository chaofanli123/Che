package com.victor.che.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.util.AbDialogUtil;
import com.victor.che.util.AbFileUtil;
import com.victor.che.widget.ChooseImageDialogFragment;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public abstract class ChooseImageFragment extends BaseFragment {

    protected static final int REQUEST_PICK_FROM_CAMERA = 6708;// 从照相机获取图片
    protected static final int REQUEST_PICK_FROM_ALBUM = 9162;// 从相册获取图片
    protected String cameraFilePath;// 拍照后的照片保存全路径
    private ArrayList<String> mSelectPath;

    public void showDialog() {
        ChooseImageDialogFragment.newInstance(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//点击相机
                _pickFromCamera();
            }
        }, new View.OnClickListener() {//点击相册

            @Override
            public void onClick(View v) {

            }
        }).show(mActivity.getFragmentManager(), getClass().getSimpleName());
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
     * 从相册选择图片
     */
    private void _pickFromAlbum() {
        AbDialogUtil.removeDialog(mContext);

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
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
     */
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case REQUEST_PICK_FROM_ALBUM://从相册获取图片
//                if (resultCode == Activity.RESULT_OK) {
//                    mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                    if (CollectionUtil.getSize(mSelectPath) == 1) {
//                        String currentFilePath = mSelectPath.get(0);
//                        if (!TextUtils.isEmpty(currentFilePath)) {
//                            // Intent intent = new Intent(this,
//                            // CropImageActivity.class);
//                            // intent.putExtra("PATH", currentFilePath);
//                            // startActivityForResult(intent, REQUEST_CROP_IMAGE);
////                            gotoCropImageActivity(currentFilePath);
//                            MyApplication.showToast(currentFilePath);
//                        } else {
//                            MyApplication.showToast("未在存储卡中找到这个文件");
//                        }
//                    }
//                }
//                break;
//            case REQUEST_PICK_FROM_CAMERA:
//                // 从照相机获取图片
////                gotoCropImageActivity(cameraFilePath);
//                MyApplication.showToast(cameraFilePath);
//                break;
//                break;
//        }
//
//    }

}
