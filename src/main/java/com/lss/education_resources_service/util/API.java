package com.lss.education_resources_service.util;

import com.lss.education_resources_service.exception.APIError;
import lombok.Data;

@Data
public class API<T> {
    private Integer status;
    private T date;
    private String msg;

    public static <T> API<T> ok(T t) {
        API<T> data = new API<T>();
        data.setDate(t);
        data.setStatus(200);
        data.setMsg("success");
        return data;
    }

    public static API e(Integer status, String msg) {
        API api = new API();
        api.setMsg(msg);
        api.setStatus(status);
        return api;
    }

    public static API ok(Integer status, String msg) {
        API api = new API();
        api.setMsg(msg);
        api.setStatus(status);
        return api;
    }

    public static API e(String msg) {
        return e(400, msg);
    }

    private static <T> String encrypt(T t) {
        return "";
    }

    public static API ok() {
        return e(200, "success");
    }


}
