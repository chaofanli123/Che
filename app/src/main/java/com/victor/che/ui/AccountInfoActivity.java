package com.victor.che.ui;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.model.TResult;
import com.qikecn.uploadfilebybase64.UploadResultBean;
import com.victor.che.R;
import com.victor.che.app.MyApplication;
import com.victor.che.ui.my.TakePhotoActivity;
import com.victor.che.util.BitmapUtil;
import com.victor.che.util.Executors;
import com.victor.che.util.ListUtils;
import com.victor.che.util.PicassoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人信息界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/1/3 0003 10:43
 */
public class AccountInfoActivity extends TakePhotoActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_head)
    CircleImageView tv_head;
    private View parentView;
    /**
     * popuwindow
     */
    private LinearLayout ll_popup;
    private PopupWindow pop = null;

    private  File file;
    private ArrayList<String> personHeaderImageList = new ArrayList<>();//头像图片集合
    private String headPic = ""; //头像图片 需要上传的头像
    private List<UploadResultBean> uploadHeadImage;
    @Override
    public int getContentView() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        parentView = getLayoutInflater().inflate(R.layout.activity_account_info, null);
        setTitle("个人信息");
        if (MyApplication.isLogined()) {
            tv_name.setText(MyApplication.getUser().username);
                PicassoUtils.loadHeadImage(mContext, MyApplication.getUser().head, tv_head);
        }
        showPicPick();
    }

    @OnClick(R.id.area_change_pwd)
    void gotoChangePwd() {
        MyApplication.openActivity(mContext, ChangePwdActivity.class);
    }

    /**
     * 修改头像
     */
    @OnClick(R.id.rl_head)
    public void onChangehead() {
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }
    /**
     * 照片选择器
     */
    private void showPicPick() {
        View view = View.inflate(mContext, R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop = new PopupWindow(mContext);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        /**
         * 拍照
         */
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getTakePhoto().onPickFromCapture(getFiles());
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        /**
         * 从本地相册选择
         */
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    getTakePhoto().onPickFromGalleryWithCrop(getFiles(), getCropOptions());
                    pop.dismiss();
                    ll_popup.clearAnimation();
            }
        });
        /**
         * 取消
         */
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }
    /**
     * 获取到照片
     * @param result
     */

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
            file = new File(getFiles().getPath());
            BitmapUtil.compressAndSaveImage(file, result.getImage().getPath(), 2);
            personHeaderImageList.clear();
            personHeaderImageList.add(file.getAbsolutePath());
            tv_head.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            Executors.cacheThreadExecutor(runnableHeaderImage);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    MyApplication.showToast("上传图片过大或网络异常，上传失败");
                    break;
                case 1: // 修改头像
                    headPic = "";
                    if (uploadHeadImage != null) {
                        for (UploadResultBean bean : uploadHeadImage) {
                            if (!TextUtils.isEmpty(bean.getRemoteFileName())) {
                                headPic += bean.getRemoteFileName() + ",";
                            }
                        }
                        if (headPic.endsWith(",")) {
                            headPic = headPic.substring(0, headPic.length() - 1);
                        }
                    }
                    break;
            }
        }
    };
    /**
     * 给后台传头像，后台返回头像字符串
     */
    Runnable runnableHeaderImage = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            if (!ListUtils.isEmpty(personHeaderImageList)) {
                ArrayList<File> localFiles = new ArrayList<File>();
                for (String path : personHeaderImageList) {
                    File  file = new File(path);
                    if (file.exists()) {
                        localFiles.add(file);
                    }
                }
                /**
                 * 给后台传图片，后台返回string 接口
                 */
//                MyParams params=new MyParams();
//                params.put("photo",file);
//                params.put("type",5);
//                VictorHttpUtil.doPost(mContext, Define.URL_PostPic, params, true, "加载中...",
//                        new BaseHttpCallbackListener<Element>() {
//                    @Override
//                    public void callbackSuccess(String url, Element element) {
//                        super.callbackSuccess(url, element);
//                        PublishImg publishImg=  JSON.parseObject(element.data, PublishImg.class);
//                        headPic = publishImg.getUrl();
//                        SharedPreferencesHelper.getInstance().putString(AppSpContact.URL, headPic);
//                    }
//                });
            }
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };
}
