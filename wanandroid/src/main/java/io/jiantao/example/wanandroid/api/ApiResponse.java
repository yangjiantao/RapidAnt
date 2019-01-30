package io.jiantao.example.wanandroid.api;

import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import retrofit2.Call;
import retrofit2.Response;

/**
 * ApiResponse, successResponse, failed, empty
 *
 * @author Created by jiantaoyang
 * @date 2019/1/24
 */
public class ApiResponse<T> {

    /**
     * error response
     */
    public static final int TYPE_ERROR = 1;
    /**
     * success response
     */
    public static final int TYPE_SUCCESS = 2;
    /**
     * empty data
     */
    public static final int TYPE_EMPTY = 3;

    @Nullable
    private final T body;
    @Nullable
    private String errorMessage;
    private int code;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_ERROR, TYPE_EMPTY, TYPE_SUCCESS})
    public @interface ResponseType {
    }

    private @ResponseType
    int mType;

    /**
     * constructor
     *
     * @param throwable
     */
    private ApiResponse(Throwable throwable) {
        code = 500;
        body = null;
        errorMessage = throwable == null ? null : throwable.getMessage();
    }

    /**
     * @param response
     */
    private ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            final int noContent = 204;
            if (body == null || response.code() == noContent) {
                mType = TYPE_EMPTY;
                errorMessage = "no content.";
            } else {
                mType = TYPE_SUCCESS;
            }

        } else {
            body = null;
            String errMsg = null;
            try {
                errMsg = response.errorBody().string();
                errMsg = TextUtils.isEmpty(errMsg) ? response.message() : errMsg;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                errorMessage = TextUtils.isEmpty(errMsg) ? "unknown error" : errMsg;
            }
            mType = TYPE_ERROR;
        }
    }

    /**
     * @return success or failed
     */
    public boolean isSuccessful(){
        return code >= 200 && code < 300;
    }

    @Nullable
    public T getBody() {
        return body;
    }

    /**
     * @return error message
     */
    public String getErrorMessage(){
        return errorMessage;
    }

    public static <R> ApiResponse<R> create(Response<R> response) {
        return new ApiResponse<>(response);
    }

    public static <R> ApiResponse<R> create(Throwable throwable) {
        return new ApiResponse<>(throwable);
    }
}
