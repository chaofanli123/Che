package com.victor.che.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.victor.che.base.BaseActivity;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.User;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.RecyclerViewItemChangedEvent;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.BottomDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员卡充值界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/6 0006 9:30
 */
public class RechargeVipcardActivity extends BaseActivity {

    @BindView(R.id.tv_vipcard_no)
    TextView tv_vipcard_no;

    @BindView(R.id.tv_remain_value)
    TextView tv_remain_value;

    @BindView(R.id.tv_customer_mobile)
    TextView tv_customer_mobile;

    @BindView(R.id.et_recharge_value)
    EditText et_recharge_value;

    @BindView(R.id.et_actual_pay)
    EditText et_actual_pay;

    @BindView(R.id.label_remain_value)
    TextView label_remain_value;

    @BindView(R.id.label_recharge_value)
    TextView label_recharge;

    @BindView(R.id.tv_worker)
    TextView tv_worker;
    @BindView(R.id.topbar_right)
    TextView topbarRight;

    private List<User> workerList = new ArrayList<>();
    private WorkerListAdapter workerListAdapter;
    private int selectedWorkerPos = 0;//选中的师傅位置

    private Vipcard mVipcard;
    private int mPosition;
    /*
    支付信息
     */
    private PayTypeListAdapter payTypeListAdapter;
    private final int[] PAY_TYPE_ICONS = {R.drawable.ic_paytype_unionpay, R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES = {"银联/现金支付", "微信支付", "支付宝"};
    private  String provider_user_id;

    @Override
    public int getContentView() {
        return R.layout.activity_recharge_vipcard;
    }

    @Override
    protected void initView() {
        super.initView();
        topbarRight.setVisibility(View.GONE);
        // 设置标题
        setTitle("会员卡充值");
        // 支付方式列表适配器
        payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES);
        mPosition = getIntent().getIntExtra("mPosition", 0);
        mVipcard = (Vipcard) getIntent().getSerializableExtra("mVipcard");

        if (mVipcard == null) {
            MyApplication.showToast("会员卡为空，请稍后重试");
            return;
        }

        // 数据回显
        tv_vipcard_no.setText(mVipcard.card_no);
        label_remain_value.setText(mVipcard.getCardTypeLabel() + "：");
        tv_remain_value.setText(mVipcard.getRemainValue());
        if (mVipcard.card_category_id == 1) {//次卡
            label_recharge.setText("充值次数");
            et_recharge_value.setHint("请输入充值次数");
            et_recharge_value.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {//余额卡
            label_recharge.setText("充值金额");
            et_recharge_value.setHint("请输入充值金额");
            et_recharge_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        tv_customer_mobile.setText(mVipcard.mobile);

        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_recharge_value));//充值金额必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_actual_pay));//实收金额必填

        // 获取所有师傅
        _doGetWorkers();
    }

    /**
     * 获取服务师傅列表
     */
    private void _doGetWorkers() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_STAFF_USER_LIST, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        workerList = JSON.parseArray(element.data, User.class);
                        if (CollectionUtil.isEmpty(workerList)) {
                            MyApplication.showToast("服务师傅列表为空");
                            return;
                        }
                        for (int i = 0; i < workerList.size(); i++) {
                            if (workerList.get(i).staff_user_id.equalsIgnoreCase(MyApplication.CURRENT_USER.staff_user_id)) {
                                selectedWorkerPos = i;
                            }
                        }
                        tv_worker.setText(MyApplication.CURRENT_USER.user_name);
                        workerListAdapter = new WorkerListAdapter(mContext, R.layout.item_bottom_dialog, workerList);
                    }
                });
    }


    /**
     * 展示所有师傅弹框
     */
    @OnClick(R.id.area_worker)
    void showWorkerDialog() {
        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("服务师傅列表为空");
            return;
        }
        BottomDialogFragment.newInstance(workerListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedWorkerPos = position;
                workerListAdapter.notifyDataSetChanged();
                tv_worker.setText(workerList.get(position).user_name);
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    int num = 0;
    double eb = 0;

    /**
     * 保存
     */
    @OnClick(R.id.btn_chongzhi)
    void doOperate() {
        String strValue = et_recharge_value.getText().toString().trim();

        if (mVipcard.card_category_id == 1) {//次卡
            if (StringUtil.isEmpty(strValue)) {
                MyApplication.showToast("请输入充值次数");
                et_recharge_value.requestFocus();
                return;
            }
            num = Integer.parseInt(strValue);
            if (num <= 0) {
                MyApplication.showToast("次数必须为正数");
                et_recharge_value.requestFocus();
                return;
            }
        } else {//余额卡
            if (StringUtil.isEmpty(strValue)) {
                MyApplication.showToast("请输入充值余额");
                et_recharge_value.requestFocus();
                return;
            }
            eb = Double.parseDouble(strValue);
            if (eb <= 0) {
                MyApplication.showToast("余额必须为正数");
                et_recharge_value.requestFocus();
                return;
            }
        }

        String strActualPay = et_actual_pay.getText().toString().trim();
        if (StringUtil.isEmpty(strActualPay)) {
            MyApplication.showToast("请输入实收金额");
            et_actual_pay.requestFocus();
            return;
        }
        final double actualPay = Double.parseDouble(strActualPay);
        if (actualPay <= 0) {
            MyApplication.showToast("实收金额必须为正数");
            et_actual_pay.requestFocus();
            return;
        }
        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("服务师傅列表为空");
            return;
        }
        // 支付方式选择弹框
        BottomDialogFragment.newInstance(payTypeListAdapter,
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:// 支付方式:  6-现金
                                AlertDialogFragment.newInstance(
                                        "友情提示",
                                        "请确认您已收到用户的现金/刷卡支付",
                                        "取消",
                                        "确定",
                                        null,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                _doPay(6, actualPay);
                                            }
                                        })
                                        .show(getSupportFragmentManager(), getClass().getSimpleName());
                                break;
                            case 1://2-微信
                                _doPay(2, actualPay);
                                break;
                            case 2://1-支付宝
                                _doPay(1, actualPay);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());

    }

    private void _doPay(final int pay_method_id, final double actualPay) {
        // 提交修改
        // 请求数据
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商编号
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id
        params.put("provider_user_card_id", mVipcard.provider_user_card_id);// 用户卡id
        params.put("sale_user_id", workerList.get(selectedWorkerPos).staff_user_id);//销售者id

        // 用户手机号---mobile 会员卡有效期--vip_end_date 次数---vip_num 余额----vip_eb
        String data = mVipcard.card_category_id == 1 ? ("num=" + num) : ("money=" + eb);
        params.put("data", data);//需要修改的用户信息; 数据格式：字段=值,字段1=值1 (修改信息对应字段参考下面的备注)
        params.put("price", actualPay);// 实收金额
        params.put("pay_method_id", pay_method_id);//支付方式： 1-支付宝 2-微信 6-现金
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_USER_CARD_EDIT, params, true, "处理中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        if (StringUtil.isEmpty(element.data)) {//现金支付
                            MyApplication.showToast("修改成功");
                            // 通知用户会员卡列表某一项改变
                            mVipcard.num += num;
                            mVipcard.money = MathUtil.add(mVipcard.money, eb);
                            mVipcard.recharge_eb = eb;//记住偏移量
                            RecyclerViewItemChangedEvent event = new RecyclerViewItemChangedEvent(mPosition, mVipcard);
                            EventBus.getDefault().post(event);
                            if (pay_method_id == 6) {//现金支付
                                _gotoReceiptResult();
                            }

                        } else {
                            JSONObject jsonobj = JSON.parseObject(element.data);
                            if (jsonobj == null) {
                                MyApplication.showToast("订单返回数据异常");
                                return;
                            }

                            String url0 = jsonobj.getString("url");//收款二维码图片地址
                            String order_id = jsonobj.getString("order_id");//订单id
                            //   int conflict = jsonobj.getIntValue("conflict");
                            provider_user_id = jsonobj.getString("provider_user_id");
                            if (!StringUtil.isEmpty(url0)) {//充值（微信或支付宝支付）
                                Bundle bundle = new Bundle();
                                bundle.putInt("mActiveVipcard",3);
                                bundle.putBoolean("isCompleted", true);//信息是否完善
                                bundle.putString("pay_type", String.valueOf(pay_method_id));//支付方式
                                bundle.putString("actual_pay", MathUtil.getFinanceValue(actualPay));//支付金额
                                bundle.putString("url", url0);//收款二维码图片地址
                                bundle.putString("order_id", order_id);//订单id
                                MyApplication.openActivity(mContext, ReceiptQrcodeActivity.class, bundle);
                                finish();
                            }
                        }
                    }
                });
    }

    private void _gotoReceiptResult() {
        // 跳转到支付结果界面
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCompleted", true);//信息是否完善
        bundle.putString("provider_user_id", provider_user_id);//用户id
        bundle.putInt("mActiveVipcard",3);
        MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);
        // 关闭当前界面
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 师傅列表适配器
     */
    class WorkerListAdapter extends VictorBaseListAdapter<User> {

        public WorkerListAdapter(Context context, int layoutResId, List<User> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, User entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.user_name);
            textView.setTextColor(getResources().getColor(selectedWorkerPos == position ? R.color.theme_color : R.color.black_text));
        }
    }


    /**
     * 支付方式列表适配器
     */
    public class PayTypeListAdapter extends VictorBaseArrayAdapter<String> {

        private PayTypeListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }

        @Override
        public void bindView(int position, View view, String entity) {
            //支付方式图标
            ImageView iv_paytype = AbViewHolder.get(view, R.id.iv_paytype);
            iv_paytype.setImageResource(PAY_TYPE_ICONS[position]);

            // 支付方式名称
            TextView tv_name = AbViewHolder.get(view, R.id.tv_name);
            tv_name.setText(PAY_TYPE_NAMES[position]);
        }
    }
}
