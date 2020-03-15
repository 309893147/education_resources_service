package com.education.resources.util.rest;


import lombok.Data;

@Data
public class API<T> {

    private Integer code;
    private T data;
    private String msg;


    public static <T> API<T> ok(T t) {
        API<T> data = new API<T>();
        data.setData(t);
        data.setCode(200);
        data.setMsg("success");
        return data;
    }

    public static API e(Integer code, String msg) {
        API api = new API();
        api.setMsg(msg);
        api.setCode(code);
        return api;
    }

    public static API ok(Integer code, String msg, String data) {
        API api = new API();
        api.setMsg(msg);
        api.setCode(code);
        api.setData(data);
        return api;
    }

    public static API e(String msg) {
        return e(400, msg);
    }

    public static API ok() {
        return e(204, "success");
    }
}
