package com.my_movingbricks.view.fragment;

import android.view.View;

import com.my_movingbricks.R;
import com.my_movingbricks.base.BaseMvpFragment;
import com.my_movingbricks.base.BasePresenter;

/**
 * 搜索好友页面
 *
 * @author itlanbao.com
 */
public class SearchFriendFragment extends BaseMvpFragment {

    private boolean isRefresh = true;// 获取数据成功还是失败 为后面执行刷新还是加载 成功或者失败


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {

    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onVisible(boolean isInit) {

        if(isInit){

        }
    }



}
