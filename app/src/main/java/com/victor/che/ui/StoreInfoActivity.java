package com.victor.che.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.PickImageActivity;
import com.victor.che.event.ProvEditEvent;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的账户界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 15:32
 */
public class StoreInfoActivity extends PickImageActivity {

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    @BindView(R.id.tv_store_name)
    TextView tv_store_name;

    @BindView(R.id.tv_store_tel)
    TextView tv_store_tel;

    @BindView(R.id.tv_store_address)
    TextView tv_store_address;

    @Override
    public int getContentView() {
        return R.layout.activity_store_info;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("店面信息");

        // 数据回显
        ImageLoaderUtil.display(mContext, iv_avatar, MyApplication.CURRENT_USER.thumb_image_url, R.drawable.def_store, R.drawable.def_store);
        tv_store_name.setText(MyApplication.CURRENT_USER.name);
        tv_store_tel.setText(MyApplication.CURRENT_USER.service_tel);
        tv_store_address.setText(MyApplication.CURRENT_USER.address);
    }

    /**
     * 修改门头图片
     */
    @OnClick(R.id.area_change_avatar)
    void changeAvatar() {
//        showChooseAvatarDialog(420, 140, new OnCropImageCompleteListener() {
//
//            @Override
//            public void onCropImageComplete(String croppedImagePath) {
//                // TODO 上传头像
//            }
//        });
    }


    @OnClick({R.id.area_store_name, R.id.area_store_tel, R.id.area_store_address})
    void gotoEdit(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.area_store_name://编辑姓名
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_NAME);
                bundle.putString("mData", MyApplication.CURRENT_USER.name);
                break;
            case R.id.area_store_tel://编辑电话
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_TEL);
                bundle.putString("mData", MyApplication.CURRENT_USER.service_tel);
                break;
            default://编辑地址
                bundle.putString("mAction", ProvEditActivity.ACTION_EDIT_ADDRESS);
                bundle.putString("mData", MyApplication.CURRENT_USER.address);
                break;
        }
        MyApplication.openActivity(mContext, ProvEditActivity.class, bundle);
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
            tv_store_tel.setText(event.data);
            MyApplication.CURRENT_USER.service_tel = event.data;
        } else if (ProvEditActivity.ACTION_EDIT_NAME.equalsIgnoreCase(event.action)) {//修改姓名
            tv_store_name.setText(event.data);
            MyApplication.CURRENT_USER.name = event.data;
        } else if (ProvEditActivity.ACTION_EDIT_ADDRESS.equalsIgnoreCase(event.action)) {//修改地址
            tv_store_address.setText(event.data);
            MyApplication.CURRENT_USER.address = event.data;
        }
        MyApplication.saveUser(MyApplication.CURRENT_USER);
    }
}
