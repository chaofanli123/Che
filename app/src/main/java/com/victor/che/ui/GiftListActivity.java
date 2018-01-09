package com.victor.che.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.victor.che.domain.OrderGift;
import com.victor.che.util.CollectionUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单列表子界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 17:05
 */
public class GiftListActivity extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.common_no_data)
    View common_no_data;

    private GiftListAdapter mAdapter;

    ArrayList<OrderGift> giftList;

    @Override
    public int getContentView() {
        return R.layout.activity_gift_list;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("核销");

        ArrayList<OrderGift> giftList = (ArrayList<OrderGift>) getIntent().getSerializableExtra("giftList");
        if (CollectionUtil.isEmpty(giftList)) {
            MyApplication.showToast("数据为空，请稍后重试");
            return;
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mAdapter = new GiftListAdapter(R.layout.item_gift_use, giftList);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 订单列表适配器
     */
    private class GiftListAdapter extends QuickAdapter<OrderGift> {

        public GiftListAdapter(int layoutResId, List<OrderGift> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final OrderGift item) {
            helper.setText(R.id.tv_product_name, item.goods_name)//名称
                    .setText(R.id.tv_available_num, item.available_num + "次")//可用次数
                    .setText(R.id.tv_time, "赠送时间：" + item.create_time)//赠送时间
            ;


            // 核销
            helper.getView(R.id.tv_operate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _doUseGift(item.order_detail_id, helper.getLayoutPosition());

                    //                    AlertDialogFragment.newInstance(
                    //                            "友情提示",
                    //                            "该用户有赠品可以使用，是否使用？",
                    //                            "确定",
                    //                            "取消",
                    //                            new View.OnClickListener() {
                    //                                @Override
                    //                                public void onClick(View v) {
                    //                                    _doUseGift(item.order_detail_id, helper.getLayoutPosition());
                    //                                }
                    //                            },
                    //                            null)
                    //                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                }
            });

        }
    }

    /**
     * 核销赠品
     *
     * @param order_detail_id
     */
    private void _doUseGift(String order_detail_id, final int position) {
        // 下架服务商服务商品
        MyParams params = new MyParams();
        params.put("order_detail_id", order_detail_id);//订单详情id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        VictorHttpUtil.doPost(mContext, Define.URL_ORDER_CONSUME, params, true, "处理中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                // 刷新界面
                MyApplication.showToast(element.msg);
                //                giftList.remove(position);
                if (CollectionUtil.getSize(giftList) == 1) {
                    // 无数据
                    mRecyclerView.setVisibility(View.GONE);
                    common_no_data.setVisibility(View.VISIBLE);
                }
                finish();
            }
        });
    }
}
