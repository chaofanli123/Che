package com.victor.che.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.che.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 同用的标题栏控件
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 11:55
 */
public class CommonTitleBar extends RelativeLayout {

    @BindView(R.id.left)
    ImageButton left;//左边操作

    @BindView(R.id.tv_title)
    TextView tv_title;//标题

    @BindView(R.id.right)
    ImageButton right;//右边操作

    public CommonTitleBar(Context context) {
        super(context);
        initView(context, null);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.widget_common_title_bar, this);
        ButterKnife.bind(this, view);
    }
}
