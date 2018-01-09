package com.victor.che.ui.fragment;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.ui.CustomerReportActivity;
import com.victor.che.ui.SalesReportActivity;
import com.victor.che.util.MathUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 日报界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class DailyReportsFragment extends BaseFragment {

    @BindView(R.id.tv_current_count)
    TextView tv_current_count;

    @BindView(R.id.tv_last_count)
    TextView tv_last_count;

    @BindView(R.id.tv_increase_rate1)
    TextView tv_increase_rate1;

    @BindView(R.id.tv_current_amount)
    TextView tv_current_amount;

    @BindView(R.id.tv_last_amount)
    TextView tv_last_amount;

    @BindView(R.id.tv_increase_rate2)
    TextView tv_increase_rate2;

    /**
     * 创建实例
     *
     * @return
     */
    public static DailyReportsFragment newInstance() {

        DailyReportsFragment fragment = new DailyReportsFragment();
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_daily_reports;
    }


    @Override
    protected void initView() {

        // 获取日报数据
        // 获取报表数据
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("type", 1);//报表类型 1-日报 2-周报 3-月报
        params.put("start", 0);//列表记录开始位置
        params.put("pageSize", 0);//一页显示行数
        VictorHttpUtil.doGet(mContext, Define.URL_REPORT_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = JSON.parseObject(element.data);
                        if (jsonobj == null) {
                            MyApplication.showToast("日报数据为空，请稍后重试");
                            return;
                        }
                        JSONObject current = jsonobj.getJSONObject("current");
                        int current_count = 0;
                        double current_amount = 0;
                        int last_count = 0;
                        double last_amount = 0;
                        if (current != null) {
                            current_count = current.getIntValue("count");
                            current_amount = current.getDoubleValue("amount");
                        }
                        JSONObject last = jsonobj.getJSONObject("last");
                        if (last != null) {
                            last_count = last.getIntValue("count");
                            last_amount = last.getDoubleValue("amount");
                        }
                        // 今天用户数
                        tv_current_count.setText(current_count + "");

                        // 昨天用户数
                        tv_last_count.setText(last_count + "");

                        // 用户增长率
                        tv_increase_rate1.setText(MathUtil.calcIncreaseRate(current_count, last_count));

                        // 今天交易额
                        tv_current_amount.setText(MathUtil.getFinanceValue(current_amount));

                        // 昨天交易额
                        tv_last_amount.setText(MathUtil.getFinanceValue(last_amount));

                        // 交易额增长率
                        tv_increase_rate2.setText(MathUtil.calcIncreaseRate(current_amount, last_amount));
                    }

                });
    }

    /**
     * 去用户报表界面
     */
    @OnClick({R.id.area_customer_report, R.id.tv_current_count})
    void gotoUserReport() {
        MyApplication.openActivity(mContext, CustomerReportActivity.class);
    }


    /**
     * 去营业额报表界面
     */
    @OnClick({R.id.area_sales_report, R.id.tv_current_amount})
    void gotoSalesReport() {
        MyApplication.openActivity(mContext, SalesReportActivity.class);
    }

}
