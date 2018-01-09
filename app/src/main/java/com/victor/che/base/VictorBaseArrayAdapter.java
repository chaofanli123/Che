package com.victor.che.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类说明: 供子类继承的BaseAdapter
 *
 * @author 作者 LUYA, E-mail: 468034043@qq.com
 * @version 创建时间：2015年12月9日 上午9:52:39
 */
public abstract class VictorBaseArrayAdapter<T> extends SimpleBaseAdapter<T> {
	protected Context context;
	protected int layoutResId;
	protected T[] array;

	public VictorBaseArrayAdapter(Context context, int layoutResId) {
		this.context = context;
		this.layoutResId = layoutResId;
	}

	public VictorBaseArrayAdapter(Context context, int layoutResId, T[] array) {
		this(context, layoutResId);
		this.array = array;
	}

	@Override
	public int getCount() {
		return array == null ? 0 : array.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, layoutResId, null);
		}

		bindView(position, convertView, array[position]);

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
