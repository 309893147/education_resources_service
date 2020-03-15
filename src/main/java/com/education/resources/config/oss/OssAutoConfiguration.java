package com.education.resources.config.oss;


import com.education.resources.service.oss.MinioOssService;
import com.education.resources.service.oss.OssService;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioConfig.class)
public class OssAutoConfiguration {


    @Bean(value = "MinioOssService")
    @ConditionalOnProperty(prefix = "oss.minio.config", value = "initialization",havingValue = "true")
    public OssService<MinioConfig, MinioClient> minioOssService(MinioConfig minioConfig){
        OssService<MinioConfig, MinioClient> minioOssService=new MinioOssService();
        minioOssService.setConfig(minioConfig);
        return minioOssService;
    }



}
