package com.victor.che.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.base.VictorBaseListAdapter;
import com.victor.che.domain.Channel;
import com.victor.che.domain.Customer;
import com.victor.che.domain.Product;
import com.victor.che.domain.User;
import com.victor.che.domain.Vipcard;
import com.victor.che.domain.VipcardCategory;
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

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * 自定义开卡界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/2 0002 13:54
 */
public class CustomActiveVipcardActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText et_mobile;

    @BindView(R.id.et_pln)
    EditText et_pln;

    @BindView(R.id.et_vipcard_name)
    EditText et_vipcard_name;

    @BindView(R.id.tv_category)
    TextView tv_category;

    @BindView(R.id.label_face_value)
    TextView label_face_value;

    @BindView(R.id.et_face_value)
    EditText et_face_value;

    @BindView(R.id.tv_service_content)
    TextView tv_service_content;

    @BindView(R.id.et_sale_price)
    EditText et_sale_price;

    @BindView(R.id.tv_worker)
    TextView tv_worker;

    @BindView(R.id.tv_gift)
    TextView tv_gift;
    @BindView(R.id.et_user_name)
    ClearEditText etUserName;

    private List<User> workerList = new ArrayList<>();
    private WorkerListAdapter workerListAdapter;
    private int selectedWorkerPos = 0;//选中的师傅位置

    private List<VipcardCategory> vipcardCategoryList;
    private int selectedCategoryPos = 0;//选中的类别位置
    private VipcardCategoryListAdapter vipcardCategoryListAdapter;

    private ArrayList<Channel> allCategoryList;//产品分类列表（内部包含所有产品）
    private int card_category_id;//要上传的卡类型id

    @Override
    public int getContentView() {
        return R.layout.activity_custom_active_vipcard;
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

        // 设置标题
        setTitle("自定义开卡");

        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_mobile));//手机号必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_vipcard_name));//卡名称必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_face_value));//初始次数必填
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_sale_price));//售价必填

        String mMobile = getIntent().getStringExtra("mMobile");
        String mPln = getIntent().getStringExtra("mPln");
        if (!StringUtil.isEmpty(mMobile)) {
            et_mobile.setText(mMobile);
        }
        if (!StringUtil.isEmpty(mPln)) {
            et_pln.setText(mPln);
        }

        // 支付方式列表适配器
        payTypeListAdapter = new PayTypeListAdapter(mContext, R.layout.item_pay_type, PAY_TYPE_NAMES);

        // 获取卡类型
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        VictorHttpUtil.doGet(mContext, Define.URL_CARD_CATEGORY_LIST, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        vipcardCategoryList = JSON.parseArray(element.body, VipcardCategory.class);
                        if (CollectionUtil.isEmpty(vipcardCategoryList)) {
                            MyApplication.showToast("会员卡类型列表为空");
                            return;
                        }
//                        card_category_id = vipcardCategoryList.get(0).card_category_id;
//                        tv_category.setText(vipcardCategoryList.get(0).name);
                        vipcardCategoryListAdapter = new VipcardCategoryListAdapter(mContext, R.layout.item_bottom_dialog, vipcardCategoryList);
                        _render();
                    }
                });

        et_mobile.addTextChangedListener(mobileTextWatcher);
        et_pln.addTextChangedListener(plnTextWatcher);

        // 获取所有师傅
        _doGetWorkers();

        // 获取所有分类和产品
        _doGetAllCategoryAndProduct();
    }

    /**
     * 获取所有分类和产品
     */
    private void _doGetAllCategoryAndProduct() {
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);// 服务商id
        VictorHttpUtil.doGet(mContext, Define.URL_GOODS_CATEGORY_DETAIL, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        allCategoryList = (ArrayList<Channel>) JSON.parseArray(element.body, Channel.class);

                        if (CollectionUtil.isEmpty(allCategoryList)) {
                            MyApplication.showToast("服务类型为空");
                            return;
                        }
                        // 过滤掉无产品的分类
                        List<Channel> temp = new ArrayList<>();
                        for (Channel item : allCategoryList) {
                            if (!CollectionUtil.isEmpty(item.goods)) {
                                temp.add(item);
                            }
                        }
                        allCategoryList.clear();
                        allCategoryList.addAll(temp);
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
                        JSONObject jsonobj = JSON.parseObject(element.body);

                        // 车牌号
                        if (jsonobj != null) {
                            et_pln.setText(jsonobj.getString("car_plate_no"));
                            et_mobile.setText(jsonobj.getString("mobile"));
                        }
                    }
                });
    }

    /**
     * 获取销售员列表
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
                            MyApplication.showToast("销售员列表为空");
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
     * 展示所有卡类型弹框
     */
    @OnClick(R.id.area_category)
    void showCategoryDialog() {
        if (CollectionUtil.isEmpty(vipcardCategoryList)) {
            MyApplication.showToast("卡类型列表为空");
            return;
        }
        BottomDialogFragment.newInstance(vipcardCategoryListAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedCategoryPos == position) {
                    return;
                }
                selectedCategoryPos = position;
                _render();
            }
        }).show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    /**
     * 选择服务范围
     */
    @OnClick(R.id.area_choose_product)
    void gotoChooseProduct() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mAllCategoryList", allCategoryList);
        MyApplication.openActivity(mContext, ChooseProductActivity.class, bundle);
    }

    /**
     * 选择完适用服务范围后
     *
     * @param allCategoryList
     */
    @Subscribe
    public void onChooseProducts(ArrayList<Channel> allCategoryList) {
        this.allCategoryList = allCategoryList;

        if (CollectionUtil.isEmpty(allCategoryList)) {
            tv_service_content.setText(getString(R.string.choose_product_range));
        } else {
            StringBuilder serviceTextBuidler = new StringBuilder();//服务内容
            StringBuilder rulesBuilder = new StringBuilder();//服务内容rules
            for (Channel category : allCategoryList) {
                if (category.checked) {//分类全选
                    serviceTextBuidler.append(",").append("全部" + category.name + "服务");

                    rulesBuilder.append(",").append("goods_category_id=").append(category.goods_category_id);
                } else {//分类未全选
                    if (!CollectionUtil.isEmpty(category.goods)) {
                        for (Product product : category.goods) {
                            if (product.checked) {
                                serviceTextBuidler.append(",").append(product.name);

                                rulesBuilder.append(",").append("goods_id=").append(product.goods_id);
                            }
                        }
                    }
                }
            }
            if (serviceTextBuidler.length() > 0) {//移除首位的“,”
                serviceTextBuidler.deleteCharAt(0);
            } else {// 未选中任何一个产品
                serviceTextBuidler.append(getString(R.string.choose_product_range));
            }
            tv_service_content.setText(serviceTextBuidler.toString());

            if (rulesBuilder.length() > 0) {
                rulesBuilder.deleteCharAt(0);
            }
            rules = rulesBuilder.toString();
        }

    }

    private String toastMsg = "";

    /**
     * 渲染界面
     */
    private void _render() {
        vipcardCategoryListAdapter.notifyDataSetChanged();
        card_category_id = vipcardCategoryList.get(selectedCategoryPos).card_category_id;
        tv_category.setText(vipcardCategoryList.get(selectedCategoryPos).name);
        et_face_value.setText("");
        // 切换label和输入框限制
        if (1 == card_category_id) {// 选中的是次卡
            label_face_value.setText("次数");
            et_face_value.setHint("请输入卡的初始次数");
            toastMsg = "请输入卡的初始次数";
            et_face_value.setInputType(TYPE_CLASS_NUMBER);
        } else if (2 == card_category_id) {//选中的是储值卡
            label_face_value.setText("余额");
            et_face_value.setHint("请输入卡的初始余额");
            toastMsg = "请输入卡的年数，如1、2";
            et_face_value.setInputType(TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (3 == card_category_id) {//选中的是年卡
            label_face_value.setText("年数");
            et_face_value.setHint("请输入卡的年数，如1、2");
            toastMsg = "请输入卡的年数，如1、2";
            et_face_value.setInputType(TYPE_CLASS_NUMBER);
        }
        ViewUtil.setLabelRequired((TextView) findViewById(R.id.label_face_value));//初始次数必填
    }

    /**
     * 展示所有师傅弹框
     */
    @OnClick(R.id.area_worker)
    void showWorkerDialog() {
        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("销售员列表为空");
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
                //        MyApplication.openActivity(mContext, ScanActivity.class, bundle);
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

    private Vipcard mVipcard;

    /**
     * 选择会员卡
     */
    @OnClick(R.id.iv_choose_vipcard)
    void gotoChooseVipcard() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mVipcard", mVipcard);
        MyApplication.openActivity(mContext, ChooseActiveVipcardActivity.class, bundle);
    }

    private String rules = "";//选择的可用服务（组装成请求接口的格式）

    /**
     * 选中会员卡后
     *
     * @param entity
     */
    @Subscribe
    public void onChoosedVipcard(Vipcard entity) {
        this.mVipcard = entity;
        if (entity != null) {
            et_vipcard_name.setText(entity.name);
            tv_category.setText(entity.getCardTypeName());
            label_face_value.setText(entity.getFaceValueLabel());
            et_face_value.setText(entity.card_category_id == 2 ? String.valueOf(entity.face_money) : String.valueOf(entity.available_num));
            tv_service_content.setText(entity.used_goods_text);
            card_category_id=entity.card_category_id;
//            if (entity.card_category_id == 3) {//年卡
//                label_face_value.setText("年数");
//                et_face_value.setHint("请输入卡的年数，如1、2");
//                et_face_value.setInputType(TYPE_CLASS_NUMBER);
//                et_face_value.setText(entity.available_year);// 卡使用年限
//            }
            if (entity.card_category_id == 1) {// 选中的是次卡
                label_face_value.setText("次数");
                et_face_value.setHint("请输入卡的初始次数");
                toastMsg = "请输入卡的初始次数";
                et_face_value.setInputType(TYPE_CLASS_NUMBER);
            } else if (entity.card_category_id == 2) {//选中的是储值卡
                label_face_value.setText("余额");
                et_face_value.setHint("请输入卡的初始余额");
                toastMsg = "请输入卡的年数，如1、2";
                et_face_value.setInputType(TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            } else if (entity.card_category_id == 3) {//选中的是年卡
                label_face_value.setText("年数");
                et_face_value.setHint("请输入卡的年数，如1、2");
                toastMsg = "请输入卡的年数，如1、2";
                et_face_value.setInputType(TYPE_CLASS_NUMBER);
                et_face_value.setText(entity.available_year);// 卡使用年限
            }
            // 组装可用服务的rules
            if (entity.all_service != null) {
                StringBuilder builder = new StringBuilder();
                if (!CollectionUtil.isEmpty(entity.all_service.goods_category_id)) {//拼接服务分类
                    for (String item : entity.all_service.goods_category_id) {
                        builder.append(",").append("goods_category_id=").append(item);
                    }
                }
                if (!CollectionUtil.isEmpty(entity.all_service.goods_id)) {//拼接具体的产品
                    for (String item : entity.all_service.goods_id) {
                        builder.append(",").append("goods_id=").append(item);
                    }
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(0);
                }
                rules = builder.toString();

                // 标记分类和产品是否被选中
                for (Channel category : allCategoryList) {
                    if (!CollectionUtil.isEmpty(entity.all_service.goods_category_id)) {// 分类是否被选中
                        category.checked = entity.all_service.goods_category_id.contains(category.goods_category_id);
                    }
                    if (!CollectionUtil.isEmpty(entity.all_service.goods_id)
                            && !CollectionUtil.isEmpty(category.goods)) {//具体的产品是否被选中
                        for (Product p : category.goods) {
                            p.checked = entity.all_service.goods_id.contains(p.goods_id);
                        }
                    }
                }
            }
        }
    }

    /**
     * 选择赠品
     */
    @OnClick(R.id.area_gift)
    void gotoChooseGift() {
        Bundle bundle = new Bundle();
        bundle.putInt("card_category_id", vipcardCategoryList.get(selectedCategoryPos).card_category_id);//卡类型id
        bundle.putSerializable("mCategoryList", allCategoryList);//所有分类和产品
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
     * 自定义开卡
     */
    @OnClick(R.id.btn_operate)
    void doOperate() {
        final String username = etUserName.getText().toString().trim();
        if (CollectionUtil.isEmpty(vipcardCategoryList)) {
            MyApplication.showToast("卡类型列表为空");
            return;
        }
        if (CollectionUtil.isEmpty(workerList)) {
            MyApplication.showToast("销售员列表为空");
            return;
        }
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

        final String card_name = et_vipcard_name.getText().toString().trim();
        if (StringUtil.isEmpty(card_name)) {
            MyApplication.showToast("请输入卡名称为空");
            et_vipcard_name.requestFocus();
            return;
        }

        final String face_value = et_face_value.getText().toString().trim();
        if (StringUtil.isEmpty(face_value)) {
            MyApplication.showToast(toastMsg);
            et_face_value.requestFocus();
            return;
        }
        if (Double.parseDouble(face_value) <= 0) {
            MyApplication.showToast(label_face_value.getText().toString() + "必须大于0");
            et_face_value.requestFocus();
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
                                                _doPay(6,username,mobile, pln, salePrice, card_name, face_value);
                                            }
                                        })
                                        .show(getSupportFragmentManager(), getClass().getSimpleName());
                                break;
                            case 1://2-微信
                                _doPay(2,username, mobile, pln, salePrice, card_name, face_value);
                                break;
                            case 2://1-支付宝
                                _doPay(1,username, mobile, pln, salePrice, card_name, face_value);
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
    private void _doPay(final int pay_method_id, String username,String mobile, String pln, final double salePrice, final String card_name, final String face_value) {
        // 请求接口
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//服务商id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        params.put("sale_user_id", workerList.get(selectedWorkerPos).staff_user_id);//销售者id
        params.put("mobile", mobile);//用户手机号
        params.put("car_plate_no", pln);//车牌号
        params.put("card_name", card_name);//卡名称
        params.put("card_category_id", card_category_id);//卡类型
        params.put("rules", rules);//适用的服务
        params.put("card_value", face_value);//卡面值（可以为次数、储值数或年数）
        params.put("sale_price", salePrice);//	销售价
        params.put("user_name", username);//	销售价
        if (giftEvent != null) {
            if (!StringUtil.isEmpty(giftEvent.gift_value)) {
                params.put("donate", giftEvent.gift_value);//赠送次数或余额
            }
            if (!StringUtil.isEmpty(giftEvent.gift_service)) {
                params.put("donate_service", giftEvent.gift_service);//赠送的服务,格式： 商品id=赠送次数 eg:1=10(1.0.4新增)
            }
        }
        params.put("pay_method_id", pay_method_id);//支付方式： 1-支付宝 2-微信 6-现金
        VictorHttpUtil.doPost(mContext, Define.URL_PROVIDER_CARD_CUSTOM_ALLOCATE, params, true, "处理中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        if (StringUtil.isEmpty(element.body)) {//开卡无冲突
                            MyApplication.showToast("自定义开卡成功");
                            if (pay_method_id == 6) {//现金支付
                                finish();
                            }
                        } else {
                            JSONObject jsonobj = JSON.parseObject(element.body);
                            if (jsonobj == null) {
                                MyApplication.showToast("订单返回数据异常");
                                return;
                            }
                            String url0 = jsonobj.getString("url");//收款二维码图片地址
                            String order_id = jsonobj.getString("order_id");//订单id
                            int conflict = jsonobj.getIntValue("conflict");
                            final String provider_user_id = jsonobj.getString("provider_user_id");
                            if (!StringUtil.isEmpty(url0)) {//自定义开卡
                                Bundle bundle = new Bundle();
                                bundle.putInt("mActiveVipcard", 2);//自定义开卡
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

        public PayTypeListAdapter(Context context, int layoutResId, String[] array) {
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

    /**
     * 卡类型列表适配器
     */
    private class VipcardCategoryListAdapter extends VictorBaseListAdapter<VipcardCategory> {

        public VipcardCategoryListAdapter(Context context, int layoutResId, List<VipcardCategory> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, VipcardCategory entity) {
            TextView textView = (TextView) view;
            textView.setText(entity.name);
            textView.setTextColor(getResources().getColor(selectedCategoryPos == position ? R.color.theme_color : R.color.black_text));
        }
    }
}
