package com.victor.che.ui;

import android.support.v7.widget.RecyclerView;
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
import com.victor.che.domain.Car;
import com.victor.che.domain.Customer;
import com.victor.che.domain.Vipcard;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.FullyLinearLayoutManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * 会员冲突详情界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 13:57
 */
public class ConflictDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_customer_name)
    TextView tv_customer_name;

    @BindView(R.id.tv_customer_mobile)
    TextView tv_customer_mobile;

    @BindView(R.id.rv_vipcard)
    RecyclerView rv_vipcard;

    @BindView(R.id.rv_car)
    RecyclerView rv_car;

    @BindView(R.id.tv_vipcard_count)
    TextView tv_vipcard_count;

    @BindView(R.id.tv_car_count)
    TextView tv_car_count;

    @Override
    public int getContentView() {
        return R.layout.activity_conflict_details;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("用户详情");

        Customer mCustomer = (Customer) getIntent().getSerializableExtra("mCustomer");

        if (mCustomer == null) {
            MyApplication.showToast("用户编码为空");
            return;
        }

        rv_vipcard.setLayoutManager(new FullyLinearLayoutManager(mContext, FullyLinearLayoutManager.VERTICAL, false));//设置布局管理器
        rv_vipcard.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线

        rv_car.setLayoutManager(new FullyLinearLayoutManager(mContext, FullyLinearLayoutManager.VERTICAL, false));//设置布局管理器
        rv_car.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线


        //姓名
        tv_customer_name.setText(mCustomer.getName());
        //手机号
        tv_customer_mobile.setText(StringUtil.convertNull(mCustomer.mobile));

        // 请求数据
        MyParams params = new MyParams();
        params.put("provider_user_id", mCustomer.provider_user_id);// 用户编号(获取用户列表时返回)
        VictorHttpUtil.doGet(mContext, Define.URL_MERGE_USER_DETAIL, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = JSON.parseObject(element.data);
                        if (jsonobj == null) {
                            MyApplication.showToast("返回数据为空");
                            return;
                        }
                        // 会员卡数量
                        tv_vipcard_count.setText("(" + jsonobj.getString("user_card_count") + "张)");
                        // 车辆数量
                        tv_car_count.setText("(" + jsonobj.getString("user_car_count") + "辆)");

                        List<Vipcard> user_card = JSON.parseArray(jsonobj.getString("user_card"), Vipcard.class);
                        if (CollectionUtil.isNotEmpty(user_card)) {
                            VipcardListAdapter mAdapter = new VipcardListAdapter(R.layout.item_conflict_vipcard, user_card);
                            //                        mAdapter.setNewData(mList);
                            rv_vipcard.setAdapter(mAdapter);
                        }

                        // 车辆数量
                        List<Car> user_car = JSON.parseArray(jsonobj.getString("user_car"), Car.class);
                        if (CollectionUtil.isNotEmpty(user_car)) {
                            CarListAdapter mAdapter = new CarListAdapter(R.layout.item_conflict_car, user_car);
                            //                        mAdapter.setNewData(mList);
                            rv_car.setAdapter(mAdapter);
                        }
                    }
                });
    }

    /**
     * 会员卡列表适配器
     */
    private class VipcardListAdapter extends QuickAdapter<Vipcard> {

        public VipcardListAdapter(int layoutResId, List<Vipcard> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Vipcard item) {
            helper.setText(R.id.tv_card_name, item.name)// 卡的名称
                    .setText(R.id.tv_vipcard_no, item.card_no)// 卡的描述
                    .setText(R.id.tv_service_content, item.getUsedGoodsText())// 可用服务
                    .setText(R.id.tv_end_time, item.getEndTime())
                    .setText(R.id.label_remain_value, item.getCardTypeLabel())//卡类型
                    .setText(R.id.tv_remain_value, item.getRemainValue());//余额
        }
    }

    /**
     * 车辆列表适配器
     */
    private class CarListAdapter extends QuickAdapter<Car> {

        public CarListAdapter(int layoutResId, List<Car> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Car item) {
            helper.setText(R.id.tv_card_name, item.car_brand_series)// 汽车品牌车系
                    .setText(R.id.tv_vipcard_no, item.car_plate_no);//车牌号
        }
    }
}
