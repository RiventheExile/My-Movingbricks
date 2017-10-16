package com.my_movingbricks.view.viewutils;

import com.my_movingbricks.utlis.MatcherUtlis;
import com.my_movingbricks.view.bean.SpecialBean;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * abc
 * Created by 搬砖小能手 on 19/9/2017.
 * E-mail:40492459@qq.com.
 * Signature:当你的才华满足不了你的野心的时候，那么你应该静下心来学习.
 * Alert:语言的巨人，行动的矮！
 */
public class SpecialdData {
    public static String ParagraphImage(String s){
        return MatcherUtlis.GetCompleteString("<dl class=\"tags\">.*?</dl>",s);
    }

    /**
     * 有相同的则截取括号中的段落,多个括号的
     *
     * @param mRegular 字符串
     * @param mContent 内容
     * @param qq       几个括号，最少两个
     * @return
     */
    public static ArrayList GetSpecialBeanList(String mRegular, String mContent, int qq) {
        if (qq < 1) return null;
        String mImage = ParagraphImage(mContent);
        Matcher matcher = MatcherUtlis.getMatcher(mRegular, mImage);
        ArrayList<SpecialBean> listBean=new ArrayList<>();
        while (matcher.find()) {
            SpecialBean bean=new SpecialBean(matcher.group(1).toString(),matcher.group(2).toString(), matcher.group().toString().replaceAll("<.*?>",""));
            listBean.add(bean);
        }
        if (listBean.size() == 0) {
            return null;
        } else {
            return listBean;
        }
    }
    public static ArrayList GetSpecialBeanList(String mContent) {
      return GetSpecialBeanList("<dd><a href=\"(.*?)\".*?src=\"(.*?)\".*?</i></dd> ",mContent,2);
    }
}
