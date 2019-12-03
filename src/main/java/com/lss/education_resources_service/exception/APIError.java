package com.lss.education_resources_service.exception;

import com.alibaba.fastjson.JSONObject;
import com.lss.education_resources_service.util.LoggerUtils;
import lombok.Data;


@Data
public class APIError {
    private String msg;

    private Integer status;

    public static void e(String msg) {
        e(400, msg);
    }
    public static APIError error(Integer code, String msg) {
        APIError api = new APIError();
        api.setMsg(msg);
        api.setStatus(code);
        return api;
    }

    public static void NEED_LOGIN(){
        e(401,"需求登录");
    }

    public static void NOTEMPTY(){
        e(401,"不能为空");
    }


    public static void e(Integer code, String msg) {
        APIError error = new APIError();
        error.setStatus(code);
        error.setMsg(msg);
        LoggerUtils.log.info(JSONObject.toJSONString(error));
        throw new APIException(error);
    }


}
