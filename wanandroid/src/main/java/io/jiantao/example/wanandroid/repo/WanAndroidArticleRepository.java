package io.jiantao.example.wanandroid.repo;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import io.jiantao.example.wanandroid.api.ApiResponse;
import io.jiantao.example.wanandroid.api.ApiServiceManager;
import io.jiantao.example.wanandroid.api.BaseDataList;
import io.jiantao.example.wanandroid.api.WanAndroidService;
import io.jiantao.example.wanandroid.db.dao.ArticleDao;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.util.AppExecutors;

/**
 * description 文章资料获取入口。 数据来源：本地db、remoteServer
 *
 * @author Created by jiantaoyang
 * @date 2019/1/8
 */
public class WanAndroidArticleRepository {

    private static final String TAG = WanAndroidArticleRepository.class.getSimpleName();

    private AppExecutors mExecutors;
    private ArticleDao mDao;

    private WanAndroidService mService;

    public WanAndroidArticleRepository(AppExecutors executors, ArticleDao articleDao, WanAndroidService service) {
        // should be single instance
        mExecutors = executors;
        mDao = articleDao;
        mService = service;
    }

    public LiveData<Resource<List<WanAndroidArticle>>> getSelectedArticles() {
        return new NetworkBoundResource<List<WanAndroidArticle>, BaseDataList<WanAndroidArticle>>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull BaseDataList<WanAndroidArticle> dataList) {
//                Log.d(TAG, "saveCallResult .");
                List<WanAndroidArticle> list = dataList.getData();
                if (list == null) {
                    return;
                }
                for (WanAndroidArticle item : list) {
                    Log.d(TAG, item.toString());
                }
                mDao.insertArticles(list);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<WanAndroidArticle> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<WanAndroidArticle>> loadFromDb() {
                return mDao.getArticles();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseDataList<WanAndroidArticle>>> createCall() {
                // todo 处理分页
                return mService.getSelectedArticles(0);
            }
        }.asLiveData();
    }
}
