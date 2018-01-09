package com.victor.che.ui;


import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.victor.che.domain.UserCustomMessage;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class CustomEndtimeActivity extends BaseActivity {
    @BindView(R.id.ed_tiem_start)
    EditText edTiemStart;
    @BindView(R.id.et_time_end)
    EditText etTimeEnd;
    @BindView(R.id.tv_add_customer)
    TextView tvAddCustomer;
    @BindView(R.id.recycler)
    MyRecyclerView recycler;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout mPtrFrame;

    //时间区间列表
    private TimeListAdapter timeListAdapter;
    private List<UserCustomMessage> messageArrayList=new ArrayList<>();
    private PtrHelper<UserCustomMessage> mPtrHelper;
    private String minnum,maxnum,max;

    @Override
    public int getContentView() {
        return R.layout.activity_custom_endtime;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("自定义会员卡到期天数");
        recycler.setVisibility(View.VISIBLE);
        timeListAdapter = new TimeListAdapter(R.layout.item_custom_endtime_list, messageArrayList);
        mPtrHelper = new PtrHelper<>(mPtrFrame, timeListAdapter, messageArrayList);
        mPtrHelper.enableLoadMore(true, recycler);//允许加载更多
        recycler.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        recycler.setAdapter(timeListAdapter);
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                getEndtimelist(pullToRefresh, curpage, pageSize);
            }
        });
        mPtrHelper.autoRefresh(true);
    }

    @OnClick({R.id.tv_add_customer})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_add_customer:

                if (TextUtils.isEmpty(edTiemStart.getText().toString().trim())) {
                    minnum="0";
                }else {
                    minnum = edTiemStart.getText().toString().trim();

                }
                if (TextUtils.isEmpty(etTimeEnd.getText().toString().trim())) {
                    maxnum="0";
                }else {
                    maxnum = etTimeEnd.getText().toString().trim();
                    max = etTimeEnd.getText().toString().trim();

                }
                if (TextUtils.isEmpty(edTiemStart.getText().toString().trim())&&TextUtils.isEmpty(etTimeEnd.getText().toString().trim())) {
                    MyApplication.showToast("时间区间不能都为空");
                    return;
                }else if (!TextUtils.isEmpty(edTiemStart.getText().toString().trim())&&!TextUtils.isEmpty(etTimeEnd.getText().toString().trim())) {
                    if (Integer.parseInt(edTiemStart.getText().toString().trim()) >Integer.parseInt(etTimeEnd.getText().toString().trim())) {
                        MyApplication.showToast("请输入正确的时间区间");
                        return;
                    }
                }
                _doEndCustomer();
                break;
        }
    }
    /**
     * 获取时间选择器
     */
    private void getEndtimelist(final boolean pullToRefresh, int curpage, final int pageSize) {
        // 获取所有类别
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("type", ConstantValue.USER_CARD_END_TIME);//获取区间的类型 1-会员卡到期区间 2-会员卡余额不足区间 3-会员卡余次不足区间 4-会员长期未到店区间 5-用户消费区间

        VictorHttpUtil.doGet(mContext, Define.url_provider_user_track_section_v1, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        List<UserCustomMessage> userCustomMessages = JSON.parseArray(element.data, UserCustomMessage.class);
                        if (pullToRefresh) {////刷新
                            messageArrayList.clear();//清空数据
                            if (CollectionUtil.isEmpty(userCustomMessages)) {  //无数
                                // 无数据
//                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
//                                mPtrHelper.setEmptyView(common_no_data);
                                recycler.setVisibility(View.GONE);
                            }else {
                                // 有数据
                                recycler.setVisibility(View.VISIBLE);
                                messageArrayList.addAll(userCustomMessages);
                                timeListAdapter.setNewData(messageArrayList);
                                timeListAdapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(userCustomMessages) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        }else {//加载更多
                            mPtrHelper.processLoadMore(userCustomMessages);
                        }

                    }
                });
    }
    /**
     * 时间列表列表适配器
     */
    private class TimeListAdapter extends QuickAdapter<UserCustomMessage> {

        public TimeListAdapter(int layoutResId, List<UserCustomMessage> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder holder, final UserCustomMessage userCustomMessage) {
            holder.setText(R.id.tv_time_start,userCustomMessage.getMin_value()+userCustomMessage.getUnit());
            if (userCustomMessage.getMax_value()==0) { //最大值为空
                holder.setText(R.id.tv_time_end,"最大值");
            }else {
                holder.setText(R.id.tv_time_end,userCustomMessage.getMax_value()+userCustomMessage.getUnit());
            }
            holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialogFragment.newInstance(
                            "提示",
                            "您确定要删除该时间区间吗？",
                            "确定",
                            "取消",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    _doDeleteCustomer(userCustomMessage);
                                }
                            },
                            null)
                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                }

            });
    }

    }

    /**
     * 删除区间信息
     */
    private void _doDeleteCustomer(final UserCustomMessage userCustomMessage) {
        MyParams params = new MyParams();
        params.put("user_track_id", userCustomMessage.getUser_track_id());//区间id
        VictorHttpUtil.doPost(mContext, Define.url_provider_user_track_del_section_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("删除成功");
                        messageArrayList.remove(userCustomMessage);
                        timeListAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(messageArrayList);
                    }
                });
    }
    /**
     *添加区间信息
     */
    private void _doEndCustomer() {
        final UserCustomMessage message =new UserCustomMessage();
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商i的
        params.put("type", ConstantValue.USER_CARD_END_TIME);//数据类型 1-会员卡到期区间 2-会员卡余额不足区间 3-会员卡余次不足区间 4-会员长期未到店区间 5-用户消费区间
        params.put("min_value", minnum);//区间最小值
        params.put("max_value",maxnum);//区间最大值
        VictorHttpUtil.doPost(mContext, Define.url_provider_user_track_add_section_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("添加成功");
                        mPtrHelper.autoRefresh(true);
                        recycler.setVisibility(View.VISIBLE);
                        edTiemStart.setText("");
                        etTimeEnd.setText("");
                        EventBus.getDefault().post(messageArrayList);
                    }
                });
    }

}
