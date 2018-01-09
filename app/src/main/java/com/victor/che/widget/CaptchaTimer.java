package com.victor.che.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 验证码计时器
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/16 0016 10:47
 */
public class CaptchaTimer extends CountDownTimer {

    private TextView btn_get_captcha;

    public CaptchaTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CaptchaTimer(long millisInFuture, long countDownInterval, TextView btn_get_captcha) {
        super(millisInFuture, countDownInterval);
        this.btn_get_captcha = btn_get_captcha;
    }

    public void onFinish() {
        btn_get_captcha.setText("重发验证码");
        btn_get_captcha.setEnabled(true);
    }

    public void onTick(long millisUntilFinished) {
        btn_get_captcha.setText(millisUntilFinished / 1000L + "s");
        btn_get_captcha.setEnabled(false);
    }
}