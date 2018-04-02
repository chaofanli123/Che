package com.victor.che.ui.my;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.adapter.ShuiZhiJianCheAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.bean.ShuiZhiJianCheJiLu;

import java.util.ArrayList;

import butterknife.BindView;

public class ShuiZhiJianCheJiLuActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.address_expandable_listview)
    ExpandableListView addressExpandableListview;
    ArrayList<ArrayList<ShuiZhiJianCheJiLu.FirmListBean>> contactPersonsList;
    @Override
    public int getContentView() {
        return R.layout.activity_shui_zhi_jian_che_ji_lu;
    }


    @Override
    protected void initView() {
        super.initView();
        setTitle("查看水质监测记录");
        init();
    }

    private void init() {
        loadData();
    }

    /**
     * 获取商家优惠券
     *
     * @param
     */
    private void loadData() {
        MyParams params = new MyParams();
        VictorHttpUtil.doPost(mContext, Define.URL_ShuiZhiXingXi + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        contactPersonsList=new ArrayList<>();
                        ShuiZhiJianCheJiLu notify = JSON.parseObject(element.body, ShuiZhiJianCheJiLu.class);
                        ArrayList<ShuiZhiJianCheJiLu.FirmListBean> fujiedian = new ArrayList<>();
                        ArrayList<ShuiZhiJianCheJiLu.FirmListBean> zijiedian = new ArrayList<>();
                        ArrayList<ShuiZhiJianCheJiLu.FirmListBean> shuju = new ArrayList<>();
                        for (ShuiZhiJianCheJiLu.FirmListBean data: notify.getFirmList()){
                            if (data.getPId().equals("0")){
                                fujiedian.add(data);
                            }else {
                                zijiedian.add(data);
                            }
                        }
                        for (ShuiZhiJianCheJiLu.FirmListBean fdata: fujiedian){
                            for (ShuiZhiJianCheJiLu.FirmListBean zdata: zijiedian){
                                if (fdata.getId().equals(zdata.getPId())){
                                    zdata.firstLetter=fdata.getName();
                                    shuju.add(zdata);
                                }
                            }
                            if (shuju.size()>0){
                                contactPersonsList.add(shuju);
                                shuju = new ArrayList<>();
                            }
                        }
                        setListView();
                    }
                });
    }


    /**
     * 设置ListView
     */
    private void setListView() {
        /**设置适配器*/
        addressExpandableListview.setAdapter(new ShuiZhiJianCheAdapter(mContext, contactPersonsList));

        /**设置group不可点击*/
        addressExpandableListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//注意：一定要返回true，返回true这个方法才有效。
            }
        });

        /**展开所有条目*/
        for (int i = 0; i < contactPersonsList.size(); i++){
            addressExpandableListview.expandGroup(i);
        }

        addressExpandableListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                startActivity(new Intent(mContext,ShuiZhiJianCheTuBiaoActivity.class).putExtra("id",contactPersonsList.get(i).get(i1).getId()));
                return true;
            }
        });
    }


}
