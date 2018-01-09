package com.victor.che.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.ui.fragment.NewaddUserCarFragment;
import com.victor.che.ui.fragment.UsercarHistoryValuesFragment;
import com.victor.che.util.StringUtil;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UsedCarValuesActivity extends BaseActivity {

    @BindView(R.id.pager_tab)
    SmartTabLayout pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;


    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private String provider_user_id;

    private Fragment fragment;


    private static final String[] CONTENT = new String[]{"新增估值", "历史估值"};

  //  private UserDetalis.CarBean mCar;

    @Override
    public int getContentView() {
        return R.layout.activity_used_car_values;
    }
    @Override
    protected void initView() {
        super.initView();
        //mCar= (UserDetalis.CarBean) getIntent().getSerializableExtra("mCar");
        tvTopbarTitle.setText("二手车估值");
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
    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    //新增估值
                    fragment = new NewaddUserCarFragment();
                    break;
                case 1:
                    //历史估值
                    fragment = new UsercarHistoryValuesFragment();
                    break;
                default:
                    fragment = new NewaddUserCarFragment();
                    break;
            }
            Bundle args = new Bundle();
            args.putString("type", (position + 1) + "");
      // args.putSerializable("mCar",mCar);
//        args.putSerializable("querybaoxianCar",querybaoxianCar);
            args.putString("provider_user_id",provider_user_id);
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
    @Subscribe
    public void intenttoEvent(String string) {
        if (StringUtil.isEmpty(string)) {
            return;
        }
        fragment = new UsercarHistoryValuesFragment();
        mViewPager.setCurrentItem(1);
    }
}
