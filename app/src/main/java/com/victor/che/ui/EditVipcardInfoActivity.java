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
import com.victor.che.domain.Vipcard;
import com.victor.che.event.VipcardInfoEvent;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑会员卡具体信息界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/30 0030 9:19
 */
public class EditVipcardInfoActivity extends BaseActivity {

    @BindView(R.id.label)
    TextView label;

    @BindView(R.id.et_input)
    EditText et_input;

    private VipcardInfoEvent mVipcardInfo;
    private Vipcard mVipcard;


    @Override
    public int getContentView() {
        return R.layout.activity_edit_vipcard_info;
    }

    @Override
    protected void initView() {
        super.initView();

        mVipcardInfo = (VipcardInfoEvent) getIntent().getSerializableExtra("mVipcardInfo");
        mVipcard = mVipcardInfo.data;

        switch (mVipcardInfo.action) {
            case VipcardInfoEvent.EDIT_VIPCARD_NAME://修改会员卡姓名
                et_input.setText(mVipcard.name);
                setTitle("卡名称");
                label.setText("名称");
                et_input.setHint("请输入卡名称");
                et_input.setInputType(InputType.TYPE_CLASS_TEXT); //输入类型
                et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
                break;
            case VipcardInfoEvent.EDIT_REMAIN_VALUE://修改可用次数/可用余额
                if (mVipcard.card_category_id == 1) {//次卡
                    et_input.setText(mVipcard.available_num + "");
                    setTitle("修改可用次数");
                    label.setText("可用次数");
                    et_input.setHint("请输入可用次数");
                    et_input.setInputType(InputType.TYPE_CLASS_NUMBER); //输入类型
                } else if (mVipcard.card_category_id == 2) {//储值卡
                    et_input.setText(mVipcard.face_money + "");
                    setTitle("修改可用金额");
                    label.setText("可用金额");
                    et_input.setHint("请输入可用金额");
                    et_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
                break;
            case VipcardInfoEvent.EDIT_SALE_PRICE://修改售价
                et_input.setText(mVipcard.sale_price + "");
                setTitle("修改售价");
                label.setText("售价");
                et_input.setHint("请输入售价");
                et_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)}); //最大输入长度
                break;
            case VipcardInfoEvent.EDIT_ORIGINAL_PRICE://修改原价
                et_input.setText(mVipcard.price + "");
                setTitle("修改原价");
                label.setText("原价");
                et_input.setHint("请输入原价");
                et_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            default:
                break;
        }
    }

    int face_num = 0;
    double face_value = 0;
    double price = 0;

    /**
     * 保存
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {
        String data = "";
        final String strValue = et_input.getText().toString().trim();

        if (StringUtil.isEmpty(strValue)) {
            MyApplication.showToast("输入不能为空");
            et_input.requestFocus();
            return;
        }
        switch (mVipcardInfo.action) {
            case VipcardInfoEvent.EDIT_VIPCARD_NAME://修改会员卡姓名
                if (strValue.equalsIgnoreCase(mVipcard.name)) {//无修改
                    finish();
                    return;
                }
                data = "name=" + strValue;
                break;
            case VipcardInfoEvent.EDIT_REMAIN_VALUE://修改可用次数/可用余额
                if (mVipcard.card_category_id == 1) {//次卡
                    face_num = Integer.parseInt(strValue);
                    if (face_num <= 0) {
                        MyApplication.showToast("次数必须为正数");
                        et_input.requestFocus();
                        return;
                    }
                    if (face_num == mVipcard.available_num) {//无修改
                        finish();
                        return;
                    }
                    data = "available_num=" + face_num;
                } else if (mVipcard.card_category_id == 2) {//储值卡
                    face_value = Double.parseDouble(strValue);
                    if (face_value <= 0) {
                        MyApplication.showToast("金额必须为正数");
                        et_input.requestFocus();
                        return;
                    }
                    if (face_value == mVipcard.face_money) {//无修改
                        finish();
                        return;
                    }
                    data = "face_money=" + face_value;
                }
                break;
            case VipcardInfoEvent.EDIT_SALE_PRICE://修改售价
                price = Double.parseDouble(strValue);
                if (price <= 0) {
                    MyApplication.showToast("售价必须为正数");
                    et_input.requestFocus();
                    return;
                }
                if (price == mVipcard.sale_price) {
                    finish();
                    return;
                }
                data = "sale_price=" + price;
                break;
            case VipcardInfoEvent.EDIT_ORIGINAL_PRICE://修改原价
                price = Double.parseDouble(strValue);
                if (price <= 0) {
                    MyApplication.showToast("原价必须为正数");
                    et_input.requestFocus();
                    return;
                }
                if (price == mVipcard.price) {
                    finish();
                    return;
                }
                data = "price=" + price;
                break;
            default:
                break;
        }
        MyParams params = new MyParams();
        params.put("provider_card_id", mVipcard.provider_card_id);// 服务商卡id
        params.put("data", data);//修改字段
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_CARD_EDIT, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        switch (mVipcardInfo.action) {
                            case VipcardInfoEvent.EDIT_VIPCARD_NAME://修改会员卡姓名
                                mVipcard.name = strValue;
                                break;
                            case VipcardInfoEvent.EDIT_REMAIN_VALUE://修改可用次数/可用余额
                                if (mVipcard.card_category_id == 1) {//次卡
                                    mVipcard.available_num = face_num;
                                } else if (mVipcard.card_category_id == 2) {//储值卡
                                    mVipcard.face_money = face_value;
                                }
                                break;
                            case VipcardInfoEvent.EDIT_SALE_PRICE://修改售价
                                mVipcard.sale_price = price;
                                break;
                            case VipcardInfoEvent.EDIT_ORIGINAL_PRICE://修改原价
                                mVipcard.price = price;
                                break;
                            default:
                                break;
                        }

                        MyApplication.showToast("修改成功");
                        mVipcardInfo.data = mVipcard;
                        EventBus.getDefault().post(mVipcardInfo);// 回传信息
                        finish();
                    }
                });
    }

}
