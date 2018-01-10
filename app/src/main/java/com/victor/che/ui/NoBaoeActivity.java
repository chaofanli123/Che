package com.victor.che.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.victor.che.R;

public class NoBaoeActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_no_baoe);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //////设置为true点击区域外消失
        setFinishOnTouchOutside(true);
    }
}
