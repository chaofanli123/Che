package com.victor.che.util;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.InputCallback;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by lucky on 2016/10/17.
 */

public class MaterialDialogUtils {

    private static MaterialDialog dialog;

    /**
     * 设置基础对话框，如果negativeClick是null，关闭对话框
     *
     * @param context
     * @param content
     * @param positiveClick
     * @param negativeClick
     */
    public static void baseDialog(Context context, String content, SingleButtonCallback positiveClick, SingleButtonCallback negativeClick) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(positiveClick);
        if (negativeClick == null) {
            builder.onNegative(new SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }

    /**
     * 基础对话框
     *
     * @param context
     * @param content
     * @param hint
     * @param positiveClick
     */
    public static void baseInputDialog(final Context context, String content, String hint, MaterialDialog.InputCallback positiveClick) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(hint, "", positiveClick)

                .positiveText("确定")
                .negativeText("取消")
                .onNegative(new SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public static void maxInputDialog(final Context context, String content, String hint, int num, MaterialDialog.InputCallback positiveClick) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(hint, "", positiveClick)
                .inputMaxLength(num)
                .positiveText("确定")
                .negativeText("取消")
                .onNegative(new SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public static void checkInputDialog(final Context context, String content, String hint, InputCallback inputCallback, SingleButtonCallback positiveCallback, SingleButtonCallback negativeCallback) {
        MaterialDialog materialDialog;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(hint, "", inputCallback)
                .positiveText("确定")
                .negativeText("取消")
                .alwaysCallInputCallback()
                .onPositive(positiveCallback)
                .onNegative(negativeCallback);
        materialDialog = builder.show();
    }

    public static void inputDialog(Context context, String content, String hint, MaterialDialog.InputCallback inputCallback,
                                   SingleButtonCallback positiveClick, SingleButtonCallback negativeClick) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(hint, "", inputCallback)
                .onPositive(positiveClick)
                .onNegative(negativeClick);
        builder.show();
    }

    public static void inputNumDialog(final Context context, String content, String hint, MaterialDialog.InputCallback positiveClick) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input(hint, "", positiveClick)
                .positiveText("确定")
                .negativeText("取消")
                .onNegative(new SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    /**
     * 只有一项条目的对话框
     *
     * @param context
     * @param item
     * @param listCallback
     */
    public static void showListDialog(Context context, String item, MaterialDialog.ListCallback listCallback) {
        ArrayList<String> list = new ArrayList<>();
        list.add(item);
        showListDialog(context, list, listCallback);
    }

    /**
     * 显示列表对话框
     *
     * @param context
     * @param collection
     * @param listCallback
     */
    public static void showListDialog(Context context, @NonNull Collection<String> collection, MaterialDialog.ListCallback listCallback) {
        new MaterialDialog.Builder(context)
                .items(collection)
                .itemsCallback(listCallback).show();
    }

    public static void showListDialog(Context context, String title, @NonNull Collection<String> collection, MaterialDialog.ListCallback listCallback) {
        new MaterialDialog.Builder(context)
                .items(collection)
                .title(title)
                .itemsCallback(listCallback).show();
    }

    /**
     * 显示列表对话框
     *
     * @param context
     * @param itemsRes
     * @param listCallback
     */
    public static void showListDialog(Context context, @ArrayRes int itemsRes, MaterialDialog.ListCallback listCallback) {
        new MaterialDialog.Builder(context)
                .items(itemsRes)
                .itemsCallback(listCallback).show();
    }

    public static void showPorgressDialog(Context context, String content) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(content)
                .progress(true, 0);

        dialog = builder.build();
        dialog.show();
    }


    public static void closePorgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    public static void showCustomView(Context context, View view) {
        boolean wrapInScrollView = false;
        new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView)
                .show();
    }
}
