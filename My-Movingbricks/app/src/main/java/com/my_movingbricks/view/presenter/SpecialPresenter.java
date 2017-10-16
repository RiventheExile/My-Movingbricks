package com.my_movingbricks.view.presenter;

import android.support.v4.app.Fragment;

import com.my_movingbricks.base.BasePresenter;
import com.my_movingbricks.http.OnLoginListener;
import com.my_movingbricks.utlis.MatcherUtlis;
import com.my_movingbricks.view.bean.SpecialBean;
import com.my_movingbricks.view.model.SpecialModel;
import com.my_movingbricks.view.modelimpl.SpecialModelImpl;
import com.my_movingbricks.view.viewinterface.SpecialView;
import com.my_movingbricks.view.viewutils.SpecialdData;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * abc
 * Created by 搬砖小能手 on 18/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class SpecialPresenter extends BasePresenter<SpecialView> {
    private SpecialModel specialModel;

    public SpecialPresenter(Fragment activity) {
        super(activity);
        specialModel = new SpecialModelImpl();

    }

    /**
     * 获取首页源码，并拿到专题页面的url的
     *
     * @param url
     */
    public void netGetData(String url) {
        getView().showLoading();
        getView().GetUrl(url);
        specialModel.login(mTag,url, new OnLoginListener() {
            @Override
            public void loginSuccess(String response, int id) {
                response = MatcherUtlis.GetReplaceData(response);
                ArrayList<SpecialBean> listbean = SpecialdData.GetSpecialBeanList(response);
                getView().AdapterData(listbean);
                getView().hideLoading();
            }

            @Override
            public void loginError(Call call, Exception e, int id) {
                getView().hideLoading();
            }
        });
    }
}
