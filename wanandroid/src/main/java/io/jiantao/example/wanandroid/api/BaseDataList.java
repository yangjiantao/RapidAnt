package io.jiantao.example.wanandroid.api;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/1/29
 */
public class BaseDataList<T> {

    @Nullable
    private DataObj<T> data;

    public static class DataObj<T> {
        private List<T> datas;

        public List<T> getDatas() {
            return datas;
        }
    }

    /**
     * @return dataList from remote service, or null
     */
    @Nullable
    public List<T> getData() {
        return data == null ? null : data.getDatas();
    }
}
