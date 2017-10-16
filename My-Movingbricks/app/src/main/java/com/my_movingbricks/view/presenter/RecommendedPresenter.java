package com.my_movingbricks.view.presenter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.my_movingbricks.base.BasePresenter;
import com.my_movingbricks.http.OnLoginListener;
import com.my_movingbricks.http.U;
import com.my_movingbricks.view.bean.RecommendedBean;
import com.my_movingbricks.view.model.RecommendedModel;
import com.my_movingbricks.view.modelimpl.RecommendedModelImpl;
import com.my_movingbricks.view.viewinterface.RecommendedView;
import com.my_movingbricks.view.viewutils.RecommendedData;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class RecommendedPresenter extends BasePresenter<RecommendedView> {
    private RecommendedModel recommendedModel;
    public RecommendedPresenter(Activity activity) {
        super(activity);
        recommendedModel = new RecommendedModelImpl();
    }


    /**
     * 获取首页源码，并拿到推荐页面的url的
     *
     * @param url
     */
    public void netGetData(String url) {
        getView().showLoading();
        getView().GetUrl(url);
        recommendedModel.login(mTag,url, new OnLoginListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void loginSuccess(String response, int id) {
                response = RecommendedData.GetReplaceData(response);
                ArrayList<RecommendedBean> listbean = RecommendedData.GetMapListData(response);
                getView().AdapterData(listbean);
                getView().GetMaximum(RecommendedData.GetMaximum(response, 1));
                getView().hideLoading();
            }

            @Override
            public void loginError(Call call, Exception e, int id) {
                getView().hideLoading();
            }
        });
    }

    public void netGetListData(String url,final int type,final int i) {
        getView().showLoading();
        getView().GetUrl(RecommendedData.GetNextUrl(url, i));
        recommendedModel.login(mTag,RecommendedData.GetNextUrl(url, i), new OnLoginListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void loginSuccess(String response, int id) {
                response = RecommendedData.GetReplaceData(response);
                ArrayList<RecommendedBean> listbean = RecommendedData.GetMapListData(response);
                getView().GetNextUrlnotify(RecommendedData.GetNextUrl(U.mBaseTypeUrl(type), i), i, listbean);
                getView().hideLoading();
            }

            @Override
            public void loginError(Call call, Exception e, int id) {
                getView().hideLoading();
            }
        });
    }
}
