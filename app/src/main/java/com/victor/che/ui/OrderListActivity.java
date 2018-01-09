package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.phillipcalvin.iconbutton.IconButton;
import com.victor.che.R;
import com.victor.che.adapter.OrderitemListAdapter;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Order;
import com.victor.che.domain.OrderCategory;
import com.victor.che.domain.Worker;
import com.victor.che.event.ScreenStyleResultEvent;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.victor.che.widget.ListDialogFragment;
import com.victor.che.widget.PtrMeiTuanHeader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 订单列表子界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 17:05
 */
public class OrderListActivity extends BaseActivity {

    @BindView(R.id.btn_order_state)
    Button btn_order_state;

    @BindView(R.id.btn_order_type)
    Button btn_order_type;

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_order_worker)
    IconButton btnOrderWorker;
    @BindView(R.id.tv_srceen_result)
    TextView tvSrceenResult;
    @BindView(R.id.rl_srceen_result)
    RelativeLayout rlSrceenResult;

    private List<Order> mList = new ArrayList<>();
    private OrderListAdapter mAdapter;

    private PtrHelper<Order> mPtrHelper;

    private String provider_user_id;

    //    1未支付，2待使用，3待评价，4已完成 6退款
    private String[] ORDER_STATES = {"全部", "未支付", "待使用", "待评价", "已完成"};

    private int selectedOrderStatePos = 0; //订单状态
    private OrderStateListAdapter orderStateListAdapter;

    private int selectedOrderTypePos = 0;//全部师傅
    private List<OrderCategory> orderCategoryList;
    private List<Worker> orderWorkerList;

    private OrderWorkerListAdapter orderdworkerListAdapter;

    private String sale_user_id;//上传的师傅id
    private int pay_method;//上传的支付方式
    private int goods_category_id=0;//服务类别id
    private String order_time;
    private String server_name,order_name;
    private int order_category_id;
    private PtrMeiTuanHeader hedaderView;

    @Override
    public int getContentView() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        super.initView();
        provider_user_id = getIntent().getStringExtra("provider_user_id");
        // 设置标题
        setTitle(StringUtil.isEmpty(provider_user_id) ? "订单" : "消费记录");

        /**
         * 订单状态
         */
        orderStateListAdapter = new OrderStateListAdapter(mContext, R.layout.item_list_dialog, ORDER_STATES);

        /*
        获取职员类型
         */
        _getorderWorker();
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线
        mAdapter = new OrderListAdapter(R.layout.item_order, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
        // 单击进入会员详情界面
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("mOrder", mList.get(position));
                MyApplication.openActivity(mContext, OrderDetailsActivity.class, bundle);
            }
        });

    }

    /**
     * 筛选过后
     */
    @Subscribe
    public void onSelectChexing(ScreenStyleResultEvent event) {
        if (event == null) {
            rlSrceenResult.setVisibility(View.GONE);
            return;
        }
        if (event.Paystyle != null) {
            switch (event.Paystyle.toString()){
                case "微信":
                    pay_method=2;
                    break;
                case "支付宝":
                    pay_method=1;
                    break;
                case "会员卡":
                    pay_method=3;
                    break;
                case "现金":
                    pay_method=6;
                    break;
                default:
                    pay_method=0;
                    break;
            }
        }else {
            pay_method=0;
        }
        if (event.server==null) {
            goods_category_id=0;
            server_name="";
        }else {
            goods_category_id=Integer.parseInt(event.server.goods_category_id);
            server_name=event.server.name;
        }

        if (!TextUtils.isEmpty(event.time)) {
          //  2017-4-27至2017-4-27
            order_time=event.time.replace("至","_");
        }else {
            order_time="";
        }
        if (event.order==null) {
            order_category_id=0;
            order_name="";
        }else {
            order_category_id=event.order.order_category_id;
            order_name=event.order.name;
        }
        rlSrceenResult.setVisibility(View.VISIBLE);
        tvSrceenResult.setText(event.time + "/" + event.Paystyle+ "/" + order_name + "/" + server_name);
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 获取职员列表
     */
    private void _getorderWorker() {
        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        VictorHttpUtil.doGet(mContext, Define.URL_STAFF_USER_LIST, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        /***职工类型增加“全部职员”***/
                        orderWorkerList = new ArrayList<>();
                        Worker orderWorker = new Worker();
                        orderWorker.staff_user_id = "0";
                        orderWorker.user_name = "全部职员";
                        orderWorkerList.add(orderWorker);

                        orderWorkerList.addAll(JSON.parseArray(element.data, Worker.class));
                        if (CollectionUtil.isEmpty(orderWorkerList)) {
                            MyApplication.showToast("职员列表为空");
                            return;
                        }
                       btnOrderWorker.setText(orderWorkerList.get(selectedOrderTypePos).user_name);
                        btnOrderWorker.setText(orderWorkerList.get(selectedOrderTypePos).user_name);
                        btnOrderWorker.requestLayout();// 防止文字和图片覆盖
                        sale_user_id = orderWorkerList.get(selectedOrderTypePos).staff_user_id;
                        orderdworkerListAdapter = new OrderWorkerListAdapter(mContext, R.layout.item_list_dialog, orderWorkerList);

//                        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
//                            @Override
//                            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
//                                _reqData(pullToRefresh, curpage, pageSize);
//                            }
//                        });
                    }
                });
    }

    /**
     * 显示订单状态对话框
     */
    @OnClick(R.id.btn_order_state)
    void showOrderStateDialog() {
        ListDialogFragment.newInstance(orderStateListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderStatePos = position;
                orderStateListAdapter.notifyDataSetChanged();
                btn_order_state.setText(ORDER_STATES[position]);
                btn_order_state.requestLayout();// 防止文字和图片覆盖
                mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
                    @Override
                    public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                        _reqData(pullToRefresh, curpage, pageSize);
                    }
                });
                // 刷新列表
                mPtrHelper.autoRefresh(true);
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 点击更多筛选跳转到筛选界面
     */
    @OnClick(R.id.btn_order_type)
    void showOrderTypeDialog() {
        MyApplication.openActivity(mContext, ScreeningActivity.class);
    }

    /**
     * 显示职工列表对话框
     */
    @OnClick(R.id.btn_order_worker)
    void showOrderWorkerDialog() {
        ListDialogFragment.newInstance(orderdworkerListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderTypePos = position;
                btnOrderWorker.setText(orderWorkerList.get(selectedOrderTypePos).user_name);
                sale_user_id = orderWorkerList.get(selectedOrderTypePos).staff_user_id;
                // 刷新列表
                mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
                    @Override
                    public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                        _reqData(pullToRefresh, curpage, pageSize);
                    }
                });
                mPtrHelper.autoRefresh(true);
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());

    }

    /**
     * 获取订单状态
     * //  0全部  1未支付，2待使用，3待评价，4已完成 6退款
     *
     * @return
     */
    private int _getOrderStatus() {
        return selectedOrderStatePos == 5 ? 6 : selectedOrderStatePos;
    }

    /**
     * 请求数据
     *
     * @param pullToRefresh
     * @param curpage
     * @param pageSize
     */
    private void _reqData(final boolean pullToRefresh, final int curpage, final int pageSize) {
        // 获取订单列表
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数
        params.put("order_category_id", order_category_id);// 订单类型，默认为0（代表全部）
        params.put("order_status", _getOrderStatus());// 订单状态，默认为0（代表全部）
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id(商户app必传,用户自己获取时不传)
        params.put("sale_user_id",sale_user_id);// 师傅id

        params.put("pay_method", pay_method);// 否	支付方式, 1-支付宝 2-微信 3-会员卡 4-现金 (默认为0,代表全部)
        params.put("goods_category_id", goods_category_id);//否	服务类别id,默认为0（代表全部）
        params.put("order_time", order_time);// 否	订单时间区间,格式： 开始时间_截至时间,默认为''(代表全部)


        if (!StringUtil.isEmpty(provider_user_id)) {
            params.put("provider_user_id", provider_user_id);// 用户编号,(获取用户消费记录时必传)
        }
        VictorHttpUtil.doGet(mContext, Define.URL_ORDER_LIST, params, false, null,
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Order> temp = JSON.parseArray(element.data, Order.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            mAdapter.setNewData(mList);
                            mAdapter.notifyDataSetChanged();
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                ImageView nodata_img = (ImageView) common_no_data.findViewById(R.id.iv_no_data);
                                nodata_img.setBackgroundResource(R.drawable.ic_empty_ording);
                                TextView tv_tip = (TextView) common_no_data.findViewById(R.id.tv_tip);
                                tv_tip.setText("暂无订单");
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
     * 点击关闭筛选 也就是重置 刷新列表
     */
    @OnClick(R.id.img_close_srceen)
    public void onViewClicked() {
        rlSrceenResult.setVisibility(View.GONE);
    }

    /**
     * 订单状态适配器
     */
    private class OrderStateListAdapter extends VictorBaseArrayAdapter<String> {

        public OrderStateListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }

        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            ViewUtil.setDrawableRight(mContext, textView, selectedOrderStatePos == position ? R.drawable.ic_checked : R.drawable.ic_unchecked);
        }
    }

    /**
     * 所有职工适配器
     */
    private class OrderWorkerListAdapter extends VictorBaseListAdapter<Worker> {

        public OrderWorkerListAdapter(Context context, int layoutResId, List<Worker> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, Worker entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.user_name);
            ViewUtil.setDrawableRight(mContext, textView, selectedOrderTypePos == position ? R.drawable.ic_checked : R.drawable.ic_unchecked);
        }
    }

    /**
     * 订单列表适配器
     */
    private class OrderListAdapter extends QuickAdapter<Order> {

        public OrderListAdapter(int layoutResId, List<Order> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, Order item) {
            helper.setText(R.id.tv_order_type, item.order_category_name)//订单类型
                    .setImageResource(R.id.iv_order_type, item.getOrderCategoryIcon())//订单类型图标
                    .setText(R.id.tv_order_state, item.getOrderState())//订单状态
                    .setText(R.id.tv_order_time, item.create_time)//订单时间
                    .setText(R.id.tv_pay_type, item.getPayMethod() + "支付")//支付方式
                    .setText(R.id.tv_car_brand_name, item.car_plate_no)//车牌号
                    .setVisible(R.id.tv_car_brand_name, !TextUtils.isEmpty(item.car_plate_no))
                    .setText(R.id.tv_car_user_mobile, item.mobile)//手机号
                    .setVisible(R.id.tv_car_user_mobile, !TextUtils.isEmpty(item.mobile))
                    .setVisible(R.id.tv_pay_type, item.order_status >= 2)
                    .setText(R.id.tv_order_sum, MathUtil.getMoneyText(item.total_actual_price));//订单合计

            TextView view = helper.getView(R.id.tv_order_state);
            if (view.getText().equals("已完成")) {
                view.setTextColor(getResources().getColor(R.color.dark_gray_text));
            }else {
                view.setTextColor(getResources().getColor(R.color.on_sale));
            }

            RecyclerView listview = helper.getView(R.id.listview);
            listview.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
            List<Order.OrderDetailBean> order_detailList = item.getOrder_detail();
            OrderitemListAdapter adapteritem = new OrderitemListAdapter(mContext, order_detailList);
            listview.setAdapter(adapteritem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
}
