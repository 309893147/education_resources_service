package com.education.resources.util.rest;


import lombok.Data;

@Data
public class APIError {

    private String msg;

    private Integer code;

    public static void e(String msg){
        e(400,msg);
    }

    public static void NEED_LOGIN(){
        e(401,"需要登录");
    }

    public static void INVALID_REQ(){
        e(400,"参数错误");
    }
    public static void CONFLICT(){
        e(409,"请勿重复操作");
    }

    public static void FORBIDDEN(){
        e(403,"没有权限");
    }

    public static void NOT_FOUND(){
        e(404,"没有找到");
    }

    public static void e(Integer code, String msg){
        APIError error = new APIError();
        error.setCode(code);
        error.setMsg(msg);
        throw new APIException(error);
    }
}
