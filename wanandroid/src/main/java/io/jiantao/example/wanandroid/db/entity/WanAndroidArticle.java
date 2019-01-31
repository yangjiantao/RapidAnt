package io.jiantao.example.wanandroid.db.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import io.jiantao.example.wanandroid.model.IArticle;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/1/29
 */
@Entity(tableName = "articles")
public class WanAndroidArticle implements IArticle {

    /**
     * apkLink :
     * author : kingwang666
     * chapterId : 294
     * chapterName : 完整项目
     * collect : false
     * courseId : 13
     * desc : APP信息是一个免费的工具应用. 它有以下功能特点:

     查看已安装的app信息.
     查看未安装的apk信息.
     导出已安装的app应用的apk文件.
     复制apk的签名信息到剪切板.
     * envelopePic : http://wanandroid.com/blogimgs/f16b7060-38e2-4ebd-87d9-d61b59a000e2.png
     * fresh : false
     * id : 7892
     * link : http://www.wanandroid.com/blog/show/2493
     * niceDate : 2019-01-23
     * origin :
     * projectLink : https://github.com/kingwang666/GetApk
     * publishTime : 1548247914000
     * superChapterId : 294
     * superChapterName : 开源项目主Tab
     * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
     * title : 一个可以显示app或者apk信息。并且可导出已安装的app的apk文件工具应用
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    public String apkLink;
    @SerializedName("author")
    public String author;
    public int chapterId;
    public String chapterName;
    public boolean collect;
    public int courseId;
    @SerializedName("desc")
    public String desc;
    public String envelopePic;
    public boolean fresh;
    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("link")
    public String link;
    public String niceDate;
    public String origin;
    public String projectLink;
    public long publishTime;
    public int superChapterId;
    public String superChapterName;
    @SerializedName("title")
    public String title;
    public int type;
    public int userId;
    public int visible;
    public int zan;
//    @Embedded
//    public List<TagsBean> tags;

    public WanAndroidArticle() {
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static class TagsBean {
        /**
         * name : 项目
         * url : /project/list/1?cid=294
         */

        @ColumnInfo(name = "tag_name")
        public String name;
        @ColumnInfo(name = "tag_url")
        public String url;

        public TagsBean() {
        }
    }

    @Override
    public String toString() {
        return "WanAndroidArticle{" +
                "author='" + author + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
