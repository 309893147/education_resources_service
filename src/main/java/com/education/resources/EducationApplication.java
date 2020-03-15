package com.education.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EntityScan(basePackages = "com.education.resources")
@EnableJpaRepositories(basePackages = "com.education.resources")
@EnableTransactionManagement
@SpringBootApplication
public class EducationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationApplication.class, args);
        System.out.println("启动成功。。。");
    }

    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
