package com.education.resources.framework.shiro;


import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;


@Data
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1282057025599826155L;

    private String token;

    private String platform;


    public JwtToken(String token, String platform) {
        this.token = token;
        this.platform = platform;
    }


    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }


}
