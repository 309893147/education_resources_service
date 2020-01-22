package com.education.resources.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
//@ConfigurationProperties("media")
public class MediaConfig {
    private String mediaPath;
    private String mediaPrefix;
    private String managerPath;
    private String staticPath;
    private String templatePath;
}
