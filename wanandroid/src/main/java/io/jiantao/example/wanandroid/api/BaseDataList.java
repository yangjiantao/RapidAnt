package io.jiantao.example.wanandroid.api;

import java.util.List;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/1/29
 */
public class BaseDataList<T> {

    private DataObj<T> data;

    public static class DataObj<T>{
        private List<T> datas;

        public List<T> getDatas() {
            return datas;
        }
    }

    /**
     * @return datas from remote service
     */
    public List<T> getData() {
        return data.getDatas();
    }
}
