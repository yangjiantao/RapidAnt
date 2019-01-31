package io.jiantao.example.wanandroid;

import android.app.Application;

import io.jiantao.example.wanandroid.db.AppDatabase;
import io.jiantao.example.wanandroid.util.AppExecutors;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/1/31
 */
public class WanAndroidApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
