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
import com.victor.che.bean.XunJianJiLuChaKan;

import butterknife.BindView;

public class XunJianJiLuChaKanActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.tv_yangzhichangmingcheng)
    TextView tvYangzhichangmingcheng;
    @BindView(R.id.tv_cunzaiwenti)
    TextView tvCunzaiwenti;
    @BindView(R.id.tv_zhenggaijianyi)
    TextView tvZhenggaijianyi;
    @BindView(R.id.tv_jiancharen)
    TextView tvJiancharen;
    @BindView(R.id.tv_beijiancharen)
    TextView tvBeijiancharen;
    @BindView(R.id.tv_jianchashijian)
    TextView tvJianchashijian;
    @Override
    public int getContentView() {
        return R.layout.activity_xun_jian_ji_lu_cha_kan;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("查看巡查记录");
        loadData(getIntent().getStringExtra("id"));
    }

    private void loadData(String id) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("id", id);
        VictorHttpUtil.doPost(mContext, Define.URL_XunJianJiLuChaKan + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        XunJianJiLuChaKan Policy = JSON.parseObject(element.body, XunJianJiLuChaKan.class);
                        XunJianJiLuChaKan.InspectionBean shopsCoupon = Policy.getInspection();
                        tvYangzhichangmingcheng.setText("养殖场名称：" + shopsCoupon.getAquFarm().getFarmName());
                        tvCunzaiwenti.setText("存在问题: " + shopsCoupon.getOthers());
                        tvZhenggaijianyi.setText("整改建议: " + shopsCoupon.getDispose());
                        tvJiancharen.setText("检查人: " + shopsCoupon.getUser().getName());
                        tvBeijiancharen.setText("被检查人: " + shopsCoupon.getCheckedName());
                        tvJianchashijian.setText("检查时间: " + shopsCoupon.getUpdateDate());
                    }
                });

    }
}
