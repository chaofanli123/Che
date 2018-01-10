package com.victor.che.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.victor.che.R;
import com.victor.che.adapter.ChannelAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Channel;
import com.victor.che.listener.ItemDragHelperCallBack;
import com.victor.che.listener.OnChannelDragListener;
import com.victor.che.listener.OnChannelListener;
import com.victor.che.util.ConstanceValue;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.victor.che.domain.Channel.TYPE_MY_CHANNEL;

public class ChannelActivity extends BaseActivity implements OnChannelDragListener {
    Unbinder unbinder;
    @BindView(R.id.topbar_right)
    TextView topbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<Channel> mDatas = new ArrayList<>();
    private ChannelAdapter mAdapter;
    private ItemTouchHelper mHelper;

    private List<Channel> selectedDatas;
    private List<Channel> unselectedDatas;

    private OnChannelListener mOnChannelListener;
    private String goods_category_id = "";

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        mOnChannelListener = onChannelListener;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_channel;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("管理服务类别");
        topbarRight.setText("确定");
        processLogic();
    }

    private void setDataType(List<Channel> datas, int type) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemType(type);
        }
    }

    private void processLogic() {
        mDatas.add(new Channel(Channel.TYPE_MY, "已选择服务", ""));
        selectedDatas = (List<Channel>) getIntent().getSerializableExtra(ConstanceValue.DATA_SELECTED);
        unselectedDatas = (List<Channel>) getIntent().getSerializableExtra(ConstanceValue.DATA_UNSELECTED);
        setDataType(selectedDatas, TYPE_MY_CHANNEL);
        setDataType(unselectedDatas, Channel.TYPE_OTHER_CHANNEL);
        mDatas.addAll(selectedDatas);
        mDatas.add(new Channel(Channel.TYPE_OTHER, "未选择服务", ""));
        mDatas.addAll(unselectedDatas);
        mAdapter = new ChannelAdapter(mDatas);

        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == TYPE_MY_CHANNEL || itemViewType == Channel.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        //attachRecyclerView
        mHelper.attachToRecyclerView(recyclerView);
    }

    @OnClick(R.id.topbar_right)
    public void onViewClicked() {
        goods_category_id = "";
        for (int i=0;i<mDatas.size();i++){
            int itemType = mDatas.get(i).getItemType();
            if (itemType == TYPE_MY_CHANNEL) {
                goods_category_id += mDatas.get(i).goods_category_id+"_"+i+1+ ",";
            }
        }
        if (goods_category_id.endsWith(",")) {
            goods_category_id = goods_category_id.substring(0, goods_category_id.length() - 1);
        }
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("data", goods_category_id);//服务商编号
        VictorHttpUtil.doPost(mContext, Define.url_subbranch_goods_category_manage_v1, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                      EventBus.getDefault().post(ProductMgrActivity.class);
                        MyApplication.openActivity(mContext,ProductMgrActivity.class);
                       finish();
                    }
                });
    }

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
        Logger.i("开始拖动");
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
//        if (starPos < 0||endPos<0) return;
        //我的频道之间移动
        if (mOnChannelListener != null)
            mOnChannelListener.onItemMove(starPos - 1, endPos - 1);//去除标题所占的一个index
        onMove(starPos, endPos);
    }

    private void onMove(int starPos, int endPos) {
        Channel startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        onMove(starPos, endPos);
        if (mOnChannelListener != null)
            mOnChannelListener.onMoveToMyChannel(starPos - 1 - mAdapter.getMyChannelSize(), endPos - 1);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        onMove(starPos, endPos);
        if (mOnChannelListener != null)
            mOnChannelListener.onMoveToOtherChannel(starPos - 1, endPos - 2 - mAdapter.getMyChannelSize());
    }


}
