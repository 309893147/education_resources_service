package com.lss.education_resources_service.controller.common;

import com.lss.education_resources_service.service.UploadService;
import com.lss.education_resources_service.util.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
@Api(tags = "上传工具类")
public class FileController {


    @Autowired
    UploadService uploadService;




    @PostMapping("/addPictureUrl")
    @ApiOperation(value = "获取上传路径", notes = "获取上传路径")
    public API<String> updateHeadUrl(MultipartFile file) {
        return API.ok(uploadService.getUploadFileDir(file));
    }

}
