package com.victor.che.base;


import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.util.StringUtil;

/**
 * 获取手机验证码
 *
 * @author Administrator
 */
public abstract class PhoneCaptchaActivity extends BaseActivity {

    protected String mobile;// 要发送验证码的手机号

    protected Button btn_get_captcha;// 获取验证码的按钮
    protected EditText et_mobile;// 手机号输入框
    protected EditText et_captcha;// 手机号输入框

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // 初始化获取验证码的按钮
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_captcha = (EditText) findViewById(R.id.et_captcha);
        btn_get_captcha = (Button) findViewById(R.id.btn_get_captcha);
    }

    /**
     * 获取短信验证码
     *
     * @param v
     */
    public final void doGetCaptcha(View v) {
        if (et_mobile != null) {
            mobile = et_mobile.getText().toString().trim();
            if (TextUtils.isEmpty(mobile)) {
                MyApplication.showToast("手机号码不能为空");
                et_mobile.requestFocus();
                return;
            }
            // 手机号格式验证
            if (!StringUtil.isPhoneNumber(mobile)) {
                MyApplication.showToast("手机号格式不正确");
                et_mobile.requestFocus();
                return;
            }
        }

        /**** 请求发送验证码，交给子类实现 ****/
        doRequestSendCaptcha();
    }

    protected void doRequestSendCaptcha() {
    }

    /**
     * 验证码计时器
     *
     * @author Administrator
     */
    protected class CaptchaTimer extends CountDownTimer {

        public CaptchaTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            btn_get_captcha.setText("重发验证码");
            btn_get_captcha.setEnabled(true);
        }

        public void onTick(long millisUntilFinished) {
            btn_get_captcha.setText(millisUntilFinished / 1000L + "s请等待");
            btn_get_captcha.setEnabled(false);
        }
    }

}
