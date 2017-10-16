package com.my_movingbricks.view.bean;

import java.io.Serializable;

/**
 * abc
 * Created by 搬砖小能手 on 15/6/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class RecommendedBean implements Serializable{
    String DetailsUrl;//详情页面
    String mDescribe;//描述
    String ImagrUrl;//图片url
    String mTime;//时间
    String mBrowse;//浏览数量

    public String getDetailsUrl() {
        return DetailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        DetailsUrl = detailsUrl;
    }

    public String getImagrUrl() {
        return ImagrUrl;
    }

    public void setImagrUrl(String imagrUrl) {
        ImagrUrl = imagrUrl;
    }

    public String getmDescribe() {
        return mDescribe;
    }

    public void setmDescribe(String mDescribe) {
        this.mDescribe = mDescribe;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmBrowse() {
        return mBrowse;
    }

    public void setmBrowse(String mBrowse) {
        this.mBrowse = mBrowse;
    }

    public RecommendedBean(String detailsUrl, String mDescribe, String imagrUrl, String mTime, String mBrowse) {
        DetailsUrl = detailsUrl;
        ImagrUrl = imagrUrl;
        this.mDescribe = mDescribe;
        this.mTime = mTime;
        this.mBrowse = mBrowse;
    }
}
