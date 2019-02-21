package io.jiantao.example.wanandroid.model;

/**
 * description 文章信息接口
 *
 * @author Created by jiantaoyang
 * @date 2019/1/8
 */
public interface IArticle {
    /**
     * article's title
     * @return  title
     */
    String getTitle();

    /**
     * @return author
     */
    String getAuthor();

    /**
     * @return article's url
     */
    String getLink();
}
