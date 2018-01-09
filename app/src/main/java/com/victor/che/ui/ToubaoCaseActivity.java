package com.victor.che.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.domain.BaoxianStyle;
import com.victor.che.util.PtrHelper;
import com.victor.che.widget.MyRecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.ui.widget.HorizontalDividerItemDecoration;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 投保方案
 */
public class ToubaoCaseActivity extends BaseActivity {
    @BindView(R.id.topbar_right)
    TextView topbarRight;
    @BindView(R.id.pcfl)
    PtrFrameLayout pcfl;
    @BindView(R.id.mRecyclerView)
    MyRecyclerView mRecyclerView;

    private int index;
    private int posti;

    /**
     * 愿望adapter
     */
    private ToubaocaseListAdapter messageListAdapter;
    private List<BaoxianStyle> messageArrayList = new ArrayList<BaoxianStyle>();
    private PtrHelper<BaoxianStyle> mPtrHelper;
    private SharedPreferences sp;

    @Override
    public int getContentView() {
        return R.layout.activity_toubao_case;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("投保方案");
        topbarRight.setText("添加方案");
        messageListAdapter = new ToubaocaseListAdapter(R.layout.item_toubao_case, messageArrayList);
        mPtrHelper = new PtrHelper<>(pcfl, messageListAdapter, messageArrayList);
        mPtrHelper.enableLoadMore(true, mRecyclerView);//允许加载更多
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局管理器
        mRecyclerView.setAdapter(messageListAdapter);

        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .sizeResId(R.dimen.common_divider_dp)
                .colorResId(R.color.divider)
                .build());//添加分隔线

        mPtrHelper.setOnRequestDataListener(new PtrHelper.OnRequestDataListener() {
            @Override
            public void onRequestData(boolean pullToRefresh, int curpage, int pageSize) {
//                loadData(pullToRefresh, curpage, pageSize);
                messageListAdapter.setNewData(messageArrayList);
                messageListAdapter.notifyDataSetChanged();

                mPtrHelper.loadMoreEnd();
                mPtrHelper.refreshComplete();
            }
        });

        mRecyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                posti=position;
                BaoxianStyle baoxianStyle_case = messageArrayList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("style","item");
                bundle.putInt("position",position);
                bundle.putSerializable("Xingshizheng",getIntent().getSerializableExtra("Xingshizheng"));
                bundle.putSerializable("baoxianStyle_case",baoxianStyle_case);
                MyApplication.openActivity(mContext,AddFanganActivity.class,bundle);
            }
        });

        //取值操作

        sp = mContext.getSharedPreferences("mylist",Context.MODE_PRIVATE);
        String liststr = sp.getString("mylistStr", "");
        if (!TextUtils.isEmpty(liststr)) {
            try {
                messageArrayList = String2SceneList(liststr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean ison_off = getIntent().getBooleanExtra("ison_off", false);
        BaoxianStyle baoxianStyle = (BaoxianStyle)getIntent().getSerializableExtra("BaoxianStyle");
        baoxianStyle.setIson_off(ison_off);
        int size=messageArrayList.size()+1;

        baoxianStyle.setFaxh(size+"");
        baoxianStyle.setMc("方案"+size);
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
        baoxianStyle.setNr(nr);
        if (getIntent().getStringExtra("style")!=null&&getIntent().getStringExtra("style").equals("item")){
            messageArrayList.remove(getIntent().getIntExtra("position",0));
            size=getIntent().getIntExtra("position",0)+1;
            baoxianStyle.setFaxh(size+"");
            baoxianStyle.setMc("方案"+size);
            messageArrayList.add(getIntent().getIntExtra("position",0),baoxianStyle);

        }else {
            messageArrayList.add(baoxianStyle);
        }


        try {
            //存储操作
            sp = this.getSharedPreferences("mylist",Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            try {
                //将list集合转成字符串
                String listStr = SceneList2String(messageArrayList);
                //存储
                edit.putString("mylistStr", listStr);
                edit.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
//        mRecyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
//                index = position;
//                Bundle bundle = new Bundle();
//                if (position == -1 || position >= messageArrayList.size()) {
//                    return;
//                }
////                if (bundle == null) { //三者险
////                    MyApplication.openActivity(mContext,SanzheBaoxianActivity.class);
////                }
//                if (bundle == null) {  //其他保险进入投保不投保
//                    MyApplication.openActivity(mContext,BaoxianOtherActivity.class);
//                }
//
//            }
//        });
        mPtrHelper.autoRefresh(true);
    }

    //将字符串转换成list集合

    @SuppressWarnings("unchecked")
    public static List String2SceneList(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }


    //将list集合转换成字符串

    public static String SceneList2String(List SceneList) throws IOException {
// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
// 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
// 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    /**
     * 点击添加方案
     */
    @OnClick({R.id.topbar_right, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topbar_right:
                Bundle bundle=new Bundle();
                bundle.putSerializable("Xingshizheng",getIntent().getSerializableExtra("Xingshizheng"));
                MyApplication.openActivity(mContext, AddFanganActivity.class,bundle);
                break;

            case R.id.btn_next:
                String insurances_data="";
                for (BaoxianStyle data :messageArrayList){
                    if (data.ison_off()){
                        if (data.get交强险()!=null&&data.get交强险().size()>0){
                            for (BaoxianStyle.交强险Bean sj : data.get交强险()){
                                insurances_data=insurances_data+","+data.getFaxh()+"_"+sj.getInsurance_category_id()+"_"+"0"+"_"+"0";
                            }
                        }
                        if (data.get商业险()!=null&&data.get商业险().size()>0){
                            for (BaoxianStyle.商业险Bean sj : data.get商业险()){
                                if (sj.getJe()!=null&&!sj.getJe().equals("不投保")){
                                    insurances_data=insurances_data+","+data.getFaxh()+"_"+sj.getInsurance_category_id()+"_"+sj.getIs_free()+"_"+sj.getJe();
                                }else {
                                    if (sj.getTb()!=null&&!sj.getTb().equals("不投保")){
                                        insurances_data=insurances_data+","+data.getFaxh()+"_"+sj.getInsurance_category_id()+"_"+sj.getIs_free()+"_"+0;
                                    }
                                }
                            }
                        }

                    }else {
                        if (data.get商业险()!=null&&data.get商业险().size()>0){
                            for (BaoxianStyle.商业险Bean sj : data.get商业险()){
                                if (sj.getJe()!=null&&!sj.getJe().equals("不投保")){
                                    insurances_data=insurances_data+","+data.getFaxh()+"_"+sj.getInsurance_category_id()+"_"+sj.getIs_free()+"_"+sj.getJe();
                                }else {
                                    if (sj.getTb()!=null&&!sj.getTb().equals("不投保")){
                                        insurances_data=insurances_data+","+data.getFaxh()+"_"+sj.getInsurance_category_id()+"_"+sj.getIs_free()+"_"+0;
                                    }
                                }
                            }
                        }
                    }
                }
                insurances_data=insurances_data.substring(1,insurances_data.length());
                System.out.println("insurances_data:"+insurances_data+"******************8");

                startActivity(new Intent(mContext, SelectBaoxianCompanyActivity.class).putExtra("insurances_data",insurances_data).putExtra("Xingshizheng",getIntent().getSerializableExtra("Xingshizheng")));
//                MyApplication.openActivity(mContext, SelectBaoxianCompanyActivity.class);
                finish();
                break;
        }
    }

    /**
     * 订单列表适配器
     */
    private class ToubaocaseListAdapter extends QuickAdapter<BaoxianStyle> {

        public ToubaocaseListAdapter(int layoutResId, List<BaoxianStyle> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, BaoxianStyle baoxianstyle) {
            holder.setText(R.id.tv_baoxian_name,baoxianstyle.getMc());
            holder.setText(R.id.tv_content,baoxianstyle.getNr());
        }

    }
}
