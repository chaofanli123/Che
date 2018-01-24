package com.victor.che.ui.my;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.bean.ShiPing;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class WoDeSheBeiListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrame)
    PtrClassicFrameLayout PtrHelper;
    protected Context mContext;
    private List<ShiPing.VideoListBean> mList = new ArrayList<>();
    private WoDeSheBeiListAdapter mAdapter;
    private PtrHelper<ShiPing.VideoListBean> mPtrHelper;
    public static final String APPKEY = "AppKey";
    public static final String AccessToekn = "AccessToekn";
    public static final String PLAY_URL = "play_url";
    private String ac;

    @Override
    public int getContentView() {
        return R.layout.activity_wo_de_she_bei_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("监控记录");
        init();
    }
    private void init() {
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);//设置布局管理器
        mAdapter = new WoDeSheBeiListAdapter(R.layout.item_wodeshebei, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPtrHelper = new PtrHelper<>(PtrHelper, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                loadData(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("mobileLogin", true);
        VictorHttpUtil.doPost(WoDeSheBeiListActivity.this, Define.URL_SHIPING + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        ShiPing shiPing = JSON.parseObject(element.body, ShiPing.class);
                        MyApplication.getOpenSDK().setAccessToken(shiPing.getAccessToken());
                        ac = shiPing.getAccessToken();
                        List<ShiPing.VideoListBean> videoList = shiPing.getVideoList();
                        if (pullToRefresh) {////刷新
                            mList.clear();//清空数据
                            if (CollectionUtil.isEmpty(videoList)) {
                                // 无数据
                                View common_no_data = View.inflate(WoDeSheBeiListActivity.this, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                mList.addAll(videoList);
                                mAdapter.setNewData(mList);
                                mAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(mList) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(mList);
                        }

                    }
                });

    }

    /**
     * 消息列表适配器
     */
    private class WoDeSheBeiListAdapter extends QuickAdapter<ShiPing.VideoListBean> {

        public WoDeSheBeiListAdapter(int layoutResId, List<ShiPing.VideoListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final ShiPing.VideoListBean item) {
            helper.setText(R.id.tv_video_jj, item.getFirmId().getFirmName());//时间
                helper.getView(R.id.ll_shiping).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, Playctivity.class);
                        intent.putExtra(APPKEY, "566fb0a1d274443f8d32d74212c570e7");
                        intent.putExtra(AccessToekn, ac);
                        intent.putExtra(PLAY_URL, item.getEzopen());
                        startActivity(intent);
                    }
                });
        }
    }
}
