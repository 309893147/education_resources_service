package com.education.resources.bean.vo;

import com.education.resources.bean.entity.user.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserLoginVo implements Serializable {

    private String token;

    private User data;
}