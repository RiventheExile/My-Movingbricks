package com.my_movingbricks.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.my_movingbricks.utlis.Drawables;
import com.my_movingbricks.utlis.UiUtils;

/**
 * abc
 * Created by 搬砖小能手 on 2/5/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class ViewHolder {
    /**
     * 这个viewHolder对应的条目视图
     */
    private View mContentView;
    /**
     * 用来保存条目视图里面所有的控件
     */
    private SparseArray<View> mViews;
    /**
     * 要使用的布局文件id
     */
    private int layoutId;
    /**
     * 当前的mPosition
     */
    private int mPosition;
    private Context context;

    /**
     * 获取viewHolder对象
     * @param context
     * @param convertView
     * @param layoutId
     * @param parent
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 int layoutId,ViewGroup parent, int position) {
        if (convertView == null) {
            return new ViewHolder(context, layoutId, parent, position);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

        if (holder.getLayoutId() == layoutId) {//多布局判断，当多布局时从新实例化
            holder.mPosition = position;
            return holder;
        }
        return new ViewHolder(context, layoutId, parent, position);
    }
    /**
     * 构造函数私有化，不让创建
     * @param context
     * @param layoutId
     * @param group
     * @param position
     */
    private ViewHolder(Context context, int layoutId, ViewGroup group,
                       int position) {
        this.context = context;
        this.layoutId = layoutId;
        this.mPosition = position;
        // 初始化类似map集合的SparseArray集合
        this.mViews = new SparseArray<View>();
        // 对布局文件充气成View对象
        mContentView = LayoutInflater.from(context).inflate(layoutId, group,
                false);
        // 把自己挂载到布局文件对应的视图上面
        mContentView.setTag(this);
    }
    /**
     * 根据控件id获取控件对象
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getViewById(int viewId) {
        // 从集合中根据这个id获取view视图对象
        View view = mViews.get(viewId);
        // 如果为空，说明是第一次获取，里面没有，那就在布局文件中找到这个控件，并且存进集合中
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        // 返回控件对象
        return (T) view;
    }
    /**
     * 为textview设置内容，直接字符串
     *
     * @param resId
     * @param text
     */
    public void setTextForTv(int resId, String text) {
        TextView tv = getViewById(resId);
        tv.setText(text);
    }
    /**
     * 为textview设置内容，资源id
     *
     * @param resId
     * @param text
     */
    public void setTextForTv(int resId, int text) {
        TextView tv = getViewById(resId);
        tv.setText(UiUtils.getString(text));
    }
    /**
     * 设置文字颜色
     *
     * @param resId
     * @param colorId
     */
    public void setTextColor(int resId, int colorId) {
        TextView tv = getViewById(resId);
        tv.setTextColor(colorId);
    }

    public void setImageResource(int resId, int imgId) {
        ImageView img = getViewById(resId);
        img.setImageResource(imgId);
    }

    public void setImageBitmap(int resId, Bitmap bitmap) {
        ImageView img = getViewById(resId);
        img.setImageBitmap(bitmap);
        if (null != bitmap) {
            bitmap.recycle();
        }
    }

    /**
     * 为TextView设置文本,按钮也可以用这个方法,button是textView的子类
     *
     * @param textViewId
     * @param content
     */
    public void setText(int textViewId, String content) {
        ((TextView) getViewById(textViewId)).setText(content);
    }
    /**
     * 为ImageView设置图片
     *
     * @param iv
     * @param imageId
     */
    public void setImage(ImageView iv, int imageId) {
        iv.setImageResource(imageId);
    }
    /**
     * 获取布局文件对应的view对象
     *
     * @return
     */
    public View getConvertView() {
        return mContentView;
    }

    public int getLayoutId() {
        return layoutId;
    }
    public int getPosition() {
        return mPosition;
    }


    /**
     * 为ImageView设置图片
     *
     * @param url
     * @param Id
     */
    public void setImage(Context context, int Id, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(Drawables.sPlaceholderDrawable)//设置资源加载过程中的占位Drawable。
                .error(Drawables.sErrorDrawable)//设置load失败时显示的Drawable。
                //.centerCrop()
                .override(80, 80)
                .skipMemoryCache(true) //使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用硬盘缓存
                .into((ImageView) getViewById(Id));
    }
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @param is 是否开启内存缓存
     * @return
     */
    public void setImageByUrl(int viewId, String url,boolean is)
    {
        Glide.with(context)
                .load(buildGlideUrl(url,url))
                .placeholder(Drawables.sPlaceholderDrawable)//设置资源加载过程中的占位Drawable。
                .error(Drawables.sErrorDrawable)//设置load失败时显示的Drawable。
                //.centerCrop()
                .skipMemoryCache(is) //使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用硬盘缓存
                .into((ImageView) getViewById(viewId));

    }

    private GlideUrl buildGlideUrl(String url, String murl) {
        if (TextUtils.isEmpty(url)) {
            return null;
        } else {
            return new GlideUrl(url, new LazyHeaders.Builder().setHeader("Referer", murl).setHeader("Cookie", "pgv_info=ssid=s5697280768").build());
        }//.addHeader("Referer","http://i.meizitu.net")
    }
    public void setImageByUrl(int viewId, int url,boolean is)
    {
        Glide.with(context)
                .load(url)
                .placeholder(Drawables.sPlaceholderDrawable)//设置资源加载过程中的占位Drawable。
                .error(Drawables.sErrorDrawable)//设置load失败时显示的Drawable。
                //.centerCrop()
                .skipMemoryCache(is) //使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用硬盘缓存
                .into((ImageView) getViewById(viewId));

    }
}
