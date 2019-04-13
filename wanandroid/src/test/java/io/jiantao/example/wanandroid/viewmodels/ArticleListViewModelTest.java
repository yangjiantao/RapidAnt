package io.jiantao.example.wanandroid.viewmodels;

import android.app.Application;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.repo.Resource;
import io.jiantao.example.wanandroid.repo.WanAndroidArticleRepositroy;
import io.jiantao.example.wanandroid.util.TestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/3/12
 */
@RunWith(JUnit4.class)
public class ArticleListViewModelTest {

    /**
     * mock android sdk class
     */
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ArticleListViewModel mViewModel;
    private WanAndroidArticleRepositroy mRepo;
    private MutableLiveData<Resource<List<WanAndroidArticle>>> mTestValue;

    @Before
    public void setup() {
        mRepo = mock(WanAndroidArticleRepositroy.class);
        mTestValue = new MutableLiveData<>();
        Application app = mock(Application.class);
        when(mRepo.getSelectedArticles()).thenReturn(mTestValue);
        mViewModel = new ArticleListViewModel(app, mRepo);
    }

    @Test
    public void testNull() {
        Truth.assertThat(mViewModel.getArticles()).isNotNull();
    }

    @Test
    public void sendResultToUI() {
        List<WanAndroidArticle> articles = TestUtils.createArticles(5);
        Resource<List<WanAndroidArticle>> testData = Resource.success(articles);
        mTestValue.setValue(testData);
        Observer<Resource<List<WanAndroidArticle>>> observer = mock(Observer.class);
        mViewModel.getArticles().observeForever(observer);
        verify(observer).onChanged(testData);
    }
}
