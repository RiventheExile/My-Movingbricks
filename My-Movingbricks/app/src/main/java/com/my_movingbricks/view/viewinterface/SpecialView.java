package com.my_movingbricks.view.viewinterface;

import com.my_movingbricks.base.BaseView;
import com.my_movingbricks.view.bean.SpecialBean;

import java.util.ArrayList;

/**
 * abc
 * Created by 搬砖小能手 on 18/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public interface SpecialView extends BaseView {
    void AdapterData(ArrayList<SpecialBean> list);
    void GetUrl(String url);
}
