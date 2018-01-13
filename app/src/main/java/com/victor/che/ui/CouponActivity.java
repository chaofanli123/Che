package com.victor.che.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.ui.fragment.CouponDisableFragment;
import com.victor.che.ui.fragment.CouponUsableFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 选择优惠券页面 viewpage 可滑动 优惠券可选，不可选
 */
public class CouponActivity extends BaseActivity {
    @BindView(R.id.pager_tab)
    SmartTabLayout pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private    String useful_count="0";
    private     String unuseful_count="0";

    private Fragment fragment;
    private  String[]  CONTENT=new String[2];
    private String mobile,car_plate_no,goods_id,price;
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    CONTENT =new String[]{"可用优惠券("+useful_count+")","不可用优惠券("+unuseful_count+")"};
                    mViewPager.setAdapter(adapter);
                    pagerTab.setViewPager(mViewPager);
                    break;
            }

        }
    };
    @Override
    public int getContentView() {
        return R.layout.activity_coupon;
    }
    @Override
    protected void initView() {
        super.initView();
        mobile=getIntent().getStringExtra("mobile");
        car_plate_no=getIntent().getStringExtra("car_plate_no");
        goods_id=getIntent().getStringExtra("goods_id");
        price=getIntent().getStringExtra("price");
        CONTENT =new String[]{"可用优惠券("+useful_count+")","不可用优惠券("+unuseful_count+")"};
        loadData(1,20);
        setTitle("使用优惠券");
        adapter = new GoogleMusicAdapter(getSupportFragmentManager());
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
                    //可用优惠券
                    fragment = new CouponUsableFragment();
                    break;
                case 1:
                    //不可用优惠券
                    fragment = new CouponDisableFragment();
                    break;
//                default:
//                    fragment = new CouponUsableFragment();
//                    break;
            }
            Bundle args = new Bundle();
            args.putString("mobile", mobile);
            args.putString("car_plate_no", car_plate_no);
            args.putString("goods_id", goods_id);
            args.putString("price", price);
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
     * 获取优惠券
     *
     * @param
     */
    private void loadData(int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("pageSize", pageSize);
        params.put("start", curpage);
        params.put("mobile", mobile);
        params.put("car_plate_no", car_plate_no);
        params.put("goods_id", goods_id);
        params.put("price", price);
        params.put("is_useful", 1);
        VictorHttpUtil.doGet(mContext, Define.url_coupon_grant_record_list_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    Message message=new Message();
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = parseObject(element.body);
                        if (jsonobj != null) {
                            useful_count = jsonobj.getString("useful_count");
                            unuseful_count= jsonobj.getString("unuseful_count");
                            message.what=1;
                            handler.sendMessage(message);
                        }

                    }
                });

    }
}
