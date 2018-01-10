package com.victor.che.ui;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.adapter.CarPinpaiListAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.CarPingpai;
import com.victor.che.domain.SubCarPinpai;
import com.victor.che.slidingmenu.SlidingMenu;
import com.victor.che.ui.fragment.SelectCarPinpaiSeriesFragment;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
//, IndexableAdapter.OnItemContentClickListener<CarPingpai>

public class SelectCarNumActivity extends BaseActivity implements IndexableAdapter.OnItemContentClickListener<CarPingpai>{


    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;
    private List<CarPingpai> brandList = new ArrayList<>();// 品牌列表
    private CarPinpaiListAdapter brandAdapter;
    private SelectCarPinpaiSeriesFragment mSelectCarSeriesFragment;
    private SlidingMenu menu;

    @Override
    public int getContentView() {
        return R.layout.activity_select_car_num;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
            setTitle("品牌选择");
        //先从本地获取
            // 获取汽车在线品牌列表
            VictorHttpUtil.doGet(mContext, Define.url_usedcar_brand_series_list_v1, null, true, null, new BaseHttpCallbackListener<Element>() {
                @Override
                public void callbackSuccess(String url, Element element) {
                    if (StringUtil.isEmpty(element.data)) {
                        MyApplication.showToast("返回数据格式不正确");
                        return;
                    }
                    // 展示界面，不带热门品牌
                    render_common(element.data);

                }
            });

        // 设置左侧拉菜单
        mSelectCarSeriesFragment = new SelectCarPinpaiSeriesFragment();
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

        brandAdapter = new CarPinpaiListAdapter(mContext);
        indexableLayout.setAdapter(brandAdapter);
        brandAdapter.setOnItemContentClickListener(this);

    }


    /**
     * 展示界面，不带热门品牌
     * @param data
     */
    private void render_common(String data) {
        brandList= JSON.parseArray(data, CarPingpai.class);// 保存到全局
// 设置普通品牌
        if (CollectionUtil.isNotEmpty(brandList)) {
           // Collections.sort(brandList);//品牌列表排序
            brandAdapter.setDatas(brandList);
        }

    }

    @Override
    public void onItemClick(View v, int originalPosition, int currentPosition, CarPingpai entity) {
        showSlidingMenu(entity);
    }
    /**
     * 展示侧滑菜单
     *
     * @param carBrand
     */
    private void showSlidingMenu(final CarPingpai carBrand) {
        // 获取对应品牌的车系
        if (carBrand != null) {
            MyParams params = new MyParams();
            params.put("brand_id", carBrand.brand_series_id);// 品牌id
            VictorHttpUtil.doGet(mContext, Define.url_usedcar_brand_series_list_v1, params, true, null, new BaseHttpCallbackListener<Element>() {
                @Override
                public void callbackSuccess(String url, Element element) {
                    if (StringUtil.isEmpty(element.data)) {
                        MyApplication.showToast("返回数据格式不正确");
                        return;
                    }
                    List<SubCarPinpai> subCarBrandList = JSON.parseArray(element.data,
                            SubCarPinpai.class);
                    // 传参到fragment
                   EventBus.getDefault().post(carBrand);
                    EventBus.getDefault().post(subCarBrandList);
                }
            });
        }
        menu.showMenu();
    }
}
