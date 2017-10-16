package com.my_movingbricks.http;


import com.my_movingbricks.app.APP;
import com.my_movingbricks.http.cache.ACache;
import com.my_movingbricks.utlis.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * TP0826
 * Created by 搬砖小能手 on 2017/3/19.
 */
public class HttpPost {
    /**
     * true  则为刷新
     */
    public static boolean isLogin;
    public  static ACache aCache=ACache.get(APP.mContext);
    /**
     * addParams形式提交参数
     * @param context
     * @param params
     * @param values
     * @param url
     * @param Login
     */
    public static void LoginParams(Object context, String[] params, String[] values, String url, final OnLoginListener Login) {
        PostFormBuilder builder = OkHttpUtils.post().tag(context).url(url);
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
    public static void LoginUrl(Object context, String url, final OnLoginListener Login) {
        OkHttpUtils.post().url(url).tag(context).build().execute(new StringCallback() {
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
     * post拼接url形式,每次取数据优先从内存取，内存没有在访问网络，然后再存储到内存
     *
     * @param context tag
     * @param url     url 也是键值
     * @param Login   访问网络的接口
     */
    public static void LoginUrlACache(Object context, final String url, final OnLoginListener Login) {
        if (!isLogin) {//下拉刷新时直接访问网络，不从缓存获取
            String str = aCache.getAsString(url);
            if (str != null) {
                if (Login != null)
                    Login.loginSuccess(str, 0);
                return;
            }
        }
        OkHttpUtils.post().url(url).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (Login != null)
                    Login.loginError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                // 缓存3*60分钟 60*60*24=3600*24=86400
                aCache.put(url, response, Constants.time);
                if (Login != null)
                    Login.loginSuccess(response, id);
                isLogin = false;
            }
        });
    }

    /**
     * post拼接url形式,每次取数据优先从内存取，内存没有在访问网络，然后再存储到内存
     *
     * @param context tag
     * @param url     url 也是键值
     * @param Login   访问网络的接口
     */
    public static void LoginUrlACache(Object context, final String url, final int time, final OnLoginListener Login) {
        if (!isLogin) {//下拉刷新时直接访问网络，不从缓存获取
            String str = aCache.getAsString(url);
            if (str != null) {
                if (Login != null)
                    Login.loginSuccess(str, 0);
                return;
            }
        }
        OkHttpUtils.post().url(url).tag(context).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (Login != null)
                    Login.loginError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                // 缓存3*60分钟 60*60*24=3600*24=86400
                aCache.put(url, response, time == 0 ? Constants.time : time);
                if (Login != null)
                    Login.loginSuccess(response, id);
                isLogin = false;
            }
        });
    }
    /**
     * post拼接url形式.无this的
     * @param url
     * @param Login
     */
    public static void LoginUrlUp(String url, final OnLoginListener Login) {
        OkHttpUtils.post().url(url).build().execute(new StringCallback() {
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
     * post提交json形式
     * @param context
     * @param url
     * @param json
     * @param Login
     */
    public static void LoginString(Object context, String url, String json, final OnLoginListener Login) {
        OkHttpUtils.postString().url(url).tag(context)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json).build().execute(new StringCallback() {
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
    public static void LoginHeaderString(Object context, String url, String cookie, String mContent, final OnLoginListener Login){
        OkHttpUtils.postString().url(url).tag(context).addHeader("cookie",cookie).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(mContent).build().execute(new StringCallback() {
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
        OkHttpUtils.post().url(url).build().execute(new FileCallBack(FILE_DIR, FILE_NAME) {
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

    /**
     * 上传文件的
     * @param url
     * @param file 文件
     * @param image 文件名，后台的字段
     * @param Login
     */
    public static void LoginAddFile(String url, File[] file, String[] image, final OnLoginListener Login){
        PostFormBuilder postFormBuilder= OkHttpUtils.post().url(url);
        for (int i = 0; i <file.length ; i++) {
            postFormBuilder.addFile(image[i],file[i].getName(),file[i]);
        }
        postFormBuilder.build().execute(new StringCallback() {
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
    public static void cancelTag(Object context){
        OkHttpUtils.getInstance().cancelTag(context);
    }
    /*****************************************************************
     OkHttpUtils.post()//
     .addFile(image,  file.getName(), file)//
     .url(url)
     .build()//
     .execute(new StringCallback() {
    @Override
    public void onError(Call call, Exception e, int id) {
    LogUtils.e("11111onError", e.toString());
    }

    @Override
    public void onResponse(String response, int id) {
    LogUtils.e("11111onResponse", response);
    }
    });
     ********************************************************************/
}
