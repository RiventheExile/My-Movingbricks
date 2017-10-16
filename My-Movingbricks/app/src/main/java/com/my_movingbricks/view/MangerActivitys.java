package com.my_movingbricks.view;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itlanbao
 * @Description 管理activity，把activity增加到集閤中，以便退出時候全部銷毁
 */
public class MangerActivitys {
    public static List<Activity> listActivity = new ArrayList<Activity>();

    public static void addActivity(Activity object) // 添加新創建的activity
    {
        listActivity.add(object);
    }

    public static void removeActivity(Activity object) // 添加新創建的activity
    {
        listActivity.remove(object);
    }

    public static void exit() {
        for (Activity activity : listActivity) {
            activity.finish();
        }
        System.exit(0);
    }
}
