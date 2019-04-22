package io.jiantao.example.wanandroid.repo;

import java.util.Objects;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import io.jiantao.example.wanandroid.api.ApiResponse;
import io.jiantao.example.wanandroid.util.AppExecutors;


/**
 * 执行流程：loadFromDb -> 是否fetch网络数据->缓存到db->loadFromDb
 * tips: 如果不需要持久化数据，Repository中直接fetch网络数据即可。
 * @param <ResultType>  Type for the Resource data.
 * @param <RequestType> Type for the API response.
 * @author yangjiantao
 * @date 2019.1.29
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                result.removeSource(dbSource);
                fetchFromNetwork(dbSource);
            } else {
                setValue(Resource.success(data));
//                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    /**
     * @param dbSource
     */
    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(),
                                    newData -> setValue(Resource.success(newData)))
                    );
                });
            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> setValue(Resource.error(response.getErrorMessage(), newData)));
            }
        });
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.getBody();
    }

    /**
     * Called to save the result of the API response into the database.
     *
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    /**
     * Called to get the cached data from the database.
     * 实现类将db操作放到io线程中, 然后通过setValue回调。比如Room操作就在子线程中.
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}