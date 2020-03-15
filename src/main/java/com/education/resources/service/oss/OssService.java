package com.education.resources.service.oss;

import org.springframework.web.multipart.MultipartFile;

public interface OssService<T,K> {

    /**
     * 基本配置
     * @return
     */
    T getConfig();

    /**
     * oss客户端
     * @return
     */
    K getClient();

    /**
     * 修改配置并初始化
     * @param config
     */
    void setConfig(T config);

    /**
     * 前端上传文件所需token
     * @param fileName
     * @param expiry
     * @return
     */
    String uploadFileToken(String fileName, Integer expiry);

    /**
     * 客户端上传文件
     * @param fileName
     * @param file
     */
    void uploadFile(String fileName, MultipartFile file);


    /**
     * 空间内文件访问路径
     * @param fileName
     * @param expiry
     * @return
     */
    String getFileUrl(String fileName, Integer expiry);





}
