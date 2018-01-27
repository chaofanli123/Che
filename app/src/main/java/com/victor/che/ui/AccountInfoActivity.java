package com.victor.che.ui;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.bean.UserInfo;
import com.victor.che.domain.User;
import com.victor.che.event.StringEvent;
import com.victor.che.ui.my.TakePhotoActivity;
import com.victor.che.ui.my.util.StringUtil;
import com.victor.che.util.MaterialDialogUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static com.victor.che.app.MyApplication.spUtil;
/**
 * 个人信息界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/3 0003 10:43
 */
public class AccountInfoActivity extends TakePhotoActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;

    @Override
    public int getContentView() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("个人信息");
      getusermessage();
    }
    /**
     * 获取用户信息
     */
    private void getusermessage() {
        MyParams parms=new MyParams();
        VictorHttpUtil.doPost(mContext, Define.URL_info+ ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, parms, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        UserInfo userInfo = JSON.parseObject(element.body, UserInfo.class);
                        UserInfo.UserInformationBean userInformation = userInfo.getUserInformation();
                        if (userInformation != null) {
                            tv_name.setText(userInformation.getName());
                            tvEmail.setText(userInformation.getEmail());
                            tvPhone.setText(userInformation.getPhone());
                            tvMobile.setText(userInformation.getMobile());
                            tvRemarks.setText(userInformation.getMobile());
                        }
//
                    }
                });
    }


    @OnClick(R.id.rl_name)
    public void onViewClicked() {
        MaterialDialog.InputCallback innputCallback;//输入
        final String occupations = tv_name.getText().toString().trim();
        innputCallback = new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                if (!TextUtils.isEmpty(input)) {
                    tv_name.setText(input);
                } else {
                    MyApplication.showToast("姓名不能为空");
                }
            }
        };
        MaterialDialogUtils.baseInputDialog(mActivity, "修改姓名", occupations, innputCallback);
    }

    /**
     * 修改用户信息
     */
    @OnClick(R.id.topbar_right)
    public void updateClicked() {
        String email = tvEmail.getText().toString().trim();

        String phone = tvPhone.getText().toString().trim();

        String mobile = tvMobile.getText().toString().trim();

        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);
        params.put("name", tv_name.getText().toString().trim());
        params.put("email", email);
        params.put("phone", phone);
        params.put("mobile", mobile);
        params.put("remarks", tvRemarks.getText().toString().trim());
        VictorHttpUtil.doPost(mContext, Define.URL_infoEdit+ ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, true, "上传中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        // 登录成功
                        // 保存用户信息（手机号和默认车辆）
                        User user = new User();
                        user.name=tv_name.getText().toString().trim();
                        MyApplication.saveUser(user);
                        spUtil.setObject("CURRENT_USER", user);
                        MyApplication.showToast(element.msg);
                        StringEvent event=new StringEvent(tv_name.getText().toString().trim());
                        EventBus.getDefault().post(event);
                        // 关闭本页
                        finish();

                    }
                });
    }

    @OnClick({R.id.rl_email, R.id.rl_phone, R.id.rl_mobile, R.id.rl_remarks})
    public void onViewClicked(View view) {
        MaterialDialog.InputCallback innputCallback;//输入
        switch (view.getId()) {
            case R.id.rl_email: //email
                final String tvemail = tvEmail.getText().toString().trim();
                innputCallback = new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            if (com.victor.che.util.StringUtil.isEmail(input.toString().trim())) {
                                MyApplication.showToast("请输入正确的email");
                                return;
                            }
                            tvEmail.setText(input);
                        } else {
                            MyApplication.showToast("邮箱不能为空");
                        }
                    }
                };
                MaterialDialogUtils.baseInputDialog(mActivity, "修改邮箱", tvemail,innputCallback);
                break;
            case R.id.rl_phone:
                final String tvphone = tvPhone.getText().toString().trim();
                innputCallback = new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            if (StringUtil.isPhone(input.toString().trim())) {
                                MyApplication.showToast("电话格式不正确");
                                return;
                            }
                            tvPhone.setText(input);
                        } else {
                            MyApplication.showToast("电话不能为空");
                        }
                    }
                };
                MaterialDialogUtils.baseInputDialog(mActivity, "修改电话", tvphone,innputCallback);
                break;
            case R.id.rl_mobile:
                final String tvmobile = tvMobile.getText().toString().trim();
                innputCallback = new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {

                            if (StringUtil.isMobile(input.toString().trim())) {
                                MyApplication.showToast("手机号格式不正确");
                                return;
                            }
                            tvMobile.setText(input);
                        } else {
                            MyApplication.showToast("手机号不能为空");
                        }
                    }
                };
                MaterialDialogUtils.baseInputDialog(mActivity, "修改手机号", tvmobile,innputCallback);
                break;
            case R.id.rl_remarks:
                final String tvremarks = tvRemarks.getText().toString().trim();
                innputCallback = new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            tvRemarks.setText(input);
                        } else {
                            MyApplication.showToast("备注不能为空");
                        }
                    }
                };
                MaterialDialogUtils.baseInputDialog(mActivity, "修改备注", tvremarks,innputCallback);
                break;
        }
    }
}
