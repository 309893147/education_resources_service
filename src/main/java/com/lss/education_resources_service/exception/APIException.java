package com.lss.education_resources_service.exception;


import lombok.Getter;

@Getter
public class APIException extends RuntimeException {

    private APIError apiError;

    public APIException(APIError apiError) {
        super(apiError.getMsg());
        this.apiError = apiError;

    }


}
