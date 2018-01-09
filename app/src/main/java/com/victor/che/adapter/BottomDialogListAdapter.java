package com.victor.che.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.che.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单item 里面的item列表适配系
 */
public class BottomDialogListAdapter extends RecyclerView.Adapter<BottomDialogListAdapter.ViewHolder> {

    @BindView(R.id.tv_toubao)
    TextView tvToubao;
    @BindView(R.id.img_is_duihao)
    ImageView imgIsDuihao;
    private LayoutInflater inflater;
    private List<String> list;
    private Context context;

    public BottomDialogListAdapter(Context context, List<String> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;

    }

    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bottomdialog, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvToubao.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_toubao)
        TextView tvToubao;
        @BindView(R.id.img_is_duihao)
        ImageView imgIsDuihao;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}

