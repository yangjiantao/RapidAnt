package io.jiantao.example.wanandroid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2018/12/30
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment= getSupportFragmentManager().findFragmentByTag(SelectedArticlesFragment.TAG);
        if (fragment == null) {
            fragment = new SelectedArticlesFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment, SelectedArticlesFragment.TAG).commit();

    }
}
