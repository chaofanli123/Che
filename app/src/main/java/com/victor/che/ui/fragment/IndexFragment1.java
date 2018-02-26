package com.victor.che.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.victor.che.R;
import com.victor.che.base.BaseFragment;
import com.victor.che.ui.Coupon.JingYongYvYaoFragment;
import com.victor.che.ui.Coupon.StartUsingCouponFragment;
import com.victor.che.ui.Coupon.ZhenCeFaGuiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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


    Unbinder unbinder;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private String provider_user_id;

    private Fragment fragment;



    private static final String[] CONTENT = new String[]{"通知下达", "政策法规", "禁用鱼药"};
    private int currentPos = 0;// 查询类型 1-通知下达 2-政策法规 3-禁用鱼药 ，默认为1

    @Override
    public int getContentView() {
        return R.layout.fragment_shouye;
    }
    protected void initView() {
        super.initView();

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





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                    //政策法规
                    fragment = new ZhenCeFaGuiFragment();
                    break;
                case 2:
                    //禁用渔药
                    fragment = new JingYongYvYaoFragment();
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


}
