package com.my_movingbricks.view.modelimpl;

import com.my_movingbricks.http.HttpGet;
import com.my_movingbricks.http.OnLoginListener;
import com.my_movingbricks.view.model.RecommendedModel;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class RecommendedModelImpl implements RecommendedModel {

    @Override
    public void login(Object tag,String url, OnLoginListener loginListener) {
        HttpGet.LoginUrl(tag,url,loginListener);
    }
}
