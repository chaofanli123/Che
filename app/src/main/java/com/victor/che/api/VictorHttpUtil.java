package com.victor.che.api;

        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.text.TextUtils;

        import com.alibaba.fastjson.JSON;
        import com.alibaba.fastjson.JSONException;
        import com.lzy.okgo.OkGo;
        import com.lzy.okgo.callback.AbsCallback;
        import com.lzy.okgo.callback.FileCallback;
        import com.lzy.okgo.request.BaseRequest;
        import com.lzy.okgo.request.GetRequest;
        import com.lzy.okgo.request.PostRequest;
        import com.orhanobut.logger.Logger;
        import com.victor.che.R;
        import com.victor.che.app.ConstantValue;
        import com.victor.che.app.MyApplication;
        import com.victor.che.util.AbDialogUtil;
        import com.victor.che.util.AppUtil;
        import com.victor.che.util.MyLogger;

        import java.io.File;
        import java.util.List;

        import okhttp3.Call;
        import okhttp3.Response;

/**
 * 封装的网络请求工具类（使用OkHttp3）
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016-3-7 下午9:58:26
 */
public class VictorHttpUtil {

    private static final String AUTHORIZATION_KEY = "Authorization";// 请求头的key
    private static String SIGN_KEY = "sig";// 签名参数的key

    /**
     * GET方法
     *
     * @param context            上下文
     * @param url                api地址
     * @param params             参数
     * @param showProgressDialog 是否显示对话框
     * @param loadingText        对话框的内容
     * @param callbackListener   回调函数
     */
    public static void doGet(Context context, String url, MyParams params, boolean showProgressDialog,
                             String loadingText, HttpCallbackListener callbackListener) {
        if (!AppUtil.isNetworkAvailable(context)) {
            // 没有网络时提示
            if (callbackListener != null) {

                callbackListener.callbackNoNetwork(url);
            }
            return;
        }
        long timestamp = System.currentTimeMillis() / 1000;// 获取时间戳（单位：秒）

        GetRequest request = OkGo.get(url)
                .tag(context)
                ;//添加header .headers(AUTHORIZATION_KEY, _genRequestHeaderAuth(timestamp))

        // 添加请求参数
        _addReqParams(params, request);

        // 打印请求参数
        Logger.e(String.format("url: %s\nparams: %s", url, params));

        // 发送请求
        request.execute(new ElementCallback(context, url, showProgressDialog, loadingText, callbackListener));
    }

    /**
     * POST方法
     *
     * @param context            上下文
     * @param url                api地址
     * @param params             参数
     * @param showProgressDialog 是否显示对话框
     * @param loadingText        对话框的内容
     * @param callbackListener   回调函数
     */
    public static void doPost(Context context, String url, MyParams params, boolean showProgressDialog,
                              String loadingText, HttpCallbackListener callbackListener) {
        if (!AppUtil.isNetworkAvailable(context)) {
            // 没有网络时提示
            if (callbackListener != null) {
                callbackListener.callbackNoNetwork(url);
            }
            return;
        }
        long timestamp = System.currentTimeMillis() / 1000;// 获取时间戳（单位：秒）

        PostRequest request = OkGo.post(url)
                .tag(context)
                ;//添加header .headers(AUTHORIZATION_KEY, _genRequestHeaderAuth(timestamp))
        // 添加请求参数
        _addReqParams(params, request);
        // 打印请求参数
        Logger.e(String.format("url: %s\nparams: %s", url, params));

        // 发送请求
        request.execute(new ElementCallback(context, url, showProgressDialog, loadingText, callbackListener));

    }

    /**
     * 添加请求参数
     *
     * @param params
     * @param request
     */
    private static void _addReqParams(MyParams params, BaseRequest request) {
        if (params != null && !params.isEmpty()) {
            List<KeyValue> list = params.getParamsList();
            if (list != null && !list.isEmpty()) {
                for (KeyValue item : list) {
                    if (item.value instanceof String) {
                        request.params(item.key, (String) item.value);
                    }else if (item.value instanceof Boolean) {
                        request.params(item.key, (Boolean) item.value);
                    }
                    else if (item.value instanceof Integer) {
                        request.params(item.key, (Integer) item.value);
                    } else if (item.value instanceof Double) {
                        request.params(item.key, (Double) item.value);
                    } else if (item.value instanceof Float) {
                        request.params(item.key, (Float) item.value);
                    } else if (request instanceof PostRequest && item.value instanceof File) {
                        PostRequest pr = (PostRequest) request;
                        pr.params(item.key, (File) item.value);
                    }
                }
            }
        }
    }

    /**
     * 下载
     *
     * @param url
     * @param callback
     */
    public static void download(String url, FileCallback callback) {
        OkGo.get(url).execute(callback);
    }

    /**
     * 下载apk并安装
     *
     * @param mContext
     * @param url
     */
    public static void downloadApk(final Context mContext, String url) {
        final ProgressDialog progressDlg = new ProgressDialog(mContext);
        progressDlg.setTitle("检查更新");
        progressDlg.setMessage("正在下载，请稍候...");
        progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDlg.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_horizontal_img));
        progressDlg.setIndeterminate(false);
        progressDlg.setCancelable(false);
        progressDlg.setInverseBackgroundForced(false);
        progressDlg.setCanceledOnTouchOutside(false);
        progressDlg.setMax(100);
        OkGo.get(url).tag(mContext)
                .execute(new FileCallback() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        progressDlg.show();
                    }

                    @Override
                    public void onAfter(File file, Exception e) {
                        super.onAfter(file, e);
                        AbDialogUtil.dismissDialog(progressDlg);
                    }

                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // 开始安装最新版本
                        AppUtil.installApk(mContext, file);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        progressDlg.setProgress((int) (currentSize * 100.0 / totalSize));
                    }
                });
    }

    /**
     * 回调
     *
     * @author Victor
     * @email 468034043@qq.com
     * @time 2016年4月7日 下午2:37:11
     */
    private static class ElementCallback extends AbsCallback<Element> {
        private String url;// 接口的url路径
        private boolean showProgressDialog;
        private String loadingText;
        private HttpCallbackListener listener;
        private Context context;
        private Dialog progressDlg;

        public ElementCallback(Context context, String url, boolean showProgressDialog,
                               String loadingText, HttpCallbackListener listener) {
            this.context = context;
            this.url = url;
            this.showProgressDialog = showProgressDialog;
            this.loadingText = TextUtils.isEmpty(loadingText) ? "加载中……" : loadingText;
            this.listener = listener;
            if (listener != null && listener instanceof BaseHttpCallbackListener) {
                // 设置context
                BaseHttpCallbackListener baseHttpCallbackListener = (BaseHttpCallbackListener) listener;
                baseHttpCallbackListener.setContext(context);
            }
        }

        /**
         * 对返回数据进行操作的回调， UI线程
         */
        @Override
        public void onSuccess(Element element, Call call, Response response) {
            if (element == null) {
                MyApplication.showToast("解析JSON数据为空，请检查！！！");
                return;
            }
            if (element.isSuccess() == true) {
                if (listener != null) {
                    listener.callbackSuccess(url, element);
                }
            }else {
                if (listener != null) {
                    listener.callbackError(url, element);
                }
            }
//            switch (element.code) {
//                case 0:// 0-成功
//                    if (listener != null) {
//                        listener.callbackSuccess(url, element);
//                    }
//                    break;
//                case 5:// 5-token验证失败 需要重新登录
//                    User user = new User();
//                    MyApplication.saveUser(user);
//                    MyApplication.openActivity(context, LoginActivity.class);
//                    break;
//                default:// 1-权限验证错误，跳转到登录界面, 2-数据库操作失败， 3-业务逻辑失败， 4-服务器错误
//                    if (listener != null) {
//                        listener.callbackError(url, element);
//                    }
//                    break;
//            }
        }

        /**
         * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
         */
        @Override
        public void onError(Call call, Response response, Exception e) {
            if (showProgressDialog) {
                AbDialogUtil.removeDialog(context, url);
            }
            Logger.e("verrr", url + "=" + e.getMessage());// 打印错误
            if (listener != null) {
                if (e.getClass() == JSONException.class) {
                    listener.callbackErrorJSONFormat(url);
                } else {
                    listener.onFaliure(url, ConstantValue.HTTP_SERVER_ERROR_CODE, e.getMessage(), e);
                }
            }
        }

        /**
         * 网络失败结束之前的回调
         */
        @Override
        public void parseError(Call call, Exception e) {
            Logger.e("verrr", url + "=" + e);// 打印错误
        }
        /**
         * 请求网络开始前，UI线程
         */
        @Override
        public void onBefore(BaseRequest request) {
            if (showProgressDialog) {
                AbDialogUtil.showLoadDialog(context, loadingText, url);
            }
        }

        /**
         * 请求网络结束后，UI线程
         */
        @Override
        public void onAfter(Element element, Exception e) {
            if (showProgressDialog) {
                AbDialogUtil.removeDialog(context, url);
            }
        }

        /**
         * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
         *
         * @param response 需要转换的对象
         * @return 转换后的结果
         * @throws Exception 转换过程发生的异常
         */
        @Override
        public Element convertSuccess(Response response) throws Exception {
            String str = response.body().string();
            MyLogger.json(str);//打印返回数据
            return JSON.parseObject(str, Element.class);
        }
    }

}
