package com.my_movingbricks.utlis;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.my_movingbricks.view.bean.PageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class MatcherUtlis {
    /**
     * 删除某些字符串,target为空时就是替换成空
     *
     * @param html     原字符串
     * @param Original 需要删除的字符串,原先的字符串
     * @param target   需要替换成的字符串 目标字符串
     * @return 处理完成的字符串
     */
    public static String GetReplaceAll(String html, String[] Original, String[] target) {
        if (target == null) {
            for (int i = 0; i < Original.length; i++) {
                html = html.replaceAll(Original[i], "");
            }
        } else {
            for (int i = 0; i < Original.length; i++) {
                html = html.replaceAll(Original[i], target[i]);
            }
        }
        return html;
    }

    /**
     * 得到Matcher 对象的
     *
     * @param mRegular 正则表达式
     * @param mContent 要处理的字符串
     * @return Matcher
     */
    public static Matcher getMatcher(String mRegular, String mContent) {
        Pattern patternhref = Pattern.compile(mRegular,
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        return patternhref.matcher(mContent);
    }

    /**
     * 有相同的则截取那一段完整的
     *
     * @param mRegular 正则表达式
     * @param mContent 原字符串
     * @return 提取出来的内容
     */
    public static String GetCompleteString(String mRegular, String mContent) {
        Matcher matcher = getMatcher(mRegular, mContent);
        while (matcher.find()) {
            return matcher.group().toString();
        }
        return "";
    }

    /**
     * 单个提取的方法，不截取整段，只截取括号里面的内容
     *
     * @param mRegular 正则表达式
     * @param mContent 原字符串
     * @param qq       第几个括号
     * @return 提前到的括号的内容
     */
    public static String GetString(String mRegular, String mContent, int qq) {
        Matcher matcher = getMatcher(mRegular, mContent);
        while (matcher.find()) {
            return matcher.group(qq);
        }
        return "";
    }

    /**
     * 有相同的则截取那一段完整的段落
     *
     * @param mRegular
     * @param mContent
     * @return
     */
    public static ArrayList GetCompleteList(String mRegular, String mContent) {
        Matcher matcher = getMatcher(mRegular, mContent);
        ArrayList<String> str = new ArrayList<>();
        while (matcher.find()) {
            str.add(matcher.group().toString());
        }
        if (str.size() > 0) {
            return str;
        } else {
            return null;
        }
    }

    /**
     * 有相同的则截取括号中的段落
     *
     * @param mRegular
     * @param mContent
     * @return
     */
    public static ArrayList GetList(String mRegular, String mContent, int qq) {
        Matcher matcher = getMatcher(mRegular, mContent);
        ArrayList<String> str = new ArrayList<>();
        while (matcher.find()) {
            str.add(matcher.group(qq).toString());
        }
        if (str.size() > 0) {
            return str;
        } else {
            return null;
        }
    }

    /**
     * 有相同的则截取括号中的段落,多个括号的
     *
     * @param mRegular 字符串 正则
     * @param mContent 内容
     * @param qq       几个括号，最少两个
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<Map<Integer, String>> GetMoreList(String mRegular, String mContent, int qq) {
        if (qq < 2) return null;
        Matcher matcher = getMatcher(mRegular, mContent);
        List<Map<Integer, String>> listMap=new ArrayList<>();
        while (matcher.find()) {
            Map<Integer, String> map = new HashMap<>();
            map.put(1,matcher.group(1).toString());
            map.put(2,matcher.group(2).toString());
            if (qq >= 3) {
                map.put(3,matcher.group(3).toString());
            }
            if (qq >= 4) {
                map.put(4,matcher.group(4).toString());
            }
            if (qq >= 5) {
                map.put(5,matcher.group(5).toString());
            }
            listMap.add(map);
        }
        if (listMap.size() == 0) {
            return null;
        } else {
            return listMap;
        }
    }
    /**
     * 有相同的则截取括号中的段落,多个括号的
     *
     * @param mRegular 字符串 正则
     * @param mContent 内容
     * @param qq       几个括号，最少两个
     * @return
     */
    public static Map<Integer, String> GetMoreMap(String mRegular, String mContent, int qq) {
        if (qq < 2) return null;
        Matcher matcher = getMatcher(mRegular, mContent);
        Map<Integer, String> map=new HashMap<>();
        while (matcher.find()) {
            map.put(1,matcher.group(1).toString());
            map.put(2,matcher.group(2).toString());
            if (qq >= 3) {
                map.put(3,matcher.group(3).toString());
            }
            if (qq >= 4) {
                map.put(4,matcher.group(4).toString());
            }
            if (qq >= 5) {
                map.put(5,matcher.group(5).toString());
            }
            if (qq >= 6) {
                map.put(6,matcher.group(6).toString());
            }
            if (qq >= 7) {
                map.put(7,matcher.group(7).toString());
            }
            if (qq >= 8) {
                map.put(8,matcher.group(8).toString());
            }
        }
            return map;
    }
    /**
     * 有相同的则截取括号中的段落,多个括号的
     *
     * @param mRegular 字符串
     * @param mContent 内容
     * @param qq       几个括号，最少两个
     * @return
     */
    public static ArrayList GetPageBeanList(String mRegular, String mContent, int qq) {
        if (qq < 1) return null;
        Matcher matcher = getMatcher(mRegular, mContent);
        ArrayList<PageBean> listBean=new ArrayList<>();
        while (matcher.find()) {
            PageBean bean=new PageBean(matcher.group(1).toString(), matcher.group().toString().replaceAll("<.*?>",""));
            listBean.add(bean);
        }
        if (listBean.size() == 0) {
            return null;
        } else {
            return listBean;
        }
    }

    /**
     * 拿到原始html，做清除换行等等操作的
     *
     * @param html 原始html代码
     * @return 处理后的html
     */
    public static String GetReplaceData(String html) {
        return MatcherUtlis.GetReplaceAll(html, new String[]{"\n", "\r"}, null);
    }
}
