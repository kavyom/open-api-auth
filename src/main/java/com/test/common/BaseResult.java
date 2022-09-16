package com.test.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by pzh on 2022/1/21.
 */
@Data
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 2368333494791570676L;

    private int code;
    private String msg;
    private T data;

    public BaseResult() {}

    private BaseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private BaseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResult<T> success() {
        return new BaseResult(0, "请求成功");
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult(0, "请求成功", data);
    }

    public static <T> BaseResult<T> error() {
        return new BaseResult(-1, "请求失败");
    }

    public static <T> BaseResult<T> error(int code, String msg) {
        return new BaseResult(code, msg);
    }

    public static <T> BaseResult<T> error(int code, String msg, T data) {
        return new BaseResult(code, msg, data);
    }

}
