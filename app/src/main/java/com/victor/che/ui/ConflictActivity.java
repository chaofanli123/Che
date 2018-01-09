package com.victor.che.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
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
import com.victor.che.domain.Customer;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.victor.che.R.id.tv_pln;

/**
 * 编辑界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/30 0030 9:19
 */
public class ConflictActivity extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.chk_merge_vipcard)
    CheckBox chk_merge_vipcard;

    @BindView(R.id.chk_merge_car)
    CheckBox chk_merge_car;

    private String provider_user_id;

    private List<Customer> mList = new ArrayList<>();
    private CustomerListAdapter mAdapter;

    private int selectedPos = 0;//选中的位置

    @Override
    public int getContentView() {
        return R.layout.activity_conflict;
    }

    @Override
    protected void initView() {
        super.initView();

        setTitle("冲突用户");

        provider_user_id = getIntent().getStringExtra("provider_user_id");

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线

        mAdapter = new CustomerListAdapter(R.layout.item_conflict_customer, mList);
        mRecyclerView.setAdapter(mAdapter);

        // 获取冲突详情
        MyParams params = new MyParams();
        params.put("provider_user_id", provider_user_id);// 用户编号(获取用户列表时返回)
        VictorHttpUtil.doGet(mContext, Define.URL_MERGE_USER_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        mList = JSON.parseArray(element.data, Customer.class);
                        mAdapter.setNewData(mList);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 关闭错误提示
     */
    @OnClick(R.id.iv_close)
    void doCloseTip() {
        findViewById(R.id.area_tips).setVisibility(View.GONE);
    }

    /**
     * 删除
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        String provider_user_id = mList.get(1 - selectedPos).provider_user_id;
        String merged_provider_user_id = mList.get(selectedPos).provider_user_id;
        int is_merge_card = chk_merge_vipcard.isChecked() ? 1 : 0;
        int is_merge_car = chk_merge_car.isChecked() ? 1 : 0;

        // 请求数据
        MyParams params = new MyParams();
        params.put("provider_user_id", provider_user_id);// 	用户id(保留的用户)
        params.put("merged_provider_user_id", merged_provider_user_id);// 被合并的用户id
        params.put("is_merge_card", is_merge_card);// 	是否合并卡
        params.put("is_merge_car", is_merge_car);// 	是否合并车辆
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_MERGE, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("合并成功");

                        finish();//关闭页面
                    }
                });
    }


    /**
     * 用户列表适配器
     */
    private class CustomerListAdapter extends QuickAdapter<Customer> {

        public CustomerListAdapter(int layoutResId, List<Customer> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, Customer item) {
            helper.setText(R.id.tv_title, item.getName())// 姓名
                    .setText(R.id.tv_content, item.mobile)// 手机号
                    .setText(tv_pln, item.car_plate_no)//车牌号
                    .setImageResource(R.id.iv_checked, helper.getLayoutPosition() == selectedPos ? R.drawable.ic_common_checked : R.drawable.ic_common_unchecked);//是否选中

            // 用户头像
            ImageView iv_customer_avatar = helper.getView(R.id.iv_customer_avatar);
            ImageLoaderUtil.display(mContext, iv_customer_avatar, item.avatar_thumb, R.drawable.def_customer_avatar, R.drawable.def_customer_avatar);

            // 点击切换选中
            helper.getView(R.id.iv_checked).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = helper.getLayoutPosition();
                    mAdapter.notifyDataSetChanged();
                }
            });
            iv_customer_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = helper.getLayoutPosition();
                    mAdapter.notifyDataSetChanged();
                }
            });

            // 单击进入冲突用户详情
            helper.getView(R.id.area_car).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mCustomer", mList.get(helper.getLayoutPosition()));
                    MyApplication.openActivity(mContext, ConflictDetailsActivity.class, bundle);
                }
            });
            helper.getView(R.id.tv_pln).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mCustomer", mList.get(helper.getLayoutPosition()));
                    MyApplication.openActivity(mContext, ConflictDetailsActivity.class, bundle);
                }
            });
        }
    }

}


