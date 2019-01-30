package io.jiantao.example.wanandroid.repo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A generic class that contains data and status about loading this data.
 * 加载资源的数据和状态封装类
 *
 * @param <T> 数据类型
 * @author copy from android guide
 * @date 2019
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    /**
     * loading status
     */
    public enum Status {
        SUCCESS, ERROR, LOADING
    }
}