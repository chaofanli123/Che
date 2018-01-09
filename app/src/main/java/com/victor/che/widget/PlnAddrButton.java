package com.victor.che.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.phillipcalvin.iconbutton.IconButton;


/**
 * 车牌号归属地的按钮
 * 
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年5月26日 下午1:58:33
 */
public class PlnAddrButton extends IconButton implements View.OnClickListener {

	private ChoosePlatNumAddrPopwindow platnumAddrPPW;
	private OnClickListener onKeyClickListener;

	public void setOnKeyClickListener(OnClickListener onKeyClickListener) {
		this.onKeyClickListener = onKeyClickListener;
	}

	public PlnAddrButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PlnAddrButton(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		setOnClickListener(this);
		// 获取焦点，这样才会执行onKeyDown()方法(有bug，必须第二次点击才会显示)
		// setFocus();
	}

	@Override
	public void onClick(View v) {

		String city = getText().toString().trim();
		platnumAddrPPW = new ChoosePlatNumAddrPopwindow(getContext(), city, new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 单击省份
				setText(ChoosePlatNumAddrPopwindow.PROVINCES[position]);
//				AbDialogUtil.dismissPopwindow(platnumAddrPPW);
				if (platnumAddrPPW != null && platnumAddrPPW.isShowing()) {
					platnumAddrPPW.dismiss();
					platnumAddrPPW = null;
				}
				// 回调方法

				if (onKeyClickListener != null) {
					onKeyClickListener.onClick(view);
				}
			}
		});
		platnumAddrPPW.showAtLocation(v, 80, 0, 0);
		platnumAddrPPW.setOutsideTouchable(true);

	}

	/**
	 * 获取焦点
	 */
	public void obtainFocus() {
		requestFocus();
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
//		AbDialogUtil.dismissPopwindow(platnumAddrPPW);
		if (platnumAddrPPW != null && platnumAddrPPW.isShowing()) {
			platnumAddrPPW.dismiss();
			platnumAddrPPW = null;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 回退的时候，如果键盘显示，则隐藏
			if (platnumAddrPPW != null && platnumAddrPPW.isShowing()) {
				platnumAddrPPW.dismiss();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean isPopwindowShowing() {
		return platnumAddrPPW != null && platnumAddrPPW.isShowing();
	}

	public void dismissPopwindow() {
		platnumAddrPPW.dismiss();
	}

}
