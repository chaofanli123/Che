package com.victor.che.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.touchgallery.GalleryWidget.BasePagerAdapter;
import com.victor.che.touchgallery.GalleryWidget.FilePagerAdapter;
import com.victor.che.touchgallery.GalleryWidget.GalleryViewPager;
import com.victor.che.util.ListUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看大图界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/28 0028 10:29
 */
public class GalleryFileActivity extends AppCompatActivity {

    @BindView(R.id.indicator)
    TextView indicator;
    @BindView(R.id.bt_o)
    ImageButton btO;

    private GalleryViewPager mViewPager;
    private ArrayList<String> imageUrlList;

    private int point;

    FilePagerAdapter pagerAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        imageUrlList = getIntent().getStringArrayListExtra("imageUrlList");
        point = getIntent().getIntExtra("point", 0);

        pagerAdapter = new FilePagerAdapter(this, imageUrlList);

        mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnItemClickListener(new GalleryViewPager.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("imageUrlList", imageUrlList);
                setResult(1, intent);
                finish();
            }
        });
        CharSequence text = getString(R.string.viewpager_indicator, point + 1, mViewPager.getAdapter().getCount());
        indicator.setText(text);
        mViewPager.setCurrentItem(point);
        pagerAdapter.setOnItemChangeListener(new BasePagerAdapter.OnItemChangeListener() {
            @Override
            public void onItemChange(int currentPosition) {
                point = currentPosition;
                CharSequence text = getString(R.string.viewpager_indicator, currentPosition + 1, mViewPager.getAdapter().getCount());
                indicator.setText(text);
            }
        });
    }

    public void copy(InputStream in, File dst) throws IOException {

        OutputStream out = new FileOutputStream(dst);
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent intent = new Intent();
        intent.putStringArrayListExtra("imageUrlList", imageUrlList);
        setResult(1, intent);
        finish();
    }

    @OnClick(R.id.bt_o)
    public void onClick() {
        imageUrlList.remove(point);
        if (ListUtils.isEmpty(imageUrlList)) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("imageUrlList", imageUrlList);
            setResult(1, intent);
            finish();
        }
        //mViewPager.removeViewAt(point);
        pagerAdapter.notifyDataSetChanged();
    }
}
