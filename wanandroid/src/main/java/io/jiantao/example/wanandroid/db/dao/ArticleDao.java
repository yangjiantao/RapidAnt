package io.jiantao.example.wanandroid.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/1/31
 */
@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<WanAndroidArticle> articleList);

    /**
     * get articles from db
     * @return
     */
    @Query("SELECT * FROM articles")
    LiveData<List<WanAndroidArticle>> getArticles();

}
