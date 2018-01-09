package com.victor.che.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.PayConfirmData;
import com.victor.che.event.AlipayReuseEnent;
import com.victor.che.pay.wxpay.wxpay.Constants;
import com.victor.che.pay.wxpay.wxpay.WXPayUtil;
import com.victor.che.ui.PayPolicySuccessActivity;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private AlipayReuseEnent reuseEnent;
    private SharedPreferences sp;

    @Override
    public int getContentView() {
        return R.layout.activity_wxpay_entry;
    }

    @Override
    protected void initView() {
        super.initView();
        reuseEnent = new AlipayReuseEnent();

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
    @Override
    public void onResp(BaseResp baseResp) {
        sp = mContext.getSharedPreferences("paystyle", Context.MODE_PRIVATE);
        String paystyle = sp.getString("paystyle", "");
        if (paystyle.equals("webviwe")) { //来自webview
            if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                switch (baseResp.errCode) {
                    case 0:// 成功 展示成功页面
                        // 确认支付成功
                        reuseEnent.setPayState("1");
                        EventBus.getDefault().post(reuseEnent);// 发送信息给webview
                        //   gotoPayResult(true);
                        break;
                    case -1:// 错误
                        // 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                        //  gotoPayResult(false);// 支付失败
                        reuseEnent.setPayState("0");
                        EventBus.getDefault().post(reuseEnent);// 发送信息给webview
                        break;
                    case -2:// 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
                        finish();// 关闭本页
                        reuseEnent.setPayState("-1");
                        EventBus.getDefault().post(reuseEnent);// 发送信息给webview
                        break;
                    default:
                        break;
                }
            }

        } else if (paystyle.equals("bendi")) { //来自本地
            if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                switch (baseResp.errCode) {
                    case 0:// 成功 展示成功页面
                        // 确认支付成功
                        confirmAliPay(1);
                        break;
                    case -1:// 错误
                        // 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                        confirmAliPay(-1);
                        break;
                    case -2:// 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
                        finish();// 关闭本页
                        break;
                    default:
                        break;
                }
            }
        }

    }

    /**
     * 确认支付结果,去支付结果界面
     *
     */
    private void confirmAliPay(int state){
        MyParams params = new MyParams();
        params.put("insurance_order_id",WXPayUtil.out_trade_no);
        params.put("confirm_status",state);//-1：失败 1-成功
        VictorHttpUtil.doPost(mContext, Define.url_insurance_order_confirm_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {

                    @Override
                    public void callbackSuccess(String url, Element element) {

                        PayConfirmData payConfirmData = JSON.parseObject(element.data, PayConfirmData.class);
                        int is_success = payConfirmData.is_success;//  #支付状态 0-回调失败 1-成功 2-未回调且客户端确认成功 3-客户端确认失败
                        if(is_success==1){
                            //成功
                            MyApplication.openActivity(mContext,PayPolicySuccessActivity.class);
                            finish();
                        }else{
                            //失败
                            MyApplication.showToast("支付失败,请稍后重试!");
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}
