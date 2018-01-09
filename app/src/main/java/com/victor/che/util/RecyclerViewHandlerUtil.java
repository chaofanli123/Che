package com.victor.che.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.victor.che.R;
import com.victor.che.widget.MyRecyclerView;

import java.util.List;

/**
 * @version : v1.00
 * @Program Name  : ilinm.com.rzhkj.common.HandlerUtil.java
 * @Copyright : www.rzhkj.com
 * @Written by    李超凡

 * @Creation Date : 2016年9月6日 下午3:17:47
 * @Description : 获取Handle工具类
 * @ModificationHistory： Who                   When                         What
 * --------              ----------                   ----------
 */
public class RecyclerViewHandlerUtil {

    public static Handler getLvHandler(final Context context, final List list, final MyRecyclerView myRecyclerView, final RecyclerView.Adapter adapter, final int pageSize) {
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
             myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_empty));
                adapter.notifyDataSetChanged();
                DialogTool.closeProgressDialog();
                if (list.size() > 0) {
                    if (list.size() < pageSize) {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                    } else {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_more);
                    }
                } else {
                    myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                }
                if (msg.what == 2 || msg.what == 0) {
                    if (list.size() == 0) {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                    } else {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                    }
                }
                if (msg.what == -1) {
                   myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_error));
                    myRecyclerView.getTex_foot_more().setText(R.string.load_error);
                }
                myRecyclerView.getBar_foot_progress().setVisibility(ProgressBar.GONE);
                myRecyclerView.getFooter().setVisibility(View.VISIBLE);
            }
        };
        return handler;
    }

    public static Handler getLvHandler(final Context context, final List list, final MyRecyclerView myRecyclerView, final RecyclerView.Adapter adapter, final int pageSize, final String empty) {
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                myRecyclerView.updateEmptyView(-1, empty);
                adapter.notifyDataSetChanged();
                DialogTool.closeProgressDialog();
                if (list.size() > 0) {
                    if (list.size() < pageSize) {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                    } else {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_more);
                    }
                } else {
                    myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                }
                if (msg.what == 2 || msg.what == 0) {
                    if (list.size() == 0) {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                    } else {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                    }
                }
                if (msg.what == -1) {
                 myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_error));
                }
                myRecyclerView.getBar_foot_progress().setVisibility(ProgressBar.GONE);
                myRecyclerView.getFooter().setVisibility(View.VISIBLE);
            }
        };
        return handler;
    }

    /**
     * 非好友十条动态
     * @param type
     * @param context
     * @param list
     * @param myRecyclerView
     * @param adapter
     * @param pageSize
     * @return
     */
    public static Handler getLvHandler1(final String type, final Context context, final List list, final MyRecyclerView myRecyclerView, final RecyclerView.Adapter adapter, final int pageSize) {
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (type.equals("1")) { //是好友
                 myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_empty));
                    adapter.notifyDataSetChanged();
                    DialogTool.closeProgressDialog();
                    if (list.size() > 0) {
                        if (list.size() < pageSize) {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                        } else {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_more);
                        }
                    } else {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                    }
                    if (msg.what == 2 || msg.what == 0) {
                        if (list.size() == 0) {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                        } else {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                        }
                    }
                    if (msg.what == -1) {
                        myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_error));
                        myRecyclerView.getTex_foot_more().setText(R.string.load_error);
                    }
                    myRecyclerView.getBar_foot_progress().setVisibility(ProgressBar.GONE);
                    myRecyclerView.getFooter().setVisibility(View.VISIBLE);
                }
                else if (type.equals("0")) {
                   myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_empty));
                    adapter.notifyDataSetChanged();
                    DialogTool.closeProgressDialog();
                    if (list.size()>0&&list.size()<10) {
                        if (list.size() < pageSize) {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                        }
                        else {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_more);
                        }
                    }
                   else if (list.size() ==10) {
                        if (list.size() < pageSize) {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_full_nofrends);
                        }
                        else {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_more);
                        }
                    } else {
                        myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                    }
                    if (msg.what == 2 || msg.what == 0) {
                        if (list.size() == 0) {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_empty);
                        } else if (list.size() < 10) {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_full);
                        }else {
                            myRecyclerView.getTex_foot_more().setText(R.string.load_full_nofrends);
                        }
                    }
                    if (msg.what == -1) {
                       myRecyclerView.updateEmptyView(R.drawable.ic_empty_data, context.getString(R.string.re_load_error));
                        myRecyclerView.getTex_foot_more().setText(R.string.load_error);
                    }
                    myRecyclerView.getBar_foot_progress().setVisibility(ProgressBar.GONE);
                    myRecyclerView.getFooter().setVisibility(View.VISIBLE);
                }
                }
        };
        return handler;
    }

}
