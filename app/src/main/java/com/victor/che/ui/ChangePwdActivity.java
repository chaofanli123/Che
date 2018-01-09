package com.victor.che.ui;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/3 0003 14:22
 */
public class ChangePwdActivity extends BaseActivity {

    @BindView(R.id.et_old_pwd)
    EditText et_old_pwd;

    @BindView(R.id.et_new_pwd)
    EditText et_new_pwd;

    @BindView(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;

    @BindView(R.id.btn_operate)
    Button btn_operate;

    @Override
    public int getContentView() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("修改密码");
    }


    /**
     * 确认修改
     */
    @OnClick(R.id.btn_operate)
    public void doChangePwd() {
        String oldpwd = et_old_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldpwd)) {
            MyApplication.showToast("旧密码不能为空");
            et_old_pwd.requestFocus();
            return;
        }

        String newpwd = et_new_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(newpwd)) {
            MyApplication.showToast("新密码不能为空");
            et_new_pwd.requestFocus();
            return;
        }
        String confirmpwd = et_confirm_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(confirmpwd)) {
            MyApplication.showToast("确认密码不能为空");
            et_confirm_pwd.requestFocus();
            return;
        }

        if (oldpwd.length() < 6) {
            MyApplication.showToast("旧密码长度不能低于6位");
            et_old_pwd.requestFocus();
            return;
        }

        if (newpwd.length() < 6) {
            MyApplication.showToast("新密码长度不能低于6位");
            et_new_pwd.requestFocus();
            return;
        }

        if (confirmpwd.length() < 6) {
            MyApplication.showToast("确认密码长度不能低于6位");
            et_confirm_pwd.requestFocus();
            return;
        }

        if (!newpwd.equalsIgnoreCase(confirmpwd)) {
            MyApplication.showToast("两次密码输入不一致");
            et_confirm_pwd.requestFocus();
            return;
        }

        // 发送修改密码请求
        MyParams params = new MyParams();
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);
        params.put("old_password", oldpwd);
        params.put("new_password", newpwd);
        VictorHttpUtil.doPost(mContext, Define.URL_CHANGE_PWD, params, true, "提交中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                // 修改成功
                MyApplication.showToast("密码修改成功");
                finish();
            }
        });
    }
}
