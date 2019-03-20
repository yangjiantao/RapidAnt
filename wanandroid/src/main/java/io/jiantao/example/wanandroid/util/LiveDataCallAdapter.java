package io.jiantao.example.wanandroid.util;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LiveData;
import io.jiantao.example.wanandroid.api.ApiResponse;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 *
 * @author Created by jiantaoyang
 * @date 2019/1/24
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    /**
     * @param responseType 响应实体类型
     */
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(ApiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            postValue(ApiResponse.create(t));
                        }
                    });
                }
            }

            @Override
            protected void onInactive() {
                if (call.isCanceled()) {
                    call.cancel();
                }
            }
        };
    }
}
