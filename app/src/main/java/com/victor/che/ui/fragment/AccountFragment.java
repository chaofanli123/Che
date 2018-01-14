package com.victor.che.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.ui.AccountInfoActivity;
import com.victor.che.ui.SettingsActivity;
import com.victor.che.ui.WebViewActivity;
import com.victor.che.util.PicassoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 账户界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class AccountFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_fg_mine_head)
    CircleImageView ivFgMineHead;

    @Override
    public int getContentView() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogined()) {
            // 当前用户名
            tv_name.setText(MyApplication.CURRENT_USER.username);
            PicassoUtils.loadHeadImage(mContext, MyApplication.CURRENT_USER.head, ivFgMineHead);
            // 店铺名称
//            tv_store_name.setText(MyApplication.CURRENT_USER.name);
        } else {
            tv_name.setText("请登录");
        }
    }

    /**
     * 登录用户界面
     */
    @OnClick(R.id.area_account_info)
    void gotoAccountInfo() {
        MyApplication.openActivity(mContext, AccountInfoActivity.class, true);
    }




    /**
     * 去设置界面
     */
    @OnClick(R.id.area_settings)
    void gotoSettings() {
        MyApplication.openActivity(mContext, SettingsActivity.class);
    }

    /**
     * 去设置界面
     */
    @OnClick(R.id.area_help)
    void gotoHelp() {
        Bundle bundle = new Bundle();
        bundle.putString("mUrl", "");
        MyApplication.openActivity(mContext, WebViewActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    /**
     * 去关于我们界面
     //     */
//    @OnClick(R.id.area_aboutus)
//     void gotoAbout() {
//        Bundle bundle=new Bundle();
//        bundle.putString("mUrl", "");
//        MyApplication.openActivity(mContext, AboutusActivity.class);
//
//    }
}
