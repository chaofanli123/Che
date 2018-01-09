package com.victor.che.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.che.R;
import com.victor.che.api.BaseHttpCallbackListener;
import com.victor.che.api.Define;
import com.victor.che.api.Element;
import com.victor.che.api.MyParams;
import com.victor.che.api.VictorHttpUtil;
import com.victor.che.app.ConstantValue;
import com.victor.che.app.MyApplication;
import com.victor.che.base.BaseActivity;
import com.victor.che.base.SimpleTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈界面
 * Author Victor
 * Email 468034043@qq.com
 * Time 2016/12/28 0028 17:35
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.tv_word_count)
    TextView tv_word_count;

    @BindView(R.id.btn_operate)
    Button btn_operate;

    @Override
    public int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        // 设置标题
        setTitle("意见反馈");

        // 字数统计
        et_content.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                btn_operate.setEnabled(s.length() > 0 && s.length() <= 500);
                // 字数统计
                tv_word_count.setText((500 - s.length()) + "/500");
            }
        });
    }

    /**
     * 发送意见
     */
    @OnClick(R.id.btn_operate)
    public void postFeedback() {
        String content = et_content.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            MyApplication.showToast("请输入评价内容");
            et_content.requestFocus();
            return;
        }
        if (content.length() > 500) {
            MyApplication.showToast("字数不超过500个");
            et_content.requestFocus();
            return;
        }

        // 提交用户反馈
        MyParams params = new MyParams();
        params.put("staff_user_id", MyApplication.CURRENT_USER.staff_user_id);//登录者id
        params.put("os", ConstantValue.OS);//登录设备 0：未知，1：ios，2：Android
        params.put("os_version", android.os.Build.VERSION.RELEASE);//app操作系统版本号
        params.put("content", content);//反馈内容
        VictorHttpUtil.doPost(mContext, Define.URL_POST_FEEDBACK, params, false, null, new BaseHttpCallbackListener<Element>() {
            @Override
            public void callbackSuccess(String url, Element element) {

                MyApplication.showToast("感谢您的宝贵意见！");

                finish();
            }

        });

    }


}