//package com.lss.education_resources_service.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.util.DigestUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.ImageIO;
//import javax.imageio.stream.ImageOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.CopyOption;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class MediaService {
//    private MediaConfig mMediaConfig;
//    private Path mImageRoot;
//
//    public MediaService(MediaConfig config) {
//        this.mMediaConfig = config;
//        this.mImageRoot = Paths.get(this.mMediaConfig.getMediaPath());
//    }
//
//    private void resolvePrefix(String prefix) {
//        Path path = this.mImageRoot.resolve(prefix);
//        File pathFile = path.toFile();
//        if (!pathFile.exists()) {
//            pathFile.mkdirs();
//        }
//
//    }
//
//    public String uploadFile(String prefix, MultipartFile multipartFile) {
//        if (multipartFile.isEmpty()) {
//            return null;
//        } else {
//            String fileName = multipartFile.getOriginalFilename();
//            if (fileName == null) {
//                return null;
//            } else {
//                String saveFileName = DigestUtils.md5DigestAsHex((fileName + System.currentTimeMillis()).getBytes()) + '.' + multipartFile.getContentType().split("/")[1];
//
//                try {
//                    return this.saveFile(prefix, saveFileName, multipartFile.getInputStream());
//                } catch (IOException var6) {
//                    throw new RuntimeException(var6.getMessage());
//                }
//            }
//        }
//    }
//
//    public String saveFile(String prefix, String fileName, InputStream inputStream) {
//        this.resolvePrefix(prefix);
//        fileName = prefix + File.separator + fileName;
//        Path file = this.mImageRoot.resolve(fileName);
//        if (file.toFile().exists()) {
//            return this.mMediaConfig.getMediaPrefix() + fileName;
//        } else {
//            try {
//                Files.copy(inputStream, file, new CopyOption[0]);
//                return this.mMediaConfig.getMediaPrefix() + fileName;
//            } catch (IOException var6) {
//                throw new RuntimeException(var6.getMessage());
//            }
//        }
//    }
//
//    public String saveQRImage(String name, byte[] image) {
//        String prefix = "qr";
//        this.resolvePrefix(prefix);
//        String fileName = prefix + File.separator + DigestUtils.md5DigestAsHex((name + System.currentTimeMillis()).getBytes()) + ".png";
//        File file = this.mImageRoot.resolve(fileName).toFile();
//        if (file.exists()) {
//            return this.mMediaConfig.getMediaPrefix() + fileName;
//        } else {
//            try {
//                ImageOutputStream data = ImageIO.createImageOutputStream(file);
//                data.write(image);
//            } catch (IOException var7) {
//                var7.printStackTrace();
//            }
//
//            return this.mMediaConfig.getMediaPrefix() + fileName;
//        }
//    }
//}
