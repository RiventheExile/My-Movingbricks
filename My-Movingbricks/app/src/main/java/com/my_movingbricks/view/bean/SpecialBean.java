package com.my_movingbricks.view.bean;

import java.io.Serializable;

/**
 * abc
 * Created by 搬砖小能手 on 18/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class SpecialBean implements Serializable{
    String DetailsUrl;//详情页面
    String ImagrUrl;//图片url
    String mDescribe;//描述

    public SpecialBean(String detailsUrl, String imagrUrl, String mDescribe) {
        DetailsUrl = detailsUrl;
        ImagrUrl = imagrUrl;
        this.mDescribe = mDescribe;
    }

    public String getDetailsUrl() {
        return DetailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        DetailsUrl = detailsUrl;
    }

    public String getmDescribe() {
        return mDescribe;
    }

    public void setmDescribe(String mDescribe) {
        this.mDescribe = mDescribe;
    }

    public String getImagrUrl() {
        return ImagrUrl;
    }

    public void setImagrUrl(String imagrUrl) {
        ImagrUrl = imagrUrl;
    }
}
