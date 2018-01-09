package com.victor.che.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.util.AbViewHolder;


/**
 * 选择车牌号归属地的弹窗
 * 
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年3月24日 上午10:45:22
 */
public class ChoosePlatNumAddrPopwindow extends PopupWindow {
	public static final String[] PROVINCES = { "京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川", "渝", "辽", "吉", "黑",
			"皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁", "蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏", "港", "澳", "学", "警",
			"领" };
	private View ppw_choose_plat_num_addr;
	private GridView keyboardGridView;

	public ChoosePlatNumAddrPopwindow(final Activity activity, final String currentCity, OnItemClickListener listener) {
		super(activity);
		ppw_choose_plat_num_addr = LayoutInflater.from(activity).inflate(R.layout.ppw_choose_plat_num_addr, null);

		this.keyboardGridView = ((GridView) ppw_choose_plat_num_addr.findViewById(R.id.keyboardGridView));
		keyboardGridView
				.setAdapter(new VictorBaseArrayAdapter<String>(activity, R.layout.item_keyboard_key, PROVINCES) {

					@Override
					public void bindView(int position, View view, String entity) {
						TextView tv_key_name = AbViewHolder.get(view, R.id.tv_key_name);
						tv_key_name.setText(entity);

						if (PROVINCES[position].equalsIgnoreCase(currentCity)) {
							tv_key_name.setBackgroundResource(R.drawable.shp_plat_num_addr_keyboard_key_checked);
							tv_key_name.setTextColor(activity.getResources().getColor(R.color.keyboard_key_selected));
						} else {
							tv_key_name.setBackgroundResource(R.drawable.shp_plat_num_addr_keyboard_key);
							tv_key_name.setTextColor(activity.getResources().getColor(R.color.white));
						}
					}
				});
		keyboardGridView.setOnItemClickListener(listener);

		ppw_choose_plat_num_addr.findViewById(R.id.divider).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭ppw
				dismiss();
			}
		});

		setContentView(ppw_choose_plat_num_addr);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 1.0F, 1, 0.0F).setDuration(500L);
		new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 1.0F).setDuration(500L);
		setBackgroundDrawable(new ColorDrawable(-1342177280));
	}

	public ChoosePlatNumAddrPopwindow(final Context activity, final String currentCity, OnItemClickListener listener) {
		super(activity);
		ppw_choose_plat_num_addr = LayoutInflater.from(activity).inflate(R.layout.ppw_choose_plat_num_addr, null);

		this.keyboardGridView = ((GridView) ppw_choose_plat_num_addr.findViewById(R.id.keyboardGridView));
		keyboardGridView
				.setAdapter(new VictorBaseArrayAdapter<String>(activity, R.layout.item_keyboard_key, PROVINCES) {

					@Override
					public void bindView(int position, View view, String entity) {
						TextView tv_key_name = AbViewHolder.get(view, R.id.tv_key_name);
						tv_key_name.setText(entity);

						if (PROVINCES[position].equalsIgnoreCase(currentCity)) {
							tv_key_name.setBackgroundResource(R.drawable.shp_plat_num_addr_keyboard_key_checked);
							tv_key_name.setTextColor(activity.getResources().getColor(R.color.keyboard_key_selected));
						} else {
							tv_key_name.setBackgroundResource(R.drawable.shp_plat_num_addr_keyboard_key);
							tv_key_name.setTextColor(activity.getResources().getColor(R.color.white));
						}
					}
				});
		keyboardGridView.setOnItemClickListener(listener);

		ppw_choose_plat_num_addr.findViewById(R.id.divider).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭ppw
				dismiss();
			}
		});

		setContentView(ppw_choose_plat_num_addr);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 1.0F, 1, 0.0F).setDuration(500L);
		new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 1.0F).setDuration(500L);
		setBackgroundDrawable(new ColorDrawable(-1342177280));
	}
}
