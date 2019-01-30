package io.jiantao.example.wanandroid.api;

import androidx.lifecycle.LiveData;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * description 玩Android 首页精选文章列表api
 *
 * @author Created by jiantaoyang
 * @date 2019/1/8
 */
public interface WanAndroidService {

    /**
     * http://www.wanandroid.com/article/list/0/json
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    LiveData<ApiResponse<BaseDataList<WanAndroidArticle>>> getSelectedArticles(@Path("page") int page);
}
