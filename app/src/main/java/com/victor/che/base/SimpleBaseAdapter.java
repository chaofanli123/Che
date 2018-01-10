package com.victor.che.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月24日 下午2:53:53
 */
public class SimpleBaseAdapter<T> extends BaseAdapter {

    public void setData(List<T> mData) {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
