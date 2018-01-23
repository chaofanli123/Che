package com.victor.che.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.phillipcalvin.iconbutton.IconButton;
import com.victor.che.R;
import com.victor.che.base.BaseFragment;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.event.SearchEvent;
import com.victor.che.ui.Coupon.JingYongYvYaoFragment;
import com.victor.che.ui.Coupon.StartUsingCouponFragment;
import com.victor.che.ui.Coupon.ZhenCeFaGuiFragment;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.ListDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class IndexFragment1 extends BaseFragment {
    @BindView(R.id.pager_tab)
    SmartTabLayout pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_order_type)
    Button btn_order_type;
    @BindView(R.id.btn_order_state)
    IconButton btnOrderstate;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private String provider_user_id;

    private Fragment fragment;

    private String keywords = "";// 搜索title
    private String type = "";// 搜索类型
    private String status = "";// 搜索状态
    private int selectedOrderTypePos = 0; //类型
    private OrderTypeListAdapter ordertypeListAdapter; //类型适配器
    private OrderStateListAdapter orderStateListAdapter; //状态适配器
    private int selectedOrderStatePos = 0; //状态
    //类型 1会议通告 2奖惩通告 3活动通告
    private String[] ORDER_TYPE = {"全部", "会议通告", "奖惩通告", "活动通告"};
    //1草稿2发布

    private String[] ORDER_STATES = {"全部", "草稿", "发布"};

    private static final String[] CONTENT = new String[]{"通知下达", "政策法规", "禁用鱼药"};
    private int currentPos = 0;// 查询类型 1-通知下达 2-政策法规 3-禁用鱼药 ，默认为1

    @Override
    public int getContentView() {
        return R.layout.fragment_shouye;
    }
    @Override
    protected void initView() {
        super.initView();
        /**
         * 订单状态
         */
        ordertypeListAdapter = new OrderTypeListAdapter(mContext, R.layout.item_list_dialog, ORDER_TYPE);
        orderStateListAdapter = new OrderStateListAdapter(mContext, R.layout.item_list_dialog, ORDER_STATES);
        adapter = new GoogleMusicAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        pagerTab.setViewPager(mViewPager);
        pagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
    }
    /**
     * 显示状态
     */
    @OnClick(R.id.btn_order_state)
    void showOrderStateDialog() {
        ListDialogFragment.newInstance(orderStateListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderStatePos = position;
                orderStateListAdapter.notifyDataSetChanged();
                btnOrderstate.setText(ORDER_STATES[position]);
                btnOrderstate.requestLayout();// 防止文字和图片覆盖
                //类型 1会议通告 2奖惩通告 3活动通告
                if (selectedOrderStatePos == 0) {
                    status="";
                }else {
                    status=selectedOrderStatePos+"";
                }
                _doSearch();
            }
        }).show(getFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 显示类型
     */
    @OnClick(R.id.btn_order_type)
    void showOrdertypeDialog() {
        ListDialogFragment.newInstance(ordertypeListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderTypePos = position;
                ordertypeListAdapter.notifyDataSetChanged();
                btn_order_type.setText(ORDER_TYPE[position]);
                btn_order_type.requestLayout();// 防止文字和图片覆盖
                //类型 1会议通告 2奖惩通告 3活动通告
                if (selectedOrderTypePos == 0) {
                    type="";
                }else {
                    type=selectedOrderTypePos+"";
                }

                _doSearch();
            }
        }).show(getFragmentManager(), getClass().getSimpleName());
    }
    /**
     * 开始搜索
     */
    private void _doSearch() {
        EventBus.getDefault().post(new SearchEvent(keywords,type,status, currentPos));
    }
    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    //通知下达
                    fragment = new StartUsingCouponFragment();
                    break;
                case 1:
                    //禁用
                    fragment = new ZhenCeFaGuiFragment();
                    break;
                case 2:
                    //过期
                    fragment = new JingYongYvYaoFragment();
                    break;

                default:
                    fragment = new StartUsingCouponFragment();
                    break;
            }
            Bundle args = new Bundle();
            args.putString("type", (position + 1) + "");
            // args.putSerializable("mCar",mCar);
//        args.putSerializable("querybaoxianCar",querybaoxianCar);
            fragment.setArguments(args);
            fragments.add(position, fragment);
            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }


    /**
     * 订单类型适配器
     */
    private class OrderTypeListAdapter extends VictorBaseArrayAdapter<String> {

        public OrderTypeListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }
        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            ViewUtil.setDrawableRight(mContext, textView, selectedOrderTypePos == position ? R.drawable.ic_checked : R.drawable.ic_unchecked);
        }
    }

    /**
     * 订单状态适配器
     */
    private class OrderStateListAdapter extends VictorBaseArrayAdapter<String> {

        public OrderStateListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }

        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            ViewUtil.setDrawableRight(mContext, textView, selectedOrderStatePos == position ? R.drawable.ic_checked : R.drawable.ic_unchecked);
        }
    }
}
