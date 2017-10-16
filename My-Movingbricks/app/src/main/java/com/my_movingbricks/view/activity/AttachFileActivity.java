package com.my_movingbricks.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my_movingbricks.R;
import com.my_movingbricks.base.BaseHeaderActivity;
import com.my_movingbricks.http.HttpGet;
import com.my_movingbricks.http.HttpPost;
import com.my_movingbricks.http.OnLoginListener;
import com.my_movingbricks.libs.bgarefreshlayout.BGARefreshLayout;
import com.my_movingbricks.libs.bgarefreshlayout.BGAStickinessRefreshViewHolder;
import com.my_movingbricks.utlis.MatcherUtlis;
import com.my_movingbricks.utlis.UiUtils;
import com.my_movingbricks.view.adapter.AttachFileAdapter;
import com.my_movingbricks.view.bean.AttachFileBean;
import com.my_movingbricks.view.bean.RecommendedBean;
import com.my_movingbricks.view.viewutils.RecommendedData;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * abc 查看附件 客户信息附件是  否  联系记录
 * Created by 搬砖小能手 on 14/8/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class AttachFileActivity extends BaseHeaderActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.id_gridView)
    GridView idGridView;
    AttachFileAdapter fileAdapter;
    @BindView(R.id.mRefreshLayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.tv_context)
    TextView tvContext;
    @BindView(R.id.ll_error_refresh)
    LinearLayout llErrorRefresh;
    ArrayList<AttachFileBean> mRatingArray;
    final String mKey = "AttachFileActivity";
    RecommendedBean recommendedBean;
    int isFile;
    String html;
    int q = 20;
    int Maximum;
    AttachFileBean reBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attachfile;
    }

    @Override
    protected void initView() {
        mTitle.setText("专题");
        mAction.setText("前往分类");
        if (getIntent().getSerializableExtra(mKey) != null) {
            recommendedBean = (RecommendedBean) getIntent().getSerializableExtra(mKey);
        }
        mRatingArray = new ArrayList<>();
        fileAdapter = new AttachFileAdapter(this, mRatingArray, R.layout.item_attachfile);
        idGridView.setAdapter(fileAdapter);
        initHttpPost();
    }

    @Override
    public void initListener() {
        mRefreshLayout.setDelegate(this);
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(this, false);
        stickinessRefreshViewHolder.setStickinessColor(R.color.colorAccent);
        stickinessRefreshViewHolder.setRotateImage(R.mipmap.bga_refresh_stickiness);
        mRefreshLayout.setRefreshViewHolder(stickinessRefreshViewHolder);
        idGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", position);//第几张
                bundle.putSerializable("imageuribean", mRatingArray);
                Intent intent = new Intent(AttachFileActivity.this, ViewBigImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initHttpPost() {
        if (mRatingArray == null)
            initLoading(tvContext, llErrorRefresh);
        shouCustomProgressDialog("");
        HttpGet.LoginUrl(TAG, recommendedBean.getDetailsUrl(), new OnLoginListener() {
            @Override
            public void loginSuccess(String response, int id) {
                html = response;
                new Thread(mRunnable).start();
            }

            @Override
            public void loginError(Call call, Exception e, int id) {
                dissmisToast(UiUtils.getString(R.string.OkhttpError));
                mRefreshLayout.endRefreshing();
            }
        });
    }

    private void initTo() {
        fileAdapter.setData(mRatingArray);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        HttpPost.isLogin = true;
        initPage();
        mRatingArray.clear();
        initHttpPost();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if (q < Maximum) {
            q = q + 20;
            if (q > Maximum) {
                q = Maximum;
                for (int j = 0; j < Maximum - 20; j++) {
                    mRatingArray.add(new AttachFileBean("", "", reBean.getmClassification(), "", "", "", reBean.getmImageUrl().substring(0, reBean.getmImageUrl().length() - 6) + (j > 8 ? "" : "0") + (j + 21) + ".jpg", reBean.getmDescribe() + "(" + (j + 21) + ")"));
                }
            } else {
                for (int j = 0; j < 20; j++) {
                    mRatingArray.add(new AttachFileBean("", "", reBean.getmClassification(), "", "", "", reBean.getmImageUrl().substring(0, reBean.getmImageUrl().length() - 6) + (j > 8 ? "" : "0") + (j + 21) + ".jpg", reBean.getmDescribe() + "(" + (j + 21) + ")"));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initHandler() {
        mTitle.setText("" + mRatingArray.get(0).getmClassification());
        if (isSize(mRatingArray)) {
            initTo();
        }
        mRefreshLayout.endRefreshing();
        dissmisCustomProgressDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initThread() {
        html = RecommendedData.GetReplaceData(html);
        Maximum = Integer.valueOf(MatcherUtlis.GetCompleteString("<a href=.*?</span></a>", MatcherUtlis.GetCompleteString("dots.*?下一页", html)).replaceAll("<.*?>", ""));
        //Maximum = RecommendedData.GetMaximum(html, 1);
        Map<Integer, String> map = MatcherUtlis.GetMoreMap(//   当前页面的url，当前分类的url  当前的分类   f发布时间  浏览次数  下一页的url  当前图片的url  当前图片详情的url
                "canonical\" href=\"(.*?)\".*?main-meta.*?href=\"(.*?)\".*?tag\">(.*?)</a></span>.*?<span>(.*?)</span>.*?<span>(.*?)</span>.*?main-image\">.*?href=\"(.*?)\".*?src=\"(.*?)\" alt=\"(.*?)\" /></a></p></div>"
                , html, 8);
        initPage();
        reBean = new AttachFileBean(map.get(1), map.get(2), map.get(3), map.get(4), map.get(5), map.get(6), map.get(7), map.get(8));
        for (int j = 0; j < q; j++) {
            mRatingArray.add(new AttachFileBean("", "", map.get(3), "", "", "", reBean.getmImageUrl().substring(0, reBean.getmImageUrl().length() - 6) + (j > 8 ? "" : "0") + (j + 1) + ".jpg", reBean.getmDescribe() + "(" + (j + 1) + ")"));
        }
    }

    public void initPage() {
        if (Maximum < 20) q = Maximum;
        else q = 20;
    }


}