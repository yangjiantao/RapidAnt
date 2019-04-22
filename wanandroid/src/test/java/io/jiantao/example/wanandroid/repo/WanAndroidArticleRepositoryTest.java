package io.jiantao.example.wanandroid.repo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.jiantao.example.wanandroid.ArticleDetailActivity;
import io.jiantao.example.wanandroid.api.ApiResponse;
import io.jiantao.example.wanandroid.api.BaseDataList;
import io.jiantao.example.wanandroid.api.WanAndroidService;
import io.jiantao.example.wanandroid.db.dao.ArticleDao;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.util.ApiUtil;
import io.jiantao.example.wanandroid.util.InstantAppExecutors;
import io.jiantao.example.wanandroid.util.TestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/4/20
 */
@RunWith(JUnit4.class)
public class WanAndroidArticleRepositoryTest {

    private WanAndroidArticleRepository mRepository;
    private ArticleDao mDao;
    private WanAndroidService mService;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        mDao = mock(ArticleDao.class);
        mService = mock(WanAndroidService.class);
        mRepository = new WanAndroidArticleRepository(new InstantAppExecutors(), mDao, mService);
    }

    @Test
    public void loadArticlesFromDb(){
        // 测试资源获取逻辑：repo.getSelectedArticles -> articleDao.getArticles
        mRepository.getSelectedArticles();
        verify(mDao).getArticles();
    }

    @Test
    public void loadArticlesFromServer(){
        MutableLiveData<List<WanAndroidArticle>> dbData = new MutableLiveData<>();
        List<WanAndroidArticle> articles = TestUtils.createArticles(5);
        // dbData onChanged 才会触发网络请求等后续逻辑
        dbData.setValue(articles);
        when(mDao.getArticles()).thenReturn(dbData);

        BaseDataList<WanAndroidArticle> dataList = new BaseDataList<>();
        LiveData<ApiResponse<BaseDataList<WanAndroidArticle>>> successCall = ApiUtil.successCall(dataList);
        when(mService.getSelectedArticles(0)).thenReturn(successCall);

        Observer<Resource<List<WanAndroidArticle>>> observer = mock(Observer.class);
        // 这里需要先添加观察者observer，否则addSource等逻辑不会观察其它数据源的变更事件。
        mRepository.getSelectedArticles().observeForever(observer);
//        mRepository.getSelectedArticles();

        verify(mService).getSelectedArticles(0);

    }

}
