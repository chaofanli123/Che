package com.victor.che.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.astuetz.PagerSlidingTabStrip;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.Channel;
import com.victor.che.domain.GoodCategory;
import com.victor.che.event.ProductEvent;
import com.victor.che.ui.fragment.ProductListFragment;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ConstanceValue;
import com.victor.che.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 产品管理子界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class ProductMgrActivity extends BaseActivity {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.pager_tab)
    PagerSlidingTabStrip pager_tab;

    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.icon_category)
    ImageView iconCategory;
    @BindView(R.id.topbar_right)
    ImageView topbarRight;
    private List<Channel> categoryList;//列表要展示的

    public List<Channel> mSelectedDatas = new ArrayList<>(); //选中的产品列表
    public List<Channel> mUnSelectedDatas = new ArrayList<>();//未选中的产品列表

    @Override
    public int getContentView() {
        return R.layout.activity_product_mgr;
    }
    @Override
    protected void initView() {
        super.initView();
        topbarRight.setVisibility(View.GONE);
        pager_tab.setTextColorResource(R.color.black_text);
        pager_tab.setTextSize(ViewUtil.dip2px(mContext, 14));
        // 获取所有产品类型
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.url_subbranch_goods_category_list_v1, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = new ArrayList<Channel>();
                        Channel category = new Channel();
                        category.goods_category_id = "0";
                        category.name = "全部";
                        categoryList.add(0, category);

                        GoodCategory goodCategory = JSON.parseObject(element.data, GoodCategory.class);
                        mSelectedDatas = goodCategory.getUsed_goods_category();
                        mUnSelectedDatas = goodCategory.getUnUsed_goods_category();
//                        if (CollectionUtil.isEmpty(mSelectedDatas)) {
//                            MyApplication.showToast("服务类型为空");
//                            return;
//                        }
                        categoryList.addAll(mSelectedDatas);
                        // 设置viewpager和指示器
                        mViewPager.setAdapter(new ProductFragmentAdapter(getSupportFragmentManager()));
                        pager_tab.setViewPager(mViewPager);
                    }
                });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rdo_in_sale://在售
                        EventBus.getDefault().post(new ProductEvent(1));
                        break;
                    case R.id.rdo_off_sale://下架
                        EventBus.getDefault().post(new ProductEvent(0));
                        break;
                }
            }
        });
    }
    @Subscribe
    public void receiveMessage(Activity event){
        if (event == null) {
            return;
        }
        event.finish();
    }


    /**
     * 进入服务列表管理
     */
    @OnClick(R.id.icon_category)
    void onserverMangerClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstanceValue.DATA_SELECTED, (Serializable) mSelectedDatas);
        bundle.putSerializable(ConstanceValue.DATA_UNSELECTED, (Serializable) mUnSelectedDatas);
        MyApplication.openActivity(mContext, ChannelActivity.class, bundle);
    }
    private class ProductFragmentAdapter extends FragmentPagerAdapter {
        public ProductFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return CollectionUtil.getSize(categoryList);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryList.get(position).name;
        }

        @Override
        public Fragment getItem(int position) {
            return ProductListFragment.newInstance(categoryList.get(position).goods_category_id);
        }
    }

}
