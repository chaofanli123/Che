package com.victor.che.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.UserCustomMessage;
import com.victor.che.event.EndMoneyNumEvent;
import com.victor.che.ui.fragment.EndMoneyFragment;
import com.victor.che.ui.fragment.EndTimesFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 用户会员卡余额不足，余次不足
 */
public class EndMoneyUsercardActivity extends BaseActivity {

    @BindView(R.id.pager_tab)
    SmartTabLayout pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;


    private List<Fragment> fragments=new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private String provider_user_id;

    private Fragment fragment;

    private static final String[] CONTENT = new String[]{"余额不足", "余次不足"};
    @Override
    public int getContentView() {
        return R.layout.activity_end_money;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("会员卡余额不足用户");
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
                     //   fragment = new EndMoneyFragment();
                        break;
                    case 1:
                     //   fragment = new EndMoneyFragment();
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
                    //余额不足
                    fragment = new EndMoneyFragment();
                    break;
                case 1:
                    //余次不足
                  fragment = new EndTimesFragment();
                    break;
                default:
                    fragment = new EndMoneyFragment();
                    break;
            }
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
     */

    /**
     * 自定义过后
     */
    @Subscribe
    public void onSelectend_num(EndMoneyNumEvent event) {
        if (event == null) {
            return;
        }
        if (event.num == 2) {
            fragment = new EndTimesFragment();
        }else if (event.num == 1) {
            fragment = new EndMoneyFragment();
        }

        //  getEndtimelist();
        // mPtrHelper.autoRefresh(true);
    }

}
