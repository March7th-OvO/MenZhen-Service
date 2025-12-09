package com.oms.menzhenservice.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一 API 响应结果封装
 * @param <T> 数据载体类型
 */
@Data
public class Result<T> implements Serializable {
    private Integer code; // 200:成功, 400:失败, 500:错误
    private String message;
    private T data;

    // 私有构造，强制通过静态方法创建
    private Result() {}

    // 成功（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        return r;
    }

    // 成功（不带数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 失败
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    // 失败（默认 400）
    public static <T> Result<T> error(String msg) {
        return error(400, msg);
    }
}