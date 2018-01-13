package com.victor.che.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Message;
import com.victor.che.ui.my.WoDeSheBeiListActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 执法界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class MessageFragment extends BaseFragment {

    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.topbar_right)
    ImageView topbarRight;
    Unbinder unbinder;

    private List<Message> mList = new ArrayList<>();
    private MessageListAdapter mAdapter;

    private PtrHelper<Message> mPtrHelper;

    @Override
    public int getContentView() {
        return R.layout.common_pull_to_refresh_recyclerview;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        ((TextView) findViewById(R.id.tv_topbar_title)).setText("执法");
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        topbarRight.setImageResource(R.drawable.sl_topbar_more);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mAdapter = new MessageListAdapter(R.layout.item_message, mList);
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
        startActivity(new Intent(getActivity(), WoDeSheBeiListActivity.class));
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
//        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
//        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id(商户app必传,用户自己获取时不传)
        params.put("start", curpage);//列表记录开始位置
        params.put("pageSize", pageSize);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_NOTIFY_LIST, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<Message> temp = JSON.parseArray(element.body, Message.class);
                        if (pullToRefresh) {// 下拉刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(temp)) {
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                ImageView iv_no_data = (ImageView) common_no_data.findViewById(R.id.iv_no_data);
                                iv_no_data.setImageResource(R.drawable.ic_empty_message);
                                TextView tv_tip = (TextView) common_no_data.findViewById(R.id.tv_tip);
                                tv_tip.setText("暂未收到消息！");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 执法
     */
    @OnClick(R.id.topbar_right)
    public void onaddClicked() {
        //跳转到执法界面

    }

    /**
     * 消息列表适配器
     */
    private class MessageListAdapter extends QuickAdapter<Message> {

        public MessageListAdapter(int layoutResId, List<Message> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Message item) {
            helper.setText(R.id.tv_time, item.create_time)//时间
                    //                    .setImageResource(R.id.iv_order_type, item.getOrderCategoryIcon())//订单类型图标
                    .setText(R.id.tv_title, item.title)//标题
                    .setText(R.id.tv_content, item.content);//内容
        }
    }
}
