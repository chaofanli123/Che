package com.victor.che.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.victor.che.R;
import com.victor.che.adapter.QuickAdapter;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseFragment;
import com.victor.che.domain.QueryBaoxianHistory;
import com.victor.che.domain.QuoteListDetail;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Author Victor
 * Email 468034043@qq.com
 *
 * @time 2017/5/10 0010 11:16.
 * 报价方案详情列表
 */

public class QueryQuoteListFragment extends BaseFragment {
    @BindView(R.id.tv_quote_program)
    TextView tvQuoteProgram;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private List<QuoteListDetail> queryQuoteList;
    private String insurance_categorys;
    private QueryBaoxianHistory queryBaoxianHistory;

    @Override
    public int getContentView() {
        return R.layout.fragment_query_quote_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        insurance_categorys = getArguments().getString("insurance_categorys");

        queryQuoteList = (List<QuoteListDetail>) getArguments().getSerializable("queryQuoteList");

        queryBaoxianHistory = (QueryBaoxianHistory) getArguments().getSerializable("queryBaoxianHistory");

        tvQuoteProgram.setText(insurance_categorys);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        QueryQuoteAdapter adapter = new QueryQuoteAdapter(R.layout.item_query_quote_list,queryQuoteList);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(1)
                .colorResId(R.color.common_bg)
                .build());//添加分隔线

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //单击进入详细报价界面

                if("0.00".equals(queryQuoteList.get(position).actual_total_price)){
                    //说明暂无报价
                    return;
                }
                Bundle bundle = new Bundle();
//                bundle.putString("insurance_company_name",queryQuoteList.get(position).insurance_company_name);//保险公司名称
//                bundle.putString("discount",queryQuoteList.get(position).discount);
                bundle.putSerializable("quoteListDetail",queryQuoteList.get(position));
                bundle.putSerializable("queryBaoxianHistory",queryBaoxianHistory);
            }
        });
    }

    private class QueryQuoteAdapter extends QuickAdapter<QuoteListDetail>{

        public QueryQuoteAdapter(int layoutResId, List<QuoteListDetail> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QuoteListDetail item) {
            if("0.00".equals(item.actual_total_price)){
                helper.setText(R.id.tv_total_price,"暂无报价");
                helper.setTextColor(R.id.tv_total_price, Color.parseColor("#999999"));
            }else{
                helper.setText(R.id.tv_total_price,item.actual_total_price+"元");
                helper.setTextColor(R.id.tv_total_price, Color.parseColor("#45c018"));
            }
            ImageView iv = helper.getView(R.id.iv_company_icon);
            Glide.with(mContext).load(item.insurance_company_icon).error(R.drawable.icon_baoxian_commpany).dontAnimate().into(iv);
        }
    }

}
