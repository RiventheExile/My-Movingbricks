package com.my_movingbricks.http;

import com.my_movingbricks.app.APP;

/**
 * Created by Administrator on 2016/12/3.
 * 接口
 */

public class U {
    private static String mNewestUrl;
    private static String mHottestUrl;
    private static String mRecommendUrl;
    private static String mSpecialUrl;
    private static String mTitle;

    /**
     * @param type 类型
     * @return
     */
    public static String mBaseTypeUrl(int type) {
        switch (type) {
            case 1:
                return mNewestUrl == null ? "http://www.mzitu.com/" : mNewestUrl;
            case 2:
                return mHottestUrl == null ? "http://www.mzitu.com/hot/" : mHottestUrl;
            case 3:
                return mRecommendUrl == null ? "http://www.mzitu.com/best/" : mRecommendUrl;
            case 4:
                return mSpecialUrl == null ? "http://www.mzitu.com/zhuanti/" : mSpecialUrl;
        }
        return "";
    }

    public static String mBaseTypeTitle(int type) {
        return mTitle = APP.pageBeanList.get(type - 1).getmTitle();
    }

    public static void mBaseTypeUrl(String mUrl, int type) {
        switch (type) {
            case 1:
                mNewestUrl = mUrl;
            case 2:
                mHottestUrl = mUrl;
            case 3:
                mRecommendUrl = mUrl;
            case 4:
                mSpecialUrl = mUrl;
        }
    }

    public static String GetDataEngine() {
        return "http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/5item.json";
    }
}
