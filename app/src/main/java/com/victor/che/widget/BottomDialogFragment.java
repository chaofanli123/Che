package com.victor.che.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.victor.che.R;
import com.victor.che.util.ViewUtil;


/**
 * 底部列表对话框
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/10 0010 22:31
 */
public class BottomDialogFragment extends android.support.v4.app.DialogFragment {

    static BaseAdapter mAdapter;
    static AdapterView.OnItemClickListener mOnItemClickListener;

    public static BottomDialogFragment newInstance(BaseAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
        BottomDialogFragment f = new BottomDialogFragment();

        mAdapter = adapter;
        mOnItemClickListener = onItemClickListener;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 设置对话框内部的背景设为透明
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewUtil.dip2px(getActivity(), 300);//dialog宽度
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View mContentView = View.inflate(getActivity(), R.layout.bottom_dialog_fragment, null);
        builder.setView(mContentView);

        final ListView mListView = (ListView) mContentView.findViewById(R.id.mListView);

        mListView.setAdapter(mAdapter);//设置适配器

        final Dialog dialog = builder.create();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(parent, view, position, id);
                }

                // 500毫秒后关闭对话框
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭对话框
                        dialog.dismiss();
                    }
                }, 200);
            }
        });

        // 取消
        mContentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭对话框
                dialog.dismiss();
            }
        });

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//Here!
        window.setGravity(Gravity.BOTTOM);
    }

    /**
     * 释放内存
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mAdapter!=null){
            mAdapter=null;
        }
        if(mOnItemClickListener!=null)
            mOnItemClickListener=null;
    }
}
