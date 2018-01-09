package com.victor.che.ui;

import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.event.ProvEditEvent;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务商信息编写界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/7 0007 12:34
 */
public class ProvEditActivity extends BaseActivity {

    public static final String ACTION_EDIT_TEL = "edit_prov_tel";
    public static final String ACTION_EDIT_MESSAGE_TEL = "edit_prov_message_tel";
    public static final String ACTION_EDIT_NAME = "edit_prov_name";
    public static final String ACTION_EDIT_ADDRESS = "edit_prov_address";

    @BindView(R.id.label)
    TextView label;

    @BindView(R.id.et_input)
    EditText et_input;

    private String mAction;
    private String mData;

    @Override
    public int getContentView() {
        return R.layout.activity_edit_customer;
    }

    @Override
    protected void initView() {
        super.initView();

        mAction = getIntent().getStringExtra("mAction");
        mData = getIntent().getStringExtra("mData");

        et_input.setText(mData);


        if (ACTION_EDIT_TEL.equalsIgnoreCase(mAction)) {//修改电话
            // 设置标题
            setTitle("修改客服电话");
            et_input.setHint("请输入客服电话");
            et_input.setInputType(InputType.TYPE_CLASS_NUMBER); //输入类型
            et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
        } else if (ACTION_EDIT_NAME.equalsIgnoreCase(mAction)) {//修改门店名称
            // 设置标题
            setTitle("修改店名");
            et_input.setHint("请输入店名");
        } else if (ACTION_EDIT_ADDRESS.equalsIgnoreCase(mAction)) {//修改地址
            // 设置标题
            setTitle("修改地址");
            et_input.setHint("请输入地址");
        }else if (ACTION_EDIT_MESSAGE_TEL.equalsIgnoreCase(mAction)) {
            setTitle("修改短信通知电话");
            et_input.setHint("请输入短信通知电话");
        }

    }

    /**
     * 保存
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        final String format = "%s=%s";
        final String input = et_input.getText().toString().trim();
        String msg = "";
        String data = "";// 店名----name 地址--address 电话--service_tel 短信通知电话service_mobile
        if (ACTION_EDIT_TEL.equalsIgnoreCase(mAction)) {//修改电话
            msg = "电话";
            data = String.format(format, "service_tel", input);
        } else if (ACTION_EDIT_NAME.equalsIgnoreCase(mAction)) {//修改姓名
            msg = "姓名";
            data = String.format(format, "name", input);
        } else if (ACTION_EDIT_ADDRESS.equalsIgnoreCase(mAction)) {//修改地址
            msg = "地址";
            data = String.format(format, "address", input);
        }else if (ACTION_EDIT_MESSAGE_TEL.equalsIgnoreCase(mAction)) {//修改短信通知电话
            msg = "短信通知电话";
            data = String.format(format, "service_mobile", input);
        }
        // 非空校验
        if (StringUtil.isEmpty(input)) {
            MyApplication.showToast(msg + "不能为空");
            et_input.requestFocus();
            return;
        }

        if (input.equalsIgnoreCase(mData)) {//没有发生任何更改
            finish();
            return;
        }

        // 提交修改
        // 请求数据
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 	服务商编号
        // 店名----name 地址--address 电话--tel
        params.put("data", data);//需要修改的用户信息; 数据格式：字段=值,字段1=值1 (修改信息对应字段参考下面的备注)
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_EDIT, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("修改成功");
                        // 通知修改
                        ProvEditEvent event = new ProvEditEvent(mAction, input);
                        EventBus.getDefault().post(event);

                        finish();
                    }
                });
    }
}
