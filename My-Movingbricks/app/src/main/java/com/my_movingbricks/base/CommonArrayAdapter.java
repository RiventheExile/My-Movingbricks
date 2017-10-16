package com.my_movingbricks.base;

/**
 * abc
 * Created by 搬砖小能手 on 2/5/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 通用的adapter   数值的
 *
 * @author Administrator
 *
 * @param <T>
 */
public abstract class CommonArrayAdapter<T> extends BaseAdapter {

    /**
     * 要显示的数据
     */
    protected T[] data = null;

    /**
     * 上下文对象
     */
    protected Context context = null;

    /**
     * 条目对应的布局文件
     */
    protected int layout_id = 0;

    /**
     * 构造函数
     *      参数list传递的就是这一组数据的信息
    * @param context
     * @param data
     * @param layout_id
     */
    public CommonArrayAdapter(Context context, T[] data, int layout_id) {
        super();
        this.context = context;
        this.data = data;
        this.layout_id = layout_id;
    }

    /**
     *  得到总的数量
     * @return
     */
    @Override
    public int getCount() {
        return data.length;
    }
    /**
     *  根据ListView位置返回View
     * @return
     */
    @Override
    public Object getItem(int arg0) {
        return data[arg0];
    }
    /**
     *  根据ListView位置得到List中的ID
     * @return
     */
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    /**
     * 根据位置得到View对象
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = ViewHolder.get(context, view, layout_id, parent, position);

        convert(holder, data[position], position);

        return holder.getConvertView();
    }

    /**
     * 需要用户自己实现的方法，这个方法，用来处理条目的显示效果
     *
     * @param holder
     *            里面有布局文件的控件
     * @param item
     *            每个条目对应的数据对象
     */
    public abstract void convert(ViewHolder holder, T item, int position);

}
