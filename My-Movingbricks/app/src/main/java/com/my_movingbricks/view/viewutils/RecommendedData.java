package com.my_movingbricks.view.viewutils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.my_movingbricks.app.APP;
import com.my_movingbricks.utlis.MatcherUtlis;
import com.my_movingbricks.view.bean.RecommendedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class RecommendedData {

    /**
     * 拿到原始html，做清除换行等等操作的
     *
     * @param html 原始html代码
     * @return 处理后的html
     */
    public static String GetReplaceData(String html) {
        return MatcherUtlis.GetReplaceAll(html, new String[]{"\n", "\r"}, null);
    }

    /**
     * 拿4个首页推荐的
     *
     * @param html
     * @return
     */
    public static ArrayList GetPage(String html) {
        String page = MatcherUtlis.GetCompleteString("<div class=\"subnav\".*?</a></div>", html);
        return MatcherUtlis.GetPageBeanList("<a href=\"(.*?)\".*?</a>", page, 1);
    }

    /**
     * 最新模块的，拿到图片的段落的
     *
     * @param html 处理后的html源码
     * @return 截取的段落
     */
    private static String GetImageData(String html) {
        String s = MatcherUtlis.GetCompleteString("<div class=\"postlist\">.*?<div class=\"sidebar\">", html);
        return MatcherUtlis.GetCompleteString("<ul id=\"pins\">.*?</ul>", s);

    }

    /**
     * 拿到图片详情页的url，和当前图片的url，和当前图片的描述：详情页的url 1，图片url 2，描述 9
     *
     * @param html 网页源码 RecommendedBean
     * @return 其中图片网站 .replaceAll(" ","%20");
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList GetMapListData(String html) {
        String mImage = GetImageData(html);
        //<li><a href="(.*?)".*?alt='(.*?)'.*?original='(.*?)'.*?"time">(.*?)</span>.*?"view">(.*?)</span></li>
        //<li><a href="(.*?)".*?alt='(.*?)'.*?src='(.*?)'.*?"time">(.*?)</span>.*?"view">(.*?)</span></li>
        List<Map<Integer, String>> listmap = MatcherUtlis.GetMoreList("<li><a href=\"(.*?)\".*?alt='(.*?)'.*?original='(.*?)'.*?\"time\">(.*?)</span>.*?\"view\">(.*?)</span></li>", mImage, 5);
        ArrayList<RecommendedBean> arrayRecommendedBean = new ArrayList<>();
        for (int i = 0; i < listmap.size(); i++) {
            RecommendedBean reBean = new RecommendedBean(listmap.get(i).get(1), listmap.get(i).get(2), listmap.get(i).get(3), listmap.get(i).get(4), listmap.get(i).get(5));
            arrayRecommendedBean.add(reBean);
        }
        return arrayRecommendedBean;
    }

    /**
     * 取下一页的url的,直接加算了
     */
    public static String GetNextUrl(String url, int i) {
        return url + "/page/" + i + "/";
    }

    public static int GetMaximum(String html, int i) {
        String sb;
        sb = MatcherUtlis.GetCompleteString("<a.*?</span></a>", MatcherUtlis.GetString("dots(.*?)next", html, i)).replaceAll("<.*?>", "").replaceAll(" ", "");
        if (!APP.NotNull(sb)) {
            sb = MatcherUtlis.GetCompleteString("<a.*?</span></a>", MatcherUtlis.GetCompleteString("<nav>.*?</nav>", html)).replaceAll("<.*?>", "");
        }
        return APP.NotNull(sb)?Integer.valueOf(sb):1;
    }
}