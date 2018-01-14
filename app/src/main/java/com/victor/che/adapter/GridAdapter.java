package com.victor.che.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.victor.che.R;

import java.util.List;

/**
 * 李超凡
 * 发布，添加图片adtapter 6张
 */
public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<String> imageFilePath;
    private Context context;

    public GridAdapter(Context context, List<String> imageFilePath) {
        inflater = LayoutInflater.from(context);
        this.imageFilePath = imageFilePath;
        this.context = context;
    }

    public int getCount() {
        if (imageFilePath.size() == 6) {
            return 6;
        }
        return (imageFilePath.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_published_grida, parent, false);

        ImageView image = (ImageView) convertView.findViewById(R.id.item_grida_image);

        if (position == imageFilePath.size()) {
            image.setImageResource(R.drawable.icon_addpic);
            if (position == 6) {
                image.setVisibility(View.GONE);
            }
        } else {
            Glide.with(context).load(imageFilePath.get(position)).diskCacheStrategy(DiskCacheStrategy.NONE).into(image);
         /*   Glide.load
            holder.image.setImageBitmap(BitmapUtil.getSmallBitmap(imageFilePath.get(position),800,600));*/
            //PicassoUtils.loadImage(context,imageFilePath.get(position),holder.image);
        }
        return convertView;
    }


}
