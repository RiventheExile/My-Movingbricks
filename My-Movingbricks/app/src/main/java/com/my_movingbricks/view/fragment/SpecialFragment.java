package com.my_movingbricks.view.fragment;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my_movingbricks.R;
import com.my_movingbricks.base.BaseMvpFragment;
import com.my_movingbricks.http.U;
import com.my_movingbricks.recyclerview.XRecyclerView;
import com.my_movingbricks.view.activity.SpecialActivity;
import com.my_movingbricks.view.adapter.SpecialdAdapter;
import com.my_movingbricks.view.bean.SpecialBean;
import com.my_movingbricks.view.presenter.SpecialPresenter;
import com.my_movingbricks.view.viewinterface.SpecialView;
import com.my_movingbricks.view.widgets.recyevent.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * abc   专题
 * Created by 搬砖小能手 on 14/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class SpecialFragment extends BaseMvpFragment<SpecialView, SpecialPresenter> implements SpecialView, AdapterView.OnItemClickListener {
    @BindView(R.id.xrv_welfare)
    public XRecyclerView mRecyclerView;
    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.ll_error_refresh)
    LinearLayout llErrorRefresh;
    private SpecialdAdapter mAdapter;
    ArrayList<SpecialBean> mRatingArray;
    boolean isRefresh;
    String NextUrl;
    String mUrl = "http://www.mzitu.com/";
    final String mKey = "SpecialActivity";
    int q = 1;
    int Maximum;

    @Override
    protected void onVisible(boolean isInit) {
        if (isInit) {
            // initView();//初始化控件
            presenter.netGetData(U.mBaseTypeUrl(4));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended;
    }

    @Override
    protected void initView(View view) {
        // 下载html

        mRatingArray = new ArrayList<>();
        /**
         * 设置分割线
         */
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,

                                       RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5, 5, 5, 5);// 这个方法可以设置分割线
            }
        });
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // 关键步骤1.适配器与recyclerView绑定
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.netGetData(U.mBaseTypeUrl(4));
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.noMoreLoading();
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                // RecommendedAdapter.MyViewHolder viewHolder1 = (RecommendedAdapter.MyViewHolder) viewHolder;
                jumpToActivity(SpecialActivity.class, mKey, mRatingArray.get(viewHolder.getAdapterPosition() - 1));
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(mActivity, "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //for (int i = 'A'; i < 'z' + 1; i++) {
    //    mDatas.add(R.mipmap.ic_launcher);
    //}
    @Override
    public SpecialPresenter initPresenter() {
        return new SpecialPresenter(this);
    }

    private void initDatas() {
        if (isSize(mRatingArray)) {
            initSuccess(llErrorRefresh);
            if (mAdapter == null) {
                mAdapter = new SpecialdAdapter(mActivity, mRatingArray);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setmDatas(mRatingArray);
            }
        } else {
            initError(tvContext, llErrorRefresh, new OnLoading() {
                @Override
                public void Refresh(View v) {
                    presenter.netGetData(U.mBaseTypeUrl(4));
                }
            });
        }
    }

    /**
     * 控件监听事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected boolean isToolbarShow() {
        return false;
    }

    @Override
    public void showLoading() {
        shouCustomProgressDialog("");
    }

    @Override
    public void hideLoading() {
        hideCustomProgressDialog();
    }

    @Override
    public void AdapterData(ArrayList<SpecialBean> list) {
        mRecyclerView.refreshComplete();
        if (isRefresh) mRatingArray.clear();
        mRatingArray.addAll(list);
        initDatas();
    }

    @Override
    public void GetUrl(String url) {
        mUrl = url;
    }

}
