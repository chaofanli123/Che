package com.victor.che.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;


/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/4/21 0021 9:11.
 */

public class DisallowParentTouchViewPager extends ViewPager {

    private ViewGroup mView;
    public DisallowParentTouchViewPager(Context context) {
        super(context);
        initView();
    }



    public DisallowParentTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

        mView = (ViewGroup) getParent();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (this.mView != null && e.getAction() != MotionEvent.ACTION_UP) {
            this.mView.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(e);
    }
}
