package com.victor.che.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.util.ViewUtil;
/**
 * 自定义确认对话框
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/10 0010 19:51
 */
public class AlertDialogFragment extends android.support.v4.app.DialogFragment {

    String mTitle;//标题
    String mMessage;//信息
    String mNegativeButtonText;
    String mPositiveButtonText;

    static View.OnClickListener mOnPositiveClickListener;
    static View.OnClickListener mOnNegativeClickListener;

    private TextView tv_title;
    private TextView tv_msg;
    private Button btn_positive;
    private Button btn_negative;

    /**
     * 创建一个AlertDialog实例
     *
     * @param title                   标题
     * @param message                 内容
     * @param mNegativeButtonText     negatvie按钮文本
     * @param mPositiveButtonText     positive按钮文本
     * @param onNegativeClickListener negative按钮点击事件
     * @param onPositiveClickListener positive按钮点击事件
     * @return AlertDialogFragment的实例
     */
    public static AlertDialogFragment newInstance(String title,
                                                  String message,
                                                  String mNegativeButtonText,
                                                  String mPositiveButtonText,
                                                  View.OnClickListener onNegativeClickListener,
                                                  View.OnClickListener onPositiveClickListener) {
        AlertDialogFragment f = new AlertDialogFragment();

        mOnNegativeClickListener = onNegativeClickListener;
        mOnPositiveClickListener = onPositiveClickListener;

        Bundle args = new Bundle();
        args.putString("mTitle", TextUtils.isEmpty(title) ? "" : title);
        args.putString("mMessage", TextUtils.isEmpty(message) ? "" : message);
        args.putString("mNegativeButtonText", TextUtils.isEmpty(mNegativeButtonText) ? "取消" : mNegativeButtonText);
        args.putString("mPositiveButtonText", TextUtils.isEmpty(mPositiveButtonText) ? "确定" : mPositiveButtonText);
        f.setArguments(args);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置对话框内部的背景设为透明
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置dialog的宽度
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewUtil.dip2px(getActivity(), 300), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mTitle = getArguments().getString("mTitle");
        mMessage = getArguments().getString("mMessage");
        mNegativeButtonText = getArguments().getString("mNegativeButtonText");
        mPositiveButtonText = getArguments().getString("mPositiveButtonText");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        View mContentView = View.inflate(getActivity(), R.layout.alert_dialog_fragment, null);
        builder.setView(mContentView);

        tv_title = (TextView) mContentView.findViewById(R.id.tv_title);
        tv_msg = (TextView) mContentView.findViewById(R.id.tv_msg);
        btn_negative = (Button) mContentView.findViewById(R.id.btn_negative);
        btn_positive = (Button) mContentView.findViewById(R.id.btn_positive);
        // 设置标题
        tv_title.setText(mTitle);

        // 设置内容
        tv_msg.setText(Html.fromHtml(mMessage));

        // neative button text
        btn_negative.setText(mNegativeButtonText);

        // positive button text
        btn_positive.setText(mPositiveButtonText);

        final AlertDialog dialog = builder.create();

        btn_negative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();//关闭对话框
                if (mOnNegativeClickListener != null) {
                    mOnNegativeClickListener.onClick(btn_negative);
                }
            }
        });

        btn_positive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();//关闭对话框
                if (mOnPositiveClickListener != null) {
                    mOnPositiveClickListener.onClick(btn_positive);
                }
            }
        });

        return dialog;
    }

    /**
     * 释放内存
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mOnNegativeClickListener!=null)
            mOnNegativeClickListener=null;
        if(mOnPositiveClickListener!=null)
            mOnPositiveClickListener=null;
    }
}
