package io.jiantao.example.wanandroid.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.jiantao.example.wanandroid.WanAndroidApp;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.repo.Resource;
import io.jiantao.example.wanandroid.repo.WanAndroidArticleRepositroy;

/**
 * 文章列表viewModel
 *
 * @author Created by jiantaoyang
 * @date 2019/1/7
 */
public class ArticleListViewModel extends AndroidViewModel {

    private LiveData<Resource<List<WanAndroidArticle>>> articles;
    private WanAndroidArticleRepositroy mRepo;

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        WanAndroidApp androidApp = (WanAndroidApp) application;
        mRepo = new WanAndroidArticleRepositroy(androidApp.getAppExecutors(), androidApp.getDatabase().articleDao());
        articles = mRepo.getSelectedArticles();
    }

    public LiveData<Resource<List<WanAndroidArticle>>> getArticles() {
        return articles;
    }
}
