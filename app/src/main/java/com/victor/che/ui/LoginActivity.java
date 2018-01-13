package com.victor.che.ui;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.orhanobut.logger.Logger;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.User;
import com.victor.che.util.AppUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.CaptchaTimer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 9:35
 */
public class LoginActivity extends BaseActivity {

    private Class<?> mRedirectTargetClass;// 重定向的界面
    private Bundle mBundle;// 重定向要传的参数

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.area_pwd)
    View area_pwd;

    @BindView(R.id.et_captcha)
    EditText et_captcha;

    @BindView(R.id.btn_get_captcha)
    TextView btn_get_captcha;

    @BindView(R.id.tv_switch_login_type)
    TextView tv_switch_login_type;

    @BindView(R.id.divider)
    View divider;

    @BindView(R.id.chk_show_pwd)
    CheckBox chk_show_pwd;

    private boolean firstStartedApp;// 是否是第一次启动app

    private boolean mLoginByPwd = true;// 默认是密码登录
    private String deviceId;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        new TedPermission(MyApplication.CONTEXT)
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .setDeniedMessage(R.string.rationale_shebei)
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        deviceId = AppUtil.getDeviceId(mContext);
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();

        firstStartedApp = MyApplication.spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);
        if (firstStartedApp) {// 第一次进入
            MyApplication.openActivity(mContext, GuideActivity.class);
            finish();
            return;
        } else if (MyApplication.isLogined()) {//已登录进入首页
            MyApplication.openActivity(mContext, TabBottomActivity.class);
            finish();
            return;
        }

        mRedirectTargetClass = (Class<?>) getIntent().getSerializableExtra("mRedirectTargetClass");
        mBundle = getIntent().getBundleExtra("mBundle");
        et_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doLogin();
                }
                return false;
            }
        });

        //        et_username.addTextChangedListener(new SimpleTextWatcher() {
        //            @Override
        //            public void afterTextChanged(Editable s) {
        //                String text = s.toString().trim();
        //                if (StringUtil.isPhoneNumber(text)) {//输入的是手机号
        //                    tv_switch_login_type.setVisibility(View.VISIBLE);
        //                } else {//用户名
        //                    tv_switch_login_type.setVisibility(View.INVISIBLE);
        //                }
        //            }
        //        });

        chk_show_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //显示隐藏密码
                if (isChecked) {// 显示密码
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {//隐藏密码
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 光标移动到最后
                Editable etext = et_pwd.getText();
                Selection.setSelection(etext, etext.length());
            }
        });
    }

    /**
     * 切换登录类型(密码登录<==>验证码登录)
     */
    @OnClick(R.id.tv_switch_login_type)
    public void switchLoginType() {
        mLoginByPwd = !mLoginByPwd;
        if (mLoginByPwd) {// 切换到密码登录
            area_pwd.setVisibility(View.VISIBLE);
            et_captcha.setVisibility(View.GONE);
            et_username.setHint("请输入用户名或手机号");
            et_username.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); //输入类型
            et_username.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)}); //最大输入长度
            btn_get_captcha.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            tv_switch_login_type.setText("验证码登录");
        } else {// 切换到验证码登录
            area_pwd.setVisibility(View.GONE);
            et_captcha.setVisibility(View.VISIBLE);

            et_username.setHint("请输入手机号");
            et_username.setInputType(InputType.TYPE_CLASS_NUMBER); //输入类型
            et_username.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度

            btn_get_captcha.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            tv_switch_login_type.setText("密码登录");
        }
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.btn_get_captcha)
    void doGetCaptcha() {
        final String username = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            MyApplication.showToast("手机号不能为空");
            et_username.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNumber(username)) {
            MyApplication.showToast("手机号格式不正确");
            et_username.requestFocus();
            return;
        }
        // 获取短信验证码
        MyParams params = new MyParams();
        params.put("mobile", username);
        VictorHttpUtil.doPost(mContext, Define.URL_GET_CAPTCHA, params, true, "获取中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        super.callbackSuccess(url, element);
                        // 成功
                        new CaptchaTimer(60000L, 1000L, btn_get_captcha).start();
                        MyApplication.showToast("验证码已发送到" + username + "的手机中");
                        // 验证码框获得焦点
                        et_captcha.requestFocus();
                    }
                });
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_operate)
    void doLogin() {
        String username = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            MyApplication.showToast("用户名或手机号不能为空");
            et_username.requestFocus();
            return;
        }
        String captcha = "";
        String pwd = "";
        if (mLoginByPwd) {// 密码登录
            pwd = et_pwd.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                MyApplication.showToast("密码不能为空");
                et_pwd.requestFocus();
                return;
            }
        } else {// 验证码登录
            if (!StringUtil.isPhoneNumber(username)) {
                MyApplication.showToast("手机号格式不正确");
                et_username.requestFocus();
                return;
            }

            captcha = et_captcha.getText().toString().trim();
            if (TextUtils.isEmpty(captcha)) {
                MyApplication.showToast("验证码不能为空");
                et_captcha.requestFocus();
                return;
            }
        }
        // 发送登录请求
        MyParams params = new MyParams();
        params.put("mobileLogin", true);//用户名或手机号
        params.put("username", username);//用户名或手机号
        if (mLoginByPwd) {// 密码登录
            params.put("password", pwd);
        } else {// 验证码登录MyApplication.showToast("登录成功");
            params.put("smscode", captcha);
    }
        VictorHttpUtil.doPost(mContext, Define.URL_LOGIN, params, true, "登录中...",
                new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                // 登录成功
                // 保存用户信息（手机号和默认车辆）
                User user = JSON.parseObject(element.data, User.class);
                user.tokenExpireTime = DateUtil.getDateByOffset(new Date(), Calendar.DAY_OF_YEAR,
                        ConstantValue.TOKEN_EXPIRE_DAY);// 更新过期时间
                MyApplication.saveUser(user);
                // 如果推送停止，则重新启动
                if (JPushInterface.isPushStopped(getApplicationContext())) {
                    JPushInterface.resumePush(getApplicationContext());
                }
                // 设置JPush别名(staff_user_id)
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, user.staff_user_id));

             MyApplication.openActivity(mContext, TabBottomActivity.class);
             //设置acToken
             MyApplication.getOpenSDK().setAccessToken("at.c9izof7adwq7i89ubvn1udd974bn2nqr-7znpcab916-1hyd7e8-gi0qfpfkr");

                // 关闭本页
                finish();
            }
        });
    }

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
            }
        }
    };

    /**
     * 设置JPush别名的回调
     */
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:// 设置成功
                    Logger.e("设置jpush成功");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    boolean setJPushAlias = MyApplication.spUtil.getBoolean(ConstantValue.SP_KEY_SET_JPUSH_ALIAS + alias);
                    if (!setJPushAlias) {
                        MyApplication.spUtil.setBoolean(ConstantValue.SP_KEY_SET_JPUSH_ALIAS + alias, true);
                    }
                    break;
                case 6002:// 设置超时，延迟 60 秒来调用 Handler 设置别名
                default:
                    Logger.e("设置jpush失败，重试");
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
            }
        }
    };
}
