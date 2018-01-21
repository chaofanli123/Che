package com.victor.che.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.victor.che.adapter.QuickAdapter;

import java.io.Serializable;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 *
 *
 * 下拉刷新和加载更多的工具类（适用于RecyclerView）
 */
public class PtrHelper<T extends Serializable> {

    public static final int START_INDEX = 0;//分页数据开始的下标

    private PtrFrameLayout mPtrFrame;

    protected int curpage = START_INDEX;// 当前页码

    private int pageSize = 10;// 每个页面的数据数量

    protected QuickAdapter<T> mAdapter;// 适配器

    protected RecyclerView.Adapter mAdapter_my;
    private RecyclerView mRecyclerView;

    private OnRequestDataListener onRequestDataListener;

    private List<T> mData;

    public PtrHelper(PtrFrameLayout mPtrFrame) {
        this(mPtrFrame, null, null);
    }

    public PtrHelper(PtrFrameLayout mPtrFrame, QuickAdapter<T> mAdapter, List<T> mData) {
        this.mPtrFrame = mPtrFrame;
        mPtrFrame.setEnabledNextPtrAtOnce(true);//可以连续下拉刷新
        this.mAdapter = mAdapter;
        this.mData = mData;
    }

//    public PtrHelper(PtrFrameLayout mPtrFrame, RecyclerView.Adapter mAdapter_my, List<T> mData) {
//        this.mPtrFrame = mPtrFrame;
//        mPtrFrame.setEnabledNextPtrAtOnce(true);//可以连续下拉刷新
//        this.mAdapter_my = mAdapter_my;
//        this.mData = mData;
//    }
    /**
     * 设置适配器
     *
     * @param mAdapter
     */
    public void setAdapter(QuickAdapter<T> mAdapter) {
        this.mAdapter = mAdapter;
    }

    /**
     * 下拉刷新完成
     */
    public void refreshComplete() {
        mPtrFrame.refreshComplete();
    }

    /**
     * 加载更多完成
     */
    public void loadMoreComplete() {
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
        }
    }

    public void setEmptyView(View emptyView) {
        if (mAdapter != null) {
            mAdapter.setEmptyView(emptyView);
        }
    }

    /**
     * 加载更多没有更多数据
     */
    public void loadMoreEnd() {
        if (mAdapter != null) {
            mAdapter.loadMoreEnd();
        }
    }

    /**
     * 允许下拉刷新
     *
     * @param enable
     */
    public void enablePullToRefresh(boolean enable) {
        mPtrFrame.setPullToRefresh(enable);
    }

    /**
     * 允许加载更多
     *
     * @param enable
     */
    public void enableLoadMore(boolean enable, RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(enable);

        }
    }

    /**
     * 自动下拉刷新
     *
     * @param performAnimation 是否执行下拉刷新动画
     */
    public void autoRefresh(boolean performAnimation) {
        if (performAnimation) {
            mPtrFrame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrFrame.autoRefresh(false);
                }
            }, 150);
        } else {
            curpage = START_INDEX;
            onRequestDataListener.onRequestData(true, curpage, pageSize);
        }
    }

    public void setOnRequestDataListener(final OnRequestDataListener onRequestDataListener) {
        this.onRequestDataListener = onRequestDataListener;
        if (onRequestDataListener == null) {
            return;
        }

        /**
         * 下拉刷新
         */
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrRecyclerViewHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                curpage = START_INDEX;
                onRequestDataListener.onRequestData(true, curpage, pageSize);
            }
        });

        /**
         * 上拉加载
         */
        if (mAdapter != null && mAdapter.isLoadMoreEnable() && mRecyclerView != null) {
            mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            curpage += pageSize;
                            onRequestDataListener.onRequestData(false, curpage, pageSize);
                        }
                    }, 200);
                }
            });

        }
    }

    public void processLoadMore(List<T> temp) {
        int newSize = temp == null ? 0 : temp.size();
        if (newSize == 0) {//未加载一条新的数据
            loadMoreEnd();
            return;
        }
        mData.addAll(temp);
        mAdapter.notifyDataSetChanged();
        if (newSize < pageSize) {//翻页的数据不够pageSize，说明没有下一页
            loadMoreEnd();
        } else {//翻页的数据足够pageSize，说明还有下一页
            loadMoreComplete();
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置PageSize
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 请求数据的接口
     */
    public static interface OnRequestDataListener {
        void onRequestData(final boolean pullToRefresh, int curpage, int pageSize);
    }
}
