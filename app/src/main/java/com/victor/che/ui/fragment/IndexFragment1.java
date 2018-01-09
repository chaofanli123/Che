package com.victor.che.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Banner;
import com.victor.che.ui.Coupon.CouponListActivity;
import com.victor.che.ui.CustomerListActivity;
import com.victor.che.ui.GuanggaoWebActivity;
import com.victor.che.ui.MarketingActivity;
import com.victor.che.ui.MyAccountActivity;
import com.victor.che.ui.OrderListActivity;
import com.victor.che.ui.ProductMgrActivity;
import com.victor.che.ui.ProductMgrHeadActivity;
import com.victor.che.ui.ShoppingWebActivity;
import com.victor.che.ui.StoreActivity;
import com.victor.che.ui.VipcardStatisticActivity;
import com.victor.che.ui.WebDataAnalysisActivity;
import com.victor.che.util.ListUtils;
import com.victor.che.util.PicassoUtils;
import com.victor.che.util.StringUtil;
import com.victor.che.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 首页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class IndexFragment1 extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;


    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.guidePages)
    ViewPager guidePages;
    @BindView(R.id.viewGroup)
    LinearLayout viewGroup;

    @BindView(R.id.relativeLyaout)
    RelativeLayout relativeLyaout;
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
        return R.layout.fragment_shouye;
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void initView() {
        super.initView();

//获取挂单信息
        mPtrFrame.setEnabledNextPtrAtOnce(true);//可以连续下拉刷新
        // 下拉刷新
        PtrDefaultHandler ptrDefaultHandler = new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 同时获取banner
                _getBanner();
            }
        };
        mPtrFrame.setPtrHandler(ptrDefaultHandler);
        // 获取banner信息
        _getBanner();
        //初始化viewpage 滑动翻页、
        _viewpage();
    }

    /**
     * 初始化viewpage 滑动翻页、
     */
    private void _viewpage() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        pageViews = new ArrayList<View>();
        View view1 = inflater.from(mContext).inflate(R.layout.item01, null);
        View view2 = inflater.from(mContext).inflate(R.layout.item02, null);
        view1.findViewById(R.id.area_product_mgr).setOnClickListener(this);
        view1.findViewById(R.id.area_vipcard_mgr).setOnClickListener(this);
        view1.findViewById(R.id.area_customer).setOnClickListener(this);
        view1.findViewById(R.id.area_order_list).setOnClickListener(this);
        view1.findViewById(R.id.area_marketing).setOnClickListener(this);
        view2.findViewById(R.id.area_shopping).setOnClickListener(this);
        view2.findViewById(R.id.area_report).setOnClickListener(this);
        view2.findViewById(R.id.area_myzhanghu).setOnClickListener(this);
        view2.findViewById(R.id.area_mendian).setOnClickListener(this);
        view1.findViewById(R.id.area_coupon_list).setOnClickListener(this);
        pageViews.add(view1);
        pageViews.add(view2);
        imageViews = new ImageView[pageViews.size()];

        for (i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(14, 14);//设置宽高
            layoutParams.setMargins(20, 0, 0, 0); //设置间距
            imageView.setLayoutParams(layoutParams);
            imageViews[i] = imageView;
            if (i == 0) {
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.ic_select_point);

            } else {
                imageViews[i].setBackgroundResource(R.drawable.ic_normal_point);
            }
            imageViews[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int k = i;
                    guidePages.setCurrentItem(where(imageViews, (ImageView) v));
                    Log.d("-------", "-------------" + i);
                }
            });
            viewGroup.addView(imageViews[i]);
        }
        guidePages.setAdapter(new GuidePageAdapter());
        guidePages.setOnPageChangeListener(new GuidePageChangeListener());
    }

    public int where(ImageView[] imageviews, ImageView imageview) {
        for (int i = 0; i < imageviews.length; i++) {
            if (imageviews[i] == imageview) {
                return i;
            }
        }
        return -1;
    }

    private void initialize() {
        List<String> images = new ArrayList<>();
        if (!ListUtils.isEmpty(bannerList)) {
            for (int i = 0; i < bannerList.size(); i++) {
                if (StringUtils.isNotBlank(bannerList.get(i).image_url)) {
                    ListUtils.addListNotNullValue(images, bannerList.get(i).image_url);
                }
            }
        }
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {

                PicassoUtils.loadImage(getActivity(), model, itemView);
            }
        });
        banner.setData(images, null);
        banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                if (StringUtils.isNotBlank(bannerList.get(position).image_url) && StringUtil.isNotEmpty(bannerList.get(position).redirect_data)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mUrl", bannerList.get(position).redirect_data);
                    MyApplication.openActivity(mContext, GuanggaoWebActivity.class, bundle);
                }
            }
        });
    }

    /**
     * 获取banner信息
     */
    private void _getBanner() {
        // 检查版本更新
        MyParams params = new MyParams();
        params.put("position_type", "1");//	展示位置 ,默认为1 ，1-banner
        VictorHttpUtil.doGet(mContext, Define.URL_APP_CONFIG, params, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                bannerList = JSON.parseArray(element.data, Banner.class);
                initialize();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.area_product_mgr://产品管理
                if (MyApplication.CURRENT_USER.is_initial_provider == 1) {//总店身份
                    MyApplication.openActivity(mContext, ProductMgrHeadActivity.class, true);
                } else if (MyApplication.CURRENT_USER.is_initial_provider == 0) {//分店身份
                    MyApplication.openActivity(mContext, ProductMgrActivity.class, true);
                }
                break;
            case R.id.area_vipcard_mgr://会员卡管理
                MyApplication.openActivity(mContext, VipcardStatisticActivity.class, true);
                break;
            case R.id.area_customer://用户
                MyApplication.openActivity(mContext, CustomerListActivity.class, true);
                break;
            case R.id.area_order_list://订单界面
                MyApplication.openActivity(mContext, OrderListActivity.class, true);
                break;
            case R.id.area_marketing: //营销
                MyApplication.openActivity(mContext, MarketingActivity.class);
                break;
            case R.id.area_myzhanghu: //我的账户
                MyApplication.openActivity(mContext, MyAccountActivity.class, true);
                break;
            case R.id.area_shopping://商城
                Bundle bundle = new Bundle();
                bundle.putString("mURL", Define.MWEB_DOMAIN + "mall/#/" + MyApplication.CURRENT_USER.provider_id + "/" + MyApplication.CURRENT_USER.staff_user_id + "?_k=l01xoq");
                MyApplication.openActivity(mContext, ShoppingWebActivity.class, bundle);
                break;
            case R.id.area_report://数据分析
                Bundle bundle1 = new Bundle();
                bundle1.putString("mUrl", Define.MWEB_DOMAIN + "web/analyse/dataAnalysis.html" + "?provider_id=" + MyApplication.CURRENT_USER.provider_id);
                MyApplication.openActivity(mContext, WebDataAnalysisActivity.class, bundle1);
                break;
            case R.id.area_mendian://门店
                MyApplication.openActivity(mContext, StoreActivity.class);
                break;
            case R.id.area_coupon_list://优惠券
                MyApplication.openActivity(mContext, CouponListActivity.class);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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
