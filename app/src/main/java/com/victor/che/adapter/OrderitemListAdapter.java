package com.victor.che.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.domain.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单item 里面的item列表适配系
 */
public class OrderitemListAdapter extends RecyclerView.Adapter<OrderitemListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Order.OrderDetailBean> list;
    private Context context;

    public OrderitemListAdapter(Context context, List<Order.OrderDetailBean> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;

    }
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvProductName.setText(list.get(position).getGoods_name());
        holder.tvProductCount.setText("x" + list.get(position).getBuy_num());
        holder.tvProductPrice.setText(list.get(position).getSale_price() + "元");
        holder.tvGoodsCategoryName.setText(list.get(position).getGoods_category_name());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_count)
        TextView tvProductCount;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_goods_category_name)
        TextView tvGoodsCategoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    }

