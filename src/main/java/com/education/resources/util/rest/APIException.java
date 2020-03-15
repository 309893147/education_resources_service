package com.education.resources.util.rest;

import lombok.Getter;

public class APIException  extends RuntimeException {

    @Getter
    private APIError apiError;

    public APIException(APIError apiError) {
        super(apiError.getMsg());
        this.apiError = apiError;
    }


}
