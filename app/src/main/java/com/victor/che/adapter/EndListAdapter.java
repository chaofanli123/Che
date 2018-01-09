package com.victor.che.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.domain.UsercardEnd;
import com.victor.che.util.AppUtil;
import com.victor.che.util.StringUtil;
import com.victor.che.widget.AlertDialogFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class EndListAdapter extends QuickAdapter<UsercardEnd>{
    private Context context;
    private int type;
    private FragmentManager manager;
    public EndListAdapter(Context context,int layoutResId, List<UsercardEnd> data,int type,FragmentManager manager) {
        super(layoutResId, data);
        this.context=context;
        this.type=type;
        this.manager=manager;
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final UsercardEnd usercardEnd) {
        baseViewHolder.setText(R.id.tv_name,usercardEnd.getUser_name());
       baseViewHolder.setText(R.id.tv_end_time,usercardEnd.getEnd_time());
        baseViewHolder.setText(R.id.tv_car_plate_no,usercardEnd.getCar_plate_no());

        baseViewHolder.setText(R.id.tv_mobile,usercardEnd.getMobile());
        baseViewHolder.setOnClickListener(R.id.img_call_phone, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtil.isEmpty(usercardEnd.getMobile())) {
                    MyApplication.showToast("没有用户电话号码");
                }else {
                    AlertDialogFragment.newInstance(
                            "提示",
                            "是否拨打电话到"+usercardEnd.getMobile(),
                            "确定",
                            "取消",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppUtil.call(context,usercardEnd.getMobile());
                                }
                            },
                            null)
                            .show(manager, getClass().getSimpleName());
                }


            }
        });

        TextView tv_num = baseViewHolder.getView(R.id.tv_num);
        TextView tv_name = baseViewHolder.getView(R.id.tv_card_name);
        TextView tv_time = baseViewHolder.getView(R.id.tv_end_time);
        if (type == 1) {
            tv_num.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            tv_num.setText("余次："+usercardEnd.getNum()+"次");
            tv_name.setText(usercardEnd.getCard_name());
            tv_time.setText(usercardEnd.getEnd_time());
        }else if (type == 2) {
            tv_num.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            tv_num.setText("余额："+usercardEnd.getMoney()+"元");
            tv_name.setText(usercardEnd.getCard_name());
            tv_time.setText(usercardEnd.getEnd_time());
        }
    }
}
