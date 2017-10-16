package com.my_movingbricks.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my_movingbricks.R;
import com.my_movingbricks.app.APP;
import com.my_movingbricks.http.HttpPost;
import com.my_movingbricks.utlis.Constants;
import com.my_movingbricks.utlis.StatusBarUtil;
import com.my_movingbricks.utlis.UiUtils;
import com.my_movingbricks.view.MangerActivitys;
import com.my_movingbricks.view.activity.WebViewActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/11/27.
 * 搬砖小能手  BaseActivity基类
 */

public abstract class BaseHeaderActivity<T> extends AppCompatActivity {

    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.action)
    public TextView mAction;
    @BindView(R.id.back)
    public TextView mBack;
    @BindView(R.id.title_bar)
    public RelativeLayout title_bar;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MangerActivitys.addActivity(this);
        TAG = this.getClass().getSimpleName();
        init();
        // 禁止横屏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //沉浸式状态栏
        StatusBarUtil.setColorNoTranslucent(this, UiUtils.getColor(R.color.colorPrimaryDark));
        if (!isToolbarShow()) {
            title_bar.setVisibility(View.GONE);
        }
        if (!isToolbarCanBack()) {
            mBack.setVisibility(View.GONE);
        }
        initView();
        initListener();
        initData();
    }

    // 返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            //根据 Tag 取消请求
            OkHttpUtils.getInstance().cancelTag(TAG);
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected Dialog progressDialog;

    public void shouCustomProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.request_dialog);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent);
        }
        if (progressDialog.isShowing()) {
            return;
        }
        if (!TextUtils.isEmpty(message)) {
            TextView msg = (TextView) progressDialog
                    .findViewById(R.id.id_tv_loadingmsg);
            msg.setVisibility(VISIBLE);
            msg.setText(message);
        }
        progressDialog.show();
    }

    public void dissmisCustomProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void hideCustomProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    public void Onfinish(View view) {
        //根据 Tag 取消请求
        dissmisToast("");
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
        finish();
    }

    /**
     * 初始化控件 强制类型转换 泛型通配符的使用
     * 仅是初始化控件  $1
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <A extends View> A $1(int id) {
        return (A) findViewById(id);
    }

    //在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    public void init() {
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * onCreate子类回调
     *
     * @param
     */
    protected abstract void initView();

    public void initData() {
        //可以考虑把initPreload在这里预先初始化，调用就表示要隐藏，统一操作
    }

    public void initListener() {
    }

    /**
     * 是否让Toolbar有返回按钮(默认可以，一般一个应用中除了主界面，其他界面都是可以有返回按钮的)
     */
    protected boolean isToolbarCanBack() {
        return true;
    }

    /**
     * 是否让头布局显示
     */
    protected boolean isToolbarShow() {
        return true;
    }

    /*------------------ toolbar的一些视图操作 ------------------*/
    public void setHeaderTitle(String title) {
        mTitle.setText(title);
    }

    public void setHeaderTitle(int Id) {
        mTitle.setText(UiUtils.getString(Id));
    }

    public void setHeaderAction(String subTitle) {
        mAction.setText(subTitle);
        mAction.setVisibility(subTitle.length() > 0 ? VISIBLE : View.GONE);
    }

    public void setHeaderAction(int id) {
        mAction.setText(UiUtils.getString(id));
        mAction.setVisibility(UiUtils.getString(id).length() > 0 ? VISIBLE : View.GONE);
    }

    public void dissmisToast(String str) {
        dissmisCustomProgressDialog();
        if (APP.NotNull(str))
            APP.mToast(str);
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return 真，不为空，假 为空
     */
    public boolean isSize(ArrayList list) {
        if (list != null) {
            if (list.size() != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dissmisCustomProgressDialog();
        OkHttpUtils.getInstance().cancelTag(this);
        MangerActivitys.removeActivity(this);
    }

    /**
     * activity普通跳转
     *
     * @param intent
     */
    public void jumpToActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        jumpToActivity(intent);

    }

    public void jumpToActivity(Class activity, String v, String s) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(v, s);
        jumpToActivity(intent);
    }

    public void jumpToActivity(Class activity, String v, int s) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(v, s);
        jumpToActivity(intent);
    }

    public void jumpToActivity(Class activity, String mKey, T t) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(mKey, (Serializable) t);
        jumpToActivity(intent);
    }

    public void jumpToWebViewActivity(String url, String title) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        jumpToActivity(intent);
    }


    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        jumpToActivity(intent);
        finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        jumpToActivity(intent);
    }

    /**
     * 反传值跳转
     *
     * @param intent
     * @param V
     */
    public void jumpToActivityForResult(Intent intent, int V) {
        startActivityForResult(intent, V);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    public void jumpToActivityForResult(Class activity, int V) {
        Intent intent = new Intent(this, activity);
        jumpToActivityForResult(intent, V);
    }

    public void jumpToActivityForResult(Class activity, String mKey, T t, int V) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(mKey, (Serializable) t);
        jumpToActivityForResult(intent, V);
    }

    public void sendEmail(String emailReciver) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + emailReciver));
        data.putExtra(Intent.EXTRA_SUBJECT, "请输入标题");
        data.putExtra(Intent.EXTRA_TEXT, "请输入内容");
        jumpToActivity(data);
    }

    public void sendObject(String emailReciver) {
        //系统邮件系统的动作为Android.content.Intent.ACTION_SEND
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/text");
        //emailReciver = new String[]{"zhouyongyang122@gmail.com", "421134693@qq.com"};
        //emailSubject = "你有一条短信";
        //emailBody = sb.toString();
        //设置邮件默认地址
        email.putExtra(Intent.EXTRA_EMAIL, emailReciver);
        //设置邮件默认标题
        email.putExtra(Intent.EXTRA_SUBJECT, "请输入标题");
        //设置要默认发送的内容
        email.putExtra(Intent.EXTRA_TEXT, "请输入内容");
        //调用系统的邮件系统
        jumpToActivity(Intent.createChooser(email, "请选择邮件发送软件"));
    }


    /**
     * 为子类提供一个权限检查方法
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 为子类提供一个权限请求方法
     *
     * @param code
     * @param permissions
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.CALL_PHONE:
                doCallPhone();
                break;
            case Constants.WRITE_EXTERNAL_STORAGE:
                doSDCardPermission();
                break;
        }
    }

    /**
     * 默认的打电话权限
     */
    public void doCallPhone() {
    }

    /**
     * 默认的写SD权限处理
     */
    public void doSDCardPermission() {
    }

    /**
     * 网络问题的覆盖页面
     *
     * @param tvContext      网络原因的textview
     * @param llErrorRefresh 所在的父布局
     */
    public void initRefresh(TextView tvContext, int id, LinearLayout llErrorRefresh) {
        tvContext.setText(UiUtils.getString(id));
        llErrorRefresh.setVisibility(View.VISIBLE);
    }

    /**
     * 网络问题的覆盖页面
     *
     * @param tvContext      网络原因的textview
     * @param llErrorRefresh 所在的父布局
     */
    public void initLoading(TextView tvContext, LinearLayout llErrorRefresh) {
        tvContext.setText(UiUtils.getString(R.string.loading));
        llErrorRefresh.setVisibility(View.VISIBLE);

    }

    /**
     * 预加载，一进入页面需要加载
     *
     * @param tvContext      网络原因的textview
     * @param llErrorRefresh 所在的父布局
     */
    public void initPreload(TextView tvContext, int id, LinearLayout llErrorRefresh, final OnLoading loading) {
        tvContext.setText(UiUtils.getString(id));
        llErrorRefresh.setVisibility(View.VISIBLE);
        if (loading != null)
            llErrorRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loading.Refresh(v);
                }
            });
    }

    public void initError(TextView tvContext, LinearLayout llErrorRefresh, final OnLoading loading) {
        tvContext.setText(UiUtils.getString(R.string.error_refresh));
        llErrorRefresh.setVisibility(View.VISIBLE);
        if (loading != null)
            HttpPost.isLogin = true;
        llErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.Refresh(v);
            }
        });
    }

    public void initSuccess(LinearLayout llErrorRefresh) {
        if (llErrorRefresh.getVisibility() != View.GONE)
            llErrorRefresh.setVisibility(View.GONE);
    }

    public interface OnLoading {
        void Refresh(View v);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initHandler();
        }
    };

    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            initThread();
            // 通知Handler完成
            mHandler.sendEmptyMessage(0x110);
        }
    };
    public void initHandler() {
    }

    public void initThread() {
    }
}