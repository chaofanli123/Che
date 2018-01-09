package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Coupon;
import com.victor.che.ui.fragment.CouponUsableFragment;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.MyRecyclerView;
import com.victor.che.widget.TipDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 收款结果界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/30 0030 9:19
 */
public class ReceiptResultActivity extends BaseActivity {

    @BindView(R.id.area_perfect_user_info)
    View area_perfect_user_info;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @BindView(R.id.btn_goto_receipt)
    TextView btn_goto_receipt;
    @BindView(R.id.mRecyclerView)
    MyRecyclerView recycler;
    @BindView(R.id.lin_coupon)
    LinearLayout linCoupon;
//    @BindView(R.id.mPtrFrame)
//    PtrClassicFrameLayout pcflUserCar;


    private CouponUsableListAdapter messageListAdapter;
    private List<Coupon> messageArrayList = new ArrayList<Coupon>();
    private List<Coupon> list ;
    private PtrHelper<Coupon> mPtrHelper;

    private int select;

    @Override
    public int getContentView() {
        return R.layout.activity_receipt_result;
    }

    private String provider_user_id;//用户id
    private int mActiveVipcard;//1-开卡，2-自定义开卡 3 充值

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("收款成功");
        provider_user_id = getIntent().getStringExtra("provider_user_id");// 用户id
        boolean isCompleted = getIntent().getBooleanExtra("isCompleted", false);// 信息是否完善
        mActiveVipcard = getIntent().getIntExtra("mActiveVipcard", 0);
      //  linCoupon.setVisibility(View.VISIBLE);

        /**
         * 初始化优惠券列表
          */
        initCoupon();
        // area_perfect_user_info.setVisibility(isCompleted ? View.GONE : View.VISIBLE);// 是否显示“完善信息”的按钮
        if (isCompleted || StringUtil.isEmpty(provider_user_id)) {
            area_perfect_user_info.setVisibility(View.GONE);
        } else {
            area_perfect_user_info.setVisibility(View.VISIBLE);
        }
        if (mActiveVipcard == 1 || mActiveVipcard == 2) {
           // linCoupon.setVisibility(View.GONE);
            tv_msg.setText("开卡成功");
            ViewUtil.setDrawableTop(mContext, tv_msg, R.drawable.ic_active_vipcard_success);
            btn_goto_receipt.setText("继续开卡");
        } else if (mActiveVipcard == 3) {
         //   linCoupon.setVisibility(View.GONE);
            tv_msg.setText("充值成功");
            ViewUtil.setDrawableTop(mContext, tv_msg, R.drawable.ic_active_vipcard_success);
            btn_goto_receipt.setText("继续充值");
        }
    }

    private void initCoupon(){
//        mPtrHelper = new PtrHelper<>(pcflUserCar, messageListAdapter, messageArrayList);
//        mPtrHelper.enableLoadMore(true, recycler);//允许加载更多
        recycler.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器

        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        loadData();
//        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
//            @Override
//            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
//
//            }
//        });
//        mPtrHelper.autoRefresh(false);

    }

    /**
     * 完善用户信息
     */
    @OnClick(R.id.btn_perfect)
    void gotoPerfect() {

        Bundle bundle = new Bundle();
        bundle.putString("provider_user_id", provider_user_id);//用户id
        MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
        finish();
    }

    /**
     * 继续收款
     */
    @OnClick(R.id.btn_goto_receipt)
    void gotoReceipt() {
        EventBus.getDefault().post(ReceiptQrcodeActivity.class);//关闭收款码界面
        if (mActiveVipcard == 1) {// 开卡操作
            MyApplication.openActivity(mContext, ActiveVipcardActivity.class);//关闭开卡界面
        } else if (mActiveVipcard == 2) {//自定义开卡
            MyApplication.openActivity(mContext, CustomActiveVipcardActivity.class);// 关闭自定义开卡界面
        } else if (mActiveVipcard == 3) {
            MyApplication.openActivity(mContext, RechargeVipcardActivity.class);// 关闭自定义开卡界面
        } else {// 收款操作
//            EventBus.getDefault().post(ConstantValue.Event.DELETE_SHOPPING_CAR);
            MyApplication.openActivity(mContext, ReceiptActivity.class);// 关闭收款界面
        }
        finish();
    }

    /**
     * 回到首页
     */
    @OnClick(R.id.btn_goto_index)
    void gotoIndex() {

        EventBus.getDefault().post(ReceiptQrcodeActivity.class);

        MyApplication.openActivity(mContext, TabBottomActivity.class);

        finish();
    }
    /**
     * 优惠券列表适配器
     */
    private class CouponUsableListAdapter extends QuickAdapter<Coupon> {

        public CouponUsableListAdapter(int layoutResId, List<Coupon> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final Coupon coupon) {
            holder.setText(R.id.tv_coupon_price, coupon.getMoney());
            holder.setText(R.id.tv_coupon_message, coupon.getName());
            TextView money_off = holder.getView(R.id.tv_money_off);
            if ("0.00".equals(coupon.getFull_money())) { //不限制
                money_off.setVisibility(View.GONE);
            } else {
                money_off.setVisibility(View.VISIBLE);
                money_off.setText("满" + coupon.getFull_money() + "可用");
            }

            TextView tv_coupon_time = holder.getView(R.id.tv_coupon_time);//时间截止
            if (StringUtil.isEmpty(coupon.getAvailable_end_time())) { //不限制时间
                tv_coupon_time.setText("不限期限");
            } else {
                tv_coupon_time.setText("限" + coupon.getAvailable_start_time() + "至" + coupon.getAvailable_end_time() + "使用");
            }
            RelativeLayout coupon_xinagxi = holder.getView(R.id.rl_coupon_xiangxi);
            coupon_xinagxi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show(coupon.getDescription());
                }
            });
            final ImageView select = holder.getView(R.id.img_select);
            select.setVisibility(View.GONE);

            final TextView gave = holder.getView(R.id.img_coupon_gave);
            gave.setVisibility(View.VISIBLE);
            if (!coupon.checked) {//选择状态
                gave.setText("赠送");
                gave.setBackgroundResource(R.drawable.sl_common_theme_btn_corner);
                gave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gaveCoupon(coupon.coupon_id+""); //参数，优惠券id
                        coupon.checked = !coupon.checked;
                        messageListAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                gave.setText("已赠送");
                gave.setBackgroundResource(R.drawable.sl_select_gray_btn_corner);
                gave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyApplication.showToast("已赠送优惠券不能重复赠送");
                    }
                });
            }

        }
    }
    private void show(String msg) {
        TipDialogFragment.newInstance(
                "详细信息",
                msg
        )
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 获取优惠券
     *
     * @param
     */
    private void loadData() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("provider_user_id", provider_user_id);
        VictorHttpUtil.doGet(mContext, Define.url_coupon_list_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        messageArrayList = JSON.parseArray(element.data, Coupon.class);// 会员卡列表
                        if (CollectionUtil.isEmpty(messageArrayList)) {
                            MyApplication.showToast("优惠券列表为空");
                            return;
                        }
                        messageListAdapter = new CouponUsableListAdapter(R.layout.item_coupon_usable, messageArrayList);
                        recycler.setAdapter(messageListAdapter);
                        }
                });

    }
    /**
     * 赠送优惠券
     *
     * @param
     */
    private void gaveCoupon(String coupon_id) {
        MyParams params = new MyParams();
        params.put("coupon_id",coupon_id);//优惠券id
        params.put("provider_user_id", provider_user_id);
        VictorHttpUtil.doPost(mContext, Define.url_coupon_grant_record_donate_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast(element.msg);
                        }
                });

    }


}
