package com.victor.che.ui.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
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
import com.victor.che.ui.my.PublichaddActivity;
import com.victor.che.ui.my.util.StringUtil;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.LinearLayoutManagerWrapper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    Unbinder unbinder;
    @BindView(R.id.ed_tiem_start)
    TextView edTiemStart;
    @BindView(R.id.et_time_end)
    TextView etTimeEnd;
    @BindView(R.id.topbar_right1)
    ImageView topbarRight1;
    @BindView(R.id.btn_notarize)
    Button btnNotarize;
    @BindView(R.id.lin_notarize)
    LinearLayout linNotarize;
    private List<Message.PageBean.ListBean> mList = new ArrayList<>();
    private CouponAdapter mAdapter;

    private PtrHelper<Message.PageBean.ListBean> mPtrHelper;
    private boolean ischeckd = false;
    private String ids = ""; // 列表id
    private int select;
    private List<String> list_id = new ArrayList<>();
    private List<Message.PageBean.ListBean> list;
    private List<String> selectID = new ArrayList<>();
    private String begin = "";//开始时间
    private String end = "";//结束时间

    @Override
    public int getContentView() {
        return R.layout.common_pull_to_refresh_recyclerview;
    }

    @Override
    protected void initView() {
        super.initView();
        linNotarize.setVisibility(View.GONE);
        // 设置标题
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(mContext, LinearLayoutManager.VERTICAL, false));//设置布局管理器
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(20)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mAdapter = new CouponAdapter(R.layout.item_message, mList, ischeckd);  //
        mRecyclerView.setAdapter(mAdapter);
        mPtrHelper = new PtrHelper<>(mPtrFrame, mAdapter, mList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                _reqData(pullToRefresh, curpage, pageSize);
            }
        });
            mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                    select = position;
                    Message.PageBean.ListBean listBean = mList.get(position);
                    listBean.checked = !listBean.checked;
                    if (listBean.checked) {
                        list_id.add(listBean.getId() + "");
                    } else {
                        list_id.remove(listBean.getId() + "");
                    }
                    list = new ArrayList<>();
                    list.add(listBean);
                    mAdapter.notifyDataSetChanged();
                }
            });
        mPtrHelper.autoRefresh(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPtrHelper.autoRefresh(true);
    }

    /**
     * 请求执法数据
     *
     * @param pullToRefresh
     * @param curpage
     * @param pageSize
     */
    private void _reqData(final boolean pullToRefresh, final int curpage, final int pageSize) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("pageNo", curpage / pageSize + 1);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(begin)) {
            params.put("begin", begin);
        }
        if (!StringUtil.isEmpty(end)) {
            params.put("end", end);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_govAquLaw_list + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, true, "加载中...",
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

    @OnClick({R.id.ed_tiem_start, R.id.et_time_end, R.id.img_search, R.id.topbar_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_tiem_start: //开始时间
                showDatePickerDialog(edTiemStart);
                break;
            case R.id.et_time_end://结束时间
                showDatePickerDialog(etTimeEnd);
                break;
            case R.id.img_search://搜索
                begin = edTiemStart.getText().toString().trim();
                end = etTimeEnd.getText().toString().trim();
                mPtrHelper.autoRefresh(true);
                break;
            case R.id.topbar_right1:// 编辑
                View ppw = View.inflate(mContext, R.layout.ppw_index_shortcut, null);
                final PopupWindow popupWindow = new PopupWindow(ppw, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);// 不产生焦点
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(topbarRight1, 0, 0);
                // 新增执法
                ppw.findViewById(R.id.tv_add_product).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到执法界面
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "add");
                        MyApplication.openActivity(mContext, PublichaddActivity.class, bundle);
                        popupWindow.dismiss();
                    }
                });
                // 删除
                ppw.findViewById(R.id.tv_add_customer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ischeckd = true;
                        linNotarize.setVisibility(View.VISIBLE);
                        mAdapter = new CouponAdapter(R.layout.item_message, mList, ischeckd);  //
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();  //
                        popupWindow.dismiss();
                    }
                });
                break;
        }
    }

    @OnClick(R.id.btn_notarize)
    public void onViewClicked() {
        AlertDialogFragment.newInstance(
                "提示",
                "是否要删除",
                "是",
                "否",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upcommit();
                    }
                },
                null)
                .show(getFragmentManager(), getClass().getSimpleName());

    }

    /**
     * 确定删除
     */
    private void upcommit() {
        // 发送登录请求
        if (CollectionUtil.isEmpty(list_id)) {
            MyApplication.showToast("请选择要删除的执法信息");
            return;
        }
        selectID.addAll(list_id);
        for (String s : selectID) {
            ids += s + ",";
        }
        ids = ids.substring(0, ids.length() - 1);

        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);
        params.put("ids", ids);//多个id以，隔开
        VictorHttpUtil.doPost(mContext, Define.URL_govAquLaw_deleteAll + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, true, "删除中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast(element.msg);
                        linNotarize.setVisibility(View.GONE);
                        ischeckd = false;
                        mAdapter = new CouponAdapter(R.layout.item_message, mList, ischeckd);  //
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                     // mPtrHelper.autoRefresh(true);
                    }
                });
    }

    /**
     * 取消删除
     */
    @OnClick(R.id.btn_cancel)
    public void onCancelClicked() {
        linNotarize.setVisibility(View.GONE);
        ischeckd = false;
        mAdapter = new CouponAdapter(R.layout.item_message, mList, ischeckd);  //
        mRecyclerView.setAdapter(mAdapter);
        //initView();
   //  mPtrHelper.autoRefresh(true);
    }

    /**
     * 订单列表适配器
     */
    private class CouponAdapter extends QuickAdapter<Message.PageBean.ListBean> {
        private boolean isdelete;

        public CouponAdapter(int layoutResId, List<Message.PageBean.ListBean> data, boolean isdelete) {
            super(layoutResId, data);
            this.isdelete = isdelete;
        }

        @Override
        protected void convert(BaseViewHolder holder, final Message.PageBean.ListBean shopsCoupon) {
            holder.setText(R.id.tv_title_name, "单位名称：" + shopsCoupon.getLawName());

            if (shopsCoupon.getLawQual() == 0) {
                holder.setText(R.id.tv_lawQual_message, "质量管理制度:有");
            } else {
                holder.setText(R.id.tv_lawQual_message, "质量管理制度:无");
            }

            if (shopsCoupon.getLawSta() == 0) {
                holder.setText(R.id.tv_lawSta, "接受监管情况:接受监管并有监管记录");
            } else {
                holder.setText(R.id.tv_lawSta, "接受监管情况:曾接受监管但无记录");
            }

            if (shopsCoupon.getLawAqu() == 0) {
                holder.setText(R.id.tv_coupon_message, "养殖证或苗种生产许可证:有");
            } else if (shopsCoupon.getLawAqu() == 1) {
                holder.setText(R.id.tv_coupon_message, "养殖证或苗种生产许可证:应该持有但没有");
            } else {
                holder.setText(R.id.tv_coupon_message, "养殖证或苗种生产许可证:不需办理");
            }

            if (shopsCoupon.getLawTrea() == 0) {
                holder.setText(R.id.tv_lawSta_message, "处理情况:合格，没有发现违规行为");
            } else {
                holder.setText(R.id.tv_lawSta_message, "处理情况:不合格项或者需要整改的地方");
            }

            TextView tv_coupon_time = holder.getView(R.id.tv_coupon_time);//检查时间
            tv_coupon_time.setText("检查时间" + shopsCoupon.getLawTime());
            final ImageView select = holder.getView(R.id.img_select);
            if (isdelete) {
                if (shopsCoupon.checked) {//选择状态
                    select.setImageResource(R.drawable.ic_select_compan_click);
                } else {
                    select.setImageResource(R.drawable.ic_select_company_normal);
                }
            } else {
                select.setImageResource(R.drawable.ic_arrow_right);
            }
            LinearLayout item = holder.getView(R.id.rl_item);
            /**
             * 修改 进入详情
             */
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "list");
                    bundle.putSerializable("shopsCoupon", shopsCoupon);
                    MyApplication.openActivity(mContext, PublichaddActivity.class, bundle);
                }
            });

        }
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

    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog(final TextView tv) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = tv.getText().toString().trim();
        if (!com.victor.che.util.StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }
        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        tv.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - _100year)//设置最小时间
                //.setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getFragmentManager(), getClass().getSimpleName());
    }
}
