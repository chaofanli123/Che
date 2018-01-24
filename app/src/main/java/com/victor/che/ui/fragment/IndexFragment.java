package com.victor.che.ui.fragment;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Banner;
import com.victor.che.ui.my.WoDeSheBeiListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener{
    /*
    小圆点图片
     */
    private ImageView[] imageViews;
    private ArrayList<View> pageViews;//翻页集合
    private ImageView imageView;//单个小圆点
    public int i = 0;
    private List<Banner> bannerList;

    @Override
    public int getContentView() {
        return R.layout.fragment_index;
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        ((TextView) findViewById(R.id.tv_topbar_title)).setText("辖区档案");
      findViewById(R.id.iv_back).setVisibility(View.GONE);
        //初始化viewpage 滑动翻页、
        _viewpage();

    }

    /**
     * 初始化viewpage 滑动翻页、
     */
    private void _viewpage() {
        findViewById(R.id.area_product_mgr).setOnClickListener(this);
        findViewById(R.id.area_vipcard_mgr).setOnClickListener(this);
        findViewById(R.id.area_customer).setOnClickListener(this);
        findViewById(R.id.area_order_list).setOnClickListener(this);
        findViewById(R.id.area_marketing).setOnClickListener(this);
        findViewById(R.id.area_shopping).setOnClickListener(this);
        findViewById(R.id.area_report).setOnClickListener(this);
        findViewById(R.id.area_myzhanghu).setOnClickListener(this);
        findViewById(R.id.area_mendian).setOnClickListener(this);
        findViewById(R.id.area_coupon_list).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.area_product_mgr://产品管理
//                if (MyApplication.CURRENT_USER.is_initial_provider == 1) {//总店身份
//                    MyApplication.openActivity(mContext, ProductMgrHeadActivity.class, true);
//                } else if (MyApplication.CURRENT_USER.is_initial_provider == 0) {//分店身份
//                    MyApplication.openActivity(mContext, ProductMgrActivity.class, true);
//                }
                break;
            case R.id.area_vipcard_mgr://会员卡管理
                break;
            case R.id.area_customer://用户
//                MyApplication.openActivity(mContext, CustomerListActivity.class, true);
//                MyApplication.openActivity(mContext, WoDeSheBeiListActivity.class, true);
                startActivity(new Intent(mContext,WoDeSheBeiListActivity.class));
                break;
            case R.id.area_order_list://订单界面
              //  MyApplication.openActivity(mContext, OrderListActivity.class, true);
                break;
            case R.id.area_marketing: //营销
                break;
            case R.id.area_myzhanghu: //我的账户
                break;
            case R.id.area_shopping://商城
//                Bundle bundle = new Bundle();
//                bundle.putString("mURL", Define.MWEB_DOMAIN + "mall/#/" + MyApplication.CURRENT_USER.provider_id + "/" + MyApplication.CURRENT_USER.staff_user_id + "?_k=l01xoq");
//                MyApplication.openActivity(mContext, ShoppingWebActivity.class, bundle);
                break;
            case R.id.area_report://数据分析
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("mUrl", Define.MWEB_DOMAIN + "web/analyse/dataAnalysis.html" + "?provider_id=" + MyApplication.CURRENT_USER.provider_id);
                break;
            case R.id.area_mendian://门店
                break;
            case R.id.area_coupon_list://优惠券
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
     //   tv_reg_days.setText(MyApplication.CURRENT_USER.getRegDays() + "");
    }
    /**
     * 指引页面Adapter
     */
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    /**
     * 指引页面改监听器
     */
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            //  guidePages.getParent().requestDisallowInterceptTouchEvent(true);

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.ic_select_point);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.ic_normal_point);
                }
            }
        }
    }
}
