//package com.education.resources.service;
//
//import com.education.resources.annotation.ResourceFileDes;
//import com.education.resources.annotation.ResourceFileSaveMode;
//import com.education.resources.bean.entity.ResourceFile;
//import com.education.resources.datasource.repository.ResourceFileRepository;
//import com.github.wenhao.jpa.Specifications;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ResourceFileService {
//
//    @Autowired
//    private ResourceFileRepository filesRepository;
//
//
//    public ResourceFile saveFiles(ResourceFile files) {
//        return filesRepository.save(files);
//    }
//
//
////    @Transactional
////    public List<ResourceFile> saveFiles(Object object) {
////        if (object == null) {
////            return null;
////        }
////        Class clazz = object.getClass();
////        String simpleName = clazz.getSimpleName();
////        Field[] fields = clazz.getDeclaredFields();
////        Integer ownerId = getIdValue(clazz, object);
////        for (Field field : fields) {
////            if (!field.isAnnotationPresent(ResourceFileDes.class)) {
////                continue;
////            }
////            ResourceFileDes resourceFileDes = field.getAnnotation(ResourceFileDes.class);
////            ResourceFileSaveMode saveMode = resourceFileDes.saveMode();
////            field.setAccessible(true);
////            try {
////                Object item = field.get(object);
////                if (item == null) {
////                    continue;
////                }
////                String prefix = simpleName + "/" + field.getName();
////                String property = field.getName();
////                //处理保存模式
////                processSaveMode(saveMode,prefix,ownerId,property);
////                if (item instanceof ResourceFile) {
////                    ResourceFile renheFile = (ResourceFile) item;
////                    renheFile.setPrefix(prefix);
////                    doSaveFile(prefix, ownerId, property, renheFile);
////                } else if (item instanceof List) {
////                    doSaveFile(prefix, ownerId, property, (List<ResourceFile>) item);
////                }
////            } catch (IllegalAccessException e) {
////                e.printStackTrace();
////            }
////        }
////
////        return null;
////    }
////
////    private void processSaveMode(ResourceFileSaveMode saveMode,String prefix, Integer ownerId, String property) {
////        if (ResourceFileSaveMode.REPLACE.equals(saveMode)){
////            filesRepository.deleteRenheFileByTypeAndOwner(prefix,property,ownerId);
////        }
////    }
////
////    private Integer getIdValue(Class clazz, Object object) {
////
////        try {
////            Field idField = clazz.getDeclaredField("id");
////            idField.setAccessible(true);
////            try {
////                return (Integer) idField.get(object);
////            } catch (IllegalAccessException e) {
////                e.printStackTrace();
////            }
////        } catch (NoSuchFieldException e) {
////            if (!clazz.getSuperclass().equals(Object.class)) {
////                return getIdValue(clazz.getSuperclass(), object);
////            }
////        }
////        return 0;
////    }
////
////    private void doSaveFile(String prefix, Integer ownerId, String property, List<RenheFile> renheFile) {
////        renheFile.forEach(it -> {
////            doSaveFile(prefix, ownerId, property, it);
////        });
////    }
////
////    private void doSaveFile(String prefix, Integer ownerId, String property, RenheFile renheFile) {
////        renheFile.setPrefix(prefix);
////        renheFile.setOwnerId(ownerId);
////        renheFile.setProperty(property);
////        filesRepository.save(renheFile);
////    }
////
////    /**
////     * 获取文件
////     *
////     * @param prefix  文件前缀
////     * @param ownerId 文件拥有人
////     * @param clazz   文件容器类信息
////     * @param <T>     容器类型
////     * @return 文件容器
////     */
////    public <T> T getFiles(String prefix, Integer ownerId, Class<? extends T> clazz) {
////        List<RenheFile> files = filesRepository.findAll(Specifications.<RenheFile>and().eq("prefix", prefix).eq("ownerId", ownerId).build());
////        return getFiles(files, clazz);
////    }
////
////    public <T> T getFiles(Object object) {
////        if (object == null) {
////            return null;
////        }
////        Class clazz = object.getClass();
////        String simpleName = clazz.getSimpleName();
////        Field[] fields = clazz.getDeclaredFields();
////        Integer ownerId = getIdValue(clazz, object);
////        for (Field field : fields) {
////            if (!field.isAnnotationPresent(RenheFileDes.class)) {
////                continue;
////            }
////            String prefix = simpleName + "/" + field.getName();
////            String property = field.getName();
////            field.setAccessible(true);
////            List<RenheFile> items = filesRepository.findAll(Specifications.<RenheFile>and().eq("ownerId", ownerId).eq("prefix", prefix).eq("property", property).build(), Sort.by(Sort.Direction.ASC, "createTime"));
////            try {
////                if (field.getType().equals(RenheFile.class)) {
////                    field.set(object, items.size() > 0 ? items.get(items.size() - 1) : null);
////                } else if (field.getType().equals(List.class)) {
////                    field.set(object, items);
////                }
////            } catch (IllegalAccessException e) {
////                e.printStackTrace();
////            }
////
////        }
////        return (T) object;
////    }
////
////    /**
////     * 将文件列表转换成文件容器
////     *
////     * @param files 文件列表
////     * @param clazz 文件容器类信息
////     * @param <T>   容器类型
////     * @return 文件容器   null 如果类信息获取不到或者没有空构造器
////     */
////    public <T> T getFiles(List<RenheFile> files, Class<? extends T> clazz) {
////        try {
////            T item = clazz.newInstance();
////            if (files.size() == 0) {
////                return item;
////            }
////            List<RenheFile> extend = new ArrayList<>();
////            files.forEach(file -> {
////                try {
////                    Field field = clazz.getDeclaredField(file.getProperty());
////                    field.setAccessible(true);
////                    try {
////                        field.set(item, file);
////                    } catch (IllegalAccessException e) {
////                        e.printStackTrace();
////                    }
////                } catch (NoSuchFieldException e) {
////                    extend.add(file);
////                }
////            });
////            try {
////                Field extendField = clazz.getDeclaredField("extend");
////                extendField.setAccessible(true);
////                extendField.set(item, extend);
////            } catch (NoSuchFieldException e) {
////                e.printStackTrace();
////            }
////            return item;
////        } catch (InstantiationException | IllegalAccessException e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
//
//
//}
