package com.victor.che.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Channel;
import com.victor.che.domain.Customer;
import com.victor.che.domain.User;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.GiftEvent;
import com.victor.che.event.PlnEvent;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.util.ViewUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.ClearEditText;
import com.victor.che.widget.TipDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开卡界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 13:54
 */
public class ActiveVipcardActivity extends BaseActivity {

    @BindView(R.id.tv_vipcard_type_no)
    TextView tv_vipcard_type_no;

    @BindView(R.id.tv_vipcard_name)
    TextView tv_vipcard_name;

    @BindView(R.id.tv_vipcard_type)
    TextView tv_vipcard_type;

    @BindView(R.id.tv_service_content)
    TextView tv_service_content;

    @BindView(R.id.label_face_value)
    TextView label_face_value;

    @BindView(R.id.tv_face_value)
    TextView tv_face_value;

    @BindView(R.id.tv_original_price)
    TextView tv_original_price;

    @BindView(R.id.et_mobile)
    EditText et_mobile;

    @BindView(R.id.et_pln)
    EditText et_pln;

    @BindView(R.id.et_sale_price)
    EditText et_sale_price;

    @BindView(R.id.tv_worker)
    TextView tv_worker;

    @BindView(R.id.tv_gift)
    TextView tv_gift;

    @BindView(R.id.area_gift)
    View area_gift;
    @BindView(R.id.et_username)
    ClearEditText etUsername;

    private List<User> workerList = new ArrayList<>();
    private WorkerListAdapter workerListAdapter;
    private int selectedWorkerPos = 0;//选中的师傅位置

    private Vipcard mVipcard;

    private ArrayList<Channel> categoryList;//产品分类列表（内部包含所有产品）
    private String provider_user_id;


    @Override
    public int getContentView() {
        return R.layout.activity_active_vipcard;
    }

    private TextWatcher mobileTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            String pln = et_pln.getText().toString().trim();
            if (StringUtil.isEmpty(pln)) {// 车牌号为空的时候，才执行查询
                final String text = s.toString().trim();
                if (StringUtil.isPhoneNumber(text)) {//只有输入的是手机号的时候，才发送查询
                    // 根据手机号查询车牌号
                    _doQuery(text);
                }
            }
        }
    };
    private TextWatcher plnTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            String mobile = et_mobile.getText().toString().trim();
            if (StringUtil.isEmpty(mobile)) {// 手机号为空的时候，才执行查询
                final String text = s.toString().trim();
                if (StringUtil.isPln(text)) {//只有输入的是车牌号的时候，才发送查询
                    // 根据手机号查询车牌号
                    _doQuery(text);
                }
            }
        }
    };

    @Override
    protected void initView() {
        super.initView();

        mVipcard = (Vipcard) getIntent().getSerializableExtra("mVipcard");
        if (mVipcard == null) {
            MyApplication.showToast("会员卡为空，请稍后重试");
            return;
        }
        _render(mVipcard);//渲染会员卡

        // 设置标题
        setTitle("开卡");

        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_mobile));//手机号必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_sale_price));//售价必填

        // 支付方式列表适配器
        payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES);

        et_mobile.addTextChangedListener(mobileTextWatcher);
        et_pln.addTextChangedListener(plnTextWatcher);

        // 获取所有师傅
        _doGetWorkers();

        // 获取所有分类和产品
        _doGetCategoriesAndProducts();
    }

    /**
     * 获取所有分类和产品
     */
    private void _doGetCategoriesAndProducts() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_DETAIL, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        categoryList = (ArrayList<Channel>) JSON.parseArray(element.data, Channel.class);

                        if (CollectionUtil.isEmpty(categoryList)) {
                            MyApplication.showToast("服务类型为空");
                            return;
                        }
                        // 过滤掉无产品的分类
                        List<Channel> temp = new ArrayList<Channel>();
                        for (Channel item : categoryList) {
                            if (!CollectionUtil.isEmpty(item.goods)) {
                                temp.add(item);
                            }
                        }
                        categoryList.clear();
                        categoryList.addAll(temp);
                    }
                });
    }

    /**
     * 根据手机号查询车牌号
     *
     * @param text
     */
    private void _doQuery(String text) {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("user_input", text);// 用户编号(获取用户列表时返回)
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_INFO, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = JSON.parseObject(element.data);

                        // 车牌号
                        if (jsonobj != null) {
                            et_pln.setText(jsonobj.getString("car_plate_no"));
                            et_mobile.setText(jsonobj.getString("mobile"));
                        }
                    }
                });
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

    private void _render(Vipcard vipcard) {
        tv_vipcard_type_no.setText(vipcard.code);// 会员卡编号
        tv_vipcard_name.setText(vipcard.name);// 会员卡名称
        tv_vipcard_type.setText(vipcard.getCardTypeName());// 会员卡类型
        tv_service_content.setText(vipcard.getServiceContent());
        label_face_value.setText(vipcard.card_category_id == 2 ? "可用金额：" : "可用次数：");//面额label
        tv_face_value.setText(vipcard.getFaceValue());//面额
        tv_original_price.setText(MathUtil.getMoneyText(vipcard.price));// 原价
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

    /**
     * 显示全部可用服务
     */
    @OnClick(R.id.tv_show_more)
    void doShowMore() {
        TipDialogFragment.newInstance("可用服务", mVipcard.getServiceContent())
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 选择用户
     */
    @OnClick(R.id.iv_choose_customer)
    void gotoChooseCustomer() {
        MyApplication.openActivity(mContext, ChooseCustomerActivity.class);
    }

    /**
     * 选中用户后
     *
     * @param cust
     */
    @Subscribe
    public void onChoosedCustomer(Customer cust) {
        if (cust != null) {
            et_pln.removeTextChangedListener(plnTextWatcher);
            et_pln.setText(cust.car_plate_no);
            et_pln.addTextChangedListener(plnTextWatcher);
            // 此处首先移除，后添加TextWatcher，防止setText出发OnTextChanged事件
            et_mobile.removeTextChangedListener(mobileTextWatcher);
            et_mobile.setText(cust.mobile);
            et_mobile.addTextChangedListener(mobileTextWatcher);
        }
    }

    /**
     * 扫一扫
     */
    @OnClick(R.id.iv_scan)
    void gotoScan() {
        new TedPermission(mActivity)
                .setPermissions(Manifest.permission.CAMERA)
                .setDeniedMessage(R.string.rationale_camera)
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("mPostBack", true);//是否需要回传
                      //  MyApplication.openActivity(mContext, ScanActivity.class, bundle);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();
    }

    /**
     * 拍照识别车牌号后
     *
     * @param event
     */
    @Subscribe
    public void onReceivePln(final PlnEvent event) {
        if (event != null) {
            et_pln.setText(event.pln);
        }
    }

    /**
     * 选择赠品
     */
    @OnClick(R.id.area_gift)
    void gotoChooseGift() {
        Bundle bundle = new Bundle();
        bundle.putInt("card_category_id", mVipcard.card_category_id);//卡类型id
        bundle.putSerializable("mCategoryList", categoryList);//所有分类和产品
        if (giftEvent != null) {
            bundle.putSerializable("mChoosedGift", giftEvent.giftList);//已选的赠品列表（用于数据回显）
            bundle.putString("mGiftValue", giftEvent.gift_value);//赠送次数或余额
        }
        MyApplication.openActivity(mContext, ChooseGiftActivity.class, bundle);
    }

    private GiftEvent giftEvent;

    @Subscribe
    public void onChoosedGift(GiftEvent event) {
        this.giftEvent = event;
        if (event == null) {
            return;
        }
        tv_gift.setText(giftEvent.gift_text);
    }

    /**
     * 开卡
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        final String username = etUsername.getText().toString().trim();
        final String mobile = et_mobile.getText().toString().trim();
        if (StringUtil.isEmpty(mobile)) {
            MyApplication.showToast("请输入手机号");
            et_mobile.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNumber(mobile)) {
            MyApplication.showToast("手机号格式不正确");
            et_mobile.requestFocus();
            return;
        }

        final String pln = et_pln.getText().toString().trim();
        if (!StringUtil.isEmpty(pln) && !StringUtil.isPln(pln)) {
            MyApplication.showToast("车牌号格式不正确");
            et_pln.requestFocus();
            return;
        }

        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("服务师傅列表为空");
            return;
        }

        String strSalePrice = et_sale_price.getText().toString().trim();
        if (StringUtil.isEmpty(strSalePrice)) {
            MyApplication.showToast("请输入售价");
            et_sale_price.requestFocus();
            return;
        }
        if (!StringUtil.isMoney(strSalePrice)) {
            MyApplication.showToast("售价格式不正确");
            et_sale_price.requestFocus();
            return;
        }
        final double salePrice = Double.parseDouble(strSalePrice);
        if (salePrice <= 0) {
            MyApplication.showToast("售价必须大于0");
            et_sale_price.requestFocus();
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
                                                _doPay(6,username, mobile, pln, salePrice);
                                            }
                                        })
                                        .show(getSupportFragmentManager(), getClass().getSimpleName());
                                break;
                            case 1://2-微信
                                _doPay(2,username, mobile, pln, salePrice);
                                break;
                            case 2://1-支付宝
                                _doPay(1,username, mobile, pln, salePrice);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 实际现金支付
     */
    private void _doPay(final int pay_method_id, String username,String mobile, String pln, final double salePrice) {

        // 请求接口
        MyParams params = new MyParams();
        params.put("provider_card_id", mVipcard.provider_card_id);//服务商卡id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        params.put("sale_user_id", workerList.get(selectedWorkerPos).staff_user_id);//销售者id
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("mobile", mobile);//用户手机号
        params.put("car_plate_no", pln);//车牌号
        params.put("user_name", username);//车牌号
        if (giftEvent != null) {
            if (!StringUtil.isEmpty(giftEvent.gift_value)) {
                params.put("donate", giftEvent.gift_value);//赠送次数或余额
            }
            if (!StringUtil.isEmpty(giftEvent.gift_service)) {
                params.put("donate_service", giftEvent.gift_service);//赠送的服务,格式： 商品id=赠送次数 eg:1=10(1.0.4新增)
            }
        }
        params.put("sale_price", salePrice);//	销售价
        params.put("pay_method_id", pay_method_id);//支付方式： 1-支付宝 2-微信 6-现金
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_CARD_ALLOCATE, params, true, "处理中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        if (StringUtil.isEmpty(element.data)) {//开卡无冲突
                            MyApplication.showToast("开卡成功");
                            EventBus.getDefault().post(ConstantValue.Event.ACTIVE_VIPCARD);
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
                            int conflict = jsonobj.getIntValue("conflict");
                            provider_user_id = jsonobj.getString("provider_user_id");
                            if (!StringUtil.isEmpty(url0)) {//开卡（微信或支付宝支付）
                                Bundle bundle = new Bundle();
                                bundle.putInt("mActiveVipcard", 1);//开卡
                                bundle.putBoolean("isCompleted", true);//信息是否完善
                                bundle.putString("pay_type", String.valueOf(pay_method_id));//支付方式
                                bundle.putString("actual_pay", MathUtil.getFinanceValue(salePrice));//支付金额
                                bundle.putString("url", url0);//收款二维码图片地址
                                bundle.putString("order_id", order_id);//订单id
                                MyApplication.openActivity(mContext, ReceiptQrcodeActivity.class, bundle);
                            } else {
                                if (conflict == 1) {//开卡有冲突
                                    AlertDialogFragment.newInstance(
                                            "提示",
                                            "您输入的车牌号和手机号是两个用户，无法开卡是否编辑用户信息？",
                                            "取消",
                                            "编辑",
                                            null,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("provider_user_id", provider_user_id);
                                                    MyApplication.openActivity(mContext, ConflictActivity.class, bundle);
                                                }
                                            })
                                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                                }
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
        bundle.putInt("mActiveVipcard", 1);//开卡
        MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);

        // 关闭当前界面
        finish();
    }

    private PayTypeListAdapter payTypeListAdapter;
    private final int[] PAY_TYPE_ICONS = {R.drawable.ic_paytype_unionpay, R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES = {"银联/现金支付", "微信支付", "支付宝"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 支付方式列表适配器
     */
    private class PayTypeListAdapter extends VictorBaseArrayAdapter<String> {

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

    /**
     * 师傅列表适配器
     */
    private class WorkerListAdapter extends VictorBaseListAdapter<User> {

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

}
