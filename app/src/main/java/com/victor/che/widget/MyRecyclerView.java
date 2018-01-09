package com.victor.che.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.victor.che.R;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerViewOnScrollListener;


/**
 * Created by Android on 2015/4/27.
 */
public class MyRecyclerView extends FamiliarRecyclerView {

    private Context context;

    public OnRefreshListener refreshListener;
    public OnLoadMoreListener loadMoreListener;

    private TextView tex_foot_more;
    private ProgressBar bar_foot_progress;
    private View footer;

    private LinearLayout emptyView = null;

    public MyRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        initFooterView();
    }

    public void initFooterView() {
        if (footer == null) {
            footer = LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
            tex_foot_more = (TextView) footer.findViewById(R.id.listview_foot_more);
            bar_foot_progress = (ProgressBar) footer.findViewById(R.id.listview_foot_progress);
        }
    }

    public void setLoad() {
        addOnScrollListener(new FamiliarRecyclerViewOnScrollListener(getLayoutManager()) {
            @Override
            public void onScrolledToTop() {

            }

            @Override
            public void onScrolledToBottom() {
                onLoadMore();
            }
        });

        //设置Item增加、移除动画
        setItemAnimator(new DefaultItemAnimator());
        addFooterView(footer);
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    private void onLoadMore() {
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }


    public TextView getTex_foot_more() {
        return tex_foot_more;
    }

    public void setTex_foot_more(TextView tex_foot_more) {
        this.tex_foot_more = tex_foot_more;
    }

    public ProgressBar getBar_foot_progress() {
        return bar_foot_progress;
    }

    public void setBar_foot_progress(ProgressBar bar_foot_progress) {
        this.bar_foot_progress = bar_foot_progress;
    }

    public View getFooter() {
        return footer;
    }

    public void setFooter(View footer) {
        this.footer = footer;
    }


    public void setEmptyView(View.OnClickListener clickListener) {
        ViewGroup parentView = (ViewGroup) getParent();
        if (emptyView == null) {
            emptyView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_empty, null);
            //        0代表R.drawable.list_loading
            updateEmptyView(0, context.getString(R.string.load_ing));
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(layoutParams);
        if (parentView.indexOfChild(emptyView) == -1) {
            parentView.addView(emptyView);
        }
        if (!emptyView.equals(getEmptyView())) {
            setEmptyView(emptyView);
        }
        emptyView.setOnClickListener(clickListener);
    }

    public void updateEmptyView(int dra, String msg) {
        if (emptyView != null) {
            TextView tv_reason = (TextView) emptyView.findViewById(R.id.tv_reason);
            TextView tv_reload = (TextView) emptyView.findViewById(R.id.tv_reload);
            ImageView iv_reason = (ImageView) emptyView.findViewById(R.id.iv_pic_no);
            tv_reason.setText(msg);
            if (dra == 0) {
                // iv_reason.setImageResource(R.drawable.list_loading); 加载动画
                tv_reload.setVisibility(GONE);
                //  final AnimationDrawable ad = (AnimationDrawable) iv_reason.getDrawable();
                iv_reason.post(new Runnable() {
                    @Override
                    public void run() {
                        //  ad.start();
                    }
                });
            } else if (dra == -1) {
                iv_reason.setVisibility(GONE);
            } else {
                iv_reason.setVisibility(VISIBLE);
                tv_reload.setVisibility(VISIBLE);
                iv_reason.setImageResource(dra);
            }
        }
    }

    public void updateEmptyView(String msg) {
        if (emptyView != null) {
            TextView tv_reason = (TextView) emptyView.findViewById(R.id.tv_reason);
            tv_reason.setText(msg);
        }
    }

}
