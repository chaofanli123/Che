package com.victor.che.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.victor.che.base.BaseFragment;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.event.Region;
import com.victor.che.event.SelectCarPingpaiAndSeriesAndxilieEvent;
import com.victor.che.ui.SelectCarNumActivity;
import com.victor.che.ui.SelectServiceAreaActivity;
import com.victor.che.util.DateUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.BottomDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewaddUserCarFragment extends BaseFragment {
    @BindView(R.id.tv_area_carnum)
    TextView tvAreaCarnum;
    @BindView(R.id.area_carnum)
    RelativeLayout areaCarnum;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.area_city)
    RelativeLayout areaCity;
    @BindView(R.id.tv_first_time)
    TextView tvFirstTime;
    @BindView(R.id.area_first_time)
    RelativeLayout areaFirstTime;
    @BindView(R.id.et_rundistance)
    TextView etRundistance;
    @BindView(R.id.tv_car_message)
    TextView tvCarMessage;
    @BindView(R.id.area_car_message)
    RelativeLayout areaCarMessage;
    @BindView(R.id.tv_car_use)
    TextView tvCarUse;
    @BindView(R.id.area_car_use)
    RelativeLayout areaCarUse;
    @BindView(R.id.tv_car_price)
    EditText tvCarPrice;
    @BindView(R.id.btn_values_submit)
    Button btnValuesSubmit;
    Unbinder unbinder;

    private int selectedProductPos = 0;//选中的产品位置

    private SelectListAdapter selectedAdapter,carUseAdapter;

    private List<String> workerList =new ArrayList<String>();
    private List<String> carUseList =new ArrayList<String>();

    private int city_id;
    private  String brand_id;
    private String brand_series_id;//
    private String car_model_id;
    private int provider_user_id;


    @Override
    public int getContentView() {
        return R.layout.fragment_newadd_user_car;
    }

    @Override
    protected void initView() {
        super.initView();
        workerList.add("优秀");
        workerList.add("一般");
        workerList.add("较差");
        carUseList.add("自用");
        carUseList.add("公务商用");
        carUseList.add("营运");
        selectedAdapter = new SelectListAdapter(mContext, R.layout.item_bottom_dialog, workerList);
        carUseAdapter = new SelectListAdapter(mContext, R.layout.item_bottom_dialog, carUseList);
        String user_id = getArguments().getString("provider_user_id");
        if(!TextUtils.isEmpty(user_id))
        provider_user_id = Integer.parseInt(user_id);
    }

    @OnClick({R.id.area_carnum, R.id.area_city, R.id.area_first_time, R.id.area_car_message, R.id.area_car_use, R.id.btn_values_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.area_carnum://车辆型号
                Bundle build=new Bundle();
                build.putString("type","NewaddUserCar");
                MyApplication.openActivity(mContext, SelectCarNumActivity.class,build);
                break;
            case R.id.area_city://所在城市
                MyApplication.openActivity(mContext, SelectServiceAreaActivity.class);
                break;
            case R.id.area_first_time://上牌时间
                //显示时间对话框
                showDatePickerDialog();
                break;
            case R.id.area_car_message://车况
                BottomDialogFragment.newInstance(selectedAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedProductPos = position;
                        selectedAdapter.notifyDataSetChanged();
                        tvCarMessage.setText(workerList.get(position));
                    }
                }).show(getFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.area_car_use://车辆用途

                BottomDialogFragment.newInstance(carUseAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedProductPos = position;
                        selectedAdapter.notifyDataSetChanged();
                        tvCarUse.setText(carUseList.get(position));
                    }
                }).show(getFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.btn_values_submit://开始估值按钮
                _startvalue();
                break;
        }
    }

    private void _startvalue() {
        String sareacarnum = tvAreaCarnum.getText().toString().trim();//车牌型号
        if (TextUtils.isEmpty(sareacarnum)) {
            MyApplication.showToast("车牌型号不能为空");
            tvAreaCarnum.requestFocus();
            return;
        }
        String scity = tvCity.getText().toString().trim();//所在城市
        if (TextUtils.isEmpty(scity)) {
            MyApplication.showToast("所在城市不能为空");
            tvCity.requestFocus();
            return;
        }
        String stvcaruser = tvCarUse.getText().toString().trim();//车辆用途
        if (TextUtils.isEmpty(stvcaruser)) {
            MyApplication.showToast("车辆用途不能为空");
            tvCarUse.requestFocus();
            return;
        }
        if ("自用".equals(stvcaruser)) {
            stvcaruser="1";
        }else if ("公务商用".equals(stvcaruser)) {
            stvcaruser="2";
        }else if ("营运".equals(stvcaruser)) {
            stvcaruser="3";
        }

        String stvcarmessage = tvCarMessage.getText().toString().trim();//车况
        if (TextUtils.isEmpty(stvcarmessage)) {
            MyApplication.showToast("车况不能为空");
            tvCarMessage.requestFocus();
            return;
        }
        if ("优秀".equals(stvcarmessage)) {
            stvcarmessage="1";
        }else if ("一般".equals(stvcarmessage)) {
            stvcarmessage="2";
        }else if ("较差".equals(stvcarmessage)) {
            stvcarmessage="3";
        }


        String stvtime = tvFirstTime.getText().toString().trim();//上牌时间
        String time = stvtime.substring(0, 7);
        if (TextUtils.isEmpty(stvtime)) {
            MyApplication.showToast("上牌时间不能为空");
            tvFirstTime.requestFocus();
            return;
        }
        String stvrundiantance = etRundistance.getText().toString().trim();//行驶里程
        if (TextUtils.isEmpty(stvrundiantance)) {
            MyApplication.showToast("行驶里程不能为空");
            etRundistance.requestFocus();
            return;
        }
        String stvcarprice = tvCarPrice.getText().toString().trim();//购买价格
        if (TextUtils.isEmpty(stvcarprice)) {
            MyApplication.showToast("购买价格不能为空");
            tvCarPrice.requestFocus();
            return;
        }

        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id

        if(provider_user_id!=0)
         params.put("provider_user_car_id", provider_user_id);//用户车辆id 否

        params.put("city_id", city_id);//城市id

        params.put("brand_id", brand_id);//品牌id

        params.put("brand_series_id",brand_series_id);//车系id
        params.put("car_model_id", car_model_id);//车型id
        params.put("purpose", stvcaruser);//车辆用途：1自用 2公务商用 3营运
        params.put("car_status", stvcarmessage);//车况：1优秀 2一般 3较差
        params.put("used_date", time);// 上牌时间： 格式yyyy-mm
        params.put("mileage", stvrundiantance);// 公里数
        params.put("purchase_price", stvcarprice);// 购买价格

        VictorHttpUtil.doPost(mContext, Define.url_usedcar_assess_car_assess_v1, params, true, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                if (element.code == 0) {
                    //跳转到历史估值界面
                    EventBus.getDefault().post("UsercarHistoryValuesFragment");// 发送信息给webview
                }else {
                    MyApplication.showToast(element.msg);
                }

               // MyApplication.openActivity(mContext, UsercarHistoryValuesFragment.class);
            }
        });
    }

    /**
     * 选择城市以后
     * @param region
     */
    @Subscribe
    public void SelectCityEvent(Region region) {
        if (region == null) {
            return;
        }
        city_id = region.area_id;
        tvCity.setText(region.name);
    }
    /**
     * x选择车辆型号以后
     */
    @Subscribe
   public void onSelectChexing(SelectCarPingpaiAndSeriesAndxilieEvent event){
        if (event==null) {
            return;
        }
        brand_id =event.carBrand.brand_series_id;
        brand_series_id = event.carSeries.getBrand_series_id();
        car_model_id = event.chexing.car_model_id;
      //  tvAreaCarnum.setText(event.carBrand.name+event.carSeries.name+event.chexing.getName());
        tvAreaCarnum.setText(event.chexing.getName());
   }
    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = tvFirstTime.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }

        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        tvFirstTime.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YM));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")

                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                //.setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis()-_100year)//设置最小时间
                //.setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getActivity().getSupportFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 选择车况列表适配器
     */
    private class SelectListAdapter extends VictorBaseListAdapter<String> {

        public SelectListAdapter(Context context, int layoutResId, List<String> mList) {
            super(context, layoutResId, mList);
        }
        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            textView.setTextColor(getResources().getColor(selectedProductPos == position ? R.color.theme_color : R.color.black_text));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(selectedAdapter!=null)
            selectedAdapter=null;
        if(carUseAdapter!=null)
            carUseAdapter=null;
    }
}
