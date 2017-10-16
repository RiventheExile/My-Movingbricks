package com.my_movingbricks.view.activity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my_movingbricks.R;
import com.my_movingbricks.base.BaseMvpActivity;
import com.my_movingbricks.http.U;
import com.my_movingbricks.recyclerview.XRecyclerView;
import com.my_movingbricks.view.adapter.RecommendedAdapter;
import com.my_movingbricks.view.bean.RecommendedBean;
import com.my_movingbricks.view.bean.SpecialBean;
import com.my_movingbricks.view.presenter.RecommendedPresenter;
import com.my_movingbricks.view.viewinterface.RecommendedView;
import com.my_movingbricks.view.widgets.recyevent.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 最新
 *
 * @author itlanbao.com
 */

public class SpecialActivity extends BaseMvpActivity<RecommendedView, RecommendedPresenter> implements RecommendedView{
    @BindView(R.id.xrv_welfare)
    public XRecyclerView mRecyclerView;
    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.ll_error_refresh)
    LinearLayout llErrorRefresh;
    private RecommendedAdapter mAdapter;
    ArrayList<RecommendedBean> mRatingArray;
    boolean isRefresh;
    String NextUrl;
    String mUrl="http://www.mzitu.com/";
    final String mKey = "SpecialActivity";
    final String mKeyFileis = "AttachFileActivity";

    int q = 1;
    int Maximum;
    SpecialBean specialBean;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended;
    }

    @Override
    protected void initView() {

        if (getIntent().getSerializableExtra(mKey) != null) {
            specialBean = (SpecialBean) getIntent().getSerializableExtra(mKey);
            mTitle.setText(specialBean.getmDescribe().replaceAll("共.*?",""));
            mPresenter.netGetData(specialBean.getDetailsUrl());
        }
        mRatingArray = new ArrayList<>();

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
                q=1;
                isRefresh = true;
                mPresenter.netGetData(U.mBaseTypeUrl(1));
            }

            @Override
            public void onLoadMore() {
                ++q;
                if (q > Maximum) {
                    mRecyclerView.noMoreLoading();
                } else {
                    isRefresh = false;
                    mPresenter.netGetListData(U.mBaseTypeUrl(1),1,q);
                }

            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                // RecommendedAdapter.MyViewHolder viewHolder1 = (RecommendedAdapter.MyViewHolder) viewHolder;
                RecommendedBean recommendedBean=new RecommendedBean(mRatingArray.get(viewHolder.getAdapterPosition() - 1).getDetailsUrl(),
                        mRatingArray.get(viewHolder.getAdapterPosition() - 1).getmDescribe(),mRatingArray.get(viewHolder.getAdapterPosition() - 1).getImagrUrl()
                        ,"","");
                jumpToActivity(AttachFileActivity.class, mKeyFileis, recommendedBean);
                //jumpToActivity(AttachFileActivity.class, mKey, mRatingArray.get(viewHolder.getAdapterPosition() - 1));
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(SpecialActivity.this, "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public RecommendedPresenter initPresenter() {
        return new RecommendedPresenter(this);
    }

    private void initDatas() {
        if (isSize(mRatingArray)) {
            initSuccess(llErrorRefresh);
            if (mAdapter == null) {
                mAdapter = new RecommendedAdapter(this, mRatingArray);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setmDatas(mRatingArray);
            }
        } else {
            initError(tvContext, llErrorRefresh, new OnLoading() {
                @Override
                public void Refresh(View v) {
                    mPresenter.netGetData(U.mBaseTypeUrl(1));
                }
            });
        }
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
    public void GetMaximum(int i) {
        Maximum = i;
    }


    @Override
    public void AdapterData(ArrayList<RecommendedBean> list) {
        mRecyclerView.refreshComplete();
        if (isRefresh) mRatingArray.clear();
        mRatingArray.addAll(list);
        initDatas();
    }

    @Override
    public void GetNextUrlnotify(String url, int i, ArrayList<RecommendedBean> list) {
        mRecyclerView.refreshComplete();
        mRatingArray.addAll(list);
        initDatas();
    }

    @Override
    public void GetUrl(String url) {
        mUrl = url;
    }

}