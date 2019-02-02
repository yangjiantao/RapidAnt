package io.jiantao.example.wanandroid.db.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;
import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/2/2
 */
public class ArticleTagsConverter {

    @TypeConverter
    public static List<WanAndroidArticle.TagsBean> toTagsList(String tags) {
        if (TextUtils.isEmpty(tags)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            Type tagsType = new TypeToken<List<WanAndroidArticle.TagsBean>>() {
            }.getType();
            return gson.fromJson(tags, tagsType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String toJsonString(List<WanAndroidArticle.TagsBean> tags) {
        try {
            Gson gson = new Gson();
            return gson.toJson(tags);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
