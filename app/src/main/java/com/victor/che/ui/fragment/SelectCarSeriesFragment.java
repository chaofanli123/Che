package com.victor.che.ui.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.simplelistview.SimpleListView;
import com.victor.che.R;
import com.victor.che.base.BaseFragment;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.CarBrand;
import com.victor.che.domain.CarSeries;
import com.victor.che.domain.SubCarBrand;
import com.victor.che.event.SelectCarBrandAndSeriesEvent;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 选择车系侧滑菜单
 *
 * @author LUYA
 * @date 2016/3/19
 */
public class SelectCarSeriesFragment extends BaseFragment {

    private CircleImageView iv_car_logo;
    private TextView tv_car_brand_name;
    private ListView lv_sub_brand;
    private SubCarBrandListAdapter mAdapter;

    private CarBrand carBrand;// 汽车品牌实体
    private ArrayList<SubCarBrand> subCarBrandList;// 汽车车系列表

    @Override
    public int getContentView() {
        return R.layout.fragment_select_car_series;
    }

    @Override
    protected void initView() {
        lv_sub_brand = (ListView) findViewById(R.id.lv_sub_brand);
        mAdapter = new SubCarBrandListAdapter(mContext, R.layout.item_sub_car_brand);
        lv_sub_brand.setAdapter(mAdapter);
    }
    /**
     * 获取汽车品牌
     *
     * @param carBrand
     */
    @Subscribe
    public void onEventMainThread(CarBrand carBrand) {
        this.carBrand = carBrand;
        if (carBrand == null) {
            return;
        }
        // 品牌logo
        iv_car_logo = (CircleImageView) findViewById(R.id.iv_car_logo);
        ImageLoaderUtil.display(mContext, iv_car_logo, carBrand.image);

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
    public void onEventMainThread(ArrayList<SubCarBrand> subCarBrandList) {
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
    private class SubCarBrandListAdapter extends VictorBaseListAdapter<SubCarBrand> {

        public SubCarBrandListAdapter(Context context, int layoutResId) {
            super(context, layoutResId);
        }
        @Override
        public void bindView(int position, View view, final SubCarBrand entity) {
            // 子品牌名称
            TextView tv_sub_car_brand_name = AbViewHolder.get(view, R.id.tv_sub_car_brand_name);
            tv_sub_car_brand_name.setText(entity.name);

            // 车系列表
            SimpleListView lv_car_series = AbViewHolder.get(view, R.id.lv_car_series);
            lv_car_series.setAdapter(
                    new VictorBaseListAdapter<CarSeries>(context, R.layout.item_car_series, entity.getCarSeries()) {

                        @Override
                        public void bindView(int position, View view, CarSeries entity) {
                            // 车系名称
                            TextView tv_car_series_name = AbViewHolder.get(view, R.id.tv_car_series_name);
                            tv_car_series_name.setText(entity.name);
                        }
                    });
            lv_car_series.setOnItemClickListener(new SimpleListView.OnItemClickListener() {
                @Override
                public void onItemClick(Object parent, View view, int position) {
                    // 点击车系后回到添加车辆界面（Event事件）
                    SelectCarBrandAndSeriesEvent event = new SelectCarBrandAndSeriesEvent(carBrand, entity.getCarSeries().get(position));
                    EventBus.getDefault().post(event);
                    getActivity().finish();
                }
            });
        }

    }

}
