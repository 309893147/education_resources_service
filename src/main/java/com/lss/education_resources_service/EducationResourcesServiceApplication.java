package com.lss.education_resources_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(basePackages = "com.lss.education_resources_service")
@EnableJpaRepositories(basePackages = "com.lss.education_resources_service")
@EnableSwagger2
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class EducationResourcesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationResourcesServiceApplication.class, args);
        System.out.println("启动成功。。。");
    }

}
