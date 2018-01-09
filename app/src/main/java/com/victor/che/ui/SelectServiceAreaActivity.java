package com.victor.che.ui;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

        import com.alibaba.fastjson.JSON;
        import com.victor.che.R;
        import com.victor.che.adapter.CityAdapter;
        import com.victor.che.api.BaseHttpCallbackListener;
        import com.victor.che.api.Define;
        import com.victor.che.api.Element;
        import com.victor.che.api.VictorHttpUtil;
        import com.victor.che.app.MyApplication;
        import com.victor.che.base.BaseActivity;
        import com.victor.che.event.Region;

        import org.greenrobot.eventbus.EventBus;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import me.yokeyword.indexablerv.IndexableAdapter;
        import me.yokeyword.indexablerv.IndexableLayout;

public class SelectServiceAreaActivity extends BaseActivity implements IndexableAdapter.OnItemContentClickListener<Region>{


    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    private CityAdapter mAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_select_service_area;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTopbarTitle.setText("选择城市");
        mAdapter = new CityAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        mAdapter.setOnItemContentClickListener(this);
      /* 获取服务城市列表 */
        reqServiceAreas();

    }

    /**
     * 从json文件里面获取
     */
    private void reqServiceAreas() {
        VictorHttpUtil.doGet(mContext, Define.url_usedcar_area_list_v1, null, true, "加载中...", new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {
                MyApplication.RegionList = (ArrayList<Region>) JSON.parseArray(element.data, Region.class);// 保存到全局
                MyApplication.saveRegionList();// 保存城市列表
                // 渲染服务地区列表
                renderRegionList();
            }
        });
    }

    /**
     * 渲染服务地区列表
     */
    private void renderRegionList() {
        // 展示服务地区列表
        mAdapter.setDatas(MyApplication.RegionList);
    }

    @Override
    public void onItemClick(View v, int originalPosition, int currentPosition, Region entity) {
/*
        if (originalPosition >= 0) {//选中其他
            if (CollectionUtil.isEmpty(MyApplication.RegionList)) {
                MyApplication.showToast("城市列表为空，请稍后重试");
                return;
            }
        } else {//选中定位城市
           *//* MyApplication.CURRENT_REGION = CityUtil.getNearestRegion(getLocation().getCity(),
                    getLocation().getDistrict());// 获取到离定位地区最近的服务区域（可能是自己）*//*
        }*/
        MyApplication.saveCurrentRegion();// 保存当前服务城市
        EventBus.getDefault().post(entity);// 定位城市发生变化
        finish();
    }
}
