package com.lss.education_resources_service.service;

import com.lss.education_resources_service.exception.APIError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    @Value("${upload.url}")
    private String uploadFileDir;

    @Value("${system.base.url}")
    private String baseUrl;




    private String uploadMethod(MultipartFile mulfile) {
            String path = uploadFileDir + "/";
            String fileName = mulfile.getOriginalFilename()
                    .substring(mulfile.getOriginalFilename().lastIndexOf("/") + 1);
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            long time = System.currentTimeMillis();
            int i = fileName.lastIndexOf(".");
            String type = fileName.substring(i,fileName.length());
            String realPath = path + time +type;
            try {
                mulfile.transferTo(new File(realPath));
                return  baseUrl+"/upload/" + time+type;
            } catch (IOException e) {
                e.printStackTrace();
                APIError.e(400,"上传错误");
            }

        return  null;
    }

    public String getUploadFileDir(MultipartFile multipartFile) {
        return uploadMethod(multipartFile);
    }
}
