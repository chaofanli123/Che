package com.victor.che.widget;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.che.R;


/**
 * 进度对话框
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/12 0012 20:21
 */
public class LoadDialogFragment extends DialogFragment {

    public static LoadDialogFragment newInstance(String mMsg) {
        LoadDialogFragment f = new LoadDialogFragment();

        Bundle args = new Bundle();
        args.putString("mMsg", TextUtils.isEmpty(mMsg) ? "加载中..." : mMsg);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置对话框内部的背景设为透明
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        String mMsg = getArguments().getString("mMsg");

        View mContentView = inflater.inflate(R.layout.load_dialog_fragment, null);// 得到加载view

        ImageView imageView = (ImageView) mContentView.findViewById(R.id.imageView);
        // 加载动画
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        imageView.startAnimation(animation);

        // 提示信息
        TextView tv_msg = (TextView) mContentView.findViewById(R.id.tv_msg);
        tv_msg.setText(mMsg);

        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 对话框外部的背景设为透明
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        window.setDimAmount(0.0f);
        window.setAttributes(windowParams);
    }
}
