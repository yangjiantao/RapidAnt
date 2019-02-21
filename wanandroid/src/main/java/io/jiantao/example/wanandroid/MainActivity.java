package io.jiantao.example.wanandroid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2018/12/30
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SelectedArticlesFragment fragment = new SelectedArticlesFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, SelectedArticlesFragment.TAG).commit();

    }
}
