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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @BindView(R.id.tv_bokuanriqi)
    TextView tvBokuanriqi;

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
                        tvQiye.setText("企业：" + Policy.getGovFirmSubsidy().getFirm().getFarmName());
                        tvNiandu.setText("年度: " + Policy.getGovFirmSubsidy().getYear());
                        tvButiedanwei.setText("补贴项目: " + Policy.getGovFirmSubsidy().getSubsidyItem());
                        tvButiejine.setText("补贴金额: " + Policy.getGovFirmSubsidy().getSubsidyMoney());
                        tvFafangdanwei.setText("发放单位: " + Policy.getGovFirmSubsidy().getGrantFirm());
                        try {
                            tvBokuanriqi.setText("拨款日期: " + longToString(Long.valueOf(Policy.getGovFirmSubsidy().getGrantDate()),"yyyy-MM-dd"));
                        }catch (Exception e){
                            System.out.println(e.getMessage()+"");
                        }

                        tvGengxingshijian.setText("备注: " + Policy.getGovFirmSubsidy().getRemarks());
                        String receipt = Policy.getGovFirmSubsidy().getReceipt();
                        if (receipt.contains("|")) {
                            List<String> imgList = new ArrayList<>();
                            receipt = receipt.substring(1, receipt.length());
                            if (receipt.contains("|")) {
                                String[] split = receipt.split("\\|");
                                for (int i = 0; i < split.length; i++) {
                                    imgList.add(split[i]);
                                }

                            } else {
                                imgList.add(receipt);
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                            rvImg.setLayoutManager(linearLayoutManager);
                            ImgXQAdapter mAdapter = new ImgXQAdapter(imgList, mContext);
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

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}
