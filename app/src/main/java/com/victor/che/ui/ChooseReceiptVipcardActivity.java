package com.victor.che.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.simplelistview.SimpleListView;
import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Vipcard;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.CollectionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择会员卡支付界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/5 0005 15:55
 */
public class ChooseReceiptVipcardActivity extends BaseActivity {

    @BindView(R.id.validListView)
    SimpleListView validListView;//可用卡列表

    @BindView(R.id.invalidListView)
    SimpleListView invalidListView;//不可用卡列表

    @BindView(R.id.common_no_data)
    View common_no_data;//无数据

    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;

    @BindView(R.id.tv_tip)
    TextView tv_tip;

    @BindView(R.id.mScrollView)
    View mScrollView;

    @Override
    public int getContentView() {
        return R.layout.activity_choose_receipt_vipcard;
    }

    @Override
    public void initView() {
        super.initView();
        // 设置标题
        setTitle("选择会员卡");
        final List<Vipcard> mValidVipcards = new ArrayList<>();//可用卡集合
        List<Vipcard> mInvalidVipcards = new ArrayList<>();//不可用卡集合

        List<Vipcard> vipcardList = (List<Vipcard>) getIntent().getSerializableExtra("mVipcardList");//会员卡列表
        String mGoodsId = getIntent().getStringExtra("mGoodsId");// 商品的id
        int mTotalNum = getIntent().getIntExtra("mTotalNum", 1);//需要的总次数
        double mTotalEb = getIntent().getDoubleExtra("mTotalEb", 0);//购买产品所需总eb
        if (CollectionUtil.isEmpty(vipcardList)) {
            common_no_data.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
            iv_no_data.setImageResource(R.drawable.ic_no_vipcard);
            tv_tip.setText("无会员卡！");
            return;
        } else {
            common_no_data.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
        }

        /**筛选会员卡，分为可用卡和不可用卡***/
        if (!CollectionUtil.isEmpty(vipcardList)) {
            for (int i = 0; i < vipcardList.size(); i++) {
                Vipcard vipcard = vipcardList.get(i);
                if (vipcard.is_expire== 0) {//未过期
                    if (CollectionUtil.isEmpty(vipcard.used_goods)
                            || vipcard.used_goods.contains(mGoodsId)) {// 会员卡可用于此服务
                        if (vipcard.num >= mTotalNum
                                || vipcard.money >= mTotalEb
                                || (vipcard.card_category_id == 3 && !vipcard.isExpire())) {//次数足够或eb足够或是年卡(未过期)
                            vipcard.is_valid = true;
                            mValidVipcards.add(vipcard);
                        } else {
                            vipcard.is_valid = false;
                            mInvalidVipcards.add(vipcard);
                        }
                    } else {
                        vipcard.is_valid = false;
                        mInvalidVipcards.add(vipcard);
                    }
                }else if (vipcard.is_expire== 1) {//过期
                    vipcard.is_valid = false;
                    mInvalidVipcards.add(vipcard);
                }
            }
        }
        validListView.setAdapter(new VipCardListAdapter(mContext, R.layout.item_choose_vipcard, mValidVipcards));
        invalidListView.setAdapter(new VipCardListAdapter(mContext, R.layout.item_choose_vipcard, mInvalidVipcards));

        validListView.setOnItemClickListener(new SimpleListView.OnItemClickListener() {
            @Override
            public void onItemClick(Object item, View view, int position) {
                // 单击选中某张卡,回传给支付界面
                EventBus.getDefault().post(mValidVipcards.get(position));

                finish();//关闭这个界面
            }
        });

    }


    /**
     * 会员卡列表适配器
     *
     * @author Victor
     * @email 468034043@qq.com
     * @time 2016年6月14日 上午9:56:12
     */
    private class VipCardListAdapter extends VictorBaseListAdapter<Vipcard> {


        public VipCardListAdapter(Context context, int layoutResId, List<Vipcard> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, Vipcard entity) {
            TextView label_remain_value = AbViewHolder.get(view, R.id.label_remain_value);
            // 卡的名称
            TextView tv_card_name = AbViewHolder.get(view, R.id.tv_card_name);
            tv_card_name.setText(entity.name);

            // 卡号
            TextView tv_cardno = AbViewHolder.get(view, R.id.tv_cardno);
            tv_cardno.setText(entity.card_no);

            // 卡的余额
            TextView tv_remain_value = AbViewHolder.get(view, R.id.tv_remain_value);
            tv_remain_value.setText(entity.getRemainValue());
            label_remain_value.setText(entity.getCardTypeLabel());

            // 有效期
            TextView tv_end_time = AbViewHolder.get(view, R.id.tv_end_time);
            tv_end_time.setText(entity.getEndTime());

            ImageView iv_vipcard_logo = AbViewHolder.get(view, R.id.iv_vipcard_logo);

            TextView label_end_time = AbViewHolder.get(view, R.id.label_end_time);

            // 字体颜色改变
            iv_vipcard_logo.setImageResource(entity.is_valid ? R.drawable.ic_vipcard_valid : R.drawable.ic_vipcard_invalid);
            tv_card_name.setTextColor(getResources().getColor(entity.is_valid ? R.color.black_text : R.color.disabled));
            tv_cardno.setTextColor(getResources().getColor(entity.is_valid ? R.color.black_text : R.color.disabled));
            label_remain_value.setTextColor(getResources().getColor(entity.is_valid ? R.color.c_9a9a9a : R.color.disabled));
            tv_remain_value.setTextColor(getResources().getColor(entity.is_valid ? R.color.black_text : R.color.disabled));
            label_end_time.setTextColor(getResources().getColor(entity.is_valid ? R.color.c_9a9a9a : R.color.disabled));
            tv_end_time.setTextColor(getResources().getColor(entity.is_valid ? R.color.black_text : R.color.disabled));
        }

    }
}
