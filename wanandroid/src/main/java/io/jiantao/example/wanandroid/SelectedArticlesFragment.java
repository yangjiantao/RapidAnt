package io.jiantao.example.wanandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.jiantao.example.wanandroid.adapters.ArticleAdapter;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.repo.Resource;
import io.jiantao.example.wanandroid.viewmodels.ArticleListViewModel;

/**
 * 精选文章列表
 *
 * @author Created by jiantaoyang
 * @date 2018/12/30
 */
public class SelectedArticlesFragment extends Fragment {

    public static final String TAG = SelectedArticlesFragment.class.getSimpleName();
    private RecyclerView mSelectedList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSelectedList = (RecyclerView) inflater.inflate(R.layout.fragment_article_selected, container, false);
        return mSelectedList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArticleAdapter adapter = new ArticleAdapter();
        mSelectedList.setAdapter(adapter);

        ArticleListViewModel viewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        viewModel.getArticles().observe(this, new Observer<Resource<List<WanAndroidArticle>>>() {
            @Override
            public void onChanged(Resource<List<WanAndroidArticle>> listResource) {
                Log.d(TAG, "listResource " + listResource);
                if (listResource != null && listResource.data != null) {
                    Log.d(TAG, "onChanged resource.status : " + listResource.status);
                    adapter.refreshArticles(listResource.data);
                }
            }
        });
    }
}
