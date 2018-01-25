package com.victor.che.ui.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.model.TResult;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qikecn.uploadfilebybase64.UploadResultBean;
import com.victor.che.R;
import com.victor.che.adapter.GridAdapter;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.MyApplication;
import com.victor.che.base.VictorBaseArrayAdapter;
import com.victor.che.ui.my.util.MediaPlayUtil;
import com.victor.che.ui.my.util.StringUtil;
import com.victor.che.util.BitmapUtil;
import com.victor.che.util.DateUtil;
import com.victor.che.util.Executors;
import com.victor.che.util.ListUtils;
import com.victor.che.util.MaterialDialogUtils;
import com.victor.che.util.PicassoUtils;
import com.victor.che.widget.BottomDialogFragment;
import com.victor.che.widget.ClearEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    @BindView(R.id.ll_use_name)
    LinearLayout llUseName;
    @BindView(R.id.ll_add_yuyin)
    RelativeLayout llAddYuyin;
    @BindView(R.id.activity_follow_user)
    LinearLayout activityFollowUser;
//    @BindView(R.id.noScrollgridview)
//    NoScrollGridView noScrollgridview;
    @BindView(R.id.iv_qianming)
    ImageView ivQianming;
    @BindView(R.id.tv_tianjiayvying)
    TextView tvTianjiayvying;
    @BindView(R.id.iv_luying)
    ImageView ivLuying;
    @BindView(R.id.chat_tv_voice_len)
    TextView mTvTimeLengh;
    @BindView(R.id.iv_voice_image)
    ImageView mIvVoice;
    @BindView(R.id.iv_voice_image_anim)
    ImageView mIvVoiceAnim;
    @BindView(R.id.voice_layout)
    RelativeLayout mRlVoiceLayout;
    @BindView(R.id.ll_qianming)
    LinearLayout llQianming;
    @BindView(R.id.et_unitname)
    ClearEditText etUnitname; //单位名称
    @BindView(R.id.tv_firsttime)
    TextView tvFirsttime;
    @BindView(R.id.tv_lawWaters)
    TextView tvLawWaters;
    @BindView(R.id.topbar_right)
    TextView topbarRight;
    @BindView(R.id.tv_lawAqu)
    TextView tvLawAqu;
    @BindView(R.id.tv_lawMed)
    TextView tvLawMed;
    @BindView(R.id.tv_lawPro)
    TextView tvLawPro;
    @BindView(R.id.tv_lawSal)
    TextView tvLawSal;
    @BindView(R.id.tv_lawDeli)
    TextView tvLawDeli;
    @BindView(R.id.tv_lawMedi)
    TextView tvLawMedi;
    @BindView(R.id.tv_lawQual)
    TextView tvLawQual;
    @BindView(R.id.tv_lawTech)
    TextView tvLawTech;
    @BindView(R.id.tv_lawDate)
    TextView tvLawDate;
    @BindView(R.id.lin_lawDate)
    LinearLayout linLawDate;
    @BindView(R.id.tv_lawSta)
    TextView tvLawSta;
    @BindView(R.id.tv_lawOld)
    TextView tvLawOld;
    @BindView(R.id.tv_lawTrea)
    TextView tvLawTrea;
    @BindView(R.id.et_lawProb)
    ClearEditText etLawProb;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.et_lawOther)
    EditText etLawOther;
    @BindView(R.id.lin_lawTrea)
    LinearLayout linLawTrea;

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

    private MediaPlayUtil mMediaPlayUtil;
    private String mVoiceData; //语音string
    private AnimationDrawable mImageAnim;
    private LawWatersListAdapter lawWatersListAdapter;
    private String[] lawWaters = {"全民所有", "集体所有"};
    private int selectedlawWatersPos = 0;

    private LawWatersListAdapter LawAquListAdapter;
    private String[] lawAqu = {"有", "应该持有但没有", "不需办理"};  //0 有1 应该持有但没有 2 不需办理
    private int selectedLawAquPos = 0;
    //用药纪录
    private LawWatersListAdapter lawMedListAdapter;
    private String[] lawMed = {"真实完整", "不真实", "不完整", "不能提供"};  //0 真实完整 1 不真实 2 不完整 3 不能提供
    private int selectedlawMed = 0;
    //生产纪录
    private LawWatersListAdapter lawProListAdapter;
    private String[] lawPro = {"真实完整", "不真实", "不完整", "不能提供"};  //0 真实完整 1 不真实 2 不完整 3 不能提供
    private int selectedlawPro = 0;
    //销售纪录
    private LawWatersListAdapter lawSalListAdapter;
    private String[] lawSal = {"真实完整", "不真实", "不完整", "不能提供"};  //0 真实完整 1 不真实 2 不完整 3 不能提供
    private int selectedlawSal = 0;
    //苗种来源
    private LawWatersListAdapter lawDeliListAdapter;
    private String[] lawDeli = {"苗种来源于合法的生产企业,且来源记录清晰,存有供货方苗种生产许可证", "无法证明来源及其合法性"};
    private int selectedlawDeliPos = 0;
    //用药和饲料情况
    private LawWatersListAdapter lawMediListAdapter;
    private String[] lawMedi = {"现场未发现禁用药物和非法添加物", "现场发现禁用药物或者非法添加物", "药残检测超标"};
    private int selectedlawMediPos = 0;
    //质量管理制度
    private LawWatersListAdapter lawQualListAdapter;
    private String[] lawQual = {"有", "无"};
    private int selectedlawQualPos = 0;
    //是否参加过渔业部门组织的法律法规培训
    private LawWatersListAdapter lawTechListAdapter;
    private String[] lawTech = {"是", "否"};
    private int selectedlawTechPos = 0;
    private String lawDate;//培训时间
    //接受监管情况
    private LawWatersListAdapter lawStaListAdapter;
    private String[] lawSta = {"接受监管并有监管记录", "曾接受监管但无记录"};
    private int selectedlawStaPos = 0;
    //对以往检查发现问题的整改情况
    private LawWatersListAdapter lawOldListAdapter;
    private String[] lawOld = {"整改彻底", "整改不彻底", "未整改"};
    private int selectedlawOldPos = 0;
    //处理情况
    private LawWatersListAdapter lawTreaListAdapter;
    private String[] lawTrea = {"合格,没有发现违规行为", "不合格项或者需要整改的地方"};
    private int selectedlawTreaPos = 0;
    private String lawprob, remarks,lawOther;
    private File qianmingFile,recordFile;

    @Override
    public int getContentView() {
        return R.layout.activity_follow_user;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("添加执法");
        topbarRight.setText("提交");
        parentView = getLayoutInflater().inflate(R.layout.activity_follow_user, null);
        showpic();
        /**
         * 上传图片初始化
         */
//        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        adapter = new GridAdapter(this, imagePathList);
//        noScrollgridview.setAdapter(adapter);
//        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                if (arg2 == imagePathList.size() && imagePathList.size() < 6) {
//                    ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
//                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//                } else {
//                    Intent intent = new Intent(mContext, GalleryFileActivity.class);
//                    intent.putExtra("isDelete", true);
//                    intent.putExtra("point", arg2);
//                    intent.putStringArrayListExtra("imageUrlList", imagePathList);
//                    startActivityForResult(intent, 3);
//                }
//            }
//        });
        mMediaPlayUtil = MediaPlayUtil.getInstance();
        /**
         * 初始化适配器
         */
        lawWatersListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawWaters, selectedlawWatersPos);
        LawAquListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawAqu, selectedLawAquPos);

        lawMedListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawMed, selectedlawMed);
        lawProListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawPro, selectedlawPro);
        lawSalListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawSal, selectedlawSal);

        lawDeliListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawDeli, selectedlawDeliPos);
        lawMediListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawMedi, selectedlawMediPos);
        lawQualListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawQual, selectedlawQualPos);
        lawTechListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawTech, selectedlawTechPos);

        lawStaListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawSta, selectedlawStaPos);
        lawOldListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawOld, selectedlawOldPos);
        lawTreaListAdapter = new LawWatersListAdapter(mContext, R.layout.item_bottom_dialog, lawTrea, selectedlawTreaPos);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 666:  //签名文件
                String imgpath = data.getStringExtra("imgpath");
                qianmingFile = new File(imgpath);
                PicassoUtils.loadFileImage(mContext, qianmingFile, ivQianming);
                break;
            case 66:
                mVoiceData = data.getStringExtra("LYpath");
                String time = data.getStringExtra("time");
                String mSoundData = data.getStringExtra("mSoundData");
                if (mVoiceData != null && mVoiceData.length() > 0) {
                    mRlVoiceLayout.setVisibility(View.VISIBLE);
                    mTvTimeLengh.setText(time);
                }
                if (!StringUtil.isEmpty(mSoundData) ) {
                    recordFile=new File(mSoundData);
                }
                break;
        }
    }

    /**
     * 语音播放效果
     */
    public void startAnim() {
        mImageAnim = (AnimationDrawable) mIvVoiceAnim.getBackground();
        mIvVoiceAnim.setVisibility(View.VISIBLE);
        mIvVoice.setVisibility(View.GONE);
        mImageAnim.start();
        mMediaPlayUtil.setPlayOnCompleteListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mIvVoice.setVisibility(View.VISIBLE);
                mIvVoiceAnim.setVisibility(View.GONE);
            }
        });
    }
    @OnClick({R.id.tv_lawAqu, R.id.iv_luying, R.id.ll_qianming, R.id.voice_layout, R.id.tv_firsttime,
            R.id.tv_lawWaters, R.id.topbar_right, R.id.tv_lawMed, R.id.tv_lawPro, R.id.tv_lawSal,
            R.id.tv_lawDeli, R.id.tv_lawMedi, R.id.tv_lawQual, R.id.tv_lawTech, R.id.tv_lawDate,
            R.id.tv_lawSta, R.id.tv_lawOld, R.id.tv_lawTrea})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topbar_right://提交
                saveup();
                break;
            case R.id.iv_luying:
                startActivityForResult(new Intent(mContext, YuYingActivity.class), 33);
                break;
            case R.id.ll_qianming:
                startActivityForResult(new Intent(mContext, ShouXieQianMingActivity.class), 22);
                break;
            case R.id.voice_layout:
                if (mMediaPlayUtil.isPlaying()) {
                    mMediaPlayUtil.stop();
                    mImageAnim.stop();
                    mIvVoice.setVisibility(View.VISIBLE);
                    mIvVoiceAnim.setVisibility(View.GONE);
                } else {
                    startAnim();
                    mMediaPlayUtil.play(StringUtil.decoderBase64File(mVoiceData));
                }
                break;
            case R.id.tv_firsttime: //
                showDatePickerDialog(tvFirsttime);
                break;
            case R.id.tv_lawWaters: //水域属性
                BottomDialogFragment.newInstance(lawWatersListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawWatersPos = position;
                        lawWatersListAdapter.notifyDataSetChanged();
                        tvLawWaters.setText(lawWaters[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());

                break;
            case R.id.tv_lawAqu: //养殖证或苗种生产许可证
                BottomDialogFragment.newInstance(LawAquListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedLawAquPos = position;
                        LawAquListAdapter.notifyDataSetChanged();
                        tvLawAqu.setText(lawAqu[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());

                break;
            case R.id.tv_lawMed://用药纪录
                BottomDialogFragment.newInstance(lawMedListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawMed = position;
                        lawMedListAdapter.notifyDataSetChanged();
                        tvLawMed.setText(lawMed[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawPro://
                BottomDialogFragment.newInstance(lawProListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawPro = position;
                        lawProListAdapter.notifyDataSetChanged();
                        tvLawPro.setText(lawPro[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawSal:
                BottomDialogFragment.newInstance(lawSalListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawSal = position;
                        lawSalListAdapter.notifyDataSetChanged();
                        tvLawSal.setText(lawSal[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawDeli://苗种来源
                BottomDialogFragment.newInstance(lawDeliListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawDeliPos = position;
                        lawDeliListAdapter.notifyDataSetChanged();
                        tvLawDeli.setText(lawDeli[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawMedi://用药和饲料情况
                BottomDialogFragment.newInstance(lawMediListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawMediPos = position;
                        lawMediListAdapter.notifyDataSetChanged();
                        tvLawMedi.setText(lawMedi[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawQual://质量管理制度
                BottomDialogFragment.newInstance(lawQualListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawQualPos = position;
                        lawQualListAdapter.notifyDataSetChanged();
                        tvLawQual.setText(lawQual[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawTech: //是否参加过渔业部门组织的法律法规培训
                BottomDialogFragment.newInstance(lawTechListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawTechPos = position;
                        lawTechListAdapter.notifyDataSetChanged();
                        tvLawTech.setText(lawTech[position]);
                        if ("是".equals(tvLawTech.getText().toString().trim())) {
                            linLawDate.setVisibility(View.VISIBLE);
                        } else {
                            linLawDate.setVisibility(View.GONE);
                        }
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawDate:
                showDatePickerDialog(tvLawDate);
                break;
            case R.id.tv_lawSta://接受监管情况
                BottomDialogFragment.newInstance(lawStaListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawStaPos = position;
                        lawStaListAdapter.notifyDataSetChanged();
                        tvLawSta.setText(lawSta[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawOld://对以往检查发现问题的整改情况
                BottomDialogFragment.newInstance(lawOldListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawOldPos = position;
                        lawOldListAdapter.notifyDataSetChanged();
                        tvLawOld.setText(lawOld[position]);
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.tv_lawTrea://处理情况
                BottomDialogFragment.newInstance(lawTreaListAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedlawTreaPos = position;
                        lawTreaListAdapter.notifyDataSetChanged();
                        tvLawTrea.setText(lawTrea[position]);
                        if ("不合格项或者需要整改的地方".equals(tvLawTrea.getText().toString().trim())) {
                            linLawTrea.setVisibility(View.VISIBLE);
                        } else {
                            linLawTrea.setVisibility(View.GONE);
                        }
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
                break;
        }
    }

    /**
     * 提交
     */
    private void saveup() {
        String Unitname = etUnitname.getText().toString().trim();
        if (TextUtils.isEmpty(Unitname)) {
            MyApplication.showToast("单位名称不能为空");
            etUnitname.requestFocus();
            return;
        }
        String lawTime = tvFirsttime.getText().toString().trim();
        if (TextUtils.isEmpty(lawTime)) {
            MyApplication.showToast("单检查时间不能为空");
            tvFirsttime.requestFocus();
            return;
        }
        String lawaqu = tvLawAqu.getText().toString().trim();
        if (TextUtils.isEmpty(lawaqu)) {
            MyApplication.showToast("养殖证或苗种生产许可证不能为空");
            tvLawAqu.requestFocus();
            return;
        }
        String lawmed = tvLawMed.getText().toString().trim();
        if (TextUtils.isEmpty(lawmed)) {
            MyApplication.showToast("用药纪录不能为空");
            tvLawMed.requestFocus();
            return;
        }
        String lawpro = tvLawPro.getText().toString().trim();
        if (TextUtils.isEmpty(lawpro)) {
            MyApplication.showToast("生产纪录不能为空");
            tvLawPro.requestFocus();
            return;
        }
        String lawsal = tvLawSal.getText().toString().trim();
        if (TextUtils.isEmpty(lawsal)) {
            MyApplication.showToast("销售纪录不能为空");
            tvLawSal.requestFocus();
            return;
        }
        String lawdeli = tvLawDeli.getText().toString().trim();
        if (TextUtils.isEmpty(lawdeli)) {
            MyApplication.showToast("苗种来源不能为空");
            tvLawDeli.requestFocus();
            return;
        }
        String lawmedi = tvLawMedi.getText().toString().trim();
        if (TextUtils.isEmpty(lawmedi)) {
            MyApplication.showToast("用药和饲料情况不能为空");
            tvLawMedi.requestFocus();
            return;
        }
        String lawqual = tvLawQual.getText().toString().trim();
        if (TextUtils.isEmpty(lawqual)) {
            MyApplication.showToast("质量管理制度不能为空");
            tvLawQual.requestFocus();
            return;
        }
        String lawtech = tvLawTech.getText().toString().trim();
        if (TextUtils.isEmpty(lawtech)) {
            MyApplication.showToast("是否参加过渔业部门组织的法律法规培训不能为空");
            tvLawTech.requestFocus();
            return;
        } else {
            if ("是".equals(lawtech)) {
                lawDate = tvLawDate.getText().toString().trim();
                if (TextUtils.isEmpty(lawDate)) {
                    MyApplication.showToast("培训时间不能为空");
                    tvLawDate.requestFocus();
                    return;
                }
            }
        }
        String lawsta = tvLawSta.getText().toString().trim();
        if (TextUtils.isEmpty(lawsta)) {
            MyApplication.showToast("质量管理制度不能为空");
            tvLawSta.requestFocus();
            return;
        }
        String lawold = tvLawOld.getText().toString().trim();
        if (TextUtils.isEmpty(lawold)) {
            MyApplication.showToast("接受监管情况不能为空");
            tvLawOld.requestFocus();
            return;
        }
        String lawtrea = tvLawTrea.getText().toString().trim();
        if (TextUtils.isEmpty(lawtrea)) {
            MyApplication.showToast("对以往检查发现问题的整改情况不能为空");
            tvLawTrea.requestFocus();
            return;
        } else {
            if ("不合格项或者需要整改的地方".equals(lawtrea)) {
                lawprob = etLawProb.getText().toString().trim();
                if (TextUtils.isEmpty(lawDate)) {
                    MyApplication.showToast("责令整改项目不能为空");
                    etLawProb.requestFocus();
                    return;
                }
                remarks = etRemarks.getText().toString().trim();
                if (TextUtils.isEmpty(lawDate)) {
                    MyApplication.showToast("整改建议不能为空");
                    etRemarks.requestFocus();
                    return;
                }
                lawOther = etLawOther.getText().toString().trim();
                if (TextUtils.isEmpty(lawDate)) {
                    MyApplication.showToast("其他处罚或处置不能为空");
                    etLawOther.requestFocus();
                    return;
                }
            }
        }
        if (qianmingFile==null) {
            MyApplication.showToast("签名文件不能为空");
            return;
        }
        MyParams params = new MyParams();
        params.put("lawName", Unitname);//单位名称
        params.put("lawTime", lawTime);//检查时间
        params.put("lawWaters", selectedlawWatersPos + 1);//养殖水域属性 1 全民所有 2 集体所有 LawAquListAdapter lawAqu,
        params.put("lawAqu", selectedLawAquPos);////0 有1 应该持有但没有 2 不需办理
        params.put("lawMed", selectedlawMed);//// 用药纪录 0 真实完整 1 不真实 2 不完整 3 不能提供
        params.put("lawPro", selectedlawPro);//// 生产纪录 0 真实完整 1 不真实 2 不完整 3 不能提供
        params.put("lawSal", selectedlawSal);//// 销售纪录 0 真实完整 1 不真实 2 不完整 3 不能提供

        params.put("lawDeli", selectedlawDeliPos);//// 苗种来源 0 苗种来源于合法的生产企业，且来源记录清晰,存有供货方苗种生产许可证1无法证明来源及其合法性
        params.put("lawMedi", selectedlawMediPos);//// 用药和饲料情况 0 现场未发现禁用药物和非法添加物1现场发现禁用药物或者非法添加物2药残检测超标
        params.put("lawQual", selectedlawQualPos);//// 质量管理制度 0 有 1 无
        params.put("lawTech", selectedlawTechPos);//// 是否参加过渔业部门组织的法律法规培训 0 是1 否
        if ("是".equals(lawtech)) {
            params.put("lawDate", lawDate);//// 培训时间 如果为 是 应输入培训时间
        }
        params.put("lawSta", selectedlawStaPos);//// 接受监管情况 0 接受监管并有监管记录1 曾接受监管但无记录
        params.put("lawOld", selectedlawOldPos);//// 对以往检查发现问题的整改情况 0 整改彻底1 整改不彻底2 未整改
        params.put("lawTrea", selectedlawTreaPos);//// 处理情况 0 合格，没有发现违规行为1 不合格项或者需要整改的地方
        if ("不合格项或者需要整改的地方".equals(lawtrea)) {
            params.put("lawProb", selectedlawStaPos);//责令整改项目
            params.put("remarks", selectedlawOldPos);//整改建议
            params.put("lawOther", selectedlawTreaPos);//其他处罚或处置
        }
        params.put("pson",qianmingFile);//// 签名文件 File
        params.put("user",recordFile);//// 录音文件 File 可空

        VictorHttpUtil.doPost(mContext, Define.URL_govAquLaw_save + ";JSESSIONID=" + MyApplication.getUser().JSESSIONID, params, true, "登录中...",
                new BaseHttpCallbackListener<Element>() {
                    @Override
                    public void callbackSuccess(String url, Element element) {
                      MyApplication.showToast(element.msg);
                    }
                });
    }
    /**
     * 显示时间对话框
     */
    private void showDatePickerDialog(final TextView tv) {
        // 回显时间，展示选择框
        Calendar calendar = new GregorianCalendar();
        String text = tv.getText().toString().trim();
        if (!com.victor.che.util.StringUtil.isEmpty(text)) {
            Date date = DateUtil.getDateByFormat(text, DateUtil.YMD);
            calendar.setTime(date == null ? new Date() : date);
        }
        long _100year = 100L * 365 * 1000 * 60 * 60 * 24L;//100年
        TimePickerDialog mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        tv.setText(DateUtil.getStringByFormat(millseconds, DateUtil.YMD));
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - _100year)//设置最小时间
                //.setMaxMillseconds(System.currentTimeMillis() + _100year)//设置最大时间+100年
                .setMaxMillseconds(System.currentTimeMillis())//设置最大时间+100年
                .setCurrentMillseconds(calendar.getTimeInMillis())//设置当前时间
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
        mDialogYearMonthDay.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 水产属性适配器
     */
    private class LawWatersListAdapter extends VictorBaseArrayAdapter<String> {
        private int pos;

        public LawWatersListAdapter(Context context, int layoutResId, String[] array, int pos) {
            super(context, layoutResId, array);
            this.pos = pos;
        }

        @Override
        public void bindView(int position, View view, String entity) {
            TextView textView = (TextView) view;
            textView.setText(entity);
            textView.setTextColor(getResources().getColor(pos == position ? R.color.theme_color : R.color.black_text));
        }
    }
}
