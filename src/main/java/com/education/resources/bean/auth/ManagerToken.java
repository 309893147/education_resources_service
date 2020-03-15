package com.education.resources.bean.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
@AllArgsConstructor
public class ManagerToken implements AuthenticationToken {

    private String token;

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
