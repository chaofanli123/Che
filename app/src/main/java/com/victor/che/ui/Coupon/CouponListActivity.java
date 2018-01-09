package com.victor.che.ui.Coupon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 优惠券列表
 */
public class CouponListActivity extends BaseActivity {

    @BindView(R.id.pager_tab)
    SmartTabLayout pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;


    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private String provider_user_id;

    private Fragment fragment;


    private static final String[] CONTENT = new String[]{"启用", "禁用", "过期"};


    @Override
    public int getContentView() {
        return R.layout.activity_coupon_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("优惠券");
        adapter = new GoogleMusicAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        pagerTab.setViewPager(mViewPager);
        provider_user_id = getIntent().getStringExtra("provider_user_id");
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

    /**
     * 新增优惠券
     */
    @OnClick(R.id.btn_newadd_conpon)
    public void onGotoNewAdd() {
        Bundle bundle=new Bundle();
        bundle.putString("type","coupon");
        MyApplication.openActivity(mContext,NewAddCouponActivity.class,bundle);
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    //启用
                    fragment = new StartUsingCouponFragment();
                    break;
                case 1:
                    //禁用
                    fragment = new ForbiddenCouponFragment();
                    break;
                case 2:
                    //过期
                    fragment = new PastCouponFragment();
                    break;

                default:
                    fragment = new StartUsingCouponFragment();
                    break;
            }
            Bundle args = new Bundle();
            args.putString("type", (position + 1) + "");
            // args.putSerializable("mCar",mCar);
//        args.putSerializable("querybaoxianCar",querybaoxianCar);
            args.putString("provider_user_id", provider_user_id);
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
