package io.jiantao.example.wanandroid.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.repo.Resource;
import io.jiantao.example.wanandroid.repo.WanAndroidArticleRepository;

/**
 * 文章列表viewModel
 *
 * @author Created by jiantaoyang
 * @date 2019/1/7
 */
public class ArticleListViewModel extends AndroidViewModel {

    private LiveData<Resource<List<WanAndroidArticle>>> articles;
    private final WanAndroidArticleRepository mRepo;

    ArticleListViewModel(@NonNull Application application, @NonNull WanAndroidArticleRepository repo) {
        super(application);
        mRepo = repo;
        articles = mRepo.getSelectedArticles();
    }

    public LiveData<Resource<List<WanAndroidArticle>>> getArticles() {
        return articles;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        private WanAndroidArticleRepository mRepo;
        private Application mApplication;
        /**
         * Creates a {@code AndroidViewModelFactory}
         *
         * @param application an application to pass in {@link AndroidViewModel}
         */
        public Factory(@NonNull Application application, WanAndroidArticleRepository repositroy) {
            mApplication = application;
            mRepo = repositroy;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ArticleListViewModel(mApplication, mRepo);
        }
    }
}
