package io.jiantao.example.wanandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import io.jiantao.example.wanandroid.adapters.ArticleAdapter;

/**
 * 精选文章列表
 *
 * @author Created by jiantaoyang
 * @date 2018/12/30
 */
public class SelectedFragment extends Fragment {

    public static final String TAG = "SelectedFragment";
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
    }
}
