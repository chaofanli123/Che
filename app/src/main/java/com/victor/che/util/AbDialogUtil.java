package com.victor.che.util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.PopupWindow;

import com.victor.che.widget.LoadDialogFragment;


/**
 * 对话框工具类
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年5月31日 下午3:14:29
 */
public class AbDialogUtil {

    /**
     * dialog 标记
     */
    public static String dialogTag = "dialog";

    public static int ThemeHoloLightDialog = android.R.style.Theme_Holo_Light_Dialog;

    public static int ThemeLightPanel = android.R.style.Theme_Light_Panel;

    private static int defaultStyle = ThemeHoloLightDialog;


    /**
     * 关闭对话框
     *
     * @param dialog
     */
    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 关闭PopupWindow
     *
     * @param ppw
     */
    public static void dismissPopwindow(PopupWindow ppw) {
        if (ppw != null && ppw.isShowing()) {
            ppw.dismiss();
            ppw = null;
        }
    }


    /**
     * 显示一个隐藏的的对话框.
     *
     * @param context
     * @param fragment
     */
    public static void showDialog(Context context, DialogFragment fragment) {
        FragmentActivity activity = (FragmentActivity) context;
        fragment.show(activity.getFragmentManager(), dialogTag);
    }


    /**
     * 描述：显示加载框.
     *
     * @param context the mContext
     * @param message the message
     */
    public static LoadDialogFragment showLoadDialog(Context context, String message,
                                                    String tag) {
        FragmentActivity activity = (FragmentActivity) context;
        LoadDialogFragment newFragment = LoadDialogFragment.newInstance(message);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.show(ft, tag);
        return newFragment;
    }

    /**
     * 描述：移除Fragment.
     *
     * @param context the mContext
     */
    public static void removeDialog(final Context context) {
        try {
            FragmentActivity activity = (FragmentActivity) context;
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            // 指定一个系统转场动画
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            Fragment prev = activity.getFragmentManager().findFragmentByTag(dialogTag);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            // 可能有Activity已经被销毁的异常
            e.printStackTrace();
        }
    }

    /**
     * 描述：移除Fragment.
     *
     * @param context the mContext
     */
    public static void removeDialog(Context context, String tag) {
        try {
            FragmentActivity activity = (FragmentActivity) context;
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            // 指定一个系统转场动画
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            Fragment prev = activity.getFragmentManager().findFragmentByTag(tag);
            if (prev != null) {
                ft.remove(prev);
            }
            /**
             * Add this transaction to the back stack.  This means that the transaction
             * will be remembered after it is committed, and will reverse its operation
             * when later popped off the stack.
             *
             * @param name An optional name for this back stack state, or null.
             */
            // ft.addToBackStack(null);
            ft.commitAllowingStateLoss();
//            ft.commit();
        } catch (Exception e) {
            // 可能有Activity已经被销毁的异常
            e.printStackTrace();
        }
    }

    /**
     * 描述：移除Fragment和View
     *
     * @param view
     */
    public static void removeDialog(View view) {
        removeDialog(view.getContext());
        ViewUtil.removeSelfFromParent(view);
    }

}
