/**
 *
 */
package com.victor.che.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.victor.che.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * 使用自定义键盘的EditText，有按下效果
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class KeyboardEditText extends EditText implements OnKeyboardActionListener, View.OnFocusChangeListener, TextWatcher {

	/**
	 * 删除按钮的引用
	 */
	private Drawable mClearDrawable;
	/**
	 * 控件是否有焦点
	 */
	private boolean hasFoucs;

	private KeyboardView mKeyboardView;
	private Keyboard mKeyboard;

	private Window mWindow;
	private View mDecorView;
	private View mContentView;

	private PopupWindow mKeyboardWindow;

	private boolean needcustomkeyboard = true; // 是否启用自定义键盘
	private boolean randomkeys = false; // 数字按键是否随机

	private int scrolldis = 0; // 输入框在键盘被弹出时，要被推上去的距离

	public static int screenw = -1;// 未知宽高
	public static int screenh = -1;
	public static int screenh_nonavbar = -1; // 不包含导航栏的高度
	public static int real_scontenth = -1; // 实际内容高度， 计算公式:屏幕高度-导航栏高度-电量栏高度

	public static float density = 1.0f;
	public static int densityDpi = 160;

	public KeyboardEditText(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public KeyboardEditText(Context context, AttributeSet attrs) {
		// super(mContext, attrs);
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);
		initAttributes(context);
		initKeyboard(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public KeyboardEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		initAttributes(context);
		initKeyboard(context, attrs);
	}

	private void init() {
		// 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
		mClearDrawable = getCompoundDrawables()[2];
		if (mClearDrawable == null) {
			// throw new NullPointerException("You can add drawableRight
			// attribute in XML");
			mClearDrawable = getResources().getDrawable(R.drawable.sl_edittext_clear);
		}

		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
		// 默认设置隐藏图标
		setClearIconVisible(false);
		// 设置焦点改变的监听
		setOnFocusChangeListener(this);
		// 设置输入框里面内容发生改变的监听
		addTextChangedListener(this);
	}

	private void initKeyboard(Context context, AttributeSet attrs) {

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.keyboard);

		if (a.hasValue(R.styleable.keyboard_xml)) {
			needcustomkeyboard = true;

			int xmlid = a.getResourceId(R.styleable.keyboard_xml, 0);

			mKeyboard = new Keyboard(context, xmlid);

			mKeyboardView = (KeyboardView) LayoutInflater.from(context).inflate(R.layout.mykeyboardview, null);
			if (a.hasValue(R.styleable.keyboard_randomkeys)) {
				boolean random = a.getBoolean(R.styleable.keyboard_randomkeys, false);
				randomkeys = random;

				if (random) {
					randomdigkey(mKeyboard);
				}
			}

			mKeyboardView.setKeyboard(mKeyboard);
			mKeyboardView.setEnabled(true);
			mKeyboardView.setPreviewEnabled(false);// 不显示预览
			mKeyboardView.setOnKeyboardActionListener(this);

			mKeyboardWindow = new PopupWindow(mKeyboardView, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			mKeyboardWindow.setAnimationStyle(R.style.AnimationFade);
			// mKeyboardWindow.setBackgroundDrawable(new BitmapDrawable());
			// mKeyboardWindow.setOutsideTouchable(true);
			mKeyboardWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// TODO Auto-generated method stub
					if (scrolldis > 0) {
						int temp = scrolldis;
						scrolldis = 0;
						if (null != mContentView) {
							mContentView.scrollBy(0, -temp);
						}
					}
				}
			});
		} else {
			needcustomkeyboard = false;
		}

		a.recycle();

	}

	private void showKeyboard() {
		if (null != mKeyboardWindow) {
			if (!mKeyboardWindow.isShowing()) {
				if (randomkeys) {
					randomdigkey(mKeyboard);
				}

				mKeyboardView.setKeyboard(mKeyboard);

				mKeyboardWindow.showAtLocation(this.mDecorView, Gravity.BOTTOM, 0, 0);
				mKeyboardWindow.update();

				if (null != mDecorView && null != mContentView) {
					int[] pos = new int[2];
					getLocationOnScreen(pos);
					float height = dpToPx(getContext(), 240);

					// int []hsmlpos=new int[2];
					// mDecorView.getLocationOnScreen(hsmlpos);

					Rect outRect = new Rect();
					mDecorView.getWindowVisibleDisplayFrame(outRect);

					int screen = real_scontenth;
					scrolldis = (int) ((pos[1] + getMeasuredHeight() - outRect.top) - (screen - height));

					if (scrolldis > 0) {
						mContentView.scrollBy(0, scrolldis);
					}
				}

			}
		}
	}

	public void hideKeyboard() {
		if (null != mKeyboardWindow && mKeyboardWindow.isShowing()) {
			mKeyboardWindow.dismiss();
		}
	}

	private void hideSysInput() {
		if (this.getWindowToken() != null) {
			InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(this.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));

				if (touchable) {
					this.setText("");
				}
			}
		}

		super.onTouchEvent(event);

		requestFocus();
		requestFocusFromTouch();

		if (needcustomkeyboard) {
			hideSysInput();
			showKeyboard();
		}

		return true;
	}

	/**
	 * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	/**
	 * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
	 *
	 * @param visible
	 */
	protected void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	/**
	 * 当输入框里面内容发生变化的时候回调的方法
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		// if (hasFoucs) {
		setClearIconVisible(s.length() > 0);
		// }
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	/**
	 * 设置晃动动画
	 */
	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	/**
	 * 晃动动画
	 *
	 * @param counts
	 *            1秒钟晃动多少下
	 * @return
	 */
	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != mKeyboardWindow) {
				if (mKeyboardWindow.isShowing()) {
					mKeyboardWindow.dismiss();
					return true;
				}
			}
		}

		return super.onKeyDown(keyCode, event);

	}

	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		this.mWindow = ((Activity) getContext()).getWindow();
		this.mDecorView = this.mWindow.getDecorView();
		this.mContentView = this.mWindow.findViewById(Window.ID_ANDROID_CONTENT);

		hideSysInput();
	}

	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		hideKeyboard();

		mKeyboardWindow = null;
		mKeyboardView = null;
		mKeyboard = null;

		mDecorView = null;
		mContentView = null;
		mWindow = null;
	}

	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub
		Editable editable = this.getText();
		int start = this.getSelectionStart();
		if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 隐藏键盘
			hideKeyboard();
		} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
			if (editable != null && editable.length() > 0) {
				if (start > 0) {
					editable.delete(start - 1, start);
				}
			}
		} else if (0x0 <= primaryCode && primaryCode <= 0x7f) {
			// 可以直接输入的字符(如0-9,.)，他们在键盘映射xml中的keycode值必须配置为该字符的ASCII码
			editable.insert(start, Character.toString((char) primaryCode));
		} else if (primaryCode > 0x7f) {
			Key mkey = getKeyByKeyCode(primaryCode);
			// 可以直接输入的字符(如0-9,.)，他们在键盘映射xml中的keycode值必须配置为该字符的ASCII码
			editable.insert(start, mkey.label);

		} else {
			// 其他一些暂未开放的键指令，如next到下一个输入框等指令
			// hideKeyboard();
			int nextFocusForwardId = getNextFocusForwardId();
			if (nextFocusForwardId == View.NO_ID) {
				hideKeyboard();
			} else {
				EditText et = (EditText) findViewById(nextFocusForwardId);
				et.requestFocus();
				et.setFocusable(true);
				et.setFocusableInTouchMode(true);
			}

		}
	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub

	}

	private Key getKeyByKeyCode(int keyCode) {
		if (null != mKeyboard) {
			List<Key> mKeys = mKeyboard.getKeys();
			for (int i = 0, size = mKeys.size(); i < size; i++) {
				Key mKey = mKeys.get(i);

				int codes[] = mKey.codes;

				if (codes[0] == keyCode) {
					return mKey;
				}
			}
		}

		return null;
	}

	private void initAttributes(Context context) {
		initScreenParams(context);
		this.setLongClickable(false);
		this.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		removeCopyAbility();

		if (this.getText() != null) {
			this.setSelection(this.getText().length());
		}

		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					hideKeyboard();
				}
			}
		});

	}

	@TargetApi(11)
	private void removeCopyAbility() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			this.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					return false;
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {

				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					return false;
				}
			});
		}
	}

	private boolean isNumber(String str) {
		String wordstr = "0123456789";
		if (wordstr.indexOf(str) > -1) {
			return true;
		}
		return false;
	}

	private void randomdigkey(Keyboard mKeyboard) {
		if (mKeyboard == null) {
			return;
		}

		List<Key> keyList = mKeyboard.getKeys();

		// 查找出0-9的数字键
		List<Key> newkeyList = new ArrayList<Key>();
		for (int i = 0, size = keyList.size(); i < size; i++) {
			Key key = keyList.get(i);
			CharSequence label = key.label;
			if (label != null && isNumber(label.toString())) {
				newkeyList.add(key);
			}
		}

		int count = newkeyList.size();

		List<KeyModel> resultList = new ArrayList<KeyModel>();

		LinkedList<KeyModel> temp = new LinkedList<KeyModel>();

		for (int i = 0; i < count; i++) {
			temp.add(new KeyModel(48 + i, i + ""));
		}

		Random rand = new SecureRandom();
		rand.setSeed(SystemClock.currentThreadTimeMillis());

		for (int i = 0; i < count; i++) {
			int num = rand.nextInt(count - i);
			KeyModel model = temp.get(num);
			resultList.add(new KeyModel(model.getCode(), model.getLable()));
			temp.remove(num);
		}

		for (int i = 0, size = newkeyList.size(); i < size; i++) {
			Key newKey = newkeyList.get(i);
			KeyModel resultmodle = resultList.get(i);
			newKey.label = resultmodle.getLable();
			newKey.codes[0] = resultmodle.getCode();
		}

	}

	class KeyModel {

		private Integer code;
		private String label;

		public KeyModel(Integer code, String lable) {
			this.code = code;
			this.label = lable;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getLable() {
			return label;
		}

		public void setLabel(String lable) {
			this.label = lable;
		}

	}

	/**
	 * 密度转换为像素值
	 *
	 * @param dp
	 * @return
	 */
	public static int dpToPx(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	private void initScreenParams(Context context) {
		DisplayMetrics dMetrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		display.getMetrics(dMetrics);

		screenw = dMetrics.widthPixels;
		screenh = dMetrics.heightPixels;
		density = dMetrics.density;
		densityDpi = dMetrics.densityDpi;

		screenh_nonavbar = screenh;

		int ver = Build.VERSION.SDK_INT;

		// 新版本的android 系统有导航栏，造成无法正确获取高度
		if (ver == 13) {
			try {
				Method mt = display.getClass().getMethod("getRealHeight");
				screenh_nonavbar = (Integer) mt.invoke(display);
			} catch (Exception e) {
			}
		} else if (ver > 13) {
			try {
				Method mt = display.getClass().getMethod("getRawHeight");
				screenh_nonavbar = (Integer) mt.invoke(display);
			} catch (Exception e) {
			}
		}

		real_scontenth = screenh_nonavbar - getStatusBarHeight(context);

	}

	/**
	 * 电量栏高度
	 *
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return sbar;
	}

}
