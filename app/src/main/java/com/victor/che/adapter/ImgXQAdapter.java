package com.victor.che.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.victor.che.R;
import com.victor.che.util.PicassoUtils;

import java.util.List;

/**
 * Created by danielmalone on 12/29/16.
 */
public class ImgXQAdapter extends RecyclerView.Adapter<ImgXQAdapter.ViewHolder> {

    private List<String> mDataset;
    private Context context;
    private int layoutPosition=-1;

    public ImgXQAdapter(List<String> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context=context;
    }

    @Override
    public ImgXQAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_img, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ImgXQAdapter.ViewHolder holder, int position) {
//        if (mDataset.get(position).get图片地址().contains("http")){
//            Picasso.with(context)
//                    .load(mDataset.get(position).get图片地址())
//                    .into(holder.imageView);
//        }else {
//            Picasso.with(context)
//                    .load(SPUtils.get(context, SPkeyutils.FILE,"")+mDataset.get(position).get图片地址())
//                    .into(holder.imageView);
//        }
//
//        holder.itemView.setTag(mDataset.get(position));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //获取当前点击的位置
//                layoutPosition = holder.getLayoutPosition();
//                notifyDataSetChanged();
//                mOnItemClickListener.onItemClick(holder.itemView, (ImgXQ.DataBean) holder.itemView.getTag(), layoutPosition);
//            }
//        });
        String photo= "http://222.143.21.157:8090/" +mDataset.get(position);
        String s = photo.replaceAll("\\\\", "/");
        System.out.println("******************"+s);
        PicassoUtils.loadImage(context,s,holder.imageView);

    }



    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data, int position);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
