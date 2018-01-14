package com.victor.che.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Banner;
import com.victor.che.domain.Pending;
import com.victor.che.ui.GuanggaoWebActivity;
import com.victor.che.ui.OrderListActivity;
import com.victor.che.ui.ReceiptActivity;
import com.victor.che.ui.SearchActivity;
import com.victor.che.ui.ShoppingWebActivity;
import com.victor.che.ui.StoreActivity;
import com.victor.che.ui.VipcardStatisticActivity;
import com.victor.che.ui.WebDataAnalysisActivity;
import com.victor.che.ui.my.WoDeSheBeiListActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ListUtils;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PicassoUtils;
import com.victor.che.util.StringUtil;
import com.victor.che.util.StringUtils;
import com.victor.che.widget.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 首页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener, CustomScrollView.OnScrollListener {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.topbar_right)
    View topbar_right;

    @BindView(R.id.tv_sales_amount)
    TextView tv_sales_amount;

    @BindView(R.id.tv_order_count)
    TextView tv_order_count;

    @BindView(R.id.tv_customer_count)
    TextView tv_customer_count;
    @BindView(R.id.banner)
    BGABanner banner;


    @BindView(R.id.tv_reg_days)
    TextView tv_reg_days;// 注册天数
    @BindView(R.id.guidePages)
    ViewPager guidePages;
    @BindView(R.id.img_putup_order)
    ImageView imgPutupOrder;
    Unbinder unbinder;
    @BindView(R.id.viewGroup)
    LinearLayout viewGroup;
    @BindView(R.id.lin_search)
    LinearLayout linSearch;
    @BindView(R.id.scrollview)
    CustomScrollView scrollview;
    @BindView(R.id.relativeLyaout)
    RelativeLayout relativeLyaout;
    @BindView(R.id.lin)
    LinearLayout lin;
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
        scrollview.setOnScrollListener(this);

        //当布局的状态或者控件的可见性发生改变回调的接口
        relativeLyaout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(scrollview.getScrollY());
                System.out.println(scrollview.getScrollY());
            }
        });

//获取挂单信息
        mPtrFrame.setEnabledNextPtrAtOnce(true);//可以连续下拉刷新
        // 下拉刷新
        PtrDefaultHandler ptrDefaultHandler = new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                _doRefreshData();
                // 同时获取banner
                _getBanner();
                //获取挂单信息
                _reqData();
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
     * 获取挂单列表
     */
    private void _reqData() {
        MyParams params = new MyParams();
       // params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("start", 0);//列表记录开始位置
        params.put("pageSize", 20);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.url_cart_list_pending_order, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Pending> temp = JSON.parseArray(element.body, Pending.class);
                        if (CollectionUtil.isEmpty(temp)) {
                            imgPutupOrder.setBackgroundResource(R.drawable.icon_putup_order_no);
                        } else {
                            imgPutupOrder.setBackgroundResource(R.drawable.ic_putup_order);
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
                bannerList = JSON.parseArray(element.body, Banner.class);
                initialize();
            }
        });
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
                MyApplication.openActivity(mContext, OrderListActivity.class, true);
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
    /**
     * 去搜索界面
     */
    @OnClick(R.id.topbar_search)
    void gotoSearch() {
    }
    /**
     * 去搜索界面
     */
    @OnClick(R.id.topbar_search1)
    void gotoSearch1() {
    }

    /**
     * 快捷方式
     */
    @OnClick(R.id.topbar_right)
    void quickOperate() {
        //        mMenuDialogFragment.show(getFragmentManager(), getClass().getSimpleName());

        View ppw = View.inflate(mContext, R.layout.ppw_index_shortcut, null);

        // 新增服务
        ppw.findViewById(R.id.tv_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 新增客户
        ppw.findViewById(R.id.tv_add_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 开卡
        ppw.findViewById(R.id.tv_active_vipcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        PopupWindow popupWindow = new PopupWindow(ppw, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 不产生焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(topbar_right, 0, 0);
    }
    /**
     * 快捷方式
     */
    @OnClick(R.id.topbar_right1)
    void quickOperate1() {
        //        mMenuDialogFragment.show(getFragmentManager(), getClass().getSimpleName());

        View ppw = View.inflate(mContext, R.layout.ppw_index_shortcut, null);

        // 新增服务
        ppw.findViewById(R.id.tv_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 新增客户
        ppw.findViewById(R.id.tv_add_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 开卡
        ppw.findViewById(R.id.tv_active_vipcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        PopupWindow popupWindow = new PopupWindow(ppw, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 不产生焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(topbar_right, 0, 0);
    }

    /**
     * 去扫一扫界面
     */
    @OnClick(R.id.tv_scan_qrcode)
    void gotoScan() {
        new TedPermission(MyApplication.CONTEXT)
                .setPermissions(Manifest.permission.CAMERA)
                .setDeniedMessage(R.string.rationale_camera)
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                  //      MyApplication.openActivity(mContext, ScanActivity.class);
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 刷新店铺实时交易数据
        if (MyApplication.isLogined()) {
            _doRefreshData();
            _reqData();
          //  tv_reg_days.setText(MyApplication.CURRENT_USER.getRegDays() + "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
     //   tv_reg_days.setText(MyApplication.CURRENT_USER.getRegDays() + "");
    }
    /**
     * 去收款界面
     */
    @OnClick(R.id.tv_receipt)
    void gotoReceipt() {
        MyApplication.openActivity(mContext, ReceiptActivity.class);
    }

    /**
     * 去挂单界面
     */
    @OnClick(R.id.img_putup_order)
    void gotoPending() {
    }

    /**
     * 刷新交易数据
     */
    private void _doRefreshData() {
        MyParams params = new MyParams();
//        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//商家id
//        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        VictorHttpUtil.doGet(mContext, Define.URL_STATISTIC_AMOUNT, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        //                        tv_refresh.setText("刷新");
                        JSONObject jsonobj = JSON.parseObject(element.body);

                        if (jsonobj != null) {
                            tv_sales_amount.setText(MathUtil.getFinanceValue(jsonobj.getDoubleValue("amount")));//营业额
                            tv_order_count.setText(jsonobj.getString("order_count"));//订单数
                            tv_customer_count.setText(jsonobj.getString("user_count"));//消费人数
                        }
                        mPtrFrame.refreshComplete();
                    }

                    @Override
                    public void callbackError(String url, Element obj) {
                        super.callbackError(url, obj);

                        mPtrFrame.refreshComplete();
                    }
                });
    }

    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, linSearch.getTop());
        lin.layout(0, mBuyLayout2ParentTop, lin.getWidth(), mBuyLayout2ParentTop + lin.getHeight());
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
