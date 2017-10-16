package com.my_movingbricks.view.fragment;

import android.view.View;

import com.my_movingbricks.R;
import com.my_movingbricks.base.BaseMvpFragment;
import com.my_movingbricks.base.BasePresenter;

/**
 * 信息
 *
 * @author itlanbao.com
 */
public class InfomationFragment extends BaseMvpFragment {

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

    }
}
