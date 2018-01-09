package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.simplelistview.SimpleListView;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.CarBrand;
import com.victor.che.domain.CarChexi;
import com.victor.che.domain.CarChexing;
import com.victor.che.domain.CarPingpai;
import com.victor.che.domain.CarSeries;
import com.victor.che.domain.SubCarBrand;
import com.victor.che.domain.SubCarPinpai;
import com.victor.che.domain.SubCardata;
import com.victor.che.event.SelectCarBrandAndSeriesEvent;
import com.victor.che.event.SelectCarPingpaiAndSeriesAndxilieEvent;
import com.victor.che.ui.fragment.SelectCarPinpaiSeriesFragment;
import com.victor.che.ui.fragment.SelectCarSeriesFragment;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CarxingActivity extends BaseActivity {


    @BindView(R.id.tv_car_brand_name)
    TextView tvCarBrandName;    //车系名
    @BindView(R.id.lv_sub_brand)
    ListView lvSubBrand;  //车型listview

    private MyListAdapter mAdapter;

    private ArrayList<SubCarBrand> subCarBrandList;// 汽车车系列表

    private CarPingpai carBrand;// 汽车品牌实体
    private String subCarPinpaiId; //汽车车系id
    private CarChexi  chexi;
    private List<SubCardata> subCardataList;


    @Override
    public int getContentView() {
        return R.layout.activity_carxing;
    }

    @Override
    protected void initView() {
        super.initView();
        carBrand= (CarPingpai) getIntent().getSerializableExtra("carpingpai");
        subCarPinpaiId =  getIntent().getStringExtra("carchexiId");
        chexi= (CarChexi) getIntent().getSerializableExtra("chexi");
        mAdapter = new MyListAdapter(mContext, R.layout.item_sub_car_brand);
        tvCarBrandName.setText(chexi.name);
        lvSubBrand.setAdapter(mAdapter);
        //获取车型列表
        // 获取对应品牌的车系
        if (carBrand != null) {
            MyParams params = new MyParams();
            params.put("brand_id", carBrand.brand_series_id);// 品牌id
            params.put("series_id", subCarPinpaiId);// 车系id
            VictorHttpUtil.doGet(mContext, Define.url_usedcar_brand_series_list_v1, params, true, null, new BaseHttpCallbackListener<Element>() {
                @Override
                public void callbackSuccess(String url, Element element) {
                    if (StringUtil.isEmpty(element.data)) {
                        MyApplication.showToast("返回数据格式不正确");
                        return;
                    }
                    subCardataList = JSON.parseArray(element.data,
                            SubCardata.class);
                    mAdapter.setData(subCardataList);
                    mAdapter.notifyDataSetChanged();

                }
            });
        }
    }

    /**
     * 汽车子品牌列表适配器
     *
     * @author Victor
     * @email 468034043@qq.com
     * @time 2016年3月22日 下午10:42:30
     */
    class MyListAdapter extends VictorBaseListAdapter<SubCardata> {

        public MyListAdapter(Context context, int layoutResId) {
            super(context, layoutResId);
        }
        @Override
        public void bindView(int position, View view, final SubCardata entity) {
            // 子品牌名称
            TextView tv_sub_car_brand_name = AbViewHolder.get(view, R.id.tv_sub_car_brand_name);
            tv_sub_car_brand_name.setText(entity.sale_year);

            // 车系列表
            SimpleListView lv_car_series = AbViewHolder.get(view, R.id.lv_car_series);
            lv_car_series.setAdapter(
                    new VictorBaseListAdapter<CarChexing>(context, R.layout.item_car_series, entity.getCarSeries()) {

                        @Override
                        public void bindView(int position, View view, CarChexing entity) {
                            // 车系名称
                            TextView tv_car_series_name = AbViewHolder.get(view, R.id.tv_car_series_name);
                            tv_car_series_name.setText(entity.name);
                        }
                    });
            lv_car_series.setOnItemClickListener(new SimpleListView.OnItemClickListener() {
                @Override
                public void onItemClick(Object parent, View view, int position) {
                    // 点击车系后回到添加车辆界面（Event事件）
                    SelectCarPingpaiAndSeriesAndxilieEvent event = new SelectCarPingpaiAndSeriesAndxilieEvent(carBrand,chexi,entity.getCarSeries().get(position));

                    EventBus.getDefault().post(event);
                    finish();
                }

            });
        }

    }

    }




