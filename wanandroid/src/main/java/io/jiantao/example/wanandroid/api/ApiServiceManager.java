package io.jiantao.example.wanandroid.api;

import java.util.HashMap;
import java.util.Map;

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
     * services map for cache
     */
    private Map<Class<?>, Object> mServices;

    /**
     * constructor
     */
    private ApiServiceManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com")
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mServices = new HashMap<>();
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
     * Returns an existing Service or creates a new one in the app's scope.
     * @param <T> service type
     * @return service instance
     */
    public static <T> T getService(Class<T> service) {
        Object obj = get().mServices.get(service);
        if (obj == null) {
            obj = get().mRetrofit.create(service);
            get().mServices.put(service, obj);
        }
        return (T) obj;
    }
}
