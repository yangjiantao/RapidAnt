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
    private static WanAndroidApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mAppExecutors = new AppExecutors();
    }

    /**
     * @return application instance
     */
    public static WanAndroidApp get(){
        return mApp;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
