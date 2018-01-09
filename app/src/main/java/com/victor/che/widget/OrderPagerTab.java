package com.victor.che.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.victor.che.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单页的ViewPager的导航条
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年10月26日 下午7:37:42
 */
public class OrderPagerTab extends LinearLayout {

    @BindView(R.id.rdobtn0)
    RadioButton rdobtn0;

    @BindView(R.id.rdobtn1)
    RadioButton rdobtn1;

    @BindView(R.id.rdobtn2)
    RadioButton rdobtn2;

    private LazyViewPager mViewPager;

    private OnTabSelectedListener onTabSelectedListener;

    public OrderPagerTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载视图
        View view = View.inflate(context, R.layout.widget_order_pager_tab, this);
        ButterKnife.bind(this, view);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    /**
     * 点击Tab的时候，Tab样式变化，ViewPager更换页
     *
     * @param v
     */
    @OnClick({R.id.rdobtn0, R.id.rdobtn1, R.id.rdobtn2, R.id.tab0, R.id.tab1, R.id.tab2})
    void onTabClick(View v) {
        int currentPos = 3;
        switch (v.getId()) {
            case R.id.rdobtn0:
            case R.id.tab0:
                currentPos = 0;
                rdobtn0.setChecked(true);
                rdobtn1.setChecked(false);
                rdobtn2.setChecked(false);
                break;
            case R.id.rdobtn1:
            case R.id.tab1:
                currentPos = 1;
                rdobtn0.setChecked(false);
                rdobtn1.setChecked(true);
                rdobtn2.setChecked(false);
                break;
            case R.id.rdobtn2:
            case R.id.tab2:
                currentPos = 2;
                rdobtn0.setChecked(false);
                rdobtn1.setChecked(false);
                rdobtn2.setChecked(true);
                break;
            default:
                break;
        }
        mViewPager.setCurrentItem(currentPos);
    }

    /**
     * 绑定ViewPager
     *
     * @param pager
     */
    public void setViewPager(LazyViewPager pager) {
        this.mViewPager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        /**
         * ViewPager换页的时候，Tab更换
         */
        mViewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rdobtn0.setChecked(true);
                        rdobtn1.setChecked(false);
                        rdobtn2.setChecked(false);
                        break;
                    case 1:
                        rdobtn0.setChecked(false);
                        rdobtn1.setChecked(true);
                        rdobtn2.setChecked(false);
                        break;
                    case 2:
                        rdobtn0.setChecked(false);
                        rdobtn1.setChecked(false);
                        rdobtn2.setChecked(true);
                        break;
                    default:
                        break;
                }
                if (onTabSelectedListener != null) {
                    onTabSelectedListener.onTabSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 选中某个tab
     */
    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

}
