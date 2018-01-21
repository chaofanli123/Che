package com.victor.che.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.victor.che.R;
import com.victor.che.base.BaseFragment;
import com.victor.che.ui.Coupon.ForbiddenCouponFragment;
import com.victor.che.ui.Coupon.PastCouponFragment;
import com.victor.che.ui.Coupon.StartUsingCouponFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class IndexFragment1 extends BaseFragment {
    @BindView(R.id.pager_tab)
    SmartTabLayout pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;


    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private String provider_user_id;

    private Fragment fragment;


    private static final String[] CONTENT = new String[]{"通知下达", "政策法规", "禁用鱼药"};


    @Override
    public int getContentView() {
        return R.layout.fragment_shouye;
    }
    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        ((TextView) findViewById(R.id.tv_topbar_title)).setText("首页");
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        adapter = new GoogleMusicAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        pagerTab.setViewPager(mViewPager);
        pagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    //通知下达
                    fragment = new StartUsingCouponFragment();
                    break;
                case 1:
                    //禁用
                  //  fragment = new ForbiddenCouponFragment();
                    fragment = new StartUsingCouponFragment();
                    break;
                case 2:
                    //过期
                  //  fragment = new PastCouponFragment();
                    fragment = new StartUsingCouponFragment();
                    break;

                default:
                    fragment = new StartUsingCouponFragment();
                    break;
            }
            Bundle args = new Bundle();
            args.putString("type", (position + 1) + "");
            // args.putSerializable("mCar",mCar);
//        args.putSerializable("querybaoxianCar",querybaoxianCar);
            fragment.setArguments(args);
            fragments.add(position, fragment);
            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }

    /**
     * 接收来自NewaddUserCarFragment的消息后
     *
     * @param string
     */
//    @Subscribe
//    public void intenttoEvent(String string) {
//        if (StringUtil.isEmpty(string)) {
//            return;
//        }
//        fragment = new UsercarHistoryValuesFragment();
//        mViewPager.setCurrentItem(1);
//    }
}
