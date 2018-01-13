package com.victor.che.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
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
import com.victor.che.domain.BaoxianCompany;
import com.victor.che.domain.Xingshizheng;
import com.victor.che.util.CollectionUtil;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.AlertDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 选择保险公司
 */
public class SelectBaoxianCompanyActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView recycler;
    @BindView(R.id.pcfl)
    PtrFrameLayout pcfl;
    private BaoxianCompanyListAdapter adapter;
    private List<BaoxianCompany> baoxianCompanyList = new ArrayList<>();
    private PtrHelper<BaoxianCompany> mPtrHelper;
    private int baoxianId;
    /*
    上传
     */
    private String brandnum, carnumber, chejianum, fadongjinum, firsttime, souyouren;
    private File filePath;
    private String insurance_company_id=""; //保险公司id
    private int select;
    private List<BaoxianCompany> list;

    private String insurances_data;//保险方案拼接

    private SharedPreferences  sp;

    private List<String> baoxian_id=new ArrayList<>();

    private List<String> selectID=new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_select_baoxian_company;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitle("选择保险公司");
        sp = mContext.getSharedPreferences("mylist", Context.MODE_PRIVATE);
        insurances_data=getIntent().getStringExtra("insurances_data");
        adapter = new BaoxianCompanyListAdapter(R.layout.item_select_company, baoxianCompanyList);
        mPtrHelper = new PtrHelper<>(pcfl, adapter, baoxianCompanyList);
        mPtrHelper.enableLoadMore(true, recycler);//允许加载更多
        recycler.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        recycler.setAdapter(adapter);
        Xingshizheng xingshizheng = (Xingshizheng)getIntent().getSerializableExtra("Xingshizheng");

        _accept_xingshizheng(xingshizheng);
        recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线
        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
                loadData(pullToRefresh, curpage, pageSize);
            }
        });
        recycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                select = position;
                BaoxianCompany baoxianCompany = baoxianCompanyList.get(position);
                baoxianCompany.checked = !baoxianCompany.checked;
                if (baoxianCompany.checked){
                    baoxian_id.add(baoxianCompany.getInsurance_company_id()+"");

                }else {
                    baoxian_id.remove(baoxianCompany.getInsurance_company_id()+"");
                }
                list = new ArrayList<BaoxianCompany>();
                list.add(baoxianCompany);
                adapter.notifyDataSetChanged();
            }
        });

        mPtrHelper.autoRefresh(true);
    }

    @OnClick(R.id.btn_notarize)
    public void onViewClicked() {
       upcommit();
    }

    /**
     * 提交车险询价
     */
    private void upcommit() {
        selectID.addAll(baoxian_id);
        for (String s : selectID) {
            insurance_company_id += s + ",";
        }
        insurance_company_id=insurance_company_id.substring(0,insurance_company_id.length()-1);
        // 发送登录请求
        if (CollectionUtil.isEmpty(baoxian_id) ) {
            MyApplication.showToast("请选择保险公司");
            return;
        }
        MyParams params = new MyParams();
        params.put("provider_id", MyApplication.CURRENT_USER.provider_id);//是	服务商id
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//是	登录者id
        params.put("car_plate_no",  carnumber);//是	车牌号
        params.put("license_brand_model", brandnum);//是	车牌型号
        params.put("engine_num", fadongjinum);//是	发动机号
        params.put("vin", chejianum);//是	车架号
        if (filePath!=null){
            params.put("license_img", filePath);//file是	上传图片的名称
        }
        params.put("register_time", firsttime);//file是	上传图片的名称
        params.put("name", souyouren);//是 所有人名称
        params.put("insurances_data",insurances_data);//是		险种信息，数据格式： 方案序号_车险id_是否免赔_保额，多个用逗号分割 eg: 1_1_1_20,1_2_0_0
        params.put("companys_data", insurance_company_id);//是	保险公司信息，数据格式：公司id集合，(多个用逗号分割）

        VictorHttpUtil.doPost(mContext, Define.url_insurance_query_add_v2, params, true, "提交中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
//                        if (element.code == 0) {
//                            show();
//                        }
//                        if (element.code==4)
                        sp.edit().clear().commit();
                    }

                });
    }
    /**
     * 接收来自行驶证信息
     *
     * @param event
     */
    public void _accept_xingshizheng(Xingshizheng event) {
        if (event == null) {
            return;
        }
        brandnum = event.getBrandnum();
        carnumber = event.getCarnumber();
        chejianum = event.getChejianum();
        fadongjinum = event.getFadongjinum();
        filePath = event.getFilePath();
        firsttime = event.getFirsttime();
        souyouren = event.getSouyouren();
    }
    private void show() {
        final String msg = "提交成功，10分钟后出查询结果\n 是否继续添加查询";
        AlertDialogFragment.newInstance(
                "提示",
                msg,
                "否",
                "是",
                new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        sp.edit().clear().commit();
                        MyApplication.openActivity(mContext,QueryHistoryActivity.class);
                        finish();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sp.edit().clear().commit();
                        MyApplication.openActivity(mContext, QueryBaoxianActivity.class);
                        finish();
                    }
                }
        )
                .show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.edit().clear().commit();
    }



    /**
     * 获取获取保险查询记录列表
     *
     * @param
     */
    private void loadData(final boolean pullToRefresh, int curpage, final int pageSize) {
        MyParams params = new MyParams();
        VictorHttpUtil.doGet(mContext, Define.url_insurance_company_list_v1, params, true, "加载中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        Gson gson = new Gson();
                        //  BaoxianCompany baoxianCompany = gson.fromJson(element.data, BaoxianCompany.class);
                        List<BaoxianCompany> baoxianCompany = JSON.parseArray(element.body, BaoxianCompany.class);
                        if (pullToRefresh) {////刷新
                            baoxianCompanyList.clear();//清空数据
                            if (baoxianCompany == null) {  //无数
                                // 无数据
                                View common_no_data = View.inflate(mContext, R.layout.common_no_data, null);
                                mPtrHelper.setEmptyView(common_no_data);
                            } else {
                                // 有数据
                                baoxianCompanyList.addAll(baoxianCompany);
                                adapter.setNewData(baoxianCompanyList);
                                adapter.notifyDataSetChanged();

                                if (CollectionUtil.getSize(baoxianCompany) < pageSize) {
                                    // 上拉加载无更多数据
                                    mPtrHelper.loadMoreEnd();
                                }
                            }
                            mPtrHelper.refreshComplete();
                        } else {//加载更多
                            mPtrHelper.processLoadMore(baoxianCompany);
                        }
                    }
                });
    }
    /**
     * 保险公司list
     */
    private class BaoxianCompanyListAdapter extends QuickAdapter<BaoxianCompany> {
        List<BaoxianCompany> data;

        public BaoxianCompanyListAdapter(int layoutResId, List<BaoxianCompany> data) {
            super(layoutResId, data);
            this.data = data;
        }
        @Override
        protected void convert(BaseViewHolder holder, final BaoxianCompany baoxianCompany) {
            ImageView logo = holder.getView(R.id.img_company_logo);
            Glide.with(mContext).load(baoxianCompany.getIcon_thumb()).error(R.drawable.icon_baoxian_commpany).dontAnimate().into(logo);
            final ImageView select = holder.getView(R.id.img_select);
            if (baoxianCompany.checked) {//选择状态
                select.setBackgroundResource(R.drawable.ic_select_compan_click);
            } else {
                select.setBackgroundResource(R.drawable.ic_select_company_normal);
            }
        }
    }

    @Override

    protected void onStop() {
        super.onStop();
        sp.edit().clear().commit();
    }
}
