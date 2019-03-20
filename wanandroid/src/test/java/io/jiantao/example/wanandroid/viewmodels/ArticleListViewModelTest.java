package io.jiantao.example.wanandroid.viewmodels;

import android.app.Application;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.repo.Resource;
import io.jiantao.example.wanandroid.repo.WanAndroidArticleRepositroy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/3/12
 */
public class ArticleListViewModelTest {

    private ArticleListViewModel mViewModel;
    private WanAndroidArticleRepositroy mRepo;
    private LiveData<Resource<List<WanAndroidArticle>>> mTestValue;

    @Before
    public void setup() {
        mRepo = mock(WanAndroidArticleRepositroy.class);
        mTestValue = new MutableLiveData<>();
        when(mRepo.getSelectedArticles()).thenReturn(mTestValue);
        Application app = mock(Application.class);
        mViewModel = new ArticleListViewModel(app, mRepo);
    }

    @Test
    public void testNull() {
        Truth.assertThat(mViewModel.getArticles()).isNotNull();
    }
}
