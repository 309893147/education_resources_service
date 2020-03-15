package com.education.resources.bean.auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ManagerLoginVO {

    private String token;

    private List<Permission> permissions;

    private Manager  info;
}
