package com.victor.che.ui.fragment;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jph.takephoto.model.TResult;
import com.qikecn.uploadfilebybase64.UploadResultBean;
import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.bean.Files;
import com.victor.che.bean.UserInfo;
import com.victor.che.event.StringEvent;
import com.victor.che.ui.AccountInfoActivity;
import com.victor.che.ui.SettingsActivity;
import com.victor.che.ui.WebViewActivity;
import com.victor.che.ui.my.util.StringUtil;
import com.victor.che.util.BitmapUtil;
import com.victor.che.util.Executors;
import com.victor.che.util.ListUtils;
import com.victor.che.util.PicassoUtils;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/27 0027 9:45
 */
public class AccountFragment extends TakePhoneFragment {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_fg_mine_head)
    CircleImageView ivFgMineHead;

    private View parentView;
    /**
     * popuwindow
     */
    private LinearLayout ll_popup;
    private PopupWindow pop = null;
    private File file;
    private ArrayList<String> personHeaderImageList = new ArrayList<>();//头像图片集合
    private String headPic = ""; //头像图片 需要上传的头像
    private List<UploadResultBean> uploadHeadImage;

    String s;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    MyApplication.showToast("上传图片过大或网络异常，上传失败");
                    break;
                case 1: // 修改头像
//                    if (uploadHeadImage != null) {
//                        for (UploadResultBean bean : uploadHeadImage) {
//                            if (!TextUtils.isEmpty(bean.getRemoteFileName())) {
//                                headPic += bean.getRemoteFileName() + ",";
//                            }
//                        }
//                        if (headPic.endsWith(",")) {
//                            headPic = headPic.substring(0, headPic.length() - 1);
//                        }
//                    }
                    //修改头像接口
                    MyParams params=new MyParams();
                    params.put("photo",headPic);
                    params.put("name",MyApplication.getUser().username);
                 //   params.put("mobileLogin",MyApplication.getUser().mobileLogin);
                    params.put("JSESSIONID",MyApplication.getUser().JSESSIONID);
                    VictorHttpUtil.doPost(mContext, Define.URL_imageUpload+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, true, "加载中...",
                            new BaseHttpCallbackListener<Element>() {
                                @Override
                                public void callbackSuccess(String url, Element element) {
                                    super.callbackSuccess(url, element);
                                    MyApplication.showToast(element.msg);
                                }
                            });
                    break;
            }
        }
    };
    @Override
    public int getContentView() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置标题
        parentView = View.inflate(getActivity(), R.layout.fragment_account, null);
        initview();
    }
    private void initview() {
            // 当前用户名
//            if (StringUtil.isEmpty(MyApplication.getUser().name)) {
//                tv_name.setText(MyApplication.getUser().username);
//            }else {
//                tv_name.setText(MyApplication.getUser().name);
//            }
        getusermessage();
        showPicPick();
    }
    /**
     * 获取用户信息
     */
    private void getusermessage() {
        MyParams parms=new MyParams();

        VictorHttpUtil.doPost(mContext, Define.URL_info+ ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, parms, false, null,
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                        UserInfo userInfo = JSON.parseObject(element.body, UserInfo.class);
                        UserInfo.UserInformationBean userInformation = userInfo.getUserInformation();//
                        if (userInformation != null) {
                            tv_name.setText(userInformation.getLoginName());
                            if (!StringUtil.isEmpty(userInformation.getPhoto())) {
                                String photo=Define.API_DOMAIN +userInformation.getPhoto().substring(6,userInformation.getPhoto().length());
                                s = photo.replaceAll("\\\\", "//");
                            }
                            PicassoUtils.loadHeadImage(getActivity(),s,ivFgMineHead);
                        }
                    }
                });
    }
    /**
     * 去修改个人资料界面
     */
    @OnClick(R.id.area_account_info)
    void gotoAccountInfo() {
            MyApplication.openActivity(getActivity(), AccountInfoActivity.class);
    }
    /**
     * 去设置界面
     */
    @OnClick(R.id.area_settings)
    void gotoSettings() {
        MyApplication.openActivity(getActivity(), SettingsActivity.class);
    }
    /**
     * 去设置界面
     */
    @OnClick(R.id.area_help)
    void gotoHelp() {
        Bundle bundle = new Bundle();
        bundle.putString("mUrl", "");
        MyApplication.openActivity(getActivity(), WebViewActivity.class);
    }
    /**
     * 修改头像
     */
    @OnClick(R.id.iv_fg_mine_head)
    public void onchangeheadClicked() {
        ll_popup.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }
    /**
     * 获取到照片
     *
     * @param result
     */

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        file = new File(getFiles().getPath());
        BitmapUtil.compressAndSaveImage(file, result.getImage().getPath(), 2);
        personHeaderImageList.clear();
        personHeaderImageList.add(file.getAbsolutePath());
        ivFgMineHead.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        Executors.cacheThreadExecutor(runnableHeaderImage);
    }

    /**
     * 给后台传头像，后台返回头像字符串
     */
    Runnable runnableHeaderImage = new Runnable() {
        @Override
        public void run() {
            final Message msg = new Message();
            if (!ListUtils.isEmpty(personHeaderImageList)) {
                ArrayList<File> localFiles = new ArrayList<File>();
                for (String path : personHeaderImageList) {
                    File file = new File(path);
                    if (file.exists()) {
                        localFiles.add(file);
                    }
                }
                /**
                 * 给后台传图片，后台返回string 接口
                 */
                MyParams params=new MyParams();
                params.put("file1",file);
                params.put("JSESSIONID",MyApplication.getUser().JSESSIONID);
                VictorHttpUtil.doPost(mContext, Define.URL_fileUpLoad+";JSESSIONID="+MyApplication.getUser().JSESSIONID, params, true, "加载中...",
                        new BaseHttpCallbackListener<Element>() {
                            @Override
                        public void callbackSuccess(String url, Element element) {
                                super.callbackSuccess(url, element);
                                Files files = JSON.parseObject(element.body, Files.class);
                               headPic = files.getFiles().get(0).getFilePath();
                              //  MyApplication.showToast(element.msg);
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        });

            }
        }
    };

    /**
     * 照片选择器
     */
    private void showPicPick() {
        View view = View.inflate(getActivity(), R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop = new PopupWindow(getActivity());
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
                getActivity().overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
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
     * 去关于我们界面
     //     */
//    @OnClick(R.id.area_aboutus)
//     void gotoAbout() {
//        Bundle bundle=new Bundle();
//        bundle.putString("mUrl", "");
//        MyApplication.openActivity(mContext, AboutusActivity.class);
//
//    }
@Subscribe
    public void rechangername(StringEvent event){
        if (event == null) {
            return;
        }
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getusermessage();
    }
}
