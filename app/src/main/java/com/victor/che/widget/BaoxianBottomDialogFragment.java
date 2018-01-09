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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.adapter.BottomDialogListAdapter;
import com.victor.che.util.ViewUtil;

import org.w3c.dom.Text;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;


/**
 * 底部列表对话框  保险，投保，不投保
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/10 0010 22:31
 */
public class BaoxianBottomDialogFragment extends android.support.v4.app.DialogFragment {

    static BottomDialogListAdapter mAdapter;
    static AdapterView.OnItemClickListener mOnItemClickListener;
    private String name;

    public BaoxianBottomDialogFragment(String name) {
        this.name = name;
    }

//    public static BaoxianBottomDialogFragment newInstance(BaseAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
//        BaoxianBottomDialogFragment f = new BaoxianBottomDialogFragment();
//        mAdapter = adapter;
//        mOnItemClickListener = onItemClickListener;
//        return f;
//    }

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
        final Dialog dialog = builder.create();

        View mContentView = View.inflate(getActivity(), R.layout.activity_baoxian_other, null);
        builder.setView(mContentView);
        TextView textView = (TextView) mContentView.findViewById(R.id.tv_baoxian_name);
        textView.setText(name);
        final MyRecyclerView mListView = (MyRecyclerView) mContentView.findViewById(R.id.mListView);
        mListView.setAdapter(mAdapter);//设置适配器

        mListView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {

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
