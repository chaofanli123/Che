package com.victor.che.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.UserCustomMessage;
import com.victor.che.domain.UsercardNocome;
import com.victor.che.util.AppUtil;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyBottomDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class NocommingUsercardActivity extends BaseActivity {


    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;
    //时间区间列表
    private List<UserCustomMessage> categoryList;
    private int selectedCategoryPos = 0;//选中的类别位置
    private CategoryListAdapter categoryListAdapter;
    private int user_track_id;

    /**
     * list adapter
     */
    private NocomeListAdapter messageListAdapter;
    private List<UsercardNocome> messageArrayList = new ArrayList<UsercardNocome>();
    private PtrHelper<UsercardNocome> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.activity_nocomming_usercard;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("长期未到店用户");
        getEndtimelist();
        messageListAdapter = new NocomeListAdapter(mContext,R.layout.item_usercard_nocome, messageArrayList);
        mPtrHelper = new PtrHelper<>(mPtrFrame, messageListAdapter, messageArrayList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mRecyclerView.setAdapter(messageListAdapter);

        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                    _doGetProducts(pullToRefresh, curpage, pageSize);

            }
        });
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 获取某个区间下的信息列表
     */
    private void _doGetProducts(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("user_track_id",user_track_id);// 区间列表id
        params.put("pageSize",pageSize);
        params.put("start",curpage);
        VictorHttpUtil.doGet(mContext, Define.url_provider_user_track_list_v1, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<UsercardNocome> usercardEnd = JSON.parseArray(element.body, UsercardNocome.class);
                        if (pullToRefresh) {////刷新
                            messageArrayList.clear();//清空数据
                            messageListAdapter.setNewData(messageArrayList);
                            messageListAdapter.notifyDataSetChanged();
                            if (CollectionUtil.isEmpty(usercardEnd)) {  //无数
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            }else {
                                // 有数据
                                messageArrayList.addAll(usercardEnd);
                                messageListAdapter.setNewData(messageArrayList);
                                messageListAdapter.notifyDataSetChanged();
                                if (CollectionUtil.getSize(usercardEnd) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        }else {//加载更多
                            mPtrHelper.processLoadMore(usercardEnd);
                        }
                    }
                });
    }
    @OnClick(R.id.rl_endtime)
    public void onViewClicked() {
        MyBottomDialogFragment myBottomDialog=new MyBottomDialogFragment(categoryListAdapter,new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedCategoryPos) {
                    return;
                }
                selectedCategoryPos = position;
                categoryListAdapter.notifyDataSetChanged();
                if (categoryList.get(selectedCategoryPos).getMax_value()==0) { //最大值为空
                    tvEndtime.setText(categoryList.get(selectedCategoryPos).getMin_value()+"-"+"最大值");
                }else {
                    tvEndtime.setText(categoryList.get(selectedCategoryPos).getMin_value()+"-"+categoryList.get(selectedCategoryPos).getMax_value()+""+categoryList.get(selectedCategoryPos).getUnit()+"");
                }
                user_track_id=categoryList.get(selectedCategoryPos).getUser_track_id();

                mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
                    @Override
                    public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                        _doGetProducts(pullToRefresh, curpage, pageSize);

                    }
                });
                mPtrHelper.autoRefresh(true);

            }
        },3,mContext);
        myBottomDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 获取时间选择器
     */
    private void getEndtimelist() {
        // 获取所有类别
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("type", ConstantValue.USER_CARD_ON_COMMING);//获取区间的类型 1-会员卡到期区间 2-会员卡余额不足区间 3-会员卡余次不足区间 4-会员长期未到店区间 5-用户消费区间

        VictorHttpUtil.doGet(mContext, Define.url_provider_user_track_section_v1, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = JSON.parseArray(element.body, UserCustomMessage.class);
                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("数据区间列表为空，请点击下拉按钮自定义添加");
                            return;
                        }
                        if (categoryList.get(selectedCategoryPos).getMax_value()==0) { //最大值为空
                            tvEndtime.setText(categoryList.get(selectedCategoryPos).getMin_value()+"-"+"最大值");
                        }else {
                            tvEndtime.setText(categoryList.get(selectedCategoryPos).getMin_value()+"-"+categoryList.get(selectedCategoryPos).getMax_value()+""+categoryList.get(selectedCategoryPos).getUnit()+"");
                        }
                        user_track_id=categoryList.get(selectedCategoryPos).getUser_track_id();
                        categoryListAdapter = new CategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);
                        // 获取对应的商品列表联动
                        // _doGetProducts(categoryList.get(0).goods_category_id);
                        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
                            @Override
                            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                                _doGetProducts(pullToRefresh, curpage, pageSize);

                            }
                        });
                    }
                });
    }

    /**
     * 产品列表适配器
     */
    private class CategoryListAdapter extends VictorBaseListAdapter<UserCustomMessage> {

        private Context mContext;

        public CategoryListAdapter(Context context, int layoutResId, List<UserCustomMessage> mList) {
            super(context, layoutResId, mList);
            this.mContext = context;
        }

        @Override
        public void bindView(int position, View view, UserCustomMessage entity) {
            TextView textView = (TextView) view;
            if (entity.getMax_value()==0) { //最大值为空
                textView.setText(entity.getMin_value()+"-"+"最大值");
            }else {
                textView.setText(entity.getMin_value()+"-"+entity.getMax_value()+""+entity.getUnit()+"");
            }
            textView.setTextColor(getResources().getColor(selectedCategoryPos == position ? R.color.theme_color : R.color.black_text));
        }
    }

    /**
     * 自定义过后
     */
    @Subscribe
    public void onSelectzidingyi(List<UserCustomMessage> event) {
        if (event == null) {
            return;
        }
        getEndtimelist();
        mPtrHelper.autoRefresh(true);

    }
    public class NocomeListAdapter extends QuickAdapter<UsercardNocome> {
        private Context context;
        public NocomeListAdapter(Context context,int layoutResId, List<UsercardNocome> data) {
            super(layoutResId, data);
            this.context=context;
        }
        @Override
        protected void convert(BaseViewHolder baseViewHolder, final UsercardNocome usercardEnd) {
            baseViewHolder.setText(R.id.tv_name,usercardEnd.getUser_name());
            baseViewHolder.setText(R.id.tv_nocome_time,"未到店"+usercardEnd.getInactived_day()+"天");
            baseViewHolder.setText(R.id.tv_car_plate_no,usercardEnd.getCar_plate_no());

            baseViewHolder.setText(R.id.tv_mobile,usercardEnd.getMobile());
            baseViewHolder.setOnClickListener(R.id.img_call_phone, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (StringUtil.isEmpty(usercardEnd.getMobile())) {
                        MyApplication.showToast("没有用户电话号码");
                    }else {
                        AlertDialogFragment.newInstance(
                                "提示",
                                "是否拨打电话到"+usercardEnd.getMobile(),
                                "确定",
                                "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AppUtil.call(context,usercardEnd.getMobile());
                                    }
                                },
                                null)
                                .show(getSupportFragmentManager(), getClass().getSimpleName());
                    }

                }
            });
        }
    }
}
