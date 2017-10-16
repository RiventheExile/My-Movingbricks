package com.my_movingbricks.view.bean;

/**
 * abc
 * Created by 搬砖小能手 on 6/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class PageBean {
    String PageTag;
    String mTitle;

    public PageBean(String pageTag, String mTitle) {
        PageTag = pageTag;
        this.mTitle = mTitle;
    }

    public String getPageTag() {
        return PageTag;
    }

    public void setPageTag(String pageTag) {
        PageTag = pageTag;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
