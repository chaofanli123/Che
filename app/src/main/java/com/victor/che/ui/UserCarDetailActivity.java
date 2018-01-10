package com.victor.che.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.UserCarDetail;

import butterknife.BindView;

/**
 * @autor Victor
 * @email 468034043@qq.com
 * @time 2017/4/17 0017 10:19.
 * 二手车估值记录详情
 */

public class UserCarDetailActivity extends BaseActivity {


    @BindView(R.id.tv_car_purchase_price)
    TextView tvCarPurchasePrice;
    @BindView(R.id.tv_car_sale_price)
    TextView tvCarSalePrice;
    @BindView(R.id.tv_car_model_name)
    TextView tvCarModelName;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.tv_used_date)
    TextView tvUsedDate;
    @BindView(R.id.tv_mileage)
    TextView tvMileage;
    @BindView(R.id.tv_car_status)
    TextView tvCarStatus;
    @BindView(R.id.tv_car_purpose)
    TextView tvCarPurpose;
    @BindView(R.id.tv_car_buy_price)
    TextView tvCarBuyPrice;

    @Override
    public int getContentView() {
        return R.layout.activity_user_car_success;
    }


    @Override
    protected void initView() {
        super.initView();

        int usedcar_assess_record_id = getIntent().getIntExtra("usedcar_assess_record_id", 1);
        String car_plate_no = getIntent().getStringExtra("car_plate_no");
        setTitle(TextUtils.isEmpty(car_plate_no)?"估值详情":car_plate_no);

        MyParams params = new MyParams();
        params.put("usedcar_assess_record_id", usedcar_assess_record_id);
        VictorHttpUtil.doGet(mContext, Define.url_usedcar_history_detail_list_v1, params, true, "加载中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                super.callbackSuccess(url, element);

                Gson gson = new Gson();
                UserCarDetail userCarDetail = gson.fromJson(element.data, UserCarDetail.class);
                if (userCarDetail == null) {//查询失败

                    showDialog();

                } else {
                    tvCarPurchasePrice.setText(userCarDetail.purchase_price+"万元");
                    tvCarSalePrice.setText(userCarDetail.deal_price+"万元");
                    tvCarModelName.setText(userCarDetail.car_model_name);
                    tvCityName.setText(userCarDetail.city_name);
                    tvUsedDate.setText(userCarDetail.used_date);
                    tvMileage.setText(userCarDetail.mileage+"万公里");
                    tvCarStatus.setText(userCarDetail.car_status_text);
                    tvCarPurpose.setText(userCarDetail.purpose_text);
                    tvCarBuyPrice.setText(userCarDetail.buy_price+"万元");
                }
            }
        });

    }

    /**
     * 显示对话框
     */
    private void showDialog() {
        String msg = "查询失败，请稍后重试！";
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog alertDialog = builder.create();
        builder.setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

}
