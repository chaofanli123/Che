package com.victor.che.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明: 供子类继承的BaseAdapter（数据）
 *
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015年12月9日 上午9:52:39
 */
public abstract class VictorBaseListAdapter<T> extends SimpleBaseAdapter<T> {
	protected Context context;
	protected int layoutResId;
	protected List<T> mData = new ArrayList<T>();

	@Override
	public void setData(List<T> mData) {
		this.mData = mData;
	}

	public VictorBaseListAdapter(Context context, int layoutResId) {
		this.context = context;
		this.layoutResId = layoutResId;
	}

	public VictorBaseListAdapter(Context context, int layoutResId, List<T> mData) {
		this(context, layoutResId);
		this.mData = mData;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, layoutResId, null);
			AutoUtils.autoSize(convertView);
		}

		bindView(position, convertView, mData.get(position));

		return convertView;
	}

	/**
	 * 绑定视图
	 *
	 * @param position
	 * @param view
	 *            视图对象
	 * @param entity
	 *            实体类
	 */
	public abstract void bindView(int position, View view, T entity);
}
