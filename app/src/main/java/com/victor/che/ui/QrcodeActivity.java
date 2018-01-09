package com.victor.che.ui;

import android.widget.ImageView;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.ImageLoaderUtil;

import butterknife.BindView;

/**
 * 二维码界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/30 0030 9:19
 */
public class QrcodeActivity extends BaseActivity {

    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;

    @Override
    public int getContentView() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        super.initView();


        // 设置标题
        setTitle("公众号二维码");

        // 获取上页传来的数据
        ImageLoaderUtil.display(mContext, iv_qrcode, MyApplication.CURRENT_USER.wxmp_qrcode);
    }
}
