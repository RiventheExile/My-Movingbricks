package com.my_movingbricks.view.bean;

import java.io.Serializable;

/**
 * abc
 * Created by 搬砖小能手 on 14/8/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子
 */
public class AttachFileBean implements Serializable{
    String mPresentPageUrl;//当前页面的url
    String mClassificationUrl;//当前分类的url
    String mClassification;//当前的分类
    String mCreateTime;//发布时间
    String mBrowseTimes;//浏览次数
    String mNextPageUrl;//下一页网址的url
    String mImageUrl;//当前图片的url
    String mDescribe;//当前图片的描述

    public AttachFileBean(String mPresentPageUrl, String mClassificationUrl, String mClassification,
                          String mCreateTime, String mBrowseTimes, String mNextPageUrl, String mImageUrl, String mDescribe) {
        this.mPresentPageUrl = mPresentPageUrl;
        this.mClassificationUrl = mClassificationUrl;
        this.mClassification = mClassification;
        this.mCreateTime = mCreateTime;
        this.mBrowseTimes = mBrowseTimes;
        this.mNextPageUrl = mNextPageUrl;
        this.mImageUrl = mImageUrl;
        this.mDescribe = mDescribe;
    }

    public String getmPresentPageUrl() {
        return mPresentPageUrl;
    }

    public void setmPresentPageUrl(String mPresentPageUrl) {
        this.mPresentPageUrl = mPresentPageUrl;
    }

    public String getmDescribe() {
        return mDescribe;
    }

    public void setmDescribe(String mDescribe) {
        this.mDescribe = mDescribe;
    }

    public String getmNextPageUrl() {
        return mNextPageUrl;
    }

    public void setmNextPageUrl(String mNextPageUrl) {
        this.mNextPageUrl = mNextPageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmClassificationUrl() {
        return mClassificationUrl;
    }

    public void setmClassificationUrl(String mClassificationUrl) {
        this.mClassificationUrl = mClassificationUrl;
    }

    public String getmClassification() {
        return mClassification;
    }

    public void setmClassification(String mClassification) {
        this.mClassification = mClassification;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public String getmBrowseTimes() {
        return mBrowseTimes;
    }

    public void setmBrowseTimes(String mBrowseTimes) {
        this.mBrowseTimes = mBrowseTimes;
    }
}
