package com.victor.che.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
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
import com.victor.che.domain.Category;
import com.victor.che.event.ProductEvent;
import com.victor.che.ui.fragment.ProductListFragment;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 总店产品管理子界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class ProductMgrHeadActivity extends BaseActivity {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    @BindView(R.id.pager_tab)
    PagerSlidingTabStrip pager_tab;

    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.icon_category)
    ImageView iconCategory;
    private List<Category> categoryList;

    @Override
    public int getContentView() {
        return R.layout.activity_product_mgr;
    }

    @Override
    protected void initView() {
        super.initView();
        iconCategory.setVisibility(View.GONE);
        pager_tab.setTextColorResource(R.color.black_text);
        pager_tab.setTextSize(ViewUtil.dip2px(mContext, 14));

        // 获取所有产品类型
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_LIST, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = JSON.parseArray(element.body, Category.class);
                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("服务类型为空");
                            return;
                        }
                        Category category = new Category();
                        category.goods_category_id = "0";
                        category.name = "全部";
                        categoryList.add(0, category);

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

    /**
     * 添加商品
     */
    @OnClick(R.id.topbar_right)
    void gotoAddProduct() {
        MyApplication.openActivity(mContext, AddProductActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
