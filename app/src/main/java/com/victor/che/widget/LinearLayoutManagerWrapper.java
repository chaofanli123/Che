package com.victor.che.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * disabling predictive animations
 * 解决bug：
 * RecyclerView and java.lang.IndexOutOfBoundsException:
 * Inconsistency detected. Invalid view holder adapter positionViewHolder
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/6 0006 9:28
 */
public class LinearLayoutManagerWrapper extends LinearLayoutManager {

    public LinearLayoutManagerWrapper(Context context) {
        super(context);
    }

    public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}