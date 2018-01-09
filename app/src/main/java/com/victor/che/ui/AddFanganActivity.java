package com.victor.che.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.BaoxianStyle;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.MyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 添加方案
 */
public class AddFanganActivity extends BaseActivity {

    @BindView(R.id.tv_jiaoandcar_baoxian)
    TextView tvJiaoandcarBaoxian;
    @BindView(R.id.mRecyclerView)
    MyRecyclerView recycler;
    @BindView(R.id.mPtrFrame)
    PtrFrameLayout pcfl;
    @BindView(R.id.img_on_off)
    ImageView imgOnOff;
    private AddbaoxianListAdapter adapter;
    private List<BaoxianStyle.商业险Bean> baoxianStylesList=new ArrayList<>();
    private List<BaoxianStyle.交强险Bean> baoxianStylesListjq=new ArrayList<>();
    private PtrHelper<BaoxianStyle.商业险Bean> mPtrHelper;
    private boolean ison_off;
    private BaoxianStyle baoxianStylefirst;
    private BaoxianStyle baoxianStyle_case;
    private String style="";
    private int position=-1;
    @Override
    public int getContentView() {
        return R.layout.activity_add_fangan;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("添加方案");
        ison_off=true;
        imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_select);
        adapter = new AddbaoxianListAdapter(R.layout.item_add_baoxian, baoxianStylesList);
        mPtrHelper = new PtrHelper<>(pcfl, adapter, baoxianStylesList);
        mPtrHelper.enableLoadMore(true, recycler);//允许加载更多
        recycler.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        recycler.setAdapter(adapter);
        baoxianStyle_case= (BaoxianStyle) getIntent().getSerializableExtra("baoxianStyle_case");
        style=getIntent().getStringExtra("style");
        position=getIntent().getExtras().getInt("position");
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        if (baoxianStyle_case!=null){
            baoxianStylesList.addAll(baoxianStyle_case.get商业险());
            if (baoxianStyle_case != null) {
                baoxianStylesListjq.addAll(baoxianStyle_case.get交强险());
            }
            adapter.setNewData(baoxianStylesList);
            adapter.notifyDataSetChanged();
        }else {
            mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
                @Override
                public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                    loadData(pullToRefresh, curpage, pageSize);
                }
            });
            mPtrHelper.autoRefresh(true);
        }

    }
    /**
     * 获取获取保险查询记录列表
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        VictorHttpUtil.doGet(mContext, Define.url_insurance_category_list_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Gson gson = new Gson();
                        baoxianStylefirst = gson.fromJson(element.data, BaoxianStyle.class);

                        List<BaoxianStyle.交强险Bean> 交强险 = baoxianStylefirst.get交强险();
                        List<BaoxianStyle.商业险Bean> baoxianStyleslist = baoxianStylefirst.get商业险();
                        if (pullToRefresh) {////刷新
                            baoxianStylesList.clear();//清空数据
                            baoxianStylesListjq.clear();
                            if (CollectionUtil.isEmpty(baoxianStyleslist)) {  //无数
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                if (!CollectionUtil.isEmpty(baoxianStylefirst.get交强险())) {
                                    tvJiaoandcarBaoxian.setText(baoxianStylefirst.get交强险().get(1).getName()+"和"+baoxianStylefirst.get交强险().get(0).getName());
                                }else {
                                    tvJiaoandcarBaoxian.setText("交强险和车船税");
                                }
                                baoxianStylesList.addAll(baoxianStyleslist);
                                for (BaoxianStyle.商业险Bean data:baoxianStylesList){
                                    data.setJe("不投保");
                                    data.setTb("不投保");
                                    data.setBzmj(false);
                                }
                                if (!CollectionUtil.isEmpty(交强险)) {
                                    baoxianStylesListjq.addAll(交强险);
                                }
                                adapter.setNewData(baoxianStylesList);
                                adapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(baoxianStyleslist) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(baoxianStyleslist);
                        }
                    }
                });
    }

    @OnClick({R.id.img_on_off, R.id.btn_operate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_on_off: //开关
                if (ison_off) {  //当前为开启 点击关闭
                    imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_normal);
                    ison_off=false;
                }else {
                    imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_select);
                    ison_off=true;
                }
                break;
            case R.id.btn_operate: //确认，进入投保方案
                BaoxianStyle baoxianStyle=new BaoxianStyle();
                baoxianStyle.set交强险(baoxianStylesListjq);
                baoxianStyle.set商业险(baoxianStylesList);
                String nr="";
                if (ison_off){
                    nr="交强险和车船险";
                    for (BaoxianStyle.商业险Bean data :baoxianStyle.get商业险()){
                        if (data.getJe()!=null&&!data.getJe().equals("不投保")){
                            if (data.isBzmj()){
                                nr=nr+"/"+data.getName()+"("+data.getJe() + "   不计免赔"+")";
                            }else {
                                nr=nr+"/"+data.getName()+"("+data.getJe()+")";
                            }
                        }else {
                            if (data.getTb()!=null&&!data.getTb().equals("不投保")){
                                nr=nr+"/"+data.getName();
                            }
                        }
                    }
                }else {
                    for (BaoxianStyle.商业险Bean data :baoxianStyle.get商业险()){
                        if (data.getJe()!=null&&!data.getJe().equals("不投保")){
                            if (data.isBzmj()){
                                nr=nr+"/"+data.getName()+"("+data.getJe() + "   不计免赔"+")";
                            }else {
                                nr=nr+"/"+data.getName()+"("+data.getJe()+")";
                            }
                        }else {
                            if (data.getTb()!=null&&!data.getTb().equals("不投保")){
                                nr=nr+"/"+data.getName();
                            }
                        }
                    }
                    if (!nr.equals("")){
                        nr = nr.substring(1, nr.length());
                    }
                }
                if (!nr.equals("")){
                    startActivity(new Intent(mContext, ToubaoCaseActivity.class).putExtra("position",position).putExtra("ison_off",ison_off).putExtra("BaoxianStyle",baoxianStyle).putExtra("style",style).putExtra("Xingshizheng",getIntent().getSerializableExtra("Xingshizheng")));
               finish();
                }else {
                    Toast.makeText(mContext,"至少选择一种保险方案",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    private class AddbaoxianListAdapter extends QuickAdapter<BaoxianStyle.商业险Bean> {

        public AddbaoxianListAdapter(int layoutResId, List<BaoxianStyle.商业险Bean> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder holder,final BaoxianStyle.商业险Bean baoxianStyle) {

          final   Bundle bundle=new Bundle();
            holder.setText(R.id.tv_baoxian_name, baoxianStyle.getName());
            final TextView tv_istoubao = holder.getView(R.id.tv_istoubao);
            if (baoxianStyle.getCoverage().size()==0) { //没有不计免赔
                if (baoxianStyle.getTb()!=null){
                    tv_istoubao.setText(baoxianStyle.getTb());
                }
                tv_istoubao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { //点击进入不投保，投保的选择activity
                        Intent intent=new Intent(mContext, BaoxianOtherActivity.class);
                        intent.putExtra("name",baoxianStyle.getName());
                        startActivityForResult(intent,444);
//                        MyApplication.openActivity(mContext, BaoxianOtherActivity.class,bundle);
                    }
                });
            }
            else  {
                //有不计免赔
                ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark));
                String money_num = baoxianStyle.getCoverage().get(0).toString();
                SpannableStringBuilder builder = new SpannableStringBuilder(money_num);
                builder.setSpan(redSpan, 0, money_num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (baoxianStyle.getJe()!=null){
                    if (baoxianStyle.isBzmj()){
                        SpannableString spannableString = new SpannableString(baoxianStyle.getJe() + "   不计免赔");
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#45c018")), 0,baoxianStyle.getJe().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        tv_istoubao.setText(spannableString);
                    }else {
                        tv_istoubao.setText(baoxianStyle.getJe());
                    }
                }else {
                    if (baoxianStyle.isBzmj()){
                        SpannableString spannableString = new SpannableString("不投保" + "   不计免赔");
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#45c018")), 0,3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        tv_istoubao.setText(spannableString);

                    }else {
                        tv_istoubao.setText("不投保");
                    }

                }

                tv_istoubao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { //点击进入不投保，投保的选择activity

                        Intent intent=new Intent(mContext, SanzheBaoxianActivity.class);
                        List<String> coverage = baoxianStyle.getCoverage();
                        ArrayList<String> data=new ArrayList<String>();
                        data.addAll(coverage);
                        intent.putExtra("name",baoxianStyle.getName());
                        intent.putExtra("baoe",baoxianStyle.getJe());
                        intent.putExtra("ison_off",baoxianStyle.isBzmj());
                        intent.putStringArrayListExtra("data",data);
                        startActivityForResult(intent,444);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case  22:
                if (data.getStringExtra("NeiRong") == null) {
                    return;
                }
                String[] split = data.getStringExtra("NeiRong").split("-");
                String lx=split[0];
                String nr=split[1];
                for (BaoxianStyle.商业险Bean baoxianStyle: baoxianStylesList){
                    if (nr.equals(baoxianStyle.getName())){
                        if (lx.equals("0")){
                            baoxianStyle.setTb("不投保");
                        }else {
                            baoxianStyle.setTb("投保");
                        }
                    }
                }
                adapter = new AddbaoxianListAdapter(R.layout.item_add_baoxian, baoxianStylesList);
                recycler.setAdapter(adapter);
                break;
            case 21:
                String Je = data.getStringExtra("baoe");
                Boolean bzmj = data.getBooleanExtra("bzmj",false);
                String Nm = data.getStringExtra("baoxian_name");
                if (StringUtil.isEmpty(Je)) {
                    return;
                }
                for (BaoxianStyle.商业险Bean baoxianStyle: baoxianStylesList){
                    if (Nm.equals(baoxianStyle.getName())){
                        baoxianStyle.setJe(Je);
                        baoxianStyle.setBzmj(bzmj);
                        if (bzmj){
                            baoxianStyle.setIs_free(1);
                        }else {
                            baoxianStyle.setIs_free(0);
                        }
                    }
                }
                adapter = new AddbaoxianListAdapter(R.layout.item_add_baoxian, baoxianStylesList);
                recycler.setAdapter(adapter);
                break;
        }
    }

}
