package com.victor.che.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.widget.AlertDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * 二维码界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/30 0030 9:19
 */
public class ReceiptQrcodeActivity extends BaseActivity {

    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;

    @BindView(R.id.tv_actual_pay)
    TextView tv_actual_pay;
    private Socket mSocket;

    @Override
    public int getContentView() {
        return R.layout.activity_receipt_qrcode;
    }

    private String provider_user_id;//用户id
    private String order_id;
    private String pay_type;
    private String actual_pay;
    private boolean isCompleted;
    private int mActiveVipcard;//1-开卡，2-自定义开卡

    @Override
    protected void initView() {
        super.initView();
        String url = getIntent().getStringExtra("url");
        order_id = getIntent().getStringExtra("order_id");
        pay_type = getIntent().getStringExtra("pay_type");
        actual_pay = getIntent().getStringExtra("actual_pay");
        isCompleted = getIntent().getBooleanExtra("isCompleted", false);// 信息是否完善
        provider_user_id = getIntent().getStringExtra("provider_user_id");// 用户id
        mActiveVipcard = getIntent().getIntExtra("mActiveVipcard", 0);

        // 设置标题
        setTitle("2".equalsIgnoreCase(pay_type) ? "微信支付" : "支付宝支付");

        // 支付金额
        tv_actual_pay.setText(actual_pay + "元");

        // 获取收款二维码图片
        ImageLoaderUtil.display(mContext, iv_qrcode, url);

        setSocket();
    }

    private void setSocket() {
        try {
            mSocket = IO.socket("http://139.196.213.202:9990");

            mSocket.on("appOrderPay_success",new Emitter.Listener() {

                @Override
                public void call(final Object... args) {
                    //主线程调用
                    ReceiptQrcodeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // 关闭收款页面
                            EventBus.getDefault().post(ReceiptActivity.class);

                            // 跳转到支付结果界面
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
                            bundle.putString("provider_user_id", provider_user_id);//用户id
                            bundle.putInt("mActiveVipcard", mActiveVipcard);//开卡
                            MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);
                            finish();
                        }
                    });
                }
            });

            mSocket.connect();
            mSocket.emit("identify", "appOrderPay_"+order_id);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSocket.connected()){
            mSocket.disconnect();
        }
    }

    @Override
    public void back(View view) {
        AlertDialogFragment.newInstance(
                "友情提示",
                "您确认要退出收款吗？",
                "确定",
                "取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        _doGoBack();
                    }
                }, null)
                .show(getSupportFragmentManager(), getClass().getSimpleName());

    }

    /**
     * 返回上一级
     */
    private void _doGoBack() {
        MyApplication.openActivity(mContext, ReceiptActivity.class);// 调到收款界面,处理从挂单界面过来收银时,直接finish()出现的信息没有清空的bug
        super.back(null);
    }

    @Override
    public void onBackPressed() {
        back(null);
    }

    /**
     * 确认支付
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        if (mActiveVipcard == 1 || mActiveVipcard == 2) {// 开卡和自定义开卡 会员卡充值
            // 跳转到支付结果界面
            Bundle bundle = new Bundle();
            bundle.putInt("mActiveVipcard", mActiveVipcard);//开卡
            bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
            MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);
        } else {//查询订单状态（服务器修改订单状态）
            MyParams params = new MyParams();
            params.put("order_id", order_id);//服务商id
            VictorHttpUtil.doGet(mContext, Define.URL_ORDER_STATUS, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {

                            // #订单状态 1-未支付 3-待评价 4-已完成
                            int order_status = JSON.parseObject(element.data).getIntValue("order_status");
                            switch (order_status) {
                                case 1:
                                    MyApplication.showToast("订单未支付成功，请等待");
                                    break;
                                case 3:

                                case 4:
                                    // 关闭收款页面
                                    EventBus.getDefault().post(ReceiptActivity.class);

                                    // 跳转到支付结果界面
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
                                    bundle.putString("provider_user_id", provider_user_id);//用户id
                                    bundle.putInt("mActiveVipcard", mActiveVipcard);//开卡
                                    MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);
                                    finish();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
        }
    }



}
