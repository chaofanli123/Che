package com.victor.che.ui;
import android.Manifest;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.domain.UserCar;
import com.victor.che.domain.UserDetalis;
import com.victor.che.domain.Xingshizheng;
import com.victor.che.event.DrivingEvent;
import com.victor.che.event.PlnEvent;
import com.victor.che.util.DateUtil;
import com.victor.che.util.PicassoUtils;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.ClearEditText;
import com.victor.che.widget.PlnAddrButton;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 车险询价页面
 * Author lichaofan
 * Email
 * Time 2017-3-28
 */
public class QueryBaoxianActivity extends BaseActivity {
    private Context ac;

    @BindView(R.id.rl_pai)
    RelativeLayout rlPai;
    @BindView(R.id.img_paizhao)
    ImageView imgPaizhao;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;
    @BindView(R.id.btn_pln_addr)
    PlnAddrButton btnPlnAddr;
    @BindView(R.id.et_brandnum)
    ClearEditText etBrandnum;
    @BindView(R.id.et_chejianum)
    ClearEditText etChejianum;
    @BindView(R.id.et_fadongjinum)
    ClearEditText etFadongjinum;
    @BindView(R.id.tv_firsttime)
    TextView tvFirsttime;
    @BindView(R.id.tv_username)
    ClearEditText tvUsername;

    @BindView(R.id.et_carnumber)
    ClearEditText et_carnumber;


    /**
     * 拍照对话框popuwindow
     */
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View parentView;

    /**
     * 照片
     */
    private String imagePathList;
    private String pic = "";


    /**
     * 问号提示框
     */
    private PopupWindow popup_qrcode = null;

    private View viewFadongji;
    private View viewChejiahao;
    private View viewQrcode;
    private View viewTime;

    private View viewSuoyouren;
    private File filePath;
    private String flPath;

    /*
  上传的图片文件
   */
    private File file;

    private UserCar mcar;
    private QueryBaoxianHistory querybaoxianCar;//从查询历史失败传过来的车辆保险信息

    private TedPermission tedPermission;
    private ArrayList<String> imageUrlList;

    private Xingshizheng xingshizheng=new Xingshizheng();//需要上传的信息
    private String carnumber,  brandnum,chejianum,fadongjinum,firsttime,souyouren;
    /**
     * 从用户详情传过来的车辆信息
     */
    private UserDetalis.CarBean userCar;
    @Override
    public int getContentView() {
        return R.layout.activity_query_baoxian;
    }
     protected void initView() {
         userCar= (UserDetalis.CarBean) getIntent().getSerializableExtra("mCar");
         if (userCar != null) {
             btnPlnAddr.setText(userCar.getCar_plate_no().substring(0,1));
             et_carnumber.setText(userCar.getCar_plate_no().substring(1,userCar.getCar_plate_no().length()));
             carnumber = btnPlnAddr.getText().toString().trim()+et_carnumber.getText().toString().trim();

         }
         parentView = View.inflate(QueryBaoxianActivity.this,  R.layout.activity_query_baoxian, null);
         ac=this;
         setTitle("车险询价");
        rlPai.setVisibility(View.VISIBLE);
        rlPic.setVisibility(View.GONE);
        /**
         * 问号提示框
         */
        viewQrcode = View.inflate(mContext, R.layout.popuwindow_wenhao, null);

        viewChejiahao = View.inflate(mContext, R.layout.popuwindow_wenhao_chejiahao, null);

        viewFadongji = View.inflate(mContext, R.layout.popuwindow_wenhao_fadongjinum, null);

        viewTime = View.inflate(mContext, R.layout.popuwindow_wenhao_time, null);

         viewSuoyouren = View.inflate(mContext, R.layout.popuwindow_wenhao_suoyouren, null);

        popup_qrcode = new PopupWindow(mContext);
        popup_qrcode.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup_qrcode.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popup_qrcode.setBackgroundDrawable(new BitmapDrawable());
        popup_qrcode.setFocusable(true);
        popup_qrcode.setOutsideTouchable(true);
        RelativeLayout parent_qrcode = (RelativeLayout) viewQrcode.findViewById(R.id.parent_qrcode);
        parent_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_qrcode.dismiss();
            }
        });

        RelativeLayout parent_chejia = (RelativeLayout) viewChejiahao.findViewById(R.id.parent_chejia);
        parent_chejia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_qrcode.dismiss();
            }
        });

        RelativeLayout parent_fadongji = (RelativeLayout) viewFadongji.findViewById(R.id.parent_fadongji);
        parent_fadongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_qrcode.dismiss();
            }
        });

        RelativeLayout parent_regeist_time = (RelativeLayout) viewTime.findViewById(R.id.parent_regeist_time);
        parent_regeist_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_qrcode.dismiss();
            }
        });

        RelativeLayout parent_regeist_suoyouren = (RelativeLayout) viewSuoyouren.findViewById(R.id.parent_suoyouren);
         parent_regeist_suoyouren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_qrcode.dismiss();
            }
        });
    }
    /**
     * 拍照识别行驶证后
     * @param event
     */
    @Subscribe
    public void onReceiveDriving(DrivingEvent event){
        if (event == null) {
            return;
        }
        rlPai.setVisibility(View.GONE);
        rlPic.setVisibility(View.VISIBLE);
        filePath = event.file;
        flPath=filePath.getPath();
        PicassoUtils.loadFileImage(mContext,event.file,imgPaizhao);
        if (!StringUtil.isEmpty(event.driving.get号牌号码())){
            btnPlnAddr.setText(event.driving.get号牌号码().substring(0,1));
            et_carnumber.setText(event.driving.get号牌号码().substring(1,event.driving.get号牌号码().length()));
            carnumber = btnPlnAddr.getText().toString().trim()+et_carnumber.getText().toString().trim();
        }
//        if (event.driving.get号牌号码()!=null&&event.driving.get号牌号码().length()>event.driving.get号牌号码().length()){
//
//        }
        if (event.driving.get品牌型号()!=null){
            etBrandnum.setText(event.driving.get品牌型号());
            brandnum = etBrandnum.getText().toString().trim();
        }
        if (event.driving.get车辆识别代号()!=null){
            etChejianum.setText(event.driving.get车辆识别代号());
            chejianum = etChejianum.getText().toString().trim();
        }
        if (event.driving.get发动机号码()!=null){
            etFadongjinum.setText(event.driving.get发动机号码());
            fadongjinum = etFadongjinum.getText().toString().trim();
        }
        if (event.driving.get注册日期()!=null){
            tvFirsttime.setText(event.driving.get注册日期());
            firsttime = tvFirsttime.getText().toString().trim();
        }
        if (event.driving.get所有人()!=null){
            tvUsername.setText(event.driving.get所有人());
            souyouren = tvUsername.getText().toString().trim();
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
            if (event.pln!=null&&event.pln.length()>=1){
                btnPlnAddr.setText(event.pln.substring(0,1));
            }
            if (event.pln!=null&&event.pln.length()>event.pln.length()){
                et_carnumber.setText(event.pln.substring(1,event.pln.length()));
            }
        }
    }

    @OnClick({R.id.tv_firsttime,R.id.rl_xunjia, R.id.rl_chuxian, R.id.btn_froen_query, R.id.img_paizhao, R.id.tv_re_pai, R.id.iv_qrcode_scan, R.id.iv_hint_wenhao, R.id.iv_hint_chejia, R.id.iv_hint_fadongjinum, R.id.iv_hint_firsttime, R.id.btn_submit})
    public void onViewClicked(View view) {
        final Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_xunjia://询价记录
                MyApplication.openActivity(mContext,QueryHistoryActivity.class);
                break;
            case R.id.rl_chuxian://出险记录
                 MyApplication.openActivity(mContext, QueryPolicyHistoryActivity.class);
                break;
            case R.id.btn_froen_query://拍行驶证正面照
                new TedPermission(MyApplication.CONTEXT)
                        .setPermissions(Manifest.permission.CAMERA)
                        .setDeniedMessage(R.string.rationale_camera)
                        .setDeniedCloseButtonText("取消")
                        .setGotoSettingButtonText("设置")
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                bundle.putBoolean("mPostBack", true);//是否需要回传
                             //   MyApplication.openActivity(mContext, DiscernDrivingActivity.class, bundle);
                            }
                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            }
                        }).check();
                break;
            case R.id.img_paizhao://点击查看大图
                if (flPath!=null){
                    bundle.putString("filePath", flPath);
                    MyApplication.openActivity(mContext, BigPicActivity.class,bundle);
                }
                break;
            case R.id.tv_re_pai:
                new TedPermission(MyApplication.CONTEXT)
                        .setPermissions(Manifest.permission.CAMERA)
                        .setDeniedMessage(R.string.rationale_camera)
                        .setDeniedCloseButtonText("取消")
                        .setGotoSettingButtonText("设置")
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                bundle.putBoolean("mPostBack", true);//是否需要回传
                          //      MyApplication.openActivity(mContext, DiscernDrivingActivity.class, bundle);
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            }
                        }).check();
                break;
            case R.id.iv_qrcode_scan://拍车牌号
                new TedPermission(MyApplication.CONTEXT)
                        .setPermissions(Manifest.permission.CAMERA)
                        .setDeniedMessage(R.string.rationale_camera)
                        .setDeniedCloseButtonText("取消")
                        .setGotoSettingButtonText("设置")
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Bundle bundle1 = new Bundle();
                                bundle1.putBoolean("mPostBack", true);//是否需要回传
                          //      MyApplication.openActivity(mContext, ScanActivity.class, bundle1);
                            }
                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            }
                        }).check();

                break;
            case R.id.iv_hint_wenhao://品牌型号提示框
                popup_qrcode.setContentView(viewQrcode);
                popup_qrcode.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.iv_hint_chejia://车架号提示框
                popup_qrcode.setContentView(viewChejiahao);
                popup_qrcode.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.iv_hint_fadongjinum://发动机提示框
                popup_qrcode.setContentView(viewFadongji);
                popup_qrcode.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.iv_hint_firsttime://日期提示框
                popup_qrcode.setContentView(viewTime);
                popup_qrcode.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.iv_hint_user://所有人提示框
                popup_qrcode.setContentView(viewSuoyouren);
                popup_qrcode.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                break;
            case R.id.tv_firsttime://时间选择框期提示框
             showDatePickerDialog();
                break;
            case R.id.btn_submit://提交 进入投保方案 或者添加方案
                commit();
                break;
        }
    }
    /**
     * 将信息传到信息到保险公司页面
     */
    private void commit() {
        carnumber = btnPlnAddr.getText().toString().trim()+et_carnumber.getText().toString().trim();
        if (TextUtils.isEmpty(carnumber)) {
            MyApplication.showToast("车牌号不能为空");
            btnPlnAddr.requestFocus();
            et_carnumber.requestFocus();
            return;
        }
        if (!StringUtil.isEmpty(carnumber) && !StringUtil.isPln(carnumber)) {
            MyApplication.showToast("车牌号格式不正确");
            btnPlnAddr.requestFocus();
            et_carnumber.requestFocus();
            return;
        }
        brandnum = etBrandnum.getText().toString().trim();
        if (TextUtils.isEmpty(brandnum)) {
            MyApplication.showToast("品牌型号不能为空");
            etBrandnum.requestFocus();
            return;
        }
         chejianum = etChejianum.getText().toString().trim();
        if (TextUtils.isEmpty(chejianum)) {
            MyApplication.showToast("车架号不能为空");
            etChejianum.requestFocus();
            return;
        }
        if (chejianum.length() != 17) {
            MyApplication.showToast("请输入17位的车架号");
            return;
        }
       fadongjinum = etFadongjinum.getText().toString().trim();
        if (TextUtils.isEmpty(fadongjinum)) {
            MyApplication.showToast("发动机号不能为空");
            etFadongjinum.requestFocus();
            return;
        }
         firsttime = tvFirsttime.getText().toString().trim();
        if (TextUtils.isEmpty(firsttime)) {
            MyApplication.showToast("初次登记时间不能为空");
            tvFirsttime.requestFocus();
            return;
        }
         souyouren = tvUsername.getText().toString().trim();
        if (TextUtils.isEmpty(souyouren)) {
            MyApplication.showToast("所有人不能为空");
            tvUsername.requestFocus();
            return;
        }
        xingshizheng.setBrandnum(brandnum);
        xingshizheng.setCarnumber(carnumber);
        xingshizheng.setChejianum(chejianum);
        xingshizheng.setFadongjinum(fadongjinum);
        xingshizheng.setFirsttime(firsttime);
        xingshizheng.setSouyouren(souyouren);
        xingshizheng.setFilePath(filePath);
        Bundle bundle=new Bundle();
        bundle.putSerializable("Xingshizheng",xingshizheng);
        bundle.putString("style","query");
        MyApplication.openActivity(mContext,AddFanganActivity.class,bundle);
        finish();
    }
    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog() {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = tvFirsttime.getText().toString().trim();
        if (!StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }
        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        tvFirsttime.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - _100year)//设置最小时间
                //.setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

}
