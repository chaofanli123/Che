package com.victor.che.base;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 供子类继承的TextWatcher
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年4月7日 下午1:22:59
 */
public class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
