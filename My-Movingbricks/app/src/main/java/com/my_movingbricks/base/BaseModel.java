package com.my_movingbricks.base;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.my_movingbricks.http.HttpPost;


/**
 * Created by chenhao on 2017/4/11.
 */


//modle 基础类
public abstract class BaseModel {
    //protected  String mUrl = 用于拼接接口的前面一部分
    protected Activity mActivity;
    protected Fragment mFragment;
    protected String mTag;

    public BaseModel(Activity activity) {
        mActivity = activity;
        //获得类名
        mTag = activity.getClass().getSimpleName();
        //存储的初始化
    }
    public BaseModel(Fragment fragment) {
        mFragment = fragment;
        //获得类名
        mTag = fragment.getClass().getSimpleName();
        //存储的初始化
    }
    //取消网络请求
    public void cancelRequest() {
        HttpPost.cancelTag(mTag);
    }
}
