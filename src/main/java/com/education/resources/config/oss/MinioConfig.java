package com.education.resources.config.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ApiModel(value = "Minio配置")
@ConfigurationProperties(prefix = "oss.minio.config")
public class MinioConfig {

    @ApiModelProperty(value = "URL，域名，IPv4地址或IPv6地址")
    private String endpoint;

    @ApiModelProperty(value = "是否初始化")
    private boolean initialization=false;

    @ApiModelProperty(value = "公钥")
    private String accessKey;

    @ApiModelProperty(value = "私钥")
    private String secretKey;

    @ApiModelProperty(value = "空间名称")
    private String bucketName;

    public MinioConfig() {

    }
    public MinioConfig(String endpoint, String accessKey, String secretKey, String bucketName) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }
}
