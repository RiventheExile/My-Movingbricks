package com.my_movingbricks.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.my_movingbricks.R;
import com.my_movingbricks.app.APP;
import com.my_movingbricks.http.HttpGet;
import com.my_movingbricks.http.HttpPost;
import com.my_movingbricks.http.OnLoginListener;
import com.my_movingbricks.http.U;
import com.my_movingbricks.utlis.SPUtils;
import com.my_movingbricks.utlis.ToastUtil;
import com.my_movingbricks.view.MainActivity;
import com.my_movingbricks.view.MangerActivitys;
import com.my_movingbricks.view.viewutils.RecommendedData;

import butterknife.BindView;
import okhttp3.Call;


/**
 * TP0826
 * Created by 搬砖小能手 on 2017/3/31.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class LoginActivity extends Activity {
    View mview;
    private AlphaAnimation start_anima;
    View view;
    String mTag;
    boolean isAnimation, isInit;
    @BindView(R.id.loadView)
    ImageView loadView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_logoing, null);
        setContentView(view);
        mTag = getClass().getSimpleName();
        MangerActivitys.addActivity(this);
        // 禁止横屏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().getDecorView().setSystemUiVisibility(View.GONE);//隐藏状态栏
        // 让引导页铺满屏幕的方法
        mview = getWindow().getDecorView();
        mview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//Activity全屏显示，且状态栏被覆盖掉
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN); //Activity全屏显示，但是状态栏不会被覆盖掉，而是正常显示，只是Activity顶端布   局会被覆盖住
        initData();
        initHttp();
    }

    private void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(2000);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimation = true;
                if (!isInit) {
                    loadView.setVisibility(View.VISIBLE);
                }else {
                    redirectTo();
                }
            }
        });
    }

    private void initHttp() {
        HttpGet.LoginUrl(mTag, U.mBaseTypeUrl(1), new OnLoginListener() {
            @Override
            public void loginSuccess(String response, int id) {
                response = RecommendedData.GetReplaceData(response);
                SPUtils.putString("mzitu", response);
                APP.mzitu = response;
                APP.pageBeanList = RecommendedData.GetPage(response);
                if (APP.pageBeanList.size() != 0) {
                    for (int i = 0; i < APP.pageBeanList.size(); i++) {
                        U.mBaseTypeUrl(APP.pageBeanList.get(i).getPageTag(), (i + 1));
                    }
                    isInit = true;
                    redirectTo();
                } else {
                    ToastUtil.showToast("数据异常");
                }
            }

            @Override
            public void loginError(Call call, Exception e, int id) {
                ToastUtil.showToast("网络异常");
            }
        });
    }

    private void redirectTo() {
        if (isAnimation && isInit) {
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            MangerActivitys.removeActivity(this);
            finish();
        }
    }

    // 获取手机号码
    public String tel() {
        TelephonyManager tm = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);// 方法还获取初始化
        String deviceid = tm.getDeviceId();// 返回唯一的设备ID,例如,IMEI GSM和MEID//
        // CDMA手机
        String tel = tm.getLine1Number();// 获取手机号码
        return "手机号码=" + tel + "/设备ID=" + deviceid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpPost.cancelTag(mTag);
    }
}
