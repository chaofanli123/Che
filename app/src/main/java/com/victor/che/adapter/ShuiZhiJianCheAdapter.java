package com.victor.che.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.bean.ShuiZhiJianCheJiLu;

import java.util.ArrayList;

/**
 * Created by fighting on 2017/5/17.
 */

public class ShuiZhiJianCheAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final ArrayList<ArrayList<ShuiZhiJianCheJiLu.FirmListBean>> mDataList;

    public ShuiZhiJianCheAdapter(Context context, ArrayList<ArrayList<ShuiZhiJianCheJiLu.FirmListBean>> dataList){
        mContext = context;
        mDataList = dataList;

    }
    @Override
    public int getGroupCount() {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.view_group, null);
            textView = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(textView);
        }else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(mDataList.get(groupPosition).get(0).firstLetter);
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.view_child, null);
            textView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(textView);
        }else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(mDataList.get(groupPosition).get(childPosition).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
