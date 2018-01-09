package com.victor.che.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.event.SearchEvent;
import com.victor.che.ui.fragment.SearchCarFragment;
import com.victor.che.ui.fragment.SearchCustomerFragment;
import com.victor.che.ui.fragment.SearchVipcardFragment;
import com.victor.che.widget.LazyViewPager;
import com.victor.che.widget.OrderPagerTab;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/1 0001 9:12
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.pager_tab)
    OrderPagerTab pager_tab;

    @BindView(R.id.mViewPager)
    LazyViewPager mViewPager;

    @BindView(R.id.et_search)
    EditText et_search;

    private List<Fragment> fragments = new ArrayList<>();

    private int currentPos = 0;// 查询类型 1-用户信息 2-车辆信息 3-卡信息 ，默认为1
    private String keywords = "";// 搜索关键字

    @Override
    public int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();
        fragments.add(new SearchCustomerFragment());
        fragments.add(new SearchCarFragment());
        fragments.add(new SearchVipcardFragment());

        // 设置viewpager和指示器
        mViewPager.setAdapter(new SearchFragmentAdpater(getSupportFragmentManager()));
        pager_tab.setViewPager(mViewPager);

        // 不停的请求搜索接口
        et_search.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                keywords = s.toString().trim();
                _doSearch();
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keywords = et_search.getText().toString().trim();
                    _doSearch();
                }
                return false;
            }
        });

        pager_tab.setOnTabSelectedListener(new OrderPagerTab.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                currentPos = position;
                _doSearch();
            }
        });
    }

    /**
     * 开始搜索
     */
    private void _doSearch() {
        EventBus.getDefault().post(new SearchEvent(keywords, currentPos));
    }

    /**
     * 订单列表适配器
     *
     * @author Victor
     * @email 468034043@qq.com
     * @time 2016年3月20日 下午3:36:03
     */
    private class SearchFragmentAdpater extends FragmentPagerAdapter {

        public SearchFragmentAdpater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

    }
}
