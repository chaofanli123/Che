package com.victor.che.ui.my;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.victor.che.R;
import com.victor.che.ui.my.util.MediaPlayUtil;
import com.victor.che.ui.my.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 录音
 */
public class YuYingActivity extends Activity {

    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    // 语音相关
    private ScaleAnimation mScaleBigAnimation;
    private ScaleAnimation mScaleLittleAnimation;
    private String mSoundData = "";//语音数据
    private String dataPath;
    private boolean isStop;  // 录音是否结束的标志 超过两分钟停止
    private boolean isCanceled = false; // 是否取消录音
    private float downY;
    private MediaRecorder mRecorder;
    private MediaPlayUtil mMediaPlayUtil;
    private long mStartTime;
    private long mEndTime;
    private int mTime;
    private String mVoiceData;
    private AnimationDrawable mImageAnim;
    private File folder;//录音存放路径
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    /**
     * Called when the activity is first created.
     */
    private TextView mTvTime, mTvNotice, mTvTimeLengh, tv_topbar_title;
    private ImageView mIvRecord, mIvVoice, mIvVoiceAnim, mFinish;
    private LinearLayout mSoundLengthLayout;
    private RelativeLayout mRlVoiceLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_ying);
        ButterKnife.bind(this);
        //存储权限检查
        new TedPermission(YuYingActivity.this)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)//存储权限
                .setDeniedMessage(getString(R.string.rationale_storage))
                .setDeniedCloseButtonText("取消")
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        initSoundData();
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    }

                }).check();
        initView();
    }

    protected void initView() {
        tv_topbar_title = (TextView) findViewById(R.id.tv_topbar_title);
        tv_topbar_title.setText("语音录制");
        mTvTime = (TextView) findViewById(R.id.chat_tv_sound_length);
        mTvNotice = (TextView) findViewById(R.id.chat_tv_sound_notice);
        mIvRecord = (ImageView) findViewById(R.id.chat_record);
        mSoundLengthLayout = (LinearLayout) findViewById(R.id.chat_tv_sound_length_layout);
        mRlVoiceLayout = (RelativeLayout) findViewById(R.id.voice_layout);
        mIvVoice = (ImageView) findViewById(R.id.iv_voice_image);
        mIvVoiceAnim = (ImageView) findViewById(R.id.iv_voice_image_anim);
        mTvTimeLengh = (TextView) findViewById(R.id.chat_tv_voice_len);
        mFinish = (ImageView) findViewById(R.id.iv_finish);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new TedPermission(YuYingActivity.this)
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .setDeniedMessage(R.string.rationale_luyin)
                .setGotoSettingButtonText("设置")
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        mIvRecord.setOnTouchListener(new VoiceTouch());
                    }
                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();

        // TODO 播放录音
        mRlVoiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayUtil.isPlaying()) {
                    mMediaPlayUtil.stop();
                    mImageAnim.stop();
                    mIvVoice.setVisibility(View.VISIBLE);
                    mIvVoiceAnim.setVisibility(View.GONE);
                } else {
                    startAnim();
                    mMediaPlayUtil.play(StringUtil.decoderBase64File(mVoiceData));
                }
            }
        });
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

    /**
     * 录音存放路径
     */
    public void initSoundData() {
        dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AsRecrod/Sounds/";
        folder = new File(dataPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        mMediaPlayUtil = MediaPlayUtil.getInstance();
    }
    /**
     * 完成录音
     */
    @OnClick(R.id.iv_reload)
    public void onViewClicked() {
        if(mSoundData.length()>0){
            Intent intent = new Intent();
            intent.putExtra("LYpath", mVoiceData);
            intent.putExtra("time", mTvTimeLengh.getText().toString().trim());
            intent.putExtra("mSoundData",mSoundData);
            setResult(66, intent);
            finish();
        }else {
            Toast.makeText(YuYingActivity.this,"请先录音",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 录音的触摸监听
     */
    class VoiceTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //permissionForM();
                    downY = motionEvent.getY();
                    mIvRecord.setImageDrawable(getResources().getDrawable(R.drawable.record_pressed));
                    mTvNotice.setText("向上滑动取消发送");
                    mSoundData = dataPath + getRandomFileName() + ".amr";
                    // TODO 防止开权限后崩溃
                    if (mRecorder != null) {
                        mRecorder.reset();
                    } else {
                        mRecorder = new MediaRecorder();
                    }
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                    mRecorder.setOutputFile(mSoundData);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.i("recoder", "prepare() failed-Exception-" + e.toString());
                    }
                    try {
                        mRecorder.start();
                        mStartTime = System.currentTimeMillis();
                        mSoundLengthLayout.setVisibility(View.VISIBLE);
                        mTvTime.setText("0" + '"');
                        // TODO 开启定时器
                        mHandler.postDelayed(runnable, 1000);
                    } catch (Exception e) {
                        Log.i("recoder", "prepare() failed-Exception-" + e.toString());
                    }
                    initScaleAnim();
                    // TODO 录音过程重复动画
                    mScaleBigAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (mScaleLittleAnimation != null) {
                                mIvRecord.startAnimation(mScaleLittleAnimation);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    mScaleLittleAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (mScaleBigAnimation != null) {
                                mIvRecord.startAnimation(mScaleBigAnimation);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    mIvRecord.startAnimation(mScaleBigAnimation);

                    break;
                case MotionEvent.ACTION_UP:
                    if (!isStop) {
                        mEndTime = System.currentTimeMillis();
                        mTime = (int) ((mEndTime - mStartTime) / 1000);
                        stopRecord();
                        mIvRecord.setVisibility(View.VISIBLE);
                        try {
                            mVoiceData = StringUtil.encodeBase64File(mSoundData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (isCanceled) {
                            deleteSoundFileUnSend();
                            mTvTime.setText("0" + '"');
                            mTvNotice.setText("录音取消");
                            mRlVoiceLayout.setVisibility(View.GONE);
                        } else {
                            mIvRecord.setImageDrawable(getResources().getDrawable(R.drawable.record));
                            mRlVoiceLayout.setVisibility(View.VISIBLE);
                            mTvTimeLengh.setText(mTime + "" + '"');
                        }
                    } else {
                        mTvTime.setText("0");
                        mIvRecord.setImageDrawable(getResources().getDrawable(R.drawable.record));
                        mTvNotice.setText("重新录音");
                    }
                    break;
                case MotionEvent.ACTION_CANCEL: // 首次开权限时会走这里，录音取消
                    Log.i("record_test", "权限影响录音录音");
                    mHandler.removeCallbacks(runnable);
                    mSoundLengthLayout.setVisibility(View.GONE);

                    // TODO 这里一定注意，先release，还要置为null，否则录音会发生错误，还有可能崩溃
                    if (mRecorder != null) {
                        mRecorder.release();
                        mRecorder = null;
                        System.gc();
                    }
                    mIvRecord.setImageDrawable(getResources().getDrawable(R.drawable.record));
                    mIvRecord.clearAnimation();
                    mTvNotice.setText("按住说话");
                    isCanceled = true;
                    mScaleBigAnimation = null;
                    mScaleLittleAnimation = null;

                    break;

                case MotionEvent.ACTION_MOVE: // 滑动手指
                    float moveY = motionEvent.getY();
                    if (downY - moveY > 100) {
                        isCanceled = true;
                        mTvNotice.setText("松开手指可取消录音");
                        mIvRecord.setImageDrawable(getResources().getDrawable(R.drawable.record));
                    }
                    if (downY - moveY < 20) {
                        isCanceled = false;
                        mIvRecord.setImageDrawable(getResources().getDrawable(R.drawable.record_pressed));
                        mTvNotice.setText("向上滑动取消发送");
                    }
                    break;

            }
            return true;
        }

    }

    /**
     * 结束录音
     */
    public void stopRecord() {
        mIvRecord.clearAnimation();
        mScaleBigAnimation = null;
        mScaleLittleAnimation = null;
        if (mTime < 1) {
            deleteSoundFileUnSend();
            isCanceled = true;
            Toast.makeText(YuYingActivity.this, "录音时间太短，长按开始录音", Toast.LENGTH_SHORT).show();
        } else {
            mTvNotice.setText("录音成功");
            // 不加  "" 空串 会出  Resources$NotFoundException 错误
            mTvTime.setText(mTime + "" + '"');
            Log.i("record_test", "录音成功");
        }
        //mRecorder.setOnErrorListener(null);
        try {
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.reset();
                mRecorder.release();
            }
        } catch (Exception e) {
            Log.i("recoder", "stop() failed");
            isCanceled = true;
            mIvRecord.setVisibility(View.VISIBLE);
            mTvTime.setText("");
            Toast.makeText(YuYingActivity.this, "录音发生错误,请重新录音", Toast.LENGTH_LONG).show();
            Log.i("record_test", "录音发生错误");
        }
        mHandler.removeCallbacks(runnable);
        if (mRecorder != null) {
            mRecorder = null;
            System.gc();
        }

    }

    // 定时器
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                long endTime = System.currentTimeMillis();
                int time = (int) ((endTime - mStartTime) / 1000);
                //mRlSoundLengthLayout.setVisibility(View.VISIBLE);
                mTvTime.setText(time + "" + '"');
                // 限制录音时间不长于两分钟
                if (time > 119) {
                    isStop = true;
                    mTime = time;
                    stopRecord();
                    Toast.makeText(YuYingActivity.this, "时间过长", Toast.LENGTH_SHORT).show();
                } else {
                    mHandler.postDelayed(this, 1000);
                    isStop = false;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    /**
     * 生成一个随机的文件名
     *
     * @return
     */
    public String getRandomFileName() {
        String rel = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        rel = formatter.format(curDate);
        rel = rel + new Random().nextInt(1000);
        return rel;
    }

    /**
     * 初始化录音动画
     */
    public void initScaleAnim() {

        // TODO 放大
        mScaleBigAnimation = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleBigAnimation.setDuration(700);

        // TODO 缩小
        mScaleLittleAnimation = new ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleLittleAnimation.setDuration(700);

    }

    /**
     * 录音完毕后，若不发送，则删除文件
     */
    public void deleteSoundFileUnSend() {
        mTime = 0;
        if (!"".equals(mSoundData)) {
            try {
                File file = new File(mSoundData);
                file.delete();
                mSoundData = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /******手机录音权限处理***************************/
    private void permissionForM() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    211);
        }
    }
}
