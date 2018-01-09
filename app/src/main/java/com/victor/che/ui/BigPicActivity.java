package com.victor.che.ui;


import android.widget.ImageView;

import com.victor.che.R;
import com.victor.che.base.BaseActivity;
import com.victor.che.util.PicassoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class BigPicActivity extends BaseActivity {
    @BindView(R.id.iv_big_img)
    ImageView ivBigImg;
    @Override
    public int getContentView() {
        return R.layout.activity_big_pic;
    }
    @Override
    protected void initView() {
        super.initView();
        String filePath = getIntent().getStringExtra("filePath");
        File file = new File(filePath);
        PicassoUtils.loadFileImage(this, file, ivBigImg);
    }
    @OnClick(R.id.iv_big_img)
    public void onViewClicked() {
        finish();
    }
}
