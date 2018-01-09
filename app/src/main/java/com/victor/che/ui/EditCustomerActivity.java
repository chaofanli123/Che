package com.victor.che.ui;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.event.UserEditEvent;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/30 0030 9:19
 */
public class EditCustomerActivity extends BaseActivity {

    @BindView(R.id.label)
    TextView label;

    @BindView(R.id.et_input)
    EditText et_info;

    private String mAction;
    private String provider_user_id;
    private String mData;

    @Override
    public int getContentView() {
        return R.layout.activity_edit_customer;
    }

    @Override
    protected void initView() {
        super.initView();

        mAction = getIntent().getStringExtra("mAction");
        provider_user_id = getIntent().getStringExtra("provider_user_id");
        mData = getIntent().getStringExtra("mData");

        et_info.setText(mData);

        if ("mobile".equalsIgnoreCase(mAction)) {//修改手机号
            // 设置标题
            setTitle("修改手机号");
            label.setText("手机号");
            et_info.setHint("请输入手机号");
            et_info.setInputType(InputType.TYPE_CLASS_NUMBER); //输入类型
            et_info.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
        } else if ("name".equalsIgnoreCase(mAction)) {//修改姓名
            // 设置标题
            setTitle("修改姓名");
            label.setText("姓名");
            et_info.setHint("请输入姓名");
            et_info.setInputType(InputType.TYPE_CLASS_TEXT); //输入类型
            et_info.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
        }
    }

    /**
     * 保存
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        if ("mobile".equalsIgnoreCase(mAction)) {//修改手机号
            final String mobile = et_info.getText().toString().trim();
            if (StringUtil.isEmpty(mobile)) {
                MyApplication.showToast("手机号不能为空");
                et_info.requestFocus();
                return;
            }
            if (!StringUtil.isPhoneNumber(mobile)) {
                MyApplication.showToast("手机号格式不正确");
                et_info.requestFocus();
                return;
            }

            if (mobile.equalsIgnoreCase(mData)) {//没有发生任何更改
                finish();
                return;
            }

            // 提交修改
            // 请求数据
            MyParams params = new MyParams();
            params.put("provider_user_id", provider_user_id);// 用户编号(获取用户列表时返回)
            // 手机号-mobile  姓名-name
            params.put("data", "mobile=" + mobile);//需要修改的用户信息; 数据格式：字段=值,字段1=值1 (修改信息对应字段参考下面的备注)
            VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_EDIT, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {

                            JSONObject jsonobj = JSON.parseObject(element.data);
                            if (jsonobj == null) {
                                MyApplication.showToast("修改成功");

                                // 通知修改
                                UserEditEvent event = new UserEditEvent();
                                event.action = "mobile";
                                event.data = mobile;
                                EventBus.getDefault().post(event);

                                finish();
                            } else {
                                int exist_user = jsonobj.getIntValue("exist_user");
                                final String provider_user_id = jsonobj.getString("provider_user_id");
                                if (exist_user == 1 ) {
                                    AlertDialogFragment.newInstance(
                                            "提示",
                                            "手机号已经存在，是否编辑已存在的用户？",
                                            "取消",
                                            "编辑",
                                            null,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("provider_user_id", provider_user_id);//用户id
                                                    MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
                                                }
                                            })
                                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                                }
                            }

                        }
                    });
        } else if ("name".equalsIgnoreCase(mAction)) {//修改姓名
            final String name = et_info.getText().toString().trim();
            if (StringUtil.isEmpty(name)) {
                MyApplication.showToast("姓名不能为空");
                et_info.requestFocus();
                return;
            }
            if (name.equalsIgnoreCase(mData)) {//没有发生任何更改
                finish();
                return;
            }

            // 提交修改
            // 请求数据
            MyParams params = new MyParams();
            params.put("provider_user_id", provider_user_id);// 用户编号(获取用户列表时返回)
            // 手机号-mobile 性别-gender(0-男 1-女) 姓名-name 生日-birthday
            params.put("data", "name=" + name);//需要修改的用户信息; 数据格式：字段=值,字段1=值1 (修改信息对应字段参考下面的备注)
            VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_EDIT, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            MyApplication.showToast("修改成功");

                            // 通知修改
                            UserEditEvent event = new UserEditEvent();
                            event.action = "name";
                            event.data = name;
                            EventBus.getDefault().post(event);

                            finish();
                        }
                    });
        }
    }


}
