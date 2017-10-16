package com.my_movingbricks.view.adapter;

import android.content.Context;
import android.view.View;

import com.my_movingbricks.R;
import com.my_movingbricks.base.CommonAdapter;
import com.my_movingbricks.base.ViewHolder;
import com.my_movingbricks.utlis.DensityUtil;
import com.my_movingbricks.view.bean.AttachFileBean;

import java.util.List;



/**
 * abc
 * Created by 搬砖小能手 on 14/8/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮子！
 */
public class AttachFileAdapter extends CommonAdapter<AttachFileBean> {
    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public AttachFileAdapter(Context context, List<AttachFileBean> data, int layout_id) {
        super(context, data, layout_id);
    }

    public void setData(List<AttachFileBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder h, AttachFileBean item, int i) {
            h.setImageByUrl(R.id.iv_welfare, item.getmImageUrl(), false);
        if (i % 2 == 0) {
            DensityUtil.setViewMargin((View) h.getViewById(R.id.iv_welfare),false, 12, 6, 12, 0);
        } else {
            DensityUtil.setViewMargin((View) h.getViewById(R.id.iv_welfare), false, 6, 12, 12, 0);
        }
    }

}
