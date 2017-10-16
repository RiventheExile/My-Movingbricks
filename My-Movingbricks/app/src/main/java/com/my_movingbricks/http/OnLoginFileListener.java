package com.my_movingbricks.http;

import java.io.File;

import okhttp3.Call;

/**
 * abc 下载文件的接口
 * Created by 搬砖小能手 on 2017/4/17.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public interface OnLoginFileListener {
    void loginProgress(float progress, long total, int id);

    void loginSuccess(File file, int i);

    void loginError(Call call, Exception e, int i);

}
