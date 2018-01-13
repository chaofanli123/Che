package com.victor.che.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.domain.QuoteProgram;
import com.victor.che.ui.fragment.QueryQuoteListFragment;
import com.victor.che.util.AppUtil;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/9 0009 9:49.
 * 保险报价界面
 */

public class QueryQuoteActivity extends BaseActivity {
    @BindView(R.id.tv_car_no)
    TextView tvCarNo;
    @BindView(R.id.tv_car_user_name)
    TextView tvCarUserName;
    @BindView(R.id.tv_car_brand_name)
    TextView tvCarBrandName;
    @BindView(R.id.pager_tab)
    PagerSlidingTabStrip pagerTab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    private FragmentPagerAdapter adapter;
    private int insurance_query_id;
    private List<QuoteProgram> quotePrograms;
    private QueryBaoxianHistory queryBaoxianHistory;
    private String mobile;
    private String mUrl;

    /**
     * 分享对话框
     */
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View parentView;

    @Override
    public int getContentView() {
        return R.layout.activity_query_price;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("报价");


        queryBaoxianHistory = (QueryBaoxianHistory) getIntent().getSerializableExtra("queryBaoxianHistory");
        insurance_query_id = queryBaoxianHistory.insurance_query_id;
        tvCarNo.setText(queryBaoxianHistory.car_plate_no);
        tvCarBrandName.setText(queryBaoxianHistory.license_brand_model);
        tvCarUserName.setText(queryBaoxianHistory.name);
        mobile = queryBaoxianHistory.mobile;

        MyParams params = new MyParams();
        params.put("insurance_query_id",insurance_query_id);
        VictorHttpUtil.doGet(mContext, Define.url_insurance_query_detail_v2, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {

                    @Override
                    public void callbackSuccess(String url, Element element) {
                        quotePrograms = JSON.parseArray(element.body, QuoteProgram.class);

                        if (CollectionUtil.isEmpty(quotePrograms)) {
                            MyApplication.showToast("报价列表为空");
                            return;
                        }
                        // 设置viewpager和指示器
                        mViewPager.setAdapter(new QuoteDetailAdapter(getSupportFragmentManager()));
                        pagerTab.setViewPager(mViewPager);
                    }
                });

        /**
         * 拍照，手机相册选择框
         */
        parentView = View.inflate(mContext, R.layout.activity_query_price, null);
        View view = View.inflate(mContext, R.layout.pop_share, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop = new PopupWindow(mContext);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
//        TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_message);
        TextView bt2 = (TextView) view.findViewById(R.id.item_popupwindows_phone);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
//        //短信分享
//        bt1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                sendSMS();
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
        //电话分享
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (StringUtil.isEmpty(mobile)) {
                    Toast.makeText(mContext,"当前车辆没有手机号，无法分享",Toast.LENGTH_SHORT).show();
                }else {
                    AppUtil.call(mContext,mobile); //拨打电话
                    pop.dismiss();
                    ll_popup.clearAnimation();
                }

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

    }


    class QuoteDetailAdapter extends FragmentPagerAdapter {
        public QuoteDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("queryBaoxianHistory",queryBaoxianHistory);
            bundle.putString("insurance_categorys",quotePrograms.get(position).insurance_categorys);
            bundle.putSerializable("queryQuoteList",quotePrograms.get(position).insurance_quote);
            Fragment fragment = new QueryQuoteListFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "方案"+(position+1);
        }

        @Override
        public int getCount() {
            return quotePrograms.size();
        }
    }

    /**
     * 分享
     */
    @OnClick(R.id.topbar_right)
    void share(){

        /**
         * 分享
         */
        /**
         * 显示选择框
         */
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 短信分享
     */
//    private void sendSMS() {
//
//        Uri smsToUri = Uri.parse(mUrl);
//        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
//        sendIntent.putExtra("address", mobile); // 电话号码，这行去掉的话，默认就没有电话
//        sendIntent.putExtra( "sms_body" , "点击链接查看保险查询结果:"+ mUrl );
//        sendIntent.setType( "vnd.android-dir/mms-sms" );
//        startActivityForResult(sendIntent, 1002 );
//    }
}
