package io.jiantao.rapidant;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.jiantao.example.wanandroid.db.AppDatabase;
import io.jiantao.example.wanandroid.db.dao.ArticleDao;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;

/**
 * androidTest 只能在application module中进行。
 * Room dao test
 *
 * @author Created by jiantaoyang
 * @date 2019-05-17
 */
@RunWith(AndroidJUnit4.class)
public class ArticleDaoTest {

    AppDatabase db;
    ArticleDao dao;
    /**
     * mock android arch sdk class
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();

        dao = db.articleDao();
    }

    @Test
    public void insertAndLoad() throws InterruptedException {
        // insert 15 articles
        List<WanAndroidArticle> articles = TestUtils.createArticles(15);
        dao.insertArticles(articles);

        LiveData<List<WanAndroidArticle>> liveData = dao.getArticles();
        List<WanAndroidArticle> articleList = LiveDataTestUtil.getValue(liveData);

        MatcherAssert.assertThat(articleList.size(), Matchers.equalTo(15));

    }

    @After
    public void closeDb() {
        db.close();
    }
}
