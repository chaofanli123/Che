package com.victor.che.ui;

import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.RecyclerViewItemChangedEvent;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑会员卡界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/6 0006 9:30
 */
public class EditVipcardActivity extends BaseActivity {

    @BindView(R.id.tv_vipcard_no)
    TextView tv_vipcard_no;

    @BindView(R.id.tv_remain_value)
    TextView tv_remain_value;

    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    @BindView(R.id.label_remain_value)
    TextView label_remain_value;

    private Vipcard mVipcard;
    private int mPosition;

    @Override
    public int getContentView() {
        return R.layout.activity_edit_vipcard;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("编辑会员卡");

        mPosition = getIntent().getIntExtra("mPosition", 0);
        mVipcard = (Vipcard) getIntent().getSerializableExtra("mVipcard");

        if (mVipcard == null) {
            MyApplication.showToast("会员卡为空，请稍后重试");
            return;
        }
        // 数据回显
        tv_vipcard_no.setText(mVipcard.card_no);
        label_remain_value.setText(mVipcard.getCardTypeLabel());
        tv_remain_value.setText(mVipcard.getRemainValue());
        tv_end_time.setText(mVipcard.getEndTime());
    }

    /**
     * 显示时间选择的对话框
     */
    @OnClick(R.id.area_end_time)
    void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();

        final TextView curEditText = tv_end_time;
        String text = curEditText.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }

        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new com.jzxiang.pickerview.listener.OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        curEditText.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())//设置最小时间
                .setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 保存
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {
        final String end_time = tv_end_time.getText().toString().trim();

        if (end_time.equalsIgnoreCase(mVipcard.getEndTime())) {//未发生更改
            finish();
            return;
        }

        // 提交修改
        // 请求数据
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id
        params.put("provider_user_card_id", mVipcard.provider_user_card_id);// 用户卡id
        // 用户手机号---mobile 会员卡有效期--end_time 次数---vip_num 余额----vip_eb
        String data = String.format("end_time=%s", end_time);
        params.put("data", data);//需要修改的用户信息; 数据格式：字段=值,字段1=值1 (修改信息对应字段参考下面的备注)
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_CARD_EDIT, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        MyApplication.showToast("修改成功");

                        // 通知用户会员卡列表某一项改变
                        mVipcard.end_time = end_time;
                        RecyclerViewItemChangedEvent event = new RecyclerViewItemChangedEvent(mPosition, mVipcard);
                        EventBus.getDefault().post(event);

                        finish();
                    }
                });
    }
}
