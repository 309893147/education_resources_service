package com.education.resources.bean.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("com.education.resource.config")
public class PackageConfiguration {

    private String basePackage;
}
