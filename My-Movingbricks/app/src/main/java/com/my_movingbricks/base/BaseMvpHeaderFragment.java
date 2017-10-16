package com.my_movingbricks.base;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my_movingbricks.R;
import com.my_movingbricks.app.APP;
import com.my_movingbricks.utlis.UiUtils;
import com.my_movingbricks.view.MangerActivitys;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;


/**
 * Created by Administrator on 2016/11/27.
 * 搬砖小能手  BaseFragment基类
 * 自定义Fragment，用来处理多层Fragment时，点击back键返回上一层header
 */

public abstract class BaseMvpHeaderFragment<V, T extends BasePresenter<V>> extends Fragment {

    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.action)
    public TextView mAction;
    @BindView(R.id.back)
    public TextView mBack;
    @BindView(R.id.title_bar)
    public RelativeLayout title_bar;
    private boolean isOnInit = true;
    private boolean isVisibleToUser = false;
    //setUserVisibleHint和onResume只记录一次,用些变量作为标记
    private boolean hasTrace = false;
    public AppCompatActivity mActivity;
    public View view;
    protected String TAG;
    public T presenter;
    public Unbinder unbinder;

    protected boolean mIsLoadedData = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = LayoutInflater.from(mActivity).inflate(getLayoutId(),
                    container, false);
            // 方法二
            // view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            mBack.setVisibility(isToolbarCanBack() ? View.VISIBLE : View.GONE);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            handleOnVisibilityChangedToUser(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(false);
        }
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser
     */
    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true;
                onLazyLoadOnce();
            }
            onVisibleToUser();
        } else {
            // 对用户不可见
            onInvisibleToUser();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisibleToUser() {
    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInvisibleToUser() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

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
    public void setHeaderTitle(int title) {
        mTitle.setText(UiUtils.getString(title));
    }
    public void setHeaderAction(String subTitle) {
        mAction.setText(subTitle);
        mAction.setVisibility(subTitle.length() > 0 ? VISIBLE : View.GONE);
    }
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

    public void dissmisToast(String str) {
        hideCustomProgressDialog();
        if (APP.NotNull(str))
            APP.mToast(str);
    }

}
