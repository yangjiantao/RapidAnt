package io.jiantao.example.wanandroid.util;

import java.util.ArrayList;
import java.util.List;

import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/4/5
 */
public class TestUtils {

    /**
     * 模拟返回count条文章数据
     *
     * @param count should be > 0
     * @return
     */
    public static List<WanAndroidArticle> createArticles(int count) {
        List<WanAndroidArticle> articles = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            articles.add(createArticle());
        }
        return articles;
    }

    private static WanAndroidArticle createArticle() {

        WanAndroidArticle article = new WanAndroidArticle();
        article.title = "mock data for article.";
        return article;
    }
}
