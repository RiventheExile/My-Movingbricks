package com.my_movingbricks.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my_movingbricks.R;
import com.my_movingbricks.app.APP;
import com.my_movingbricks.app.Constant;
import com.my_movingbricks.http.HttpPost;
import com.my_movingbricks.tabhost.FragmentInterface;
import com.my_movingbricks.utlis.UiUtils;
import com.my_movingbricks.view.MangerActivitys;
import com.my_movingbricks.view.activity.WebViewActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/11/27.
 * 搬砖小能手  BaseFragment基类
 * 自定义Fragment，用来处理多层Fragment时，点击back键返回上一层
 */

public abstract class BaseMvpFragment<V, T extends BasePresenter<V>> extends Fragment implements FragmentInterface {
    private boolean isOnInit = true;
    public boolean isVisibleToUser = false;
    //setUserVisibleHint和onResume只记录一次,用些变量作为标记
    private boolean hasTrace = false;
    public AppCompatActivity mActivity;
    public View view;
    protected String TAG;
    public T presenter;
    public Unbinder unbinder;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.action)
    public TextView mAction;
    @BindView(R.id.back)
    public TextView mBack;
    @BindView(R.id.title_bar)
    public RelativeLayout title_bar;

    @Override
    public void onBackPressed() {
        //this.getActivity().finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //判断是否使用MVP模式
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attach((V) this);//因为之后所有的子类都要实现对应的View接口
        }
    }

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && isSaveState()) {
            isOnInit = savedInstanceState.getBoolean(Constant.KEY_TAB_INIT);
        }
        if (isVisibleToUser) {
            onVisible(isOnInit);
            isOnInit = false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = LayoutInflater.from(mActivity).inflate(getLayoutId(),
                    container, false);
            // 方法二
            // view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            if (!isToolbarShow()) {
                title_bar.setVisibility(View.GONE);
            }
            if (!isToolbarCanBack()) {
                mBack.setVisibility(View.GONE);
            }
            initView(view);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent
            // 如果有parent需要从parent删除
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            hasTrace = true;
        }
        if (isVisibleToUser && getView() != null) {
            onVisible(isOnInit);
            isOnInit = false;
        }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isSaveState()) {
            outState.putBoolean(Context.KEYGUARD_SERVICE, isOnInit);
        }
    }

    @Override
    public void onResume() {
        hasTrace = false;
        super.onResume();
    }

    /**
     * 是否记录事件轨迹
     *
     * @return
     */
    protected boolean isTrace() {
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        hasTrace = false;
    }

    /**
     * 是否保存初始化状态
     *
     * @return
     */
    public boolean isSaveState() {
        return true;
    }

    /**
     * 监听Fragment是否显示，isInit是否初为首次初始化，当把Fragment加入TabFragment时使用<br>
     * <p>
     * 由于ViewPager是预加载，所以联网获取数据需要判断当前Fragment是否显示，然后在获取数据
     */
    protected abstract void onVisible(boolean isOnInit);

    /**
     * 得到控件的实例
     *
     * @param view fragment加载的view
     * @param id   控件的id
     * @param <T>  泛型
     * @return 控件
     */
    public <T extends View> T $1(View view, int id) {
        return (T) view.findViewById(id);
    }

    public void $(View view, int id) {
        $1(view, id).setOnClickListener((View.OnClickListener) this);
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 执行数据的加载
     */
    public void initData() {
    }

    ;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettach();
        }
        dissmisCustomProgressDialog();
        OkHttpUtils.getInstance().cancelTag(mActivity);
        MangerActivitys.removeActivity(mActivity);
    }

    public abstract T initPresenter();

    /**
     * 是否让头布局显示,默认显示的
     */
    protected boolean isToolbarShow() {
        return true;
    }

    /**
     * 是否让Toolbar有返回按钮(默认可以，一般一个应用中除了主界面，其他界面都是可以有返回按钮的)
     */
    protected boolean isToolbarCanBack() {
        return true;
    }


    public void init() {
    }

    protected Dialog progressDialog;

    public void shouCustomProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new Dialog(mActivity, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.request_dialog);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent);
        }
        if (progressDialog.isShowing()) {
            return;
        }
        if (APP.NotNull(message)) {
            TextView msg = (TextView) progressDialog
                    .findViewById(R.id.id_tv_loadingmsg);
            msg.setVisibility(View.VISIBLE);
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

    /*------------------ toolbar的一些视图操作 ------------------*/
    public void setHeaderTitle(String title) {
        mTitle.setText(title);
    }

    public void setHeaderAction(String subTitle) {
        mAction.setText(subTitle);
        mAction.setVisibility(subTitle.length() > 0 ? VISIBLE : View.GONE);
    }

    public void dissmisToast(String str) {
        hideCustomProgressDialog();
        if (APP.NotNull(str))
            APP.mToast(str);
    }

    /**
     * activity普通跳转
     *
     * @param intent
     */
    public void jumpToActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(mActivity, activity);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    public void jumpToWebViewActivity(String url, String title) {
        Intent intent = new Intent(mActivity, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        jumpToActivity(intent);
    }


    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(mActivity, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(mActivity, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
    }

    public void jumpToActivity(Class activity, String mKey, Object t) {
        Intent intent = new Intent(mActivity, activity);
        intent.putExtra(mKey, (Serializable) t);
        jumpToActivity(intent);
    }

    /**
     * 反传值跳转
     *
     * @param intent
     * @param V
     */
    public void jumpToActivity(Intent intent, int V) {
        startActivityForResult(intent, V);
        mActivity.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    public void jumpToActivityForResult(Class activity, int V) {
        Intent intent = new Intent(mActivity, activity);
        jumpToActivity(intent, V);
    }

    public void jumpToActivityForResult(Class activity, int V, String s, boolean b) {
        Intent intent = new Intent(mActivity, activity);
        intent.putExtra(s, b);
        jumpToActivity(intent, V);
    }

    public void jumpToActivityForResult(Class activity, String mKey, Object t, int V) {
        Intent intent = new Intent(mActivity, activity);
        intent.putExtra(mKey, (Serializable) t);
        jumpToActivity(intent, V);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initHandler();
        }
    };

    public void initHandler() {
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
}
