package com.victor.che.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Store;
import com.victor.che.event.ProvEditEvent;
import com.victor.che.ui.edit.EditStoresRunTimeActivity;
import com.victor.che.util.PicassoUtils;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 门店详情
 */
public class StoresdetailsActivity extends BaseActivity {

    @BindView(R.id.img_stores_pic)
    ImageView imgStoresPic;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_tel)
    TextView tvStoreTel;
    @BindView(R.id.tv_message_tel)
    TextView tvMessageTel;
    @BindView(R.id.tv_store_address)
    TextView tvStoreAddress;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;

    private Store store;//传过来的店铺信息
    /**
     * 二维码popuwindow
     */
    private PopupWindow popup_qrcode = null;

    @Override
    public int getContentView() {
        return R.layout.activity_storesdetails;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("门店详情");
        store = (Store) getIntent().getSerializableExtra("store");
        if (!StringUtil.isEmpty(store.getName())) {
            tvStoreName.setText(store.getName());
        }
        if (!StringUtil.isEmpty(store.getImage_url())) {
            PicassoUtils.loadbgImage(mContext,store.getImage_url(),imgStoresPic);
        }else {
            imgStoresPic.setVisibility(View.GONE);
        }
        if (!StringUtil.isEmpty(store.getService_tel())) {
            tvStoreTel.setText(store.getService_tel());
        }
        if (!StringUtil.isEmpty(store.getService_mobile())) {
            tvMessageTel.setText(store.getService_mobile());
        }
        if (!StringUtil.isEmpty(store.getAddress())) {
            tvStoreAddress.setText(store.getAddress());
        }
        if (!StringUtil.isEmpty(store.getBusiness_start_time())&&!StringUtil.isEmpty(store.getBusiness_end_time())) {
            tvWorkTime.setText(store.getBusiness_start_time()+"-"+store.getBusiness_end_time());
        }
        /**
         * 初始化二维码对话框
         */
        ercode();
    }
    private void ercode() {
        View viewQrcode = View.inflate(mContext, R.layout.popu_ercode, null);
        ImageView ercode= (ImageView) viewQrcode.findViewById(R.id.img_ercode);
       PicassoUtils.loadImage(mContext,store.getWxmp_qrcode(),ercode);
        LinearLayout lin_wxm = (LinearLayout) viewQrcode.findViewById(R.id.lin_wxmcode);
        popup_qrcode = new PopupWindow(mContext);
        popup_qrcode.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup_qrcode.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popup_qrcode.setBackgroundDrawable(new BitmapDrawable());
        popup_qrcode.setFocusable(true);
        popup_qrcode.setOutsideTouchable(true);
        popup_qrcode.setContentView(viewQrcode);
        lin_wxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_qrcode.dismiss();
            }
        });
    }

    @OnClick({R.id.area_store_name, R.id.area_store_tel, R.id.area_message_tel, R.id.area_store_address, R.id.area_work_time, R.id.area_wexin_ercode})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.area_store_name:  //修改门店名称
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_NAME);
                bundle.putString("mData",tvStoreName.getText().toString().trim());
                MyApplication.openActivity(mContext, ProvEditActivity.class, bundle);
                break;
            case R.id.area_store_tel://修改门店客服电话
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_TEL);
                bundle.putString("mData",tvStoreTel.getText().toString().trim() );
                MyApplication.openActivity(mContext, ProvEditActivity.class, bundle);
                break;
            case R.id.area_message_tel://修改门店短信通知电话
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_MESSAGE_TEL);
                bundle.putString("mData", tvMessageTel.getText().toString().trim());
                MyApplication.openActivity(mContext, ProvEditActivity.class, bundle);
                break;
            case R.id.area_store_address://修改门店地址
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_ADDRESS);
                bundle.putString("mData", tvStoreAddress.getText().toString().trim());
                MyApplication.openActivity(mContext, ProvEditActivity.class, bundle);
                break;
            case R.id.area_work_time://修改门店营业时间
                String startTime="";
                if (!StringUtil.isEmpty(store.getBusiness_start_time())) {
                    startTime =store.getBusiness_start_time();
                }
                String endTime="";
                if (!StringUtil.isEmpty(store.getBusiness_end_time())) {
                    endTime =store.getBusiness_end_time();
                }
                bundle.putString("start_time",startTime);
                bundle.putString("end_time",endTime);
                MyApplication.openActivity(mContext, EditStoresRunTimeActivity.class,bundle);
                break;
            case R.id.area_wexin_ercode://查看门店二维码
                if (!StringUtil.isEmpty(store.getWxmp_qrcode())) {
                    popup_qrcode.showAtLocation(findViewById(R.id.activity_storesdetails), Gravity.CENTER, 0, 0);
                }else {
                    MyApplication.showToast("该门店的二维码不存在");
                }

                break;
        }
    }

    /**
     * 服务商信息发生修改
     *
     * @param event
     */
    @Subscribe
    public void onInfoChanged(ProvEditEvent event) {
        if (event == null || StringUtil.isEmpty(event.action) || StringUtil.isEmpty(event.data)) {
            return;
        }
        if (ProvEditActivity.ACTION_EDIT_TEL.equalsIgnoreCase(event.action)) {//修改电话
            tvStoreTel.setText(event.data);
            MyApplication.CURRENT_USER.service_tel = event.data;
        } else if (ProvEditActivity.ACTION_EDIT_NAME.equalsIgnoreCase(event.action)) {//修改姓名
            tvStoreName.setText(event.data);
            MyApplication.CURRENT_USER.name = event.data;
        } else if (ProvEditActivity.ACTION_EDIT_ADDRESS.equalsIgnoreCase(event.action)) {//修改地址
            tvStoreAddress.setText(event.data);
            MyApplication.CURRENT_USER.address = event.data;
        }else if (ProvEditActivity.ACTION_EDIT_MESSAGE_TEL.equalsIgnoreCase(event.action)) {//修改接收短信的电话
            tvMessageTel.setText(event.data);
        }
       MyApplication.saveUser(MyApplication.CURRENT_USER);
    }

    /**
     * 营业时间发生变化
     * @param event
     */
    @Subscribe
    public void onInfoTimeChanged(ProvEditEvent event){
        if (event == null|| StringUtil.isEmpty(event.action) || StringUtil.isEmpty(event.data)) {
            return;
        }
        tvWorkTime.setText(event.action+"-"+event.data);
    }



}
