package io.jiantao.example.wanandroid.api;

import io.jiantao.example.wanandroid.util.LiveDataCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/1/29
 */
public class ApiServiceManager {
    /**
     * single instance
     */
    private static volatile ApiServiceManager sInstance;

    private Retrofit mRetrofit;

    /**
     * constructor
     */
    private ApiServiceManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com")
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static ApiServiceManager get() {
        if (sInstance == null) {
            synchronized (ApiServiceManager.class) {
                if (sInstance == null) {
                    sInstance = new ApiServiceManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * @return wanandroid api service
     */
    public static WanAndroidService getWanAndroidService(){
        return get().mRetrofit.create(WanAndroidService.class);
    }
}
