package com.victor.che.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.domain.Channel;

import java.util.List;

/**
 * 筛选页面单项选择
 */
public class MarkerServerGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Channel> imageFilePath;
    private Context context;
    private ViewHolder holder=null;
    private int pos;
    private int lastPosition = -1;   //lastPosition 记录上一次选中的图片位置，-1表示未选中

   public MarkerServerGridAdapter(Context context, List<Channel> imageFilePath) {
       inflater = LayoutInflater.from(context);
        this.context = context;
        this.imageFilePath = imageFilePath;
    }
    public int getCount() {

        return imageFilePath.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item__shaixuan_marker_grid1, parent, false);
            holder.image = (TextView) convertView.findViewById(R.id.rb_marker_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       holder.image.setText(imageFilePath.get(position).name);
        if (lastPosition == position) {//最后选择的位置
           holder.image.setBackgroundResource(R.drawable.sl_btn_get_captcha);
            holder.image.setTextColor(context.getResources().getColor(R.color.theme_color));
           } else {
           holder.image.setBackgroundResource(R.drawable.sl_bg_message);
            holder.image.setTextColor(context.getResources().getColor(R.color.black_text));
          }
        return convertView;
    }
    public class ViewHolder {
        public TextView image;
    }

   public void setSeclection(int position) {
      lastPosition = position;
      }
}