package com.my_movingbricks.utlis;

import android.widget.Toast;

import com.my_movingbricks.app.APP;

/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(APP.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static void showToast(int id) {
        if (mToast == null) {
            mToast = Toast.makeText(APP.getInstance(), UiUtils.getString(id), Toast.LENGTH_SHORT);
        }
        mToast.setText(UiUtils.getString(id));
        mToast.show();
    }
}
