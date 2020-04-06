package com.education.resources.controller.common;

import com.education.resources.annotation.AnonUrl;
import com.education.resources.config.oss.MinioConfig;
import com.education.resources.service.oss.OssService;
import com.education.resources.util.rest.API;
import io.minio.MinioClient;
//import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
//@Api(tags = "文件上传接口")
@RequestMapping("/api/upload")
public class UploadAPI {

    String path="http://49.234.218.87:9000/education/";

    @Resource(name = "MinioOssService")
    private OssService<MinioConfig, MinioClient> minioOssService;


    @AnonUrl
    @PostMapping
    public API<String> uploadFile(MultipartFile file){

        String fileName = getFileName("files", file);

         minioOssService.uploadFile(fileName,file);

        return API.ok(path+fileName);
    }

    private String getFileName(String prefix,MultipartFile file){
        String[] strArray = file.getOriginalFilename().split("\\.");
        String fileExtension = strArray[strArray.length-1];
        String fileSaveName= System.nanoTime()+"."+fileExtension;
        return prefix+"/"+getDatePath(fileSaveName);
    }


    private String getDatePath(String fileName){
        Date nowTime = new Date();
        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd/HH");
        return time.format(nowTime)+"/"+fileName;
    }




}
