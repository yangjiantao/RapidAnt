package io.jiantao.example.wanandroid.api;

import com.google.common.truth.Truth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import retrofit2.Response;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/4/22
 */
@RunWith(JUnit4.class)
public class ApiResponseTest {

    @Test
    public void exception(){
        Exception exception = new Exception("network exception");
        ApiResponse<String> apiResponse = ApiResponse.create(exception);
        Truth.assertThat(apiResponse.getBody()).isNull();
        Truth.assertThat(apiResponse.isSuccessful()).isFalse();
    }

    @Test
    public void success(){
        ApiResponse<String> apiResponse = ApiResponse.create(Response.success("success"));
        Truth.assertThat(apiResponse.getBody()).isNotEmpty();
        Truth.assertThat(apiResponse.isSuccessful()).isTrue();
    }
}
