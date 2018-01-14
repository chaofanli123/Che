package com.victor.che.ui.my;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.model.TResult;
import com.qikecn.uploadfilebybase64.UploadResultBean;
import com.victor.che.R;
import com.victor.che.adapter.GridAdapter;
import com.victor.che.app.MyApplication;
import com.victor.che.util.BitmapUtil;
import com.victor.che.util.Executors;
import com.victor.che.util.ListUtils;
import com.victor.che.util.MaterialDialogUtils;
import com.victor.che.util.PicassoUtils;
import com.victor.che.widget.ClearEditText;
import com.victor.che.widget.NoScrollGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * 执法界面
 */
public class PublichaddActivity extends TakePhotoActivity {
    @BindView(R.id.ll_ording_list)
    LinearLayout llOrdingList;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_use_name)
    LinearLayout llUseName;
    @BindView(R.id.tv_add_yuyin)
    TextView tvAddYuyin;
    @BindView(R.id.ll_add_yuyin)
    LinearLayout llAddYuyin;
    @BindView(R.id.activity_follow_user)
    LinearLayout activityFollowUser;
    @BindView(R.id.noScrollgridview)
    NoScrollGridView noScrollgridview;
    @BindView(R.id.iv_qianming)
    ImageView ivQianming;

    private GridAdapter adapter;
    private ArrayList<String> imagePathList = new ArrayList<>();
    private List<UploadResultBean> upload = new ArrayList<>();
    private List<MediaBean> mediaBeanList;
    private String pic = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    MyApplication.showToast("上传图片过大或网络异常，上传失败");
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2: // 得到图片
                    //发布
                    String path = (String) msg.obj;
                    Log.e("imageimage1", "返回的图片路径获取发票" + msg.obj);
                    //   userUploadBbs(path);
                    break;
            }
        }
    };

    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View parentView;

    @Override
    public int getContentView() {
        return R.layout.activity_follow_user;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("执法");
        parentView = getLayoutInflater().inflate(R.layout.activity_follow_user, null);
        showpic();
        /**
         * 上传图片初始化
         */
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this, imagePathList);
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == imagePathList.size() && imagePathList.size() < 6) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(mContext, GalleryFileActivity.class);
                    intent.putExtra("isDelete", true);
                    intent.putExtra("point", arg2);
                    intent.putStringArrayListExtra("imageUrlList", imagePathList);
                    startActivityForResult(intent, 3);
                }
            }
        });
    }

    /**
     * 拍照，本地相册初始化
     */
    private void showpic() {
        pop = new PopupWindow(mContext);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getTakePhoto().onPickFromCapture(getFiles());
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RxGalleryFinal.with(mContext)
                        .image()
                        .multiple()
                        .maxSize(6 - imagePathList.size())
                        .imageLoader(ImageLoaderType.GLIDE)
                        .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                mediaBeanList = imageMultipleResultEvent.getResult();
                                MaterialDialogUtils.showPorgressDialog(mContext, "正在添加图片...");
                                Executors.cacheThreadExecutor(runnable);
                            }
                        }).openGallery();
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MaterialDialogUtils.closePorgressDialog();
            adapter.notifyDataSetChanged();
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!ListUtils.isEmpty(mediaBeanList)) {
                for (int i = 0; i < mediaBeanList.size(); i++) {
                    File file = BitmapUtil.getFiles();
                    BitmapUtil.compressAndSaveImage(file, mediaBeanList.get(i).getOriginalPath(), 2);
                    imagePathList.add(file.getAbsolutePath());
                }

            }
            handler1.sendEmptyMessage(1);
        }
    };

    @Override
    public void takeSuccess(TResult result) {
        File file = new File(getFiles().getPath());
        BitmapUtil.compressAndSaveImage(file, result.getImage().getPath(), 2);
        imagePathList.add(file.getAbsolutePath());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_qianming)
    public void onClick() {
        startActivityForResult(new Intent(mContext, ShouXieQianMingActivity.class), 22);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 666:
                String imgpath = data.getStringExtra("imgpath");
                File file=new File(imgpath);
                PicassoUtils.loadFileImage(mContext,file,ivQianming);
                break;
        }
    }
}
