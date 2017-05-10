package com.moive.sus.library.base.core.retofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by linksus on 5/2 0002.
 * 网络返回基地 支持泛型
 */

public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
