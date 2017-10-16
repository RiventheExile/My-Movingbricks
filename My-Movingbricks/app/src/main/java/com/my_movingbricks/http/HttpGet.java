package com.my_movingbricks.http;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

/**
 * TP0826
 * Created by 搬砖小能手 on 2017/3/19.
 */
public class HttpGet {
    public static void Login(String[] params, String[] values, String url, final OnLoginListener Login) {
        GetBuilder builder = OkHttpUtils.get().url(url);
        for (int i = 0; i < params.length; i++) {
            builder.addParams(params[i], values[i]);
        }
        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (Login!=null)
                Login.loginError(call,e,id);
            }
            @Override
            public void onResponse(String response, int id) {
                if (Login!=null)
                Login.loginSuccess(response,id);
            }
        });

    }
    /**
     * post拼接url形式
     * @param context
     * @param url
     * @param Login
     */
    public static void LoginUrl(Object context,final String url, final OnLoginListener Login) {
        OkHttpUtils.get().url(url).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (Login!=null)
                    Login.loginError(call,e,id);
            }
            @Override
            public void onResponse(String response, int id) {
                if (Login!=null)
                    Login.loginSuccess(response,id);
            }
        });
    }
    /**
     *下载文件
     * @param url
     * @param FILE_DIR apk储存路径
     * @param FILE_NAME 文件储存的名字
     * @param Login
     */
    public static void LoginFile(String url, String FILE_DIR, String FILE_NAME, final OnLoginFileListener Login){
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(FILE_DIR, FILE_NAME) {
            @Override
            public void inProgress(float progress, long total, int id) {
                if (Login!=null)
                    Login.loginProgress(progress,total,id);
            }
            @Override
            public void onError(Call call, Exception e, int i) {
                if (Login!=null)
                    Login.loginError(call,e,i);
            }
            @Override
            public void onResponse(File file, int i) {
                if (Login!=null)
                    Login.loginSuccess(file,i);
            }
        });
    }
}
