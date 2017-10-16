package com.my_movingbricks.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.my_movingbricks.utlis.Drawables;
import com.my_movingbricks.utlis.StringUtil;
import com.my_movingbricks.view.bean.PageBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


public class APP extends Application {
    public static Context mContext;
    public List<Activity> listbe = new ArrayList<Activity>();
    private static com.my_movingbricks.app.APP APP;
    public static boolean ifSD = false; // 有无存储卡
    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
    public int borderViewPosition;//设置边界的view位置，如果是边界的view则支持左滑切换到底部的菜单
    public static String mzitu;
    public static ArrayList<PageBean> pageBeanList;
    // 屏幕数据
    public static int windowWidth;// 屏幕的宽度
    public static int windowHeight;// 屏幕的高度
    public static Gson gson;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        APP = this;
        okhttp();
        initView();
        Drawables.init(getInstance().getResources());
    }
    private void okhttp() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,
                null, null);

        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1).hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).sslSocketFactory(sslParams.sSLSocketFactory).build();
        OkHttpUtils.initClient(okHttpClient);

    }
    private void initView() {
        gson = new Gson();
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();
        windowHeight = wm.getDefaultDisplay().getHeight();
        CreateFile();
    }
    public static com.my_movingbricks.app.APP getInstance() {
        if (APP == null) {
            APP = new APP();
        }
        return APP;
    }


    public int getBorderViewPosition() {
        return borderViewPosition;
    }

    public void setBorderViewPosition(int borderViewPosition) {
        this.borderViewPosition = borderViewPosition;
    }
    // 甘莎写的，暂时不用，不会用

    public void addActivity(Activity activity) {
        listbe.add(activity);
    }

    public void exit(Context context) {
        for (Activity activity : listbe) {
            activity.finish();
        }
        System.exit(0);
    }

    // // 判断内部存储是否存在
    public static Boolean ifSD() {
        // TODO Auto-generated method stub
        // 如果手机已经插入sd卡，且应用程序具有读写sd卡的能力，方法返回true
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            ifSD = true;
        }
        return ifSD;
    }

    // 创建文件夹
    @SuppressWarnings("static-access")
    public void CreateFile() {
        // 判断内部存储是否存在
        if (APP.ifSD) {
            String topeasy = Environment.getExternalStorageDirectory()
                    + "/Movingbricks/";
            // 如果在手机内存中不存在名为“my_icon”的文件夹则创建它
            File destDir = new File(topeasy);
            if (!destDir.exists()) {// 判断文件夹目录是否存在
                destDir.mkdir();// 如果不存在则创建
            }

            File my_icon = new File(topeasy + "my_Head/");
            if (!my_icon.exists()) {
                my_icon.mkdir();
                // destDir.mkdir(); 必须有"/Topeasy/my_icon"才能生成my_icon.jpg"
            }
        } else {
            APP.mToast("请插入外部SD存储卡");
        }

    }

    // toast封装
    private static Toast toast;
    public static boolean s;

    public static void mToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    // 判断string是否为空
    public static boolean NotNull(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // ConnectivityManager connectivityManager = (ConnectivityManager)
        // StudyTwoApplication
        // .getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!StringUtil.isEmptyString(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

}
