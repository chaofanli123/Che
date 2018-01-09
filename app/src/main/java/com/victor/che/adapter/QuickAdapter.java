package com.victor.che.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 自定义的Adapter，为了使用
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/6 0006 14:57
 */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public QuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected BaseViewHolder createBaseViewHolder(View view) {
        return new AutoSizeVH(view);
    }
    /**
     * RecyclerView 使用AutoSize包装ViewHolder
     * Author Victor
     * Email 468034043@qq.com
     * Time 2017/1/6 0006 14:36
     */
    class AutoSizeVH extends BaseViewHolder {
        public AutoSizeVH(View view) {
            super(view);
            AutoUtils.autoSize(itemView);
        }
    }


}


