package com.victor.che.ui;

import android.os.Bundle;
import android.text.Editable;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.victor.che.util.StringUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.victor.che.app.MyApplication.spUtil;

/**
 * 登录界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/29 0029 9:35
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_check)
    ImageView tvCheck;
    private Class<?> mRedirectTargetClass;// 重定向的界面
    private Bundle mBundle;// 重定向要传的参数

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.area_pwd)
    View area_pwd;


    @BindView(R.id.chk_show_pwd)
    CheckBox chk_show_pwd;

    private boolean firstStartedApp;// 是否是第一次启动app

    private boolean mLoginByPwd = true;// 默认是密码登录

    private boolean isrember=false;//是否记住密码


    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        firstStartedApp = spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);
        if (spUtil.getBoolean(ConstantValue.SP.FIRST_STARTED_APP)==true) {//已登录进入首页
            isrember=MyApplication.getUser().isrember;
            if (isrember) {
               et_username.setText(MyApplication.getUser().username);
            } else {
                et_username.setText("");
            }
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

        if (isrember) {
            tvCheck.setImageResource(R.drawable.ic_common_checked);

        } else {
            tvCheck.setImageResource(R.drawable.ic_common_unchecked);
        }

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
     * 登录
     */
    @OnClick(R.id.btn_operate)
    void doLogin() {
        String username = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            MyApplication.showToast("账号不能为空");
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
        }
        // 发送登录请求
        MyParams params = new MyParams();
        params.put("mobileLogin", true);//是否客户的端登录
        params.put("username", username);//用户名或手机号
        if (isrember) {
            params.put("rememberMe", "on");//是否记住密码
        }
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
                        User user = JSON.parseObject(element.body, User.class);
                        user.isrember=isrember;
                        MyApplication.saveUser(user);
                            spUtil.setBoolean(ConstantValue.SP.FIRST_STARTED_APP, true);//是否第一次登录
                        spUtil.setObject("CURRENT_USER", user);
                        MyApplication.openActivity(mContext, TabBottomActivity.class);
                        // 关闭本页
                        finish();
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 是否记住密码
     */
    @OnClick(R.id.lin_rember)
    public void onViewClicked() {
        if (isrember) {
            tvCheck.setImageResource(R.drawable.ic_common_unchecked);
            isrember=false;
        } else {
            isrember=true;
            tvCheck.setImageResource(R.drawable.ic_common_checked);
        }
    }
}
