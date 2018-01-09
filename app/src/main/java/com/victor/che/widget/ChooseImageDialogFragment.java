package com.victor.che.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.victor.che.R;
import com.victor.che.util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 选择上传图片
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/12 0012 20:21
 */
public class ChooseImageDialogFragment extends DialogFragment {

    static View.OnClickListener mOnCameraClickListener;
    static View.OnClickListener mOnAlbumClickListener;

    public static ChooseImageDialogFragment newInstance(View.OnClickListener onCameraClickListener,
                                                        View.OnClickListener onAlbumClickListener) {
        ChooseImageDialogFragment f = new ChooseImageDialogFragment();
        mOnCameraClickListener = onCameraClickListener;
        mOnAlbumClickListener = onAlbumClickListener;
        return f;
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置对话框内部的背景设为透明
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View mContentView = inflater.inflate(R.layout.choose_image_dialog_fragment, null);// 得到加载view
        unbinder = ButterKnife.bind(this, mContentView);

        return mContentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解绑butterknife
        unbinder.unbind();
    }

    @OnClick({R.id.btn_camera, R.id.btn_album, R.id.btn_cancel})
    void doOperate(View v) {
        dismiss();
        if (v.getId() == R.id.btn_camera) {
            if (mOnCameraClickListener != null) {
                mOnCameraClickListener.onClick(v);
            }
        } else if (v.getId() == R.id.btn_album) {
            if (mOnAlbumClickListener != null) {
                mOnAlbumClickListener.onClick(v);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 对话框外部的背景设为透明
        //        Window window = getDialog().getWindow();
        //        WindowManager.LayoutParams windowParams = window.getAttributes();
        //        window.setDimAmount(0.0f);
        //        window.setAttributes(windowParams);
        // 设置dialog的宽度
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewUtil.dip2px(getActivity(), 300), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//Here!
        window.setGravity(Gravity.BOTTOM);
    }


}
