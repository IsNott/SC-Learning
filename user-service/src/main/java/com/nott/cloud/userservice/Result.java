package com.nott.cloud.userservice;

import lombok.Data;

/**
 * @author Nott
 * @Date 2022/7/11
 */

@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T obj;

    public Result(Integer code, String msg, T obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public Result(T obj) {
        this.code = 200;
        this.msg = "操作成功";
        this.obj = obj;
    }

    public Result() {
        this.code = 200;
        this.msg = "操作成功";
    }

    public static Result data(Object obj) {
        return new Result(obj);
    }

}
