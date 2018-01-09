package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.GlobalParams;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.RecyclerViewItemChangedEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 会员卡管理界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 13:42
 */
public class VipcardMgrActivity extends BaseActivity {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_prov_vipcard_type)
    TextView tv_prov_vipcard_type;

    @BindView(R.id.tv_total_num)
    TextView tv_total_num;

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;

    private CustomerVipcardListAdapter mAdapter;
    private List<Vipcard> mList = new ArrayList<>();

    private List<Vipcard> provVipcardList = new ArrayList<>();// 服务商会员卡列表

    private ProvVipcardListAdapter provVipcardAdapter;// 服务商会员卡适配器

    private PtrHelper<Vipcard> mPtrHelper;

    double amount;//总储值金额
    private int mSelectedPos = 0;//默认选中的会员卡位置（会增加“全部”这个分类）

    @Override
    public int getContentView() {
        return R.layout.activity_vipcard_mgr;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("会员卡");
        mSelectedPos = getIntent().getIntExtra("mSelectedPos", 0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mAdapter = new CustomerVipcardListAdapter(R.layout.item_vipcard_mgr, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });

        /***增加“全部”这个分类***/
        Vipcard vipcard = new Vipcard();
        vipcard.name = "全部";
        vipcard.provider_card_id = "0";
        vipcard.count = 0;
        provVipcardList.add(vipcard);
        mSelectedPos++;


        // 增加所有其他卡
        provVipcardList.addAll(GlobalParams.gProvVipcardList);

        provVipcardAdapter = new ProvVipcardListAdapter(mContext, R.layout.item_bottom_dialog, provVipcardList);

        provVipcardAdapter.setData(provVipcardList);
        provVipcardAdapter.notifyDataSetChanged();

        // 显示默认的卡
        tv_prov_vipcard_type.setText(String.format("%s(%d张)", provVipcardList.get(mSelectedPos).name, provVipcardList.get(mSelectedPos).count));

        mPtrHelper.autoRefresh(true);
    }

    private void _reqData(final boolean pullToRefresh, int curpage, final int pageSize) {

        String provider_card_id = provVipcardList.get(mSelectedPos).provider_card_id;// 0代表全部
        if (!CollectionUtil.isEmpty(provVipcardList)) {
            provider_card_id = provVipcardList.get(mSelectedPos).provider_card_id;
        }

        // 获取用户会员卡列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("provider_card_id", provider_card_id);// 卡id(全部为0)
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_CARD_STATISTIC, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {

                        // 会员卡数
                        tv_total_num.setText(element.options.getString("count") + "次");
                        // 储值金额

                        amount = element.options.getDoubleValue("amount");
                        tv_total_amount.setText(MathUtil.getFinanceValue(amount) + "元");

                        List<Vipcard> temp = JSON.parseArray(element.data, Vipcard.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                mList.addAll(temp);
                                mAdapter.setNewData(mList);
                                mAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(temp) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {// 加载更多
                            mPtrHelper.processLoadMore(temp);
                        }
                    }

                });
    }

    /**
     * 展示所有卡类型
     */
    @OnClick(R.id.tv_prov_vipcard_type)
    void showVipcardTypeDialog() {
        BottomDialogFragment.newInstance(provVipcardAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPos = position;
                provVipcardAdapter.notifyDataSetChanged();

                tv_prov_vipcard_type.setText(String.format("%s(%d张)", provVipcardList.get(position).name, provVipcardList.get(position).count));
                // 重新获取用户会员卡列表
                mPtrHelper.autoRefresh(false);
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 去搜索界面
     */
    @OnClick(R.id.topbar_right)
    void gotoSearch() {
        MyApplication.openActivity(mContext, SearchActivity.class);
    }

    /**
     * 用户会员卡发生修改后
     *
     * @param event
     */
    @Subscribe
    public void onItemChanged(RecyclerViewItemChangedEvent event) {
        if (event != null && mAdapter != null) {
            if (event.obj instanceof Vipcard) {
                Vipcard vipcard = (Vipcard) event.obj;
                mList.set(event.position, vipcard);
                mAdapter.notifyItemChanged(event.position);

                // 总储值金额发生变化
                tv_total_amount.setText(MathUtil.add(amount, vipcard.recharge_eb) + "元");
            }
        }
    }

    class ProvVipcardListAdapter extends VictorBaseListAdapter<Vipcard> {
        public ProvVipcardListAdapter(Context context, int layoutResId, List<Vipcard> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, Vipcard entity) {
            TextView textView = (TextView) view;
            textView.setText(String.format("%s(%d张)", entity.name, entity.count));
            textView.setTextColor(getResources().getColor(mSelectedPos == position ? R.color.theme_color : R.color.black_text));
        }
    }

    /**
     * 客户会员卡列表适配器
     */
    private class CustomerVipcardListAdapter extends QuickAdapter<Vipcard> {

        public CustomerVipcardListAdapter(int layoutResId, List<Vipcard> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final Vipcard item) {
            helper.setText(R.id.tv_vipcard_id, item.card_no)//会员卡编号
                    .setText(R.id.tv_vipcard_name, item.name)//会员卡名称
                    .setText(R.id.label, item.getCardTypeLabel())// 余额或余次
                    .setText(R.id.tv_vipcard_num, item.getRemainValue())//会员卡剩余次数或余额
                    .setText(R.id.tv_customer_mobile, item.mobile)//手机号
                    .setText(R.id.tv_end_time, item.getEndTime())//有效期
            ;

            // 编辑
            helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("mPosition", helper.getLayoutPosition());
                    bundle.putSerializable("mVipcard", item);
                    MyApplication.openActivity(mContext, EditVipcardActivity.class, bundle);
                }
            });

            // 充值
            TextView tv_recharge = helper.getView(R.id.tv_recharge);
            if (item.card_category_id == 1 || item.card_category_id == 2) {//次卡或储值卡
                // 充值
                tv_recharge.setEnabled(true);
                tv_recharge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("mPosition", helper.getLayoutPosition());
                        bundle.putSerializable("mVipcard", item);
                        MyApplication.openActivity(mContext, RechargeVipcardActivity.class, bundle);
                    }
                });
            } else {
                tv_recharge.setEnabled(false);
            }
        }
    }
}
