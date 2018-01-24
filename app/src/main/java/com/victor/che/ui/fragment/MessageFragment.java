package com.victor.che.ui.fragment;

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
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.RefreshLoadmoreCallbackListener;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.Message;
import com.victor.che.domain.ShopsCoupon;
import com.victor.che.ui.my.PublichaddActivity;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
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
    private List<Message.PageBean.ListBean> mList = new ArrayList<>();
    private CouponAdapter mAdapter;

    private PtrHelper<Message.PageBean.ListBean> mPtrHelper;
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
        mAdapter = new CouponAdapter(R.layout.item_message, mList);  //
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
    }
    /**
     * 请求执法数据
     * @param pullToRefresh
     * @param curpage
     * @param pageSize
     */
    private void _reqData(final boolean pullToRefresh, final int curpage, final int pageSize) {
        // 获取订单列表
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("pageNo",curpage/pageSize+1);
        params.put("pageSize", pageSize);

//        params.put("begin", pageSize);
//        params.put("end", pageSize);
        VictorHttpUtil.doGet(mContext, Define.URL_govAquLaw_list+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, true, "加载中...",
                new RefreshLoadmoreCallbackListener<Element>(mPtrHelper) {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Message message = JSON.parseObject(element.body, Message.class);
                        List<Message.PageBean.ListBean> temp = message.getPage().getList();
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
        MyApplication.openActivity(mContext, PublichaddActivity.class);
    }

    /**
     * 订单列表适配器
     */
    private class CouponAdapter extends QuickAdapter<Message.PageBean.ListBean> {

        public CouponAdapter(int layoutResId, List<Message.PageBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, final Message.PageBean.ListBean shopsCoupon) {
            holder.setText(R.id.tv_title_name, "单位名称："+shopsCoupon.getLawName());
            holder.setText(R.id.tv_lawQual_message,"质量管理制度:"+shopsCoupon.getLawQual());
            holder.setText(R.id.tv_lawSta,"接受监管情况:"+shopsCoupon.getLawSta());
            holder.setText(R.id.tv_coupon_message,"养殖证或苗种生产许可证:"+shopsCoupon.getLawAqu());
            holder.setText(R.id.tv_lawSta_message,"处理情况:"+shopsCoupon.getLawTrea());
            TextView tv_coupon_time = holder.getView(R.id.tv_coupon_time);//检查时间
            tv_coupon_time.setText(shopsCoupon.getLawTime());
            /**
             * 删除
             */
            holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  showdialog(shopsCoupon,shopsCoupon.getCoupon_id());
                }
            });
            /**
             * 修改
             */
            holder.setOnClickListener(R.id.tv_change, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("type","couponlist");
            //    bundle.putString("position",position+"");
                bundle.putSerializable("shopsCoupon",shopsCoupon);

                }
            });
        }
    }

    /**
     *禁用优惠券 禁用成功以后去掉该item刷新列表 重新获取数据；
     */
    private void  showdialog(final ShopsCoupon shopsCoupon, final int coupon_id){
        AlertDialogFragment.newInstance(
                "提示",
                "是否要删除该执法记录？",
                "是",
                "否",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyParams params = new MyParams();
                        //    params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
                        params.put("coupon_id", coupon_id);//优惠券id
                        params.put("status", 0);//要修改的状态值： 0-禁用 1-启用
                        VictorHttpUtil.doPost(mContext, Define.url_coupon_change_status, params, false, null,
                                new BaseHttpCallbackListener<Element>() {
                                    @Override
                                    public void callbackSuccess(String url, Element element) {
                                        mList.remove(shopsCoupon);
                                        mAdapter.notifyDataSetChanged();
                                        MyApplication.showToast("删除成功");
                                    }
                                });
                    }
                },
                null)
                .show(getFragmentManager(), getClass().getSimpleName());
    }

//   @Subscribe
//    public void onMessageEvent(MessageEvent event) {
//        switch (event.code) {
//            case MessageEvent.ALL_WISH_RELOAD:///*刷新全部愿望列表*/
//            //    Map<String, String> paramMap = (Map<String, String>) event.object;
//                mPtrHelper.autoRefresh(true);
//                break;
//            case MessageEvent.ALL_WISH_REFRESH:///*刷新局部愿望*/
//                if (event.position!=-1){
//                    index=event.position;
//                    //  messageListAdapter.notifyDataSetChanged();
//                }
//                if (index >= 0) {
//                    if (event.object != null) {
//                        Notify shopsCoupon= (Notify) event.object;
//                        messageArrayList.set(index, shopsCoupon);
//                    } else {
//                        messageArrayList.remove(index);
//                    }
//                    index = -1;
//                    messageListAdapter.notifyDataSetChanged();
//                }
//                break;
//        }
//    }
}
