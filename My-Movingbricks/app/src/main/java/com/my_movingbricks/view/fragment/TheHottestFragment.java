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
import com.my_movingbricks.view.activity.AttachFileActivity;
import com.my_movingbricks.view.adapter.RecommendedAdapter;
import com.my_movingbricks.view.bean.RecommendedBean;
import com.my_movingbricks.view.presenter.RecommendedPresenter;
import com.my_movingbricks.view.viewinterface.RecommendedView;
import com.my_movingbricks.view.widgets.recyevent.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 最热
 * @author itlanbao.com
 */
public class TheHottestFragment extends BaseMvpFragment<RecommendedView, RecommendedPresenter> implements RecommendedView, AdapterView.OnItemClickListener {
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
    final String mKey = "AttachFileActivity";
    int q = 1;
    int Maximum;

    @Override
    protected void onVisible(boolean isInit) {
        if (isInit) {
            // initView();//初始化控件
            presenter.netGetData(U.mBaseTypeUrl(2));
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
                q=1;
                isRefresh = true;
                presenter.netGetData(U.mBaseTypeUrl(2));
            }

            @Override
            public void onLoadMore() {
                ++q;
                if (q > Maximum) {
                    mRecyclerView.noMoreLoading();
                } else {
                    isRefresh = false;
                    presenter.netGetListData(U.mBaseTypeUrl(2),2,q);
                }

            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                // RecommendedAdapter.MyViewHolder viewHolder1 = (RecommendedAdapter.MyViewHolder) viewHolder;
                jumpToActivity(AttachFileActivity.class, mKey, mRatingArray.get(viewHolder.getAdapterPosition() - 1));
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
    public RecommendedPresenter initPresenter() {
        return new RecommendedPresenter(mActivity);
    }

    private void initDatas() {
        if (isSize(mRatingArray)) {
            initSuccess(llErrorRefresh);
            if (mAdapter == null) {
                mAdapter = new RecommendedAdapter(mActivity, mRatingArray);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setmDatas(mRatingArray);
            }
        } else {
            initError(tvContext, llErrorRefresh, new OnLoading() {
                @Override
                public void Refresh(View v) {
                    presenter.netGetData(U.mBaseTypeUrl(2));
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
