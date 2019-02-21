package io.jiantao.example.wanandroid;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.jiantao.utils.android.NetworkUtils;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/2/21
 */
public class ArticleDetailActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_URL = "key_article_url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String articleLink = getIntent().getStringExtra(KEY_ARTICLE_URL);
        WebView mWebView = new WebView(this);
        setContentView(mWebView);
        // init websettings
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setAppCacheEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        if (NetworkUtils.isConnected(this)) {
            mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.loadUrl(articleLink);
    }
}
