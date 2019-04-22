/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jiantao.example.wanandroid.util;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.jiantao.example.wanandroid.api.ApiResponse;
import retrofit2.Response;

public class ApiUtil {
    public static <T> LiveData<ApiResponse<T>> successCall(T data) {
        return createCall(Response.success(data));
    }

    public static <T> LiveData<ApiResponse<T>> createCall(Response<T> response) {
        MutableLiveData<ApiResponse<T>> data = new MutableLiveData<>();
        data.setValue(ApiResponse.create(response));
        return data;
    }
}
