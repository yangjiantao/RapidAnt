package io.jiantao.example.wanandroid.viewmodels;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.repo.Resource;
import io.jiantao.example.wanandroid.repo.WanAndroidArticleRepositroy;

/**
 * 文章列表viewModel
 *
 * @author Created by jiantaoyang
 * @date 2019/1/7
 */
public class ArticleListViewModel extends ViewModel {

    private LiveData<Resource<List<WanAndroidArticle>>> articles;
    private WanAndroidArticleRepositroy mRepo;

    public ArticleListViewModel() {
        mRepo = new WanAndroidArticleRepositroy();
        articles = mRepo.getSelectedArticles();
    }

    public LiveData<Resource<List<WanAndroidArticle>>> getArticles() {
        return articles;
    }
}
