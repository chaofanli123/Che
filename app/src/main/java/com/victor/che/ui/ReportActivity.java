package com.victor.che.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.ui.fragment.DailyReportsFragment;
import com.victor.che.ui.fragment.ReportDataFragment;

import butterknife.BindView;

/**
 * 报表界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/28 0028 10:29
 */
public class ReportActivity extends BaseActivity {

    private Fragment fragment0, fragment1, fragment2;

    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;

    @BindView(R.id.tab_bottom_0)
    RadioButton tab_bottom_0;

    @BindView(R.id.tab_bottom_1)
    RadioButton tab_bottom_1;

    @BindView(R.id.tab_bottom_2)
    RadioButton tab_bottom_2;

    private int currentTabIndex = 0;// 被选中的tab的下标

    @Override
    public int getContentView() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView() {
        super.initView();

        // 设置标题
        setTitle("报表");

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_bottom_0:// 日报
                        setTabSelection(0);
                        currentTabIndex = 0;
                        break;
                    case R.id.tab_bottom_1:// 周报
                        setTabSelection(1);
                        currentTabIndex = 1;
                        break;
                    case R.id.tab_bottom_2:// 月报
                        setTabSelection(2);
                        currentTabIndex = 2;
                        break;
                }
            }
        });

        // 默认选中首页
        setTabSelection(0);
    }

    /**
     * 选中哪个界面
     *
     * @param position
     */
    public void setTabSelection(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0://日报
                tab_bottom_0.setChecked(true);
                if (fragment0 == null) {
                    fragment0 = DailyReportsFragment.newInstance();
                    transaction.add(R.id.frame_layout, fragment0, "fragment0");
                } else {
                    transaction.show(fragment0);
                }
                break;
            case 1://周报
                tab_bottom_1.setChecked(true);
                if (fragment1 == null) {
                    fragment1 = ReportDataFragment.newInstance(2);
                    transaction.add(R.id.frame_layout, fragment1, "fragment1");
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 2://月报
                tab_bottom_2.setChecked(true);
                if (fragment2 == null) {
                    fragment2 = ReportDataFragment.newInstance(3);
                    transaction.add(R.id.frame_layout, fragment2, "fragment2");
                } else {
                    transaction.show(fragment2);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragment0 != null) {
            transaction.hide(fragment0);
        }
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentTabIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentTabIndex = savedInstanceState.getInt("position");
        setTabSelection(currentTabIndex);
        super.onRestoreInstanceState(savedInstanceState);
    }

}
