package com.victor.che.ui.my;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.bean.YangZhiChangXiangQing;

import butterknife.BindView;

public class YangZhiChangDangAnXiangQingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.tv_yangzhichangmingcheng)
    TextView tvYangzhichangmingcheng;
    @BindView(R.id.tv_dizhi)
    TextView tvDizhi;
    @BindView(R.id.tv_xiangxidizhi)
    TextView tvXiangxidizhi;
    @BindView(R.id.tv_youzhengbianma)
    TextView tvYouzhengbianma;
    @BindView(R.id.tv_yangzhipingzhu)
    TextView tvYangzhipingzhu;
    @BindView(R.id.tv_yangzhimoshi)
    TextView tvYangzhimoshi;
    @BindView(R.id.tv_yangzhimianji)
    TextView tvYangzhimianji;
    @BindView(R.id.tv_miaozhonglaiyuan)
    TextView tvMiaozhonglaiyuan;
    @BindView(R.id.tv_nianshengchangnengli)
    TextView tvNianshengchangnengli;
    @BindView(R.id.tv_qiyeleixing)
    TextView tvQiyeleixing;
    @BindView(R.id.tv_chenglishijian)
    TextView tvChenglishijian;
    @BindView(R.id.tv_lianxidianhua)
    TextView tvLianxidianhua;

    @Override
    public int getContentView() {
        return R.layout.activity_yang_zhi_chang_dang_an_xiang_qing;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("查看养殖场信息");
        loadData(getIntent().getStringExtra("id"));
    }

    private void loadData(String id) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("id", id);
        VictorHttpUtil.doPost(mContext, Define.URL_YangZhiChangXingXi + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        YangZhiChangXiangQing Policy = JSON.parseObject(element.body, YangZhiChangXiangQing.class);
                        YangZhiChangXiangQing.AppAquFarmBean appAquFarm = Policy.getAppAquFarm();
                        tvYangzhichangmingcheng.setText(appAquFarm.getAquFarm().getFarmName());
                        tvDizhi.setText(appAquFarm.getAquFarm().getArea().getName());
                        tvXiangxidizhi.setText(appAquFarm.getAquFarm().getAddress());
                        tvYouzhengbianma.setText(appAquFarm.getAquFarm().getZipcode());
                        if (appAquFarm.getAquFarm().getFarmMethod()!=null){
                            if (appAquFarm.getAquFarm().getFarmMethod().equals("1")){
                                tvYangzhimoshi.setText("池塘");
                            }else if (appAquFarm.getAquFarm().getFarmMethod().equals("2")){
                                tvYangzhimoshi.setText("大水面放养");
                            }else if (appAquFarm.getAquFarm().getFarmMethod().equals("3")){
                                tvYangzhimoshi.setText("围栏");
                            }else if (appAquFarm.getAquFarm().getFarmMethod().equals("4")){
                                tvYangzhimoshi.setText("工厂化");
                            }else if (appAquFarm.getAquFarm().getFarmMethod().equals("5")){
                                tvYangzhimoshi.setText("筏吊式");
                            }else if (appAquFarm.getAquFarm().getFarmMethod().equals("6")){
                                tvYangzhimoshi.setText("滩涂底播");
                            }else if (appAquFarm.getAquFarm().getFarmMethod().equals("7")){
                                tvYangzhimoshi.setText("网箱");
                            }
                        }
                        tvYangzhimianji.setText(String.valueOf(appAquFarm.getAquFarm().getCultureArea()));
                        tvMiaozhonglaiyuan.setText(appAquFarm.getAquFarm().getSource());
                        tvNianshengchangnengli.setText(appAquFarm.getAquFarm().getAnnualOutput());

                        if (appAquFarm.getAquFarm().getCompanyType()!=null){
                            if (appAquFarm.getAquFarm().getCompanyType().equals("1")){
                                tvQiyeleixing.setText("私营独资企业");
                            }else if (appAquFarm.getAquFarm().getCompanyType().equals("2")){
                                tvQiyeleixing.setText("私营有限责任公司");
                            }else if (appAquFarm.getAquFarm().getCompanyType().equals("3")){
                                tvQiyeleixing.setText(" 其他有限责任公司");
                            }else if (appAquFarm.getAquFarm().getCompanyType().equals("4")){
                                tvQiyeleixing.setText("其他内资企业");
                            }else if (appAquFarm.getAquFarm().getCompanyType().equals("5")){
                                tvQiyeleixing.setText("中外合资经营企业");
                            }
                        }
                        tvChenglishijian.setText(appAquFarm.getAquFarm().getFoundedTime());
                        tvLianxidianhua.setText(String.valueOf(appAquFarm.getAquFarm().getTelephone()));
                    }
                });

    }
}
