package com.my_movingbricks.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.my_movingbricks.R;
import com.my_movingbricks.utlis.Drawables;
import com.my_movingbricks.view.bean.RecommendedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/7.
 * 1.继承RecyclerView.Adapter<ViewHolder>
 * 2.创建一个类ViewHolder
 */

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MyViewHolder> {

    // 3.创建一个构造方法
    private Context context;
    private List<RecommendedBean> mDatas;
    private LayoutInflater mInflater;// 3.1声明LayoutInflater传入Item布局
    // 0.1实现瀑布流，要将MyViewHolder修改为内部类
    // 0.2实现瀑布流效果，设置随机高度
    private List<Integer> mHeights;

    public RecommendedAdapter(Context context, List<RecommendedBean> datas) {
        this.context = context;
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
        // 取0.3随机高度
        mHeights = new ArrayList<Integer>();

        for (int i = 0; i < mDatas.size(); i++) {
            // 随机产生高度
            mHeights.add((int) (100 + Math.random() * 300));
            // 100到400的高度(要强转为int)
        }
    }

    public void setmDatas(List<RecommendedBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecommendedAdapter.MyViewHolder arg0, int arg1) {
        // 0.3item的高度：随机高度(LayoutParams要是ViewGroup的包)
        // 注意：这里的arg0.tv不能用itemView
        // ViewGroup.LayoutParams lp = arg0.item_iv.getLayoutParams();
        //lp.height = mHeights.get(arg1);
        //arg0.item_iv.setLayoutParams(lp);
        // 5.赋值
        Glide.with(context)
                .load(buildGlideUrl(mDatas.get(arg1).getImagrUrl(), mDatas.get(arg1).getImagrUrl()))
                .placeholder(Drawables.sPlaceholderDrawable)//设置资源加载过程中的占位Drawable。
                .error(Drawables.sErrorDrawable)//设置load失败时显示的Drawable。
                // .override(ScreenUtils.getScreenWidth(context) / 2, 4 * ScreenUtils.getScreenWidth(context) / 6)
                .skipMemoryCache(true) //使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用硬盘缓存
                .into(arg0.item_iv);
        arg0.item_describe.setText(mDatas.get(arg1).getmDescribe());
        // if (arg1 % 2 == 0) {
        //     DensityUtil.setViewMargin(arg0.item_iv,false, 12, 6, 12, 0);
        // } else {
        //     DensityUtil.setViewMargin(arg0.item_iv, false, 6, 12, 12, 0);
        //  }
    }

    @Override
    public RecommendedAdapter.MyViewHolder onCreateViewHolder(ViewGroup arg0,
                                                              int arg1) {
        // 4.y引入Item布局
        View view = mInflater.inflate(R.layout.item_recommended, arg0,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // 2 新建一个类ViewHolder，修改为内部类
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // item的控件
        ImageView item_iv;
        TextView item_describe;

        public MyViewHolder(View v) {
            super(v); // 2.1自动生成的构造方法
            // item的控件初始化
            item_iv = (ImageView) v.findViewById(R.id.item_iv);
            item_describe = (TextView) v.findViewById(R.id.item_describe);
        }
    }

    public void displayImageView(Context context, ImageView imageView, String url, int defaultResourceId) {
        if (context instanceof Activity && !((Activity) context).isFinishing()) { //判断条件，防止Activity已经finish了，ImageView仍然还在加载图片
            Glide.with(context).load(buildGlideUrl(url, url)).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
                    .placeholder(defaultResourceId).error(defaultResourceId).into(imageView);
        }
    }

    private GlideUrl buildGlideUrl(String url, String murl) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().setHeader("Referer", murl).setHeader("Cookie", "pgv_info=ssid=s5697280768").build());
        }//.addHeader("Referer","http://i.meizitu.net")
    }

}

