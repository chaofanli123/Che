package com.victor.che.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.CarBrandListAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.CarBrand;
import com.victor.che.domain.SubCarBrand;
import com.victor.che.slidingmenu.SlidingMenu;
import com.victor.che.ui.fragment.SelectCarSeriesFragment;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ImageLoaderUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.FullyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * 选择汽车品牌
 *
 * @author LUYA
 * @date 2016/3/19
 */
public class SelectCarBrandActivity extends BaseActivity implements AdapterView.OnItemClickListener, IndexableAdapter.OnItemContentClickListener<CarBrand> {

    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    private List<CarBrand> hotBrandList = new ArrayList<>();// 热门品牌列表

    private List<CarBrand> brandList = new ArrayList<>();// 品牌列表
    private CarBrandListAdapter brandAdapter;
    private SlidingMenu menu;
    private SelectCarSeriesFragment mSelectCarSeriesFragment;

    private String type;

    @Override
    public int getContentView() {
        return R.layout.activity_select_car_brand;
    }

    @Override
    protected void initView() {
        // 设置标题
        setTitle("添加爱车");
        // 获取汽车在线品牌列表
        VictorHttpUtil.doGet(mContext, Define.URL_BRAND_SERIES_LIST, null, true, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                if (StringUtil.isEmpty(element.body)) {
                    MyApplication.showToast("返回数据格式不正确");
                    return;
                }
                // 渲染界面
                render(element.body);

            }
        });
        // 设置左侧拉菜单
        mSelectCarSeriesFragment = new SelectCarSeriesFragment();
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.sl_shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // menu视图的Fragment添加
        menu.setMenu(R.layout.sliding_menu_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, mSelectCarSeriesFragment).commit();

        brandAdapter = new CarBrandListAdapter(mContext);
        indexableLayout.setAdapter(brandAdapter);
        brandAdapter.setOnItemContentClickListener(this);

        //        if (StringUtil.isEmpty(cache)) {//缓存为空

        //        } else {//缓存不为空
        //            render(cache);
        //        }

    }


    private void render(String data) {
        JSONObject jsonobj = JSON.parseObject(data);

        // 设置热门品牌
        hotBrandList = JSON.parseArray(jsonobj.getString("hot_brand"), CarBrand.class);
        if (CollectionUtil.isNotEmpty(hotBrandList)) {
            List<CarBrand> temp = new ArrayList<>();//只是为了显示有多少个头部
            temp.add(new CarBrand());
            HeaderAdapter headerAdapter = new HeaderAdapter("热", "热门品牌", temp);
            indexableLayout.addHeaderAdapter(headerAdapter);
        }

        // 设置普通品牌
        brandList = JSON.parseArray(jsonobj.getString("brand"), CarBrand.class);
        if (CollectionUtil.isNotEmpty(brandList)) {
            Collections.sort(brandList);//品牌列表排序
            brandAdapter.setDatas(brandList);
        }
    }

    /**
     * 解决SwipeBackLayout与SlidingMenu的冲突
     */
    //    @Override
    //    public boolean dispatchTouchEvent(MotionEvent ev) {
    //        if (menu.isMenuShowing()) {
    //            if (getSwipeBackLayout().isEnableGesture()) {
    //                setSwipeBackEnable(false);
    //            }
    //        } else {
    //            if (!getSwipeBackLayout().isEnableGesture()) {
    //                setSwipeBackEnable(true);
    //            }
    //        }
    //        return super.dispatchTouchEvent(ev);
    //    }

    /**
     * 点击热门品牌
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 点击任意一个品牌，右侧侧滑出该品牌的所有汽车型号
        showSlidingMenu(hotBrandList.get(position));
    }

    /**
     * 点击普通品牌
     *
     * @param v
     * @param originalPosition
     * @param currentPosition
     * @param entity
     */
    @Override
    public void onItemClick(View v, int originalPosition, int currentPosition, CarBrand entity) {
        showSlidingMenu(entity);
    }

    /**
     * 展示侧滑菜单
     *
     * @param carBrand
     */
    private void showSlidingMenu(final CarBrand carBrand) {
        // 获取对应品牌的车系
        if (carBrand != null) {
            MyParams params = new MyParams();
            params.put("brand_id", carBrand.car_brand_series_id);// 品牌id
            VictorHttpUtil.doGet(mContext, Define.URL_BRAND_SERIES_LIST, params, true, null, new BaseHttpCallbackListener<Element>() {
                @Override
                public void callbackSuccess(String url, Element element) {
                    if (StringUtil.isEmpty(element.body)) {
                        MyApplication.showToast("返回数据格式不正确");
                        return;
                    }
                    List<SubCarBrand> subCarBrandList = JSON.parseArray(element.body,
                            SubCarBrand.class);
                    // 传参到fragment
                    EventBus.getDefault().post(carBrand);
                    EventBus.getDefault().post(subCarBrandList);
                }
            });
        }
        menu.showMenu();
    }
    /**
     * 热门品牌适配器
     */
    private class HotBrandsAdapter extends BaseQuickAdapter<CarBrand, BaseViewHolder> {

        public HotBrandsAdapter(int layoutResId, List<CarBrand> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, CarBrand item) {
            // 汽车品牌logo
            ImageView iv_car_logo = helper.getView(R.id.iv_car_logo);
            ImageLoaderUtil.display(mContext, iv_car_logo, item.image, R.drawable.ic_car_pre, R.drawable.ic_car_pre);

            // 汽车品牌名称
            helper.setText(R.id.tv_car_brand_name, item.name);

            // 点击事件
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showSlidingMenu(hotBrandList.get(helper.getLayoutPosition()));
                }
            });
        }
    }

    /**
     * 自定义的Banner Header
     */
    class HeaderAdapter extends IndexableHeaderAdapter {
        private static final int TYPE = 2;

        public HeaderAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.include_header_hot_brands, parent, false);
            VH holder = new VH(view);
            holder.gv_hot_brands.setAdapter(new HotBrandsAdapter(R.layout.item_hot_car_brand_vertical, hotBrandList));
            return holder;
        }
        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            // 数据源为null时, 该方法不用实现
        }

        private class VH extends RecyclerView.ViewHolder {
            RecyclerView gv_hot_brands;

            public VH(View itemView) {
                super(itemView);
                gv_hot_brands = (RecyclerView) itemView.findViewById(R.id.gv_hot_brands);
                gv_hot_brands.setLayoutManager(new FullyGridLayoutManager(mContext, 5));
            }
        }
    }
}
