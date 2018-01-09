package com.victor.che.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.domain.CarPingpai;
import com.victor.che.util.ImageLoaderUtil;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * 汽车品牌列表适配器
 */
public class CarPinpaiListAdapter extends IndexableAdapter<CarPingpai> {
    private LayoutInflater mInflater;
    private Context mContext;

    public CarPinpaiListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_city, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_car_brand_horizontal, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, CarPingpai entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tv.setText(entity.name);//品牌名称
        ImageLoaderUtil.display(mContext, vh.imageView, entity.image_src, R.drawable.ic_car_pre, R.drawable.ic_car_pre);//品牌logo
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }
    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView imageView;
        public ContentVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_car_brand_name);
            imageView = (ImageView) itemView.findViewById(R.id.iv_car_logo);
        }
    }
}