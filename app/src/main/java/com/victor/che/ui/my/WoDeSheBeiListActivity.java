package com.victor.che.ui.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.victor.che.R;

import butterknife.ButterKnife;

public class WoDeSheBeiListActivity extends AppCompatActivity {

//    @BindView(R.id.iv_back)
//    ImageView ivBack;
//    @BindView(R.id.tv_topbar_title)
//    TextView tvTopbarTitle;
//    @BindView(R.id.topbar)
//    RelativeLayout topbar;
//    @BindView(R.id.mRecyclerView)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.mPtrFrame)
//    PtrlMeiTuanFrameLayout mPtrFrame;
//    protected Context mContext;
//    private List<EZDeviceInfo> mList=new ArrayList<>();
//    private WoDeSheBeiListAdapter mAdapter;
//    private PtrHelperP<EZDeviceInfo> mPtrHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_she_bei_list);
        ButterKnife.bind(this);
//        mContext=this;
//        init();
    }

//    private void init() {
//        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        mRecyclerView.setLayoutManager(layoutManager);//设置布局管理器
//        mAdapter = new WoDeSheBeiListAdapter(R.layout.item_wodeshebei, mList);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mPtrHelper = new PtrHelperP<>(mPtrFrame, mAdapter, mList);
//        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
//
//        mPtrHelper.setOnRequestDataListener(new PtrHelperP.OnRequestDataListener() {
//            @Override
//            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
////                _reqData(pullToRefresh, curpage, pageSize);
//                mList.clear();//清空数据
//                try{
//                    mList = MyApplication.getOpenSDK().getDeviceList(0, 20);
//                    mAdapter.setNewData(mList);
//                    mAdapter.notifyDataSetChanged();
//                    mPtrHelper.refreshComplete();
//                }catch (Exception e){
//                    System.out.println(e.getMessage());
//                }
//
//            }
//        });
//
//        mPtrHelper.autoRefresh(true);
//    }
//
//    /**
//     * 消息列表适配器
//     */
//    private class WoDeSheBeiListAdapter extends QuickAdapter<EZDeviceInfo> {
//
//        public WoDeSheBeiListAdapter(int layoutResId, List<EZDeviceInfo> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, EZDeviceInfo item) {
//            helper.setText(R.id.tv_video_jj, item.getDeviceName());//时间
////            ImageLoaderUtil.display(mContext, helper.imageView, entity.image, R.drawable.ic_car_pre, R.drawable.ic_car_pre);//品牌logo
//        }
//    }
}
