package com.victor.che.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Channel;
import com.victor.che.domain.ChooseCar;
import com.victor.che.domain.Customer;
import com.victor.che.domain.OrderGift;
import com.victor.che.domain.Pending;
import com.victor.che.domain.Product;
import com.victor.che.domain.Shoppingcar;
import com.victor.che.domain.User;
import com.victor.che.domain.Vipcard;
import com.victor.che.event.CouponEvent;
import com.victor.che.event.PlnEvent;
import com.victor.che.util.AbViewHolder;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.MathUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;
import com.victor.che.widget.AmountView;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.ClearEditText;
import com.victor.che.widget.PlnAddrButton;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * 收款界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 22:55
 */
public class ReceiptActivity extends BaseActivity {

    @BindView(R.id.et_search)
    ClearEditText et_search;

    @BindView(R.id.tv_product)
    TextView tv_product;

    @BindView(R.id.tv_category)
    TextView tv_category;

    @BindView(R.id.amount_view)
    AmountView amount_view;

    @BindView(R.id.tv_sum_price)
    TextView et_actual_pay;

    @BindView(R.id.tv_worker)
    TextView tv_worker;
    @BindView(R.id.lin_baoyang)
    LinearLayout linBaoyang;
    @BindView(R.id.area_first_time)
    LinearLayout areaFirstTime;
    @BindView(R.id.tv_baoyang_distance)
    ClearEditText tvBaoyangDistance;
    @BindView(R.id.tv_car_next_time)
    TextView tvBaoyangNexttime;

    @BindView(R.id.et_search_car_number)
    ClearEditText etSearchCarNumber;

    @BindView(R.id.et_sale_price)
    EditText etSalePrice;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.btn_pln_addr)
    PlnAddrButton btn_pln_addr;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon; //是否有优惠券

    @BindView(R.id.tv_reality_price)
    TextView tvRelityPrice;  //实际收款金额

    @BindView(R.id.tv_coutop_price)
    TextView tvCoutopPrice; //优惠券金额

    /*******************
     * 时间原因，这些适配器，弹框都可以进行重构
     */
    private List<Channel> categoryList;
    private CategoryListAdapter categoryListAdapter;
    private int selectedCategoryPos = 0;//选中的类别位置

    private List<Product> productList = new ArrayList<>();
    private ProductListAdapter productListAdapter;
    private int selectedProductPos = 0;//选中的产品位置

    private List<User> workerList = new ArrayList<>();
    private WorkerListAdapter workerListAdapter;
    private int selectedWorkerPos = 0;//选中的师傅位置

    private ArrayList<Vipcard> vipcardList;//会员卡列表

    private String mobile = "";// 查询的手机号
    private String car_plate_no = "";//车牌号
    private String plnAdd = "豫";//车牌归属地

    private String provider_user_id;//用户id

    private String baoyangDiatance, baoyangNextTime;//行驶里程，下次保养时间

    private String mData;// 扫一扫界面传过来的车牌号

    private boolean isCompleted = false;// 信息是否完善
    private boolean userIsExist;//用户是否存在

    private double actual_pay = 0;//实际服务金额 没有减去优惠券的
    private double coupon_price=0;//优惠金额
    private double sum_price=0;//实际支付金额

    private final int[] PAY_TYPE_ICONS = {R.drawable.ic_paytype_vipcard, R.drawable.ic_paytype_unionpay, R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES = {"会员卡支付", "银联/现金支付", "微信支付", "支付宝"};
    private final int[] PAY_TYPE_ICONS2 = {R.drawable.ic_paytype_unionpay, R.drawable.ic_paytype_wxpay, R.drawable.ic_paytype_alipay};
    private final String[] PAY_TYPE_NAMES2 = {"银联/现金支付", "微信支付", "支付宝"};


    private int buy_num = 1;
    //    private ArrayList<Shoppingcar> shoppingCars = new ArrayList<>();
    private PayTypeListAdapter payTypeListAdapter;
    private Pending pending;

    private Shoppingcar shoppingcar;
    private PopupWindow popupWindow;

    private String coupon_count; //提供的可用优惠券张数

    private String coupon_id=""; //优惠券id


    @Override
    public int getContentView() {
        return R.layout.activity_receipt;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        setTitle("收银");
        mData = getIntent().getStringExtra("mData");

        pending = (Pending) getIntent().getSerializableExtra("pending");
        amount_view.setInputable(false);//输入框不允许输入

        et_search.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (StringUtil.isPhoneNumber(text)) {
                    mobile = text;
                } else {
                    mobile = "";
                }
                _doSeach(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击搜索
                    String text = et_search.getText().toString().trim();
                    if (StringUtil.isPhoneNumber(text)) {
                        mobile = text;
                    } else {
                        mobile = "";
                    }
                    _doSeach(text);
                }
                return false;
            }
        });


        etSearchCarNumber.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                text = plnAdd + text;
                if (StringUtil.isPln(text)) {
                    car_plate_no = text;
                } else {
                    car_plate_no = "";
                }
                _doSeach(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }
        });
        etSearchCarNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击搜索
                    String text = etSearchCarNumber.getText().toString().trim();
                    text = plnAdd + text;
                    if (StringUtil.isPln(text)) {
                        car_plate_no = text;
                    } else {
                        car_plate_no = "";
                    }
                    _doSeach(text);
                }
                return false;
            }
        });

        btn_pln_addr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                plnAdd = btn_pln_addr.getText().toString();
                if (TextUtils.isEmpty(etSearchCarNumber.getText().toString().trim())) {
                    return;
                }
                String text = plnAdd + etSearchCarNumber.getText().toString().trim();
                if (StringUtil.isPln(text)) {
                    car_plate_no = text;
                } else {
                    car_plate_no = "";
                }
                _doSeach(text);
            }
        });

        if (!StringUtil.isEmpty(mData) && StringUtil.isPln(mData)) {//回显扫描的结果

            plnAdd = mData.charAt(0) + "";
            btn_pln_addr.setText(mData.charAt(0) + "");
            etSearchCarNumber.setText(mData.substring(1));
        }

        if (pending == null) {//从收银界面过来
            // 获取所有类别
            MyParams params = new MyParams();
            params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
            VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_LIST, params, false, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            categoryList = JSON.parseArray(element.body, Channel.class);
                            if (CollectionUtil.isEmpty(categoryList)) {
                                MyApplication.showToast("服务类别列表为空");
                                return;
                            }
                            tv_category.setText(categoryList.get(0).name);
                            categoryListAdapter = new CategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);

                            // 获取对应的商品列表
                            _doGetProducts(categoryList.get(0).goods_category_id);
                        }
                    });
            // 获取所有师傅
            _doGetWorkers();
        } else {
            shoppingcar = pending.detail.get(0);
            et_search.setText(pending.mobile);
            if (StringUtil.isPln(pending.car_plate_no)) {
                plnAdd = pending.car_plate_no.charAt(0) + "";
                btn_pln_addr.setText(plnAdd);
                etSearchCarNumber.setText(pending.car_plate_no.substring(1));
            }
            tv_category.setText(shoppingcar.goods_category.goods_category_name);
            tv_product.setText(shoppingcar.goods.goods_name);
            buy_num = shoppingcar.goods.buy_num;
            amount_view.setAmount(buy_num);
            // tvProductPrice.setText(shoppingcar.goods.sale_price);
            etSalePrice.setText(shoppingcar.goods.actual_sale_price);
            et_actual_pay.setText(pending.total_price + "元");
            actual_pay = Double.parseDouble(pending.total_price);
            sum_price= MathUtil.sub(actual_pay,coupon_price);
            if (sum_price<0.00) {
                sum_price=0.00;
            }
            tvRelityPrice.setText(sum_price+"元");
            baoyangDiatance = pending.mileage;
            baoyangNextTime = pending.next_maintain;

            if (shoppingcar.goods_category.goods_category_name.equals("保养")) {
                linBaoyang.setVisibility(View.VISIBLE);
                tvBaoyangDistance.setText(pending.mileage);
                tvBaoyangNexttime.setText(pending.next_maintain);
            }

            etSearchCarNumber.setFocusable(false);
            et_search.setFocusable(false);
            btn_pln_addr.setFocusable(false);
            et_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //挂单过来的,不让修改
                    MyApplication.showToast("编辑挂单不能修改用户");
                }
            });

            etSearchCarNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //挂单过来的,不让修改
                    MyApplication.showToast("编辑挂单不能修改用户");
                }
            });

            btn_pln_addr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //挂单过来的,不让修改
                    MyApplication.showToast("编辑挂单不能修改用户");
                }
            });

            // 获取所有类别
            MyParams params = new MyParams();
            params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
            VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_LIST, params, false, "加载中...",
                    new BaseHttpCallbackListener<Element>() {
                        @Override
                        public void callbackSuccess(String url, Element element) {
                            categoryList = JSON.parseArray(element.body, Channel.class);
                            if (CollectionUtil.isEmpty(categoryList)) {
                                MyApplication.showToast("服务类别列表为空");
                                return;
                            }
                            categoryListAdapter = new CategoryListAdapter(mContext, R.layout.item_bottom_dialog, categoryList);

                            for (int i = 0; i < categoryList.size(); i++) {
                                if (shoppingcar.goods_category.goods_category_name.equals(categoryList.get(i).name)) {
                                    //服务名称一样是时候
                                    selectedCategoryPos = i;
                                    // 获取对应的商品列表
                                    doGetProducts(categoryList.get(i).goods_category_id);
                                }
                            }
                        }
                    });

            // 获取所有师傅
            doGetWorkers();

        }


        //销售价改变
        etSalePrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {//点击完成

                    if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
//                        MyApplication.showToast("格式不正确");
                        etSalePrice.requestFocus();
                        return false;
                    }

                    // 计算实际支付价格
                    _setActualPay(_calcActualPay());

                }
                return false;
            }
        });

        //销售价改变
        etSalePrice.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!StringUtil.isMoney(text)) {
//                    MyApplication.showToast("格式不正确");
                    etSalePrice.requestFocus();
                    return;
                }
                // 计算实际支付价格
                _setActualPay(_calcActualPay());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }
        });
/**
 *服务金额监听
 */
        et_actual_pay.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {//点击完成

                    if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
                        MyApplication.showToast("格式不正确");
                        et_actual_pay.requestFocus();
                        return false;
                    }
                    actual_pay = Double.parseDouble(et_actual_pay.getText().toString().trim());
                }
                return false;
            }
        });
        /**
         * 实收款 监听
         */
        tvRelityPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {//点击完成
                    if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
                        MyApplication.showToast("格式不正确");
                        tvRelityPrice.requestFocus();
                        return false;
                    }
                    sum_price = Double.parseDouble(tvRelityPrice.getText().toString().trim());
                }
                return false;
            }
        });

        // 数量发生变化
        amount_view.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                buy_num = amount;
                // 计算实际支付价格
                _setActualPay(_calcActualPay());
            }
        });

        tvBaoyangDistance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {//点击完成
                    baoyangDiatance = tvBaoyangDistance.getText().toString().trim();
                }
                return false;
            }
        });
        /**
         * 下次保养时间
         */
        tvBaoyangNexttime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                baoyangNextTime = tvBaoyangNexttime.getText().toString().trim();
            }
        });

    }

    /**
     * 获取挂单界面过来的某个类别下的所有商品
     *
     * @param goods_category_id
     */
    private void doGetProducts(String goods_category_id) {
        // 获取所有产品
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("goods_category_id", goods_category_id);// 商品分类id
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_LIST, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        productList = JSON.parseArray(element.body, Product.class);
                        if (CollectionUtil.isEmpty(productList)) {
                            MyApplication.showToast("服务列表为空");
                            tv_product.setText("还未有上架的服务");
                            return;
                        }
                        productListAdapter = new ProductListAdapter(mContext, R.layout.item_bottom_dialog, productList);
                        for (int i = 0; i < productList.size(); i++) {

                            if (shoppingcar.goods.goods_name.equals(productList.get(i).name)) {
                                //商品名称一致
                                selectedProductPos = i;
                            }
                        }
                    }
                });
    }

    /**
     * 获取从挂单界面的服务师傅列表
     */
    private void doGetWorkers() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        VictorHttpUtil.doGet(mContext, Define.URL_STAFF_USER_LIST, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        workerList = JSON.parseArray(element.body, User.class);
                        if (CollectionUtil.isEmpty(workerList)) {
                            MyApplication.showToast("服务师傅列表为空");
                            return;
                        }
                        for (int i = 0; i < workerList.size(); i++) {
                            if (workerList.get(i).staff_user_id.equalsIgnoreCase(pending.sale_user_id + "")) {
                                selectedWorkerPos = i;
                                tv_worker.setText(workerList.get(i).user_name);
                            }
                        }


                        workerListAdapter = new WorkerListAdapter(mContext, R.layout.item_bottom_dialog, workerList);
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
                        workerList = JSON.parseArray(element.body, User.class);
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
     * 获取某个类别下的所有商品
     *
     * @param goods_category_id
     */
    private void _doGetProducts(String goods_category_id) {
        // 获取所有产品
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("goods_category_id", goods_category_id);// 商品分类id
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_LIST, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        productList = JSON.parseArray(element.body, Product.class);
                        if (CollectionUtil.isEmpty(productList)) {
                            MyApplication.showToast("服务列表为空");
                            tv_product.setText("还未有上架的服务");
                            return;
                        }
                        tv_product.setText(productList.get(0).name);
                        //  tvProductPrice.setText(productList.get(0).sale_price + "元");
                        etSalePrice.setText(productList.get(0).sale_price + "");
                        // 计算实际支付价格
                        _setActualPay(_calcActualPay());
                        productListAdapter = new ProductListAdapter(mContext, R.layout.item_bottom_dialog, productList);
                    }
                });
    }

    /**
     * 选择用户
     */
    @OnClick(R.id.iv_choose_customer)
    void gotoChooseCustomer() {
        if (pending != null) {
            //挂单过来的,不让修改
            MyApplication.showToast("编辑挂单不能修改用户");
            return;
        }
        etSearchCarNumber.setText("");
        Intent intent = new Intent(mContext, ChooseCustomerActivity.class);
        startActivityForResult(intent, 100);
        //   MyApplication.openActivity(mContext, ChooseCustomerActivity.class);
    }

    /**
     * 选中用户后
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                Customer cust = (Customer) data.getSerializableExtra("cusstom");
                if (cust != null) {
                    if (!TextUtils.isEmpty(mobile) && mobile.equals(cust.mobile)) {
                        //如果mobile不为空并且选择的手机号和mobile相同
                        return;
                    }
                    car_plate_no = "";
                    et_search.setText(cust.mobile);
                    if (TextUtils.isEmpty(cust.mobile) && StringUtil.isPln(cust.car_plate_no)) {
                        plnAdd = cust.car_plate_no.charAt(0) + "";
                        btn_pln_addr.setText(plnAdd);
                        etSearchCarNumber.setText(cust.car_plate_no.substring(1));
                    }
                    tvUserName.setText(TextUtils.isEmpty(cust.name) ? "未知" : cust.name);
                }
                break;
        }
    }

    /**
     * 选择车牌号
     */
    @OnClick(R.id.iv_choose_car_no)
    void gotoChooseCarno() {

        if (pending != null) {
            //挂单过来的,不让修改
            MyApplication.showToast("编辑挂单不能修改用户");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);
        MyApplication.openActivity(mContext, ChooseCarActivity.class, bundle);
    }

    /**
     * 选择车辆之后
     *
     * @param userCar
     */
    @Subscribe
    public void onChoosedCar(ChooseCar userCar) {
        if (userCar != null) {

            plnAdd = userCar.car_plate_no.charAt(0) + "";
            btn_pln_addr.setText(plnAdd);
            etSearchCarNumber.setText(userCar.car_plate_no.substring(1));
        }
    }
    /**
     * 计算实际支付价格
     */
    private double _calcActualPay() {
        if (CollectionUtil.isEmpty(productList)) {
            return 0;
        }
        String sale = etSalePrice.getText().toString().trim();
        double sale_price = Double.parseDouble(sale);

        int count = amount_view.getAmount();
        if (count <= 0) {
            return sale_price;
        }
        actual_pay = MathUtil.mul(sale_price, count);
        return actual_pay;
    }
    /**
     * 去扫一扫界面
     */
    @OnClick(R.id.topbar_right)
    void gotoScan() {
        new TedPermission(mActivity)
                .setPermissions(Manifest.permission.CAMERA)
                .setDeniedMessage(R.string.rationale_camera)
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                    //    MyApplication.openActivity(mContext, ScanActivity.class);
                        finish();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();
    }

    /**
     * 展示所有类型弹框
     */
    @OnClick(R.id.area_category)
    void showCategoryDialog() {
        if (CollectionUtil.isEmpty(categoryList)) {
            MyApplication.showToast("类别列表为空");
            return;
        }
        BottomDialogFragment.newInstance(categoryListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedCategoryPos) {
                    return;
                }
                selectedCategoryPos = position;
                categoryListAdapter.notifyDataSetChanged();
                tv_category.setText(categoryList.get(position).name);
                //这个版本不上，下次再上
                if ("保养".equals(tv_category.getText().toString().trim())) {
                    linBaoyang.setVisibility(View.VISIBLE);
                } else {
                    linBaoyang.setVisibility(View.GONE);
                }
                // 产品列表联动
                _doGetProducts(categoryList.get(position).goods_category_id);

            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 展示所有产品弹框
     */
    @OnClick(R.id.area_product)
    void showProductDialog() {
        if (CollectionUtil.isEmpty(productList)) {
            MyApplication.showToast("服务列表为空");
            return;
        }
        BottomDialogFragment.newInstance(productListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProductPos = position;
                productListAdapter.notifyDataSetChanged();
                tv_product.setText(productList.get(position).name);

                //tvProductPrice.setText(productList.get(position).sale_price + "元");
                etSalePrice.setText(productList.get(position).sale_price + "");
                // 计算实际支付价格
                _setActualPay(_calcActualPay());

            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
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
     * 设置实际支付价格
     *
     * @param amount
     */
    private void _setActualPay(double amount) {
        et_actual_pay.setText(MathUtil.getFinanceValue(amount) + "元");
        double sub = MathUtil.sub(amount, coupon_price);
        if (sub<0.00) {
            sub=0.00;
        }
        tvRelityPrice.setText(MathUtil.getFinanceValue(sub) + "元");
        sum_price=MathUtil.sub(actual_pay,coupon_price);
        if (sum_price<0.00) {
            sum_price=0.00;
        }
    }
    /**
     * 显示错误提示
     */
    private void showPopUp() {
        View ppw = View.inflate(mContext, R.layout.ppw_error_input, null);
        PopupWindow popupWindow = new PopupWindow(ppw, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);// 不产生焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(et_search, 0, 0);
    }

    /**
     * 执行搜索操作
     */
    private void _doSeach(final String text) {
        if (text.length() != 11 && text.length() != 7) {
            return;
        }
        if (!StringUtil.isPhoneNumber(text) && !StringUtil.isPln(text)) {
//            showPopUp();
            return;
        }
        if (StringUtil.isPhoneNumber(text)) {
            mobile = text;
        } else if (StringUtil.isPln(text)) {
            car_plate_no = text;
        }
        // 获取用户和卡信息
        MyParams params = new MyParams();

        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("mobile", mobile);// 手机号
        params.put("car_plate_no", car_plate_no);//车牌号
        VictorHttpUtil.doGet(mContext, Define.URL_PROVIDER_USER_CARD_LIST_receipt, params, false, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        JSONObject jsonobj = parseObject(element.body);
                        JSONObject user_info = jsonobj.getJSONObject("user_info");
                        if (user_info != null) {
                            userIsExist = true;
                            tvUserName.setText(TextUtils.isEmpty(user_info.getString("name")) ? "无姓名" : user_info.getString("name"));
                            // 手机号
                            mobile = user_info.getString("mobile");
                            //车牌号
                            car_plate_no = user_info.getString("car_plate_no");
                            if (!StringUtil.isPhoneNumber(et_search.getText().toString().trim()) || !et_search.getText().toString().trim().equals(mobile)) {
                                //如果et_search不是手机号，说明是车牌号搜索，填充手机号
                                et_search.setText(mobile);
                            }
                            if (!StringUtil.isPln(plnAdd + etSearchCarNumber.getText().toString().trim()) || !(plnAdd + etSearchCarNumber.getText().toString().trim()).equals(car_plate_no)) {
                                if (StringUtil.isPln(car_plate_no)) {
                                    plnAdd = car_plate_no.charAt(0) + "";
                                    btn_pln_addr.setText(plnAdd);
                                    // 客户车牌号
                                    if (!StringUtil.isEmpty(car_plate_no) && car_plate_no.length() >= 1) {
                                        etSearchCarNumber.setText(car_plate_no.substring(1));
                                    }
                                }
                            }
                            if (user_info.getIntValue("is_completed") == 1) {//信息完善
                                isCompleted = true;

                            }
                            // 用户编号
                            provider_user_id = user_info.getString("provider_user_id");
                            /**
                             * 初始化优惠券  以下初始化 在选择完优惠券以后文字改变颜色：-¥10.00  c_ef5350
                             */

                            coupon_count = jsonobj.getString("coupon_count");
                            if (!StringUtil.isEmpty(coupon_count)) {
                                if ("0".equals(coupon_count)) {
                                    tvCoupon.setText("无优惠券");
                                    tvCoupon.setTextColor(getResources().getColor(R.color.dark_gray_text));
                                }else {
                                    tvCoupon.setText("有"+coupon_count+"张优惠券");
                                    tvCoupon.setTextColor(getResources().getColor(R.color.c_ef5350));
                                }
                            }
                        }

                        vipcardList = (ArrayList<Vipcard>) JSON.parseArray(jsonobj.getString("cards"), Vipcard.class);// 会员卡列表

                        // 赠品列表
                        giftList = (ArrayList<OrderGift>) JSON.parseArray(jsonobj.getString("available_order"), OrderGift.class);// 会员卡列表
                        if (!CollectionUtil.isEmpty(giftList)) {//有赠品，提示用户
                            if (popupWindow == null || !popupWindow.isShowing()) {
                                _showGiftPop();
                            }
                        }

                        //                        } else if (element.getCode() == 0 && element.getMsg().equals("没有用户")) {

                        if (jsonobj.getIntValue("exist_user") == -1) {
                            //用户不存在
                            AlertDialogFragment.newInstance(
                                    "提示",
                                    "还不是您的会员,是否添加？",
                                    "暂不添加",
                                    "去添加",
                                    null,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("mobile", mobile);
                                            bundle.putString("car_plate_no", car_plate_no);
                                            MyApplication.openActivity(mContext, AddCustomerActivity.class, bundle);
                                        }
                                    })
                                    .show(getSupportFragmentManager(), getClass().getSimpleName());
//                            MyApplication.showToast(element.getMsg());
                            tvUserName.setText("无姓名");
                            userIsExist = false;
                        } else {
                            userIsExist = true;
                        }
                        //                        } else if (element.getCode() == 0 && element.getMsg().equals("手机号车牌号信息不符")) {

                        if (jsonobj.getIntValue("conflict") == 1) {
                            //有冲突
                            final String user_id = jsonobj.getString("provider_user_id");

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
                                            bundle.putString("provider_user_id", user_id);
                                            MyApplication.openActivity(mContext, ConflictActivity.class, bundle);
                                        }
                                    })
                                    .show(getSupportFragmentManager(), getClass().getSimpleName());
                        }
                    }
                });
    }

    private ArrayList<OrderGift> giftList;

    /**
     * 显示赠品提示
     */
    private void _showGiftPop() {
        View ppw = View.inflate(mContext, R.layout.ppw_gift, null);
        popupWindow = new PopupWindow(ppw, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);// 不产生焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(et_search, 0, 0);
        // 取消
        ppw.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
        // 使用(去赠品列表界面)
        ppw.findViewById(R.id.tv_use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("giftList", giftList);
                MyApplication.openActivity(mContext, GiftListActivity.class, bundle);

            }
        });
    }

    /**
     * 开始支付
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        if (CollectionUtil.isEmpty(productList)) {
            MyApplication.showToast("服务列表为空");
            return;
        }

        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("服务师傅列表为空");
            return;
        }

        if (TextUtils.isEmpty(etSalePrice.getText().toString().trim())) {
            MyApplication.showToast("请输入成交单价");
            return;
        }

        if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
            MyApplication.showToast("成交单价格式不正确");
            etSalePrice.requestFocus();
            return;
        }
// || !isCompleted || vipcardList==null || vipcardList.size()==0
        if (!userIsExist) {
            // 不显示会员卡支付
            payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES2);
            payTypeListAdapter.notifyDataSetChanged();
        } else {
            // 支付方式列表适配器
            payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES);
            payTypeListAdapter.notifyDataSetChanged();
        }
        if (sum_price ==0.00) {
            _doCashPay("6");
        }else {
            BottomDialogFragment.newInstance(payTypeListAdapter,
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (userIsExist) {
                                //用户存在
                                switch (position) {
                                    case 0:// 会员卡支付
                                            _gotoSelectVipcard();
                                        break;
                                    case 1:// 银联/现金支付
                                        AlertDialogFragment.newInstance(
                                                "友情提示",
                                                "请确认您已收到用户的现金/刷卡支付",
                                                "取消",
                                                "确定",
                                                null,
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        _doCashPay("6");
                                                    }
                                                })
                                                .show(getSupportFragmentManager(), getClass().getSimpleName());
                                        break;
                                    case 2://微信支付
                                            _doWxpayAlipay("2");
                                        break;
                                    default://支付宝支付
                                            _doWxpayAlipay("1");
                                        break;
                                }
                            } else {
                                //用户不存在
                                switch (position) {
                                    case 0:// 银联/现金支付
                                        AlertDialogFragment.newInstance(
                                                "友情提示",
                                                "请确认您已收到用户的现金/刷卡支付",
                                                "取消",
                                                "确定",
                                                null,
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        _doCashPay("6");
                                                    }
                                                })
                                                .show(getSupportFragmentManager(), getClass().getSimpleName());
                                        break;
                                    case 1://微信支付
                                            _doWxpayAlipay("2");
                                        break;
                                    default://支付宝支付
                                            _doWxpayAlipay("1");
                                        break;
                                }
                            }
                        }
                    })
                    .show(getSupportFragmentManager(), getClass().getSimpleName());
        }

    }

    /**
     * 提交/编译到挂单
     */
    @OnClick(R.id.btn_pending_order)
    void doPendingOrder() {
        if (CollectionUtil.isEmpty(productList)) {
            MyApplication.showToast("服务列表为空");
            return;
        }

        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("服务师傅列表为空");
            return;
        }

        if (TextUtils.isEmpty(etSalePrice.getText().toString().trim())) {
            MyApplication.showToast("请输入销售价");
            return;
        }

        if (!StringUtil.isMoney(etSalePrice.getText().toString().trim())) {
            MyApplication.showToast("销售价格式不正确");
            etSalePrice.requestFocus();
            return;
        }
        // 获取用户和卡信息
        Product product = productList.get(selectedProductPos);
        String goods_data = categoryList.get(selectedCategoryPos).goods_category_id + "_" + product.goods_id + "_" + buy_num + "_" + etSalePrice.getText().toString().trim();

        if (pending != null) {
            //说明是编译
            editPending(goods_data);
            return;
        }
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id， APP 必传,微信不传
        params.put("sale_user_id", workerList.get(selectedWorkerPos).staff_user_id);// 销售者id， APP 必传,微信不传
        params.put("mobile", mobile);
        params.put("car_plate_no", car_plate_no);
        params.put("goods_data", goods_data);//商品数据,格式： 服务类别id_商品id_购买数量， 多个商品用逗号分割
        params.put("total_price", actual_pay);//总价
        if (!TextUtils.isEmpty(baoyangDiatance)) {
            params.put("mileage", Float.parseFloat(baoyangDiatance));//行驶里程
        }
        if (!TextUtils.isEmpty(baoyangNextTime)) {
            params.put("next_maintain", baoyangNextTime);//下次保养时间
        }
        VictorHttpUtil.doPost(mContext, Define.url_cart_add_pending_order, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
//
//                        if (element.code == 0 && element.msg.equals("成功")) {
//                            MyApplication.showToast("挂单成功");
//                            MyApplication.openActivity(mContext, PendingOrderActivity.class);
//                            finish();
//                        }
                    }
                });
    }

    //提交编译挂单
    private void editPending(String goods_data) {
        MyParams params = new MyParams();
        params.put("cart_id", pending.cart_id);
        params.put("sale_user_id", workerList.get(selectedWorkerPos).staff_user_id);
        params.put("mobile", mobile);
        params.put("car_plate_no", car_plate_no);
        params.put("goods_data", goods_data);
        params.put("total_price", actual_pay);
        if (!TextUtils.isEmpty(baoyangDiatance)) {
            params.put("mileage", Float.parseFloat(baoyangDiatance));//行驶里程
        }
        if (!TextUtils.isEmpty(baoyangNextTime)) {
            params.put("next_maintain", baoyangNextTime);//下次保养时间
        }

        VictorHttpUtil.doPost(mContext, Define.url_cart_edit_pending_order, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {

//                        if (element.code == 0 && element.msg.equals("成功")) {
//                            MyApplication.showToast("挂单编辑成功");
////                            MyApplication.openActivity(mContext,PendingOrderActivity.class);
//                            finish();
//                        }
                    }
                });
    }

    /**
     * 去用户详情
     */
    @OnClick({R.id.ll_use_name, R.id.area_first_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_use_name:
                if (StringUtil.isEmpty(et_search.getText().toString().trim())) {
                    Toast.makeText(mContext, "请输入手机号或车牌号查询信息", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(provider_user_id)) {
                    MyApplication.showToast("没有该用户");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("provider_user_id", provider_user_id);
                    MyApplication.openActivity(mContext, CustomerDetailsActivity.class, bundle);
                }
                break;
            case R.id.area_first_time://下次保养时间
                //显示时间对话框
                showDatePickerDialog();

                break;

        }

    }

    /**
     * 显示时间选择框
     */
    private void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = tvBaoyangNexttime.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }

        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        tvBaoyangNexttime.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                // .setMinMillseconds(System.currentTimeMillis()-_100year)//设置最小时间
                .setMinMillseconds(System.currentTimeMillis())//设置最小时间为当前时间
                .setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                //.setMaxMillseconds(System.currentTimeMillis())//设置最大时间是当前时间
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 去选择优惠券
     */
    @OnClick(R.id.area_coupon)
    void gotoSelectCoupon() {
        // 获取用户和卡信息
        if (!StringUtil.isEmpty(coupon_count)) {
            if ("0".equals(coupon_count)) {
                MyApplication.showToast("没有优惠券可用");
            }else {
                if (!CollectionUtil.isEmpty(productList)) {
                    Product product = productList.get(selectedProductPos);
                    Bundle bundle=new Bundle();
                    bundle.putString("mobile",mobile);
                    bundle.putString("car_plate_no",car_plate_no);
                    bundle.putString("goods_id",product.goods_id);
                    bundle.putString("price",actual_pay+"");
                    MyApplication.openActivity(mContext, CouponActivity.class,bundle);
                }else {
                    MyApplication.showToast("请选择类别服务");
                }

            }
        }
    }
    /**
     * 产品列表适配器
     */
    private class CategoryListAdapter extends VictorBaseListAdapter<Channel> {

        private Context mContext;

        public CategoryListAdapter(Context context, int layoutResId, List<Channel> mList) {
            super(context, layoutResId, mList);
            this.mContext = context;
        }

        @Override
        public void bindView(int position, View view, Channel entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);
            textView.setTextColor(getResources().getColor(selectedCategoryPos == position ? R.color.theme_color : R.color.black_text));
        }
    }

    /**
     * 产品列表适配器
     */
    private class ProductListAdapter extends VictorBaseListAdapter<Product> {

        public ProductListAdapter(Context context, int layoutResId, List<Product> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, Product entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);
            textView.setTextColor(getResources().getColor(selectedProductPos == position ? R.color.theme_color : R.color.black_text));
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

    /**
     * 拍照识别车牌号后
     *
     * @param event
     */
    @Subscribe
    public void onReceivePln(final PlnEvent event) {
        if (event != null) {
            et_search.setText(event.pln);
            _doSeach(et_search.getText().toString().trim());
        }
    }
    /**
     * 支付方式通用参数
     *
     * @return
     */
    private MyParams getParams() {
        MyParams params = new MyParams();
        // 获取用户和卡信息
        Product product = productList.get(selectedProductPos);
        String goods_data = categoryList.get(selectedCategoryPos).goods_category_id + "_" + product.goods_id + "_" + buy_num + "_" + etSalePrice.getText().toString().trim();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商编号
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);// 登陆者id， APP 必传,微信不传
        params.put("sale_user_id", workerList.get(selectedWorkerPos).staff_user_id);// 销售者id， APP 必传,微信不传
        params.put("mobile", mobile);
        params.put("car_plate_no", car_plate_no);
        params.put("goods_data", goods_data);//商品数据,格式： 服务类别id_商品id_购买数量， 多个商品用逗号分割
        if (!TextUtils.isEmpty(baoyangDiatance))
            params.put("mileage", baoyangDiatance);//行驶里程
        if (!TextUtils.isEmpty(baoyangNextTime)){
            params.put("next_maintain", baoyangNextTime);//下次保养时间
        }
            params.put("coupon_grant_record_id", coupon_id);//选择用户优惠券的id集合,多个用逗号分割，默认为空
          params.put("save_amount", coupon_price);//否	优惠金额，默认为0
        return params;
    }
    /**
     * 实际现金支付
     */
    private void _doCashPay(String type) {
        /**
         * 格式--支付方式_支付金额_卡号，(无卡的卡号为0)
         * 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
         */
        String pay_amount = String.format(type+"_%s_0", sum_price);

        MyParams params = getParams();
        params.put("pay_amount", pay_amount);//支付方式:格式--支付方式_支付金额_卡号，(无卡的卡号为000000) 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡

        if (pending != null) {
            params.put("cart_id", pending.cart_id);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_ORDER_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        // 跳转到支付结果界面
                        _gotoReceiptResult();
                    }

                    @Override
                    public void onFaliure(String url, int statusCode, String content, Throwable error) {
                        super.onFaliure(url, statusCode, content, error);
                    }
                });
    }

    /**
     * 跳转到支付结果界面
     */
    private void _gotoReceiptResult() {
        // 跳转到支付结果界面
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
        bundle.putString("provider_user_id", provider_user_id);//用户id
        MyApplication.openActivity(mContext, ReceiptResultActivity.class, bundle);
        // 关闭当前界面
        finish();
    }

    /**
     * 去选择会员卡界面
     */
    private void _gotoSelectVipcard() {
        Bundle bundle = new Bundle();
        bundle.putString("mGoodsId", productList.get(selectedProductPos).goods_id);
        bundle.putDouble("mTotalEb", sum_price);//所需总eb
        bundle.putInt("mTotalNum", productList.get(selectedProductPos).card_num_price * buy_num);// 所需总次数
        bundle.putSerializable("mVipcardList", vipcardList);//会员卡列表
        MyApplication.openActivity(mContext, ChooseReceiptVipcardActivity.class, bundle);
    }
    /**
     * 选择会员卡后
     *
     * @param vipcard
     */
    @Subscribe
    public void onSelectedVipcard(final Vipcard vipcard) {
        if (vipcard == null) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * 格式--支付方式_支付金额_卡号，(无卡的卡号为0)
                 * 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡 7.年卡
                 */
                StringBuilder pay_amount = new StringBuilder();
                pay_amount.append(vipcard.card_category_id == 1 ? "3" : (vipcard.card_category_id == 2 ? "4" : "7"))
                        .append("_");
                if (vipcard.card_category_id == 1) {
                    pay_amount.append(productList.get(selectedProductPos).card_num_price * buy_num);
                } else {
                    pay_amount.append(sum_price);
                }
                pay_amount.append("_")
                        .append(vipcard.card_no);

                MyParams params = getParams();
                params.put("pay_amount", pay_amount.toString());//支付方式:格式--支付方式_支付金额_卡id，(无卡的卡id为0) 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
                if (pending != null) {
                    params.put("cart_id", pending.cart_id);
                }
                VictorHttpUtil.doPost(mContext, Define.URL_ORDER_ADD, params, true, "加载中...",
                        new BaseHttpCallbackListener<Element>() {
                            @Override
                            public void callbackSuccess(String url, Element element) {
                                // 跳转到支付结果界面
                                _gotoReceiptResult();
                            }
                        });
            }
        }, 100);
    }

    /**
     * 微信支付，支付宝支付
     */
    private void _doWxpayAlipay(final String pay_type) {

        /**
         * 格式--支付方式_支付金额_卡号，(无卡的卡号为000000)
         * 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡
         */
        String pay_amount = String.format("%s_%s_0", pay_type, sum_price);

        MyParams params = getParams();
        params.put("pay_amount", pay_amount);//支付方式:格式--支付方式_支付金额_卡id，(无卡的卡id为0) 1.支付宝 2.微信 3.次数 4.eb 5.账户余额 6.现金或刷卡

        if (pending != null) {
            params.put("cart_id", pending.cart_id);
        }
        VictorHttpUtil.doPost(mContext, Define.URL_ORDER_ADD, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {

                        JSONObject jsonobj = JSON.parseObject(element.body);
                        if (jsonobj == null) {
                            MyApplication.showToast("订单返回数据异常");
                            return;
                        }
                        String url0 = jsonobj.getString("url");//收款二维码图片地址
                        String order_id = jsonobj.getString("order_id");//订单id

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isCompleted", isCompleted);//信息是否完善
                        bundle.putString("provider_user_id", provider_user_id);//用户id
                        bundle.putString("pay_type", pay_type);//支付方式
                        bundle.putString("actual_pay", MathUtil.getFinanceValue(sum_price));//支付金额
                        bundle.putString("url", url0);//收款二维码图片地址
                        bundle.putString("order_id", order_id);//订单id
                        MyApplication.openActivity(mContext, ReceiptQrcodeActivity.class, bundle);
                        // 关闭当前界面
                        finish();
                    }
                });
    }

    /**
     * 支付方式列表适配器
     */
    private class PayTypeListAdapter extends VictorBaseArrayAdapter<String> {

        public PayTypeListAdapter(Context context, int layoutResId, String[] array) {
            super(context, layoutResId, array);
        }
        @Override
        public void bindView(int position, View view, String entity) {
            if (array.length == PAY_TYPE_ICONS.length) {
                //支付方式图标
                ImageView iv_paytype = AbViewHolder.get(view, R.id.iv_paytype);
                iv_paytype.setImageResource(PAY_TYPE_ICONS[position]);
                // 支付方式名称
                TextView tv_name = AbViewHolder.get(view, R.id.tv_name);
                tv_name.setText(PAY_TYPE_NAMES[position]);
            } else {
                //支付方式图标
                ImageView iv_paytype = AbViewHolder.get(view, R.id.iv_paytype);
                iv_paytype.setImageResource(PAY_TYPE_ICONS2[position]);
                // 支付方式名称
                TextView tv_name = AbViewHolder.get(view, R.id.tv_name);
                tv_name.setText(PAY_TYPE_NAMES2[position]);
            }
        }
    }

    /**
     * 接收来自优惠券的信息
     */
    @Subscribe
    public  void receive (CouponEvent event){
        if (event == null) {
            return;
        }
        coupon_price=event.total_price;
        List<String> selectID = event.selectID;
        coupon_id="";
        for (String s : selectID) {
            coupon_id += s + ",";
        }
        coupon_id=coupon_id.substring(0,coupon_id.length()-1);
        tvCoupon.setText("-¥"+coupon_price);
        tvCoupon.setTextColor(getResources().getColor(R.color.c_ef5350));
        tvCoutopPrice.setText("-¥"+coupon_price);
        // 计算实际支付价格
        _setActualPay(_calcActualPay());

    }
}
