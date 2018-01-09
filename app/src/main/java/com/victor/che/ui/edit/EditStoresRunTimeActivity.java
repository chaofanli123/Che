package com.victor.che.ui.edit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.event.ProvEditEvent;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改门店营业时间信息
 */
public class EditStoresRunTimeActivity extends BaseActivity {

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private String start_time,end_time;
    @Override
    public int getContentView() {
        return R.layout.activity_edit_stores_name;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("营业时间");
        start_time=getIntent().getStringExtra("start_time");
        end_time=getIntent().getStringExtra("end_time");
        if (!StringUtil.isEmpty(start_time)) {
            tvStartTime.setText(start_time);
        }
        if (!StringUtil.isEmpty(end_time)) {
            tvEndTime.setText(end_time);
        }
    }
    @OnClick({R.id.topbar_left, R.id.topbar_right, R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topbar_left:
                finish();
                break;
            case R.id.topbar_right://保存
                // 非空校验
               String start= tvStartTime.getText().toString().trim();
                if (StringUtil.isEmpty(tvStartTime.getText().toString().trim())) {
                    MyApplication.showToast("开始时间不能为空");
                    tvStartTime.requestFocus();
                    return;
                }
                // 非空校验
                String end=tvEndTime.getText().toString().trim();
                if (StringUtil.isEmpty(tvEndTime.getText().toString().trim())) {
                    MyApplication.showToast("结束时间不能为空");
                    tvEndTime.requestFocus();
                    return;
                }
                start= start.replace(":","");
                end= end.replace(":","");
                if (Integer.parseInt(start)>=Integer.parseInt(end)) {
                    MyApplication.showToast("请输入正确的时间区间");
                    return;
                }
                String data="business_start_time="+tvStartTime.getText().toString().trim()+","+"business_end_time="+tvEndTime.getText().toString().trim();
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
                                ProvEditEvent event = new ProvEditEvent(tvStartTime.getText().toString().trim(),tvEndTime.getText().toString().trim());
                                EventBus.getDefault().post(event);
                                finish();
                            }
                        });
                break;
            case R.id.tv_start_time:
                showDatePickerDialog(tvStartTime);
                break;
            case R.id.tv_end_time:
                showDatePickerDialog(tvEndTime);
                break;
        }
    }
    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog(final TextView time) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = time.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.HM);
            calendar.setTime(date == null ? new Date() : date);
        }
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        time.setText(DateUtil.getStringByFormat(millseconds, DateUtil.HM));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.HOURS_MINS)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }
}
