package com.victor.che.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.adapter.EndListAdapter;
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
import com.victor.che.domain.UsercardEnd;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 会员卡到期界面
 */
public class EndtimeUsercardrActivity extends BaseActivity {
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
    /**
     * list adapter
     */
    private EndListAdapter messageListAdapter;
    private List<UsercardEnd> messageArrayList = new ArrayList<UsercardEnd>();
    private PtrHelper<UsercardEnd> mPtrHelper;
    private int user_track_id;
    @Override
    public int getContentView() {
        return R.layout.activity_follow_user;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("会员卡到期用户");
        getEndtimelist();
        messageListAdapter = new EndListAdapter(mContext,R.layout.item_usercard_end, messageArrayList,1,getSupportFragmentManager());
        mPtrHelper = new PtrHelper<>(mPtrFrame, messageListAdapter, messageArrayList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mRecyclerView.setAdapter(messageListAdapter);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
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
     * 获取时间选择器
     */
    private void getEndtimelist() {
        // 获取所有类别
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("type", ConstantValue.USER_CARD_END_TIME);//获取区间的类型 1-会员卡到期区间 2-会员卡余额不足区间 3-会员卡余次不足区间 4-会员长期未到店区间 5-用户消费区间

        VictorHttpUtil.doGet(mContext, Define.url_provider_user_track_section_v1, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = JSON.parseArray(element.body, UserCustomMessage.class);
                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("数据区间列表为空,点击下拉按钮自定义添加");
                            return;
                        }
                        if (categoryList.get(selectedCategoryPos).getMax_value()==0) { //最大值为空
                            tvEndtime.setText(categoryList.get(selectedCategoryPos).getMin_value()+"-"+"最大值");
                        }else {
                            tvEndtime.setText(categoryList.get(selectedCategoryPos).getMin_value()+"-"+categoryList.get(selectedCategoryPos).getMax_value()+""+categoryList.get(selectedCategoryPos).getUnit()+"");
                        }
                        user_track_id=categoryList.get(selectedCategoryPos).getUser_track_id();
                        categoryListAdapter = new CategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);
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
     * 获取某个区间下的信息列表
     */
    private void _doGetProducts(final boolean pullToRefresh, int curpage, final int pageSize ) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("user_track_id",user_track_id);// 区间列表id
        params.put("pageSize",pageSize);
        params.put("start",curpage);
        VictorHttpUtil.doGet(mContext, Define.url_provider_user_track_list_v1, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<UsercardEnd> usercardEnd = JSON.parseArray(element.body, UsercardEnd.class);
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
}
