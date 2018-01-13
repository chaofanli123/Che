package com.victor.che.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.UserDetalis;
import com.victor.che.event.SelectCarBrandAndSeriesEvent;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 车辆详情界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 17:27
 */
public class CarDetailsActivity extends BaseActivity {

    @BindView(R.id.et_pln)
    EditText et_pln;

    @BindView(R.id.iv_car_logo)
    ImageView iv_car_logo;

    @BindView(R.id.tv_customer_car)
    TextView tv_customer_car;

    private UserDetalis.CarBean mCar;
    private String provider_user_id;

    @Override
    public int getContentView() {
        return R.layout.activity_car_details;
    }

    @Override
    protected void initView() {
        super.initView();

        mCar = (UserDetalis.CarBean) getIntent().getSerializableExtra("mCar");
        provider_user_id = getIntent().getStringExtra("provider_user_id");

        // 设置标题
        setTitle(mCar == null ? "添加车辆" : "车辆详情");

        if (mCar != null) {
            et_pln.setText(mCar.getCar_plate_no());
            tv_customer_car.setText(mCar.getCar_brand_series());
            ImageLoaderUtil.display(mContext, iv_car_logo, mCar.getImage(), R.drawable.ic_car_pre, R.drawable.ic_car_pre);
        }
    }

    @OnClick(R.id.area_car_brand_series)
    void gotoSelectCarBrandSeries() {
        MyApplication.openActivity(mContext, SelectCarBrandActivity.class);
    }
    private String brand_id = "";
    private String series_id = "";

    /**
     * 选择了汽车品牌和车系后
     */
    @Subscribe
    public void onSelectedCarBrandSeries(SelectCarBrandAndSeriesEvent event) {
        if (event == null) {
            return;
        }
        iv_car_logo.setVisibility(View.VISIBLE);
        // 车品牌logo
        ImageLoaderUtil.display(mContext, iv_car_logo, event.carBrand.image);
        // 车品牌和车系名称
        tv_customer_car.setText(event.carBrand.name + event.carSeries.name);

        // 修改车辆的品牌和车系
        this.brand_id = event.carBrand.car_brand_series_id;
        this.series_id = event.carSeries.car_brand_series_id;
    }

    /**
     * 提交
     */
    @OnClick(R.id.topbar_right)
    void doOperate() {
        String pln = et_pln.getText().toString().trim();
        if (!StringUtil.isEmpty(pln) && !StringUtil.isPln(pln)) {
            MyApplication.showToast("车牌号格式不正确");
            et_pln.requestFocus();
            return;
        }
        if (StringUtil.isEmpty(brand_id) && mCar != null) {
            brand_id = mCar.getCar_brand_id()+"";
        }
        if (StringUtil.isEmpty(series_id) && mCar != null) {
            series_id = mCar.getCar_brand_series_id()+"";
        }
        if (mCar == null) {//添加车辆
            MyParams params = new MyParams();
            params.put("provider_user_id", provider_user_id);// 编辑和删除车辆时必传，设置默认非必传
            params.put("car_brand_id", brand_id);// 品牌id, 编辑车辆必传
            params.put("car_brand_series_id", series_id);// 车系id, 编辑车辆必传
            params.put("car_plate_no", pln);// 车牌号, 编辑车辆必传
            VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_CAR_ADD, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            _doAddEditCar(1, element.body);
                        }
                    });
        } else {//修改车辆
            MyParams params = new MyParams();
            params.put("provider_user_car_id", mCar.getProvider_user_car_id());// 用户车辆id
            params.put("provider_user_id", provider_user_id);// 编辑和删除车辆时必传，设置默认非必传
            params.put("car_brand_id", brand_id);// 品牌id, 编辑车辆必传
            params.put("car_brand_series_id", series_id);// 车系id, 编辑车辆必传
            params.put("car_plate_no", pln);// 车牌号, 编辑车辆必传
            params.put("type", "edit");// 操作类型： edit-更新 del-删除 set-设为默认
            VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_CAR_EDIT, params, true, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            _doAddEditCar(2, element.body);
                        }
                    });
        }

    }

    private void _doAddEditCar(int i, String data) {
        if (StringUtil.isEmpty(data)) {//
            MyApplication.showToast(i == 1 ? "车辆添加成功" : "车辆修改成功");
            EventBus.getDefault().post(ConstantValue.Event.REFRESH_CAR_LIST);
            finish();
        } else {
            JSONObject jsonObject = JSON.parseObject(data);
            if (jsonObject != null) {
                int exist_user = jsonObject.getIntValue("exist_user");
                final String provider_user_id = jsonObject.getString("provider_user_id");
                if (exist_user == 2) {//车牌号已存在
                    AlertDialogFragment.newInstance(
                            "提示",
                            "车牌号已存在，是否编辑已存在的用户？",
                            "取消",
                            "编辑",
                            null,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("provider_user_id", provider_user_id);
                                    MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
                                }
                            })
                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                }
            }
        }
    }

}
