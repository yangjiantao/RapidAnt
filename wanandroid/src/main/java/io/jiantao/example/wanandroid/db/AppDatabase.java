package io.jiantao.example.wanandroid.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.jiantao.example.wanandroid.db.dao.ArticleDao;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;

/**
 * description 数据库管理类
 *
 * @author Created by jiantaoyang
 * @date 2019/1/8
 */
@Database(entities = {WanAndroidArticle.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_FILE_NAME = "rapid_ant.db";
    private static volatile AppDatabase sInstance;

    /**
     * @param context
     * @return
     */
    public static AppDatabase getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * @return articleDao
     */
    public abstract ArticleDao articleDao();

    private static AppDatabase buildDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DB_FILE_NAME).build();
    }
}
