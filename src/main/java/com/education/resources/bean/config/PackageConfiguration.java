package com.education.resources.bean.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
@ConfigurationProperties("com.education.resource")
public class PackageConfiguration {

    private String basePackage;
}
