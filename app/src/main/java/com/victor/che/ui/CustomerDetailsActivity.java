package com.victor.che.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.UserDetalis;
import com.victor.che.event.UserEditEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.PicassoUtils;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * 会员详情界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 13:57
 */
public class CustomerDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_customer_name)
    TextView tv_customer_name;

    @BindView(R.id.tv_customer_mobile)
    TextView tv_customer_mobile;

    @BindView(R.id.tv_customer_vipcard)
    TextView tv_customer_vipcard;

    @BindView(R.id.tv_customer_car)
    TextView tv_customer_car;

    @BindView(R.id.tv_last_consume)
    TextView tv_last_consume;

    @BindView(R.id.iv_car_logo)
    ImageView iv_car_logo;

    @BindView(R.id.area_customer_name)
    RelativeLayout areaCustomerName;
    @BindView(R.id.area_customer_mobile)
    RelativeLayout areaCustomerMobile;
    @BindView(R.id.area_consume_records)
    RelativeLayout areaConsumeRecords;
    @BindView(R.id.area_vipcard)
    RelativeLayout areaVipcard;
    @BindView(R.id.area_car_list)
    RelativeLayout areaCarList;

    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;
    private String provider_user_id;
    String mobile;
    String name;
    String car_plate_no;
    int card_num;

    /*
    list初始化
     */
    private List<UserDetalis.CarBean> mList = new ArrayList<>();
    private CarMyListAdapter mAdapter;

    private PtrHelper<UserDetalis.CarBean> mPtrHelper;
    //    private View headerView;
//    private ViewHolder holder;
    private   String car_no;//车牌号
    private String pinpaichexi;

  //  private String license_img;//车辆行驶证

    /**
     * 行驶证popuwindow
     */
    private PopupWindow popup_qrcode = null;

    @Override
    public int getContentView() {
        return R.layout.activity_customer_details;
    }
    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setHasFixedSize(true); //设置scrollview 不显示全的问题。
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new CarMyListAdapter(R.layout.item_usercard, mList);
        mRecyclerView.setAdapter(mAdapter);
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                getuserdetail();
               // _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(false);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        // 设置标题
        setTitle("用户详情");
        provider_user_id = getIntent().getStringExtra("provider_user_id");
        if (StringUtil.isEmpty(provider_user_id)) {
            MyApplication.showToast("用户编码为空");
            return;
        }
    }

    /**
     * 获取用户详情
     */
 private void  getuserdetail(){
     // 请求数据
     MyParams params = new MyParams();
     params.put("provider_user_id", provider_user_id);// 用户编号(获取用户列表时返回)
     params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
     VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_DETAILS, params, false, null,
             new BaseHttpCallbackListener<Element>() {
                 @Override
                 public void callbackSuccess(String url, Element element) {
                     JSONObject jsonobj = JSON.parseObject(element.body);
                     UserDetalis userDetalis = JSON.parseObject(element.body, UserDetalis.class);
                     if (userDetalis != null) {
                         mList = userDetalis.getCar();
                         if (!CollectionUtil.isEmpty(mList)) {
                             mAdapter.setNewData(mList);
                             mAdapter.notifyDataSetChanged();
                         }
                     }
                     if (jsonobj != null) {
                         // 姓名
                         name = jsonobj.getString("name");
                         if (!StringUtil.isEmpty(name)) {
                             tv_customer_name.setText(StringUtil.isEmpty(name) ? "无姓名" : name);
                         }
                         // 手机号
                         mobile = jsonobj.getString("mobile");
                         if (!StringUtil.isEmpty(mobile)) {
                             tv_customer_mobile.setText(mobile);
                         }

                         // 会员卡数量
                         card_num = jsonobj.getIntValue("card_num");
                         tv_customer_vipcard.setText(card_num + "张");


                         // 会员默认车辆
                         car_plate_no = jsonobj.getString("car_plate_no");
                         if (!StringUtil.isEmpty(car_plate_no)) {
                             // 车牌号
                             // tv_customer_car.setText(car_plate_no);
                             // 车辆logo
                             ImageLoaderUtil.display(mContext, iv_car_logo, jsonobj.getString("car_brand_image"), R.drawable.ic_car_pre, R.drawable.ic_car_pre);
                         } else {
                             //  tv_customer_car.setText("暂无");
                             iv_car_logo.setVisibility(View.GONE);
                         }

                         //消费时间
                         tv_last_consume.setText(jsonobj.getString("consume_time"));
                     }

                 }
             });
 }
    private void cardriving(String license_img) {
        View viewQrcode = View.inflate(mContext, R.layout.popu_cardriving, null);
        ImageView ercode= (ImageView) viewQrcode.findViewById(R.id.img_ercode);
        PicassoUtils.loadWishOneImage(mContext,license_img,ercode);
        RelativeLayout lin_wxm = (RelativeLayout) viewQrcode.findViewById(R.id.lin_wxmcode);
        popup_qrcode = new PopupWindow(mContext);
        popup_qrcode.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup_qrcode.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popup_qrcode.setBackgroundDrawable(new BitmapDrawable());
        popup_qrcode.setFocusable(true);
        popup_qrcode.setOutsideTouchable(true);
        popup_qrcode.setContentView(viewQrcode);
        lin_wxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_qrcode.dismiss();
            }
        });

    }

  /*  *//**
     * 获取用户车辆信息
     *
     * @param pullToRefresh
     * @param curpage
     * @param pageSize
     *//*
    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_user_id", provider_user_id);// 	用户id
        VictorHttpUtil.doGet(mContext, Define.url_provder_user_car_list_v1, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<UserCar> temp = JSON.parseArray(element.data, UserCar.class);
                        if (pullToRefresh) {//刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                //有数据
                                mList.addAll(temp);
                                mAdapter.setNewData(mList);
                                mAdapter.notifyDataSetChanged();
                                if (CollectionUtil.getSize(temp) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(temp);
                        }
                    }

                });

    }*/

    /**
     * 开卡数量变化
     *
     * @param event
     */
    @Subscribe
    public void onCardNumChanged(String event) {
        if (ConstantValue.Event.ACTIVE_VIPCARD.equalsIgnoreCase(event)) {
            card_num++;
            tv_customer_vipcard.setText(card_num + "张");
        }
    }

    /**
     * 用户手机发生修改
     *
     * @param event
     */
    @Subscribe
    public void onDataChanged(UserEditEvent event) {
        if (event != null) {
            if ("name".equalsIgnoreCase(event.action)) {
                tv_customer_name.setText(event.data);
                name = event.data;
            } else if ("mobile".equalsIgnoreCase(event.action)) {
                tv_customer_mobile.setText(event.data);
                mobile = event.data;
            }
        }
    }


    /**
     * 去开卡
     */
    @OnClick(R.id.btn_operate)
    void gotoActiveVipcard() {
        Bundle bundle = new Bundle();
        bundle.putString("mMobile", mobile);
        bundle.putString("mPln", car_plate_no);
        MyApplication.openActivity(mContext, CustomActiveVipcardActivity.class, bundle);
    }

    /**
     * 去开卡
     */
    @OnClick(R.id.topbar_right)
    void doDeleteCustomer() {
        AlertDialogFragment.newInstance(
                "提示",
                "您确定要删除该用户吗？",
                "确定",
                "取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _doDeleteCustomer();
                    }
                },
                null)
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    private void _doDeleteCustomer() {
        MyParams params = new MyParams();
        params.put("provider_user_id", provider_user_id);//服务商编号
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_DEL, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("删除成功");

                        EventBus.getDefault().post(ConstantValue.Event.REFRESH_CUSTOMER_LIST);

                        finish();
                    }
                });
    }


    @OnClick({R.id.area_customer_name, R.id.area_customer_mobile, R.id.area_consume_records, R.id.area_vipcard, R.id.area_car_list})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.area_customer_name:
                /**
                 * 修改姓名
                 */
                bundle.putString("mAction", "name");
                bundle.putString("provider_user_id", provider_user_id);
                bundle.putString("mData", name);
                MyApplication.openActivity(mContext, EditCustomerActivity.class, bundle);
                break;
            case R.id.area_customer_mobile:
                /**
                 * 修改手机号
                 */
                bundle.putString("mAction", "mobile");
                bundle.putString("provider_user_id", provider_user_id);
                bundle.putString("mData", mobile);
                MyApplication.openActivity(mContext, EditCustomerActivity.class, bundle);
                break;
            case R.id.area_consume_records:
                /**
                 * 消费记录
                 */
                bundle.putString("provider_user_id", provider_user_id);
                MyApplication.openActivity(mContext, OrderListActivity.class, bundle);
                break;
            case R.id.area_vipcard:
                /**
                 * 会员卡
                 */
                bundle.putString("provider_user_id", provider_user_id);
                MyApplication.openActivity(mContext, VipcardListActivity.class, bundle);
                break;
            case R.id.area_car_list:
                /**
                 * 添加车辆
                 */
                bundle.putString("provider_user_id", provider_user_id);
                MyApplication.openActivity(mContext, CarDetailsActivity.class, bundle);
                break;

        }
    }



    /**
     * 车辆列表适配器
     */
    class CarMyListAdapter extends QuickAdapter<UserDetalis.CarBean> {

        public CarMyListAdapter(int layoutResId, List<UserDetalis.CarBean> list) {
            super(layoutResId, list);
        }
        @Override
        protected void convert(BaseViewHolder baseViewHolder, final UserDetalis.CarBean userCar) {
            ImageLoaderUtil.display(mContext, (ImageView) baseViewHolder.getView(R.id.img_carpic), userCar.getImage(), R.drawable.ic_car_pre,
                    R.drawable.ic_car_pre);
            baseViewHolder.setText(R.id.tv_usercard_name, userCar.getCar_brand_series());
            baseViewHolder.setText(R.id.tv_usercar_no, userCar.getCar_plate_no());
            //编辑
            baseViewHolder.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("provider_user_id", provider_user_id);
                    bundle.putSerializable("mCar",userCar);
                    MyApplication.openActivity(mContext, CarDetailsActivity.class, bundle);
                }
            });
            //车估值
            baseViewHolder.getView(R.id.tv_twocar_values).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("provider_user_id", provider_user_id);
                    bundle.putSerializable("mCar",userCar);
                    MyApplication.openActivity(mContext, UsedCarValuesActivity.class, bundle);
                }
            });
            //查保险
            baseViewHolder.getView(R.id.tv_baoxian_query).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("provider_user_id", provider_user_id);
                    bundle.putSerializable("mCar",userCar);
                    MyApplication.openActivity(mContext, QueryBaoxianActivity.class,bundle);
                }
            });
            /**
             * 查看行驶证
             */
            baseViewHolder.getView(R.id.tv_cardrving).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!StringUtil.isEmpty(userCar.getLicense_img())) {
                        /**
                         * 初始化行驶证对话框
                         */
                        cardriving(userCar.getLicense_img());
                        popup_qrcode.showAtLocation(findViewById(R.id.lin_user_details), Gravity.CENTER, 0, 0);
                    }else {
                        MyApplication.showToast("该车辆的行驶证不存在");
                    }
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getuserdetail();
    }
}
