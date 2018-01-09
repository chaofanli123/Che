package com.victor.che.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.base.VictorBaseListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SanzheBaoxianActivity extends Activity {
    @BindView(R.id.mRecyclerView)
    ListView mRecyclerView;
    @BindView(R.id.bt_qx)
    Button btQx;
    @BindView(R.id.bt_qd)
    Button btQd;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_bujimianpei)
    ImageView imgOnOff;
    private BaoeListAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    private boolean ison_off;
    private int selectPos = 0;
    private String name;
    private String Je;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanzhe_baoxian);
        ButterKnife.bind(this);
        ison_off = getIntent().getBooleanExtra("ison_off",false);
        Je = getIntent().getStringExtra("baoe");
        if (ison_off){
            imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_select);
        }else {
            imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_normal);
        }
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //////设置为true点击区域外消失
        setFinishOnTouchOutside(true);
        data.add("不投保");
        ArrayList<String> list = getIntent().getStringArrayListExtra("data");
        data.addAll(list);
        adapter = new BaoeListAdapter(this, R.layout.item_baoxian_edu, this.data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectPos = position;
                name = data.get(position);
                adapter.notifyDataSetChanged();
            }
        });
        tvName.setText(getIntent().getStringExtra("name"));
        name=getIntent().getStringExtra("baoe");
    }

    @OnClick({R.id.bt_qx, R.id.bt_qd,R.id.img_bujimianpei})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_qx:
                finish();
                break;
            case R.id.img_bujimianpei:
                if (ison_off) {  //当前为开启 点击关闭
                    imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_normal);
                    ison_off=false;
                }else {
                    imgOnOff.setBackgroundResource(R.drawable.ic_baoxian_select);
                    ison_off=true;
                }
                break;
            case R.id.bt_qd:
                Intent intent = new Intent();
                intent.putExtra("baoe", name);
                intent.putExtra("bzmj", ison_off);
                intent.putExtra("baoxian_name", tvName.getText().toString().trim());
                setResult(21, intent);
                finish();
                break;
        }
    }


    /**
     * 所有职工适配器
     */
    private class BaoeListAdapter extends VictorBaseListAdapter<String> {

        public BaoeListAdapter(Context context, int layoutResId, List<String> mList) {
            super(context, layoutResId, mList);
        }

        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view.findViewById(R.id.tv_name);
            textView.setText(entity);
            ImageView img = (ImageView) view.findViewById(R.id.img_select);


            if (Je.equals("")){
                if (selectPos == position) {
                    textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    img.setVisibility(View.VISIBLE);
                    img.setBackgroundResource(R.drawable.ic_duihao);
                } else {
                    img.setVisibility(View.GONE);
                    textView.setTextColor(context.getResources().getColor(R.color.black_text));
                }
            }else {
                if (entity.equals(Je)){
                    textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    img.setVisibility(View.VISIBLE);
                    img.setBackgroundResource(R.drawable.ic_duihao);
                    Je="";
                }
            }
        }
    }
}
