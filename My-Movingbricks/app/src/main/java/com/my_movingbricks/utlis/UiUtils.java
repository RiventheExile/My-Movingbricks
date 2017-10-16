package com.my_movingbricks.utlis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.my_movingbricks.app.APP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 迷雾 on 2016/6/30.
 */
public class UiUtils {

    /**
     * 获取strings里面的string数组
     */
    public static String[] getArray(int id) {
        return APP.getInstance().getResources().getStringArray(id);

    }

    /**
     * 获取strings里面的string
     */
    public static String getString(int id) {
        return APP.getInstance().getResources().getString(id);
    }
    /**
     * 获取tColor里面的颜色·
     */
    public static int getColor(int id) {
        return APP.getInstance().getResources().getColor(id);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取当前版本号
     */
    public static int getVersionCode() {
        try {
            return APP
                    .getInstance()
                    .getPackageManager()
                    .getPackageInfo(
                            APP.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前版本号string形式的
     */
    public static String getversionName() {
        try {
            return APP
                    .getInstance()
                    .getPackageManager()
                    .getPackageInfo(
                            APP.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.00";
    }

    /**
     * 获取屏幕px
     *
     * @param context
     * @return 分辨率
     */
    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }
    /**
     * 转码并获取到文件的内容
     */
    public static String convertCodeAndGetText(String filepath)
            throws FileNotFoundException, IOException {
        filepath = filepath.substring(6);// 截取掉前面的file:/
        String load = "";
        File file = new File(filepath);
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length() + 100];
        int length = in.read(buffer);
        load = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        in.close();
        return load;
    }

    /**
     * 获取到文件的名字
     */
    public static String getFileName(String filepath)
            throws FileNotFoundException, IOException {
        File file = new File(filepath);
        String fileName = file.getName();
        return fileName;
    }

    /**
     * 转码为utf-8
     */
    public static String deCodeUTF(String str) {
        try {
            str = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 文件大小格式化
     */
    public static String getDataSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size error";
        }
    }

    /**
     * 判断有没有安装qq
     */
    public static boolean isQQClientAvailable() {
        final PackageManager packageManager = APP.getInstance()
                .getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");


            x = Integer.parseInt(field.get(obj).toString());
            sbar = APP.getInstance().getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
