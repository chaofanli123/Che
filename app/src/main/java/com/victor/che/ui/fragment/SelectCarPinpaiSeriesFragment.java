package com.victor.che.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.simplelistview.SimpleListView;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.CarChexi;
import com.victor.che.domain.CarPingpai;
import com.victor.che.domain.SubCarPinpai;
import com.victor.che.ui.CarxingActivity;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 选择车系侧滑菜单
 *
 * @author LUYA
 * @date 2016/3/19
 */
public class SelectCarPinpaiSeriesFragment extends BaseFragment {

    private CircleImageView iv_car_logo;
    private TextView tv_car_brand_name;
    private ListView lv_sub_brand;
    private SubCarPingpaiListAdapter mAdapter;

    private CarPingpai carBrand;// 汽车品牌实体
    private List<SubCarPinpai> subCarBrandList;// 汽车车系列表

    @Override
    public int getContentView() {
        return R.layout.fragment_select_car_series;
    }

    @Override
    protected void initView() {
        lv_sub_brand = (ListView) findViewById(R.id.lv_sub_brand);
        mAdapter = new SubCarPingpaiListAdapter(mContext, R.layout.item_sub_car_brand);
        lv_sub_brand.setAdapter(mAdapter);
    }
    /**
     * 获取汽车品牌
     *
     * @param carBrand
     */
    @Subscribe
    public void onEventMainThread(CarPingpai carBrand) {
        this.carBrand = carBrand;
        if (carBrand == null) {
            return;
        }
        // 品牌logo
        iv_car_logo = (CircleImageView) findViewById(R.id.iv_car_logo);
        ImageLoaderUtil.display(mContext, iv_car_logo, carBrand.image_src);

        // 品牌名称
        tv_car_brand_name = (TextView) findViewById(R.id.tv_car_brand_name);
        tv_car_brand_name.setText(carBrand.name);
    }
    /**
     * 获取汽车车系列表
     *
     * @param subCarBrandList
     */
    @Subscribe
    public void onEventMainThread(List<SubCarPinpai> subCarBrandList) {
        this.subCarBrandList = subCarBrandList;
        if (CollectionUtil.isEmpty(subCarBrandList)) {
            return;
        }
        mAdapter.setData(subCarBrandList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 汽车子品牌列表适配器
     *
     * @author Victor
     * @email 468034043@qq.com
     * @time 2016年3月22日 下午10:42:30
     */
    private class SubCarPingpaiListAdapter extends VictorBaseListAdapter<SubCarPinpai> {

        public SubCarPingpaiListAdapter(Context context, int layoutResId) {
            super(context, layoutResId);
        }

        @Override
        public void bindView(int position, View view, final SubCarPinpai entity) {
            List<CarChexi> carSeries = entity.getCarSeries();
            // 子品牌名称
            TextView tv_sub_car_brand_name = AbViewHolder.get(view, R.id.tv_sub_car_brand_name);
            tv_sub_car_brand_name.setText(entity.name);

            // 车系列表
            SimpleListView lv_car_series = AbViewHolder.get(view, R.id.lv_car_series);
            lv_car_series.setAdapter(
                    new VictorBaseListAdapter<CarChexi>(context, R.layout.item_car_series, carSeries) {

                        @Override
                        public void bindView(int position, View view, CarChexi entity) {
                            // 车系名称
                            TextView tv_car_series_name = AbViewHolder.get(view, R.id.tv_car_series_name);
                            tv_car_series_name.setText(entity.getName());
                        }
                    });
            lv_car_series.setOnItemClickListener(new SimpleListView.OnItemClickListener() {
                @Override
                public void onItemClick(Object parent, View view, int position) {
                  //   点击车系后回到添加车辆界面（Event事件）
                    /*SelectCarPingpaiAndSeriesAndxilieEvent event = new SelectCarPingpaiAndSeriesAndxilieEvent(carBrand, entity.getCarSeries().get(position)));
                    EventBus.getDefault().post(event);*/
                    //进入车型界面
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("carpingpai",carBrand); //汽车品牌
                    bundle.putString("carchexiId",entity.getCarSeries().get(position).getBrand_series_id());//汽车车系
                    bundle.putSerializable("chexi",entity.getCarSeries().get(position));
                    MyApplication.openActivity(mContext, CarxingActivity.class,bundle);
                    getActivity().finish();
                }
            });
        }

    }

}
