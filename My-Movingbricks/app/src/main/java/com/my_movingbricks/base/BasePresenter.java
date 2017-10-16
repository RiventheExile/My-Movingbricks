package com.my_movingbricks.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.my_movingbricks.http.HttpPost;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * TP0826
 * Created by 搬砖小能手 on 2017/3/29.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public abstract class BasePresenter<T> {
    public Reference<T> mView; //View接口类型的弱引用
    //public T mView;

    public void attach(T mView) {
        this.mView = new WeakReference<T>(mView);
        ;//建立关系
    }

    public boolean isViewAttached() {
        return mView != null && mView.get() != null;
    }

    public void dettach() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }
    public T getView() {
        return mView != null ? mView.get() : null;
    }


    protected Activity mActivity;
    protected Fragment mFragment;
    protected String mTag;
    public BasePresenter(Activity activity) {
        mActivity = activity;
        //获得类名
        mTag = activity.getClass().getSimpleName();
        //存储的初始化
    }
    public BasePresenter(Fragment activity) {
        mFragment = activity;
        //获得类名
        mTag = activity.getClass().getSimpleName();
        //存储的初始化
    }
    //取消网络请求
    public void Destroy() {
        HttpPost.cancelTag(mTag);
    }
}