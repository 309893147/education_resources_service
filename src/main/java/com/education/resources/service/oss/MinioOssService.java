package com.education.resources.service.oss;

import com.education.resources.config.oss.MinioConfig;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

/**
 * @author longpeng
 * Minio OSS
 */
@Service
public class MinioOssService implements OssService<MinioConfig, MinioClient> {

    private MinioConfig minioConfig;

    private MinioClient minioClient;

    @Override
    public MinioConfig getConfig() {
        return minioConfig;
    }

    @Override
    public MinioClient getClient() {
        return minioClient;
    }

    @Override
    public void setConfig(MinioConfig config) {
        this.minioConfig = config;
        try {
            this.minioClient = new MinioClient(config.getEndpoint(), config.getAccessKey(), config.getSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFileToken(String fileName, Integer expiry) {
        String token = null;
        try {
            token = minioClient.presignedPutObject(minioConfig.getBucketName(), fileName, expiry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void uploadFile(String fileName, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(minioConfig.getBucketName(), fileName, inputStream, file.getSize(), null, null, file.getContentType());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileUrl(String fileName, Integer expiry) {
        String fileUrl = null;
        try {
            fileUrl = minioClient.presignedGetObject(minioConfig.getBucketName(), fileName, expiry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }


}
