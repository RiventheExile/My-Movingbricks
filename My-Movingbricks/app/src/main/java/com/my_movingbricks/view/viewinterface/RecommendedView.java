package com.my_movingbricks.view.viewinterface;

import com.my_movingbricks.base.BaseView;
import com.my_movingbricks.view.bean.RecommendedBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public interface RecommendedView extends BaseView {
    void GetMaximum(int i);
    void AdapterData(ArrayList<RecommendedBean> list);
    void GetNextUrlnotify(String url,int i,ArrayList<RecommendedBean> list);
    void GetUrl(String url);
}
