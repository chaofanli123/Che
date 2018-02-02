package com.victor.che.ui.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.victor.che.R;
import com.victor.che.adapter.ImgXQAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.bean.BuTieJiLuCaKan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BuTieJiLuXiangQingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topbar_title)
    TextView tvTopbarTitle;
    @BindView(R.id.topbar)
    RelativeLayout topbar;
    @BindView(R.id.tv_qiye)
    TextView tvQiye;
    @BindView(R.id.tv_niandu)
    TextView tvNiandu;
    @BindView(R.id.tv_butiedanwei)
    TextView tvButiedanwei;
    @BindView(R.id.tv_butiejine)
    TextView tvButiejine;
    @BindView(R.id.tv_fafangdanwei)
    TextView tvFafangdanwei;
    @BindView(R.id.tv_gengxingshijian)
    TextView tvGengxingshijian;
    @BindView(R.id.rv_tupian)
    RecyclerView rvImg;

    @Override
    public int getContentView() {
        return R.layout.activity_bu_tie_ji_lu_xiang_qing;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("查看补贴记录");
        loadData(getIntent().getStringExtra("id"));
    }

    private void loadData(String id) {
        MyParams params = new MyParams();
        params.put("JSESSIONID", MyApplication.getUser().JSESSIONID);//
        params.put("id", id);
        VictorHttpUtil.doPost(mContext, Define.URL_BuTieJiLuCaKan + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        BuTieJiLuCaKan Policy = JSON.parseObject(element.body, BuTieJiLuCaKan.class);
                        tvQiye.setText("企业："+Policy.getStaFirmSubsidy().getFirm().getFarmName());
                        tvNiandu.setText("年度: "+Policy.getStaFirmSubsidy().getYear());
                        tvButiedanwei.setText("补贴项目: "+Policy.getStaFirmSubsidy().getSubsidyItem());
                        tvButiejine.setText("补贴金额: "+Policy.getStaFirmSubsidy().getSubsidyMoney());
                        tvFafangdanwei.setText("发放单位: "+Policy.getStaFirmSubsidy().getGrantFirm());
                        tvGengxingshijian.setText("备注: "+Policy.getStaFirmSubsidy().getRemarks());
                        String receipt = Policy.getStaFirmSubsidy().getReceipt();
                        if (receipt.contains("|")){
                            List<String> imgList=new ArrayList<>();
                            receipt=receipt.substring(1,receipt.length());
                            if (receipt.contains("|")){
                                String[] split = receipt.split("\\|");
                                for (int i=0;i<split.length;i++){
                                    imgList.add(split[i]);
                                }

                            }else {
                                imgList.add(receipt);
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false);
                            rvImg.setLayoutManager(linearLayoutManager);
                            ImgXQAdapter mAdapter = new ImgXQAdapter(imgList,mContext);
                            rvImg.setAdapter(mAdapter);
//                            mAdapter.setOnItemClickListener(new ImgXQAdapter.OnRecyclerViewItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, String data, int position) {
////                                startActivity(new Intent(context,BigIMGActivity.class).putExtra("img",data.get图片地址()));
//                                }
//                            });
                        }

                    }
                });

    }
}
