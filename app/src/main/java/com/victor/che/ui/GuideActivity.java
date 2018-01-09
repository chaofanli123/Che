package com.victor.che.ui;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.victor.che.R;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 欢迎页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 18:51
 */
public class GuideActivity extends BaseActivity implements OnPageChangeListener {

    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3};

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        boolean firstStartedApp = MyApplication.spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP);
        if (!firstStartedApp) {// 第一次进入

            LayoutInflater inflater = LayoutInflater.from(mContext);
            views = new ArrayList<View>();
            views.add(inflater.inflate(R.layout.guide1, null));
            views.add(inflater.inflate(R.layout.guide2, null));
            views.add(inflater.inflate(R.layout.guide3, null));

            GuidePagePagerAdapter mAdapter = new GuidePagePagerAdapter(views);
            ViewPager mViewPager = (ViewPager) findViewById(R.id.mViewPager);
            mViewPager.setAdapter(mAdapter);
            mViewPager.addOnPageChangeListener(this);

            Button start_btn = (Button) views.get(2).findViewById(R.id.start_btn);
            start_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSkip();
                }
            });

            initDots();
        } else {// 第二次进入
            MyApplication.openActivity(mContext, LoginActivity.class);
        }
    }

    /**
     * 跳过
     */
    @OnClick(R.id.btn_skip)
    void doSkip() {
        // 标记第一次启动结束
        MyApplication.spUtil.setBoolean(ConstantValue.SP.FIRST_STARTED_APP, false);
        // 进入主页界面
        if (MyApplication.isLogined()) {
            MyApplication.openActivity(mContext, TabBottomActivity.class);
        } else {
            MyApplication.openActivity(mContext, LoginActivity.class);
        }
        finish();
    }

    /**
     * 初始化指示器
     */
    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++) {
            if (position == i) {
                dots[i].setImageResource(R.drawable.indicator_checked);
                // 设置沉浸式状态栏颜色
            } else {
                dots[i].setImageResource(R.drawable.indicator_uncheck);
            }
        }
    }

    /**
     * 引导页适配器
     *
     * @author Victor
     * @email 468034043@qq.com
     * @time 2016年4月27日 上午11:48:55
     */
    private class GuidePagePagerAdapter extends PagerAdapter {
        private List<View> views;

        public GuidePagePagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

    }

}
