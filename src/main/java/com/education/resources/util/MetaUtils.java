package com.education.resources.util;

import com.education.resources.annotation.Meta;
import com.education.resources.bean.entity.meta.MetaData;
//import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MetaUtils {

    /**
     * 获取类的元信息
     *
     * @param clazz
     * @return
     */
    public static MetaData getMeta(Class clazz) {
        MetaData.MetaDataBuilder builder = MetaData.builder();
        Field[] fields = clazz.getDeclaredFields();
        if (clazz.isAnnotationPresent(Meta.class)) {
            Meta meta = (Meta) clazz.getDeclaredAnnotation(Meta.class);
            builder.insertable(meta.insertable());
            builder.edit(meta.edit());
            builder.delete(meta.delete());
            builder.print(meta.print());
            builder.edit(meta.edit());
            builder.export(meta.export());
            builder.deleteKey(meta.deleteKey());
            builder.name(meta.displayName());
            builder.clickable(meta.clickable());
        }
        builder.key(clazz.getSimpleName());
        List<MetaData> fieldsData = new ArrayList<>();
        int index = 1;
        for (Field field : fields) {
            fieldsData.add(getMetaFromField(index, field));
            index += 1;
        }
        if (!clazz.getSuperclass().equals(Object.class)) {
            Class superClazz = clazz.getSuperclass();
            Field[] superField = superClazz.getDeclaredFields();
            for (Field field : superField) {
                fieldsData.add(getMetaFromField(index, field));
                index += 1;
            }
        }
        fieldsData.sort(new Comparator<MetaData>() {
            @Override
            public int compare(MetaData o1, MetaData o2) {
                return o1.getSort() - o2.getSort();
            }
        });
        builder.subs(fieldsData);
        return builder.build();
    }


    //从属性中获取元信息
    public static MetaData getMetaFromField(int index, Field field) {
        MetaData.MetaDataBuilder fieldsBuilder = MetaData.builder();
        fieldsBuilder.key(field.getName());
//        if (field.isAnnotationPresent(ApiModelProperty.class)) {
//            ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
//            fieldsBuilder.name(property.value());
//        }
        if (field.isAnnotationPresent(Meta.class)) {
            Meta meta = field.getAnnotation(Meta.class);
            if (!StringUtils.isEmpty(meta.displayName())) {
                fieldsBuilder.name(meta.displayName());
            }
            fieldsBuilder.displayInList(meta.displayInList());
            fieldsBuilder.searchable(meta.searchable());
            fieldsBuilder.type(meta.type());
            fieldsBuilder.edit(meta.edit());
            fieldsBuilder.delete(meta.delete());
            fieldsBuilder.print(meta.print());
            fieldsBuilder.edit(meta.edit());
            fieldsBuilder.export(meta.export());
            fieldsBuilder.clickable(meta.clickable());
            Class<?> type = field.getType();
            if (type.isEnum()) {
                fieldsBuilder.searchOption(getSearchOptionFromEnum(type));
            } else {
                fieldsBuilder.searchOption(getOptions(type, meta));
            }
            fieldsBuilder.sort(index);
            if (meta.sort() > -1) {
                fieldsBuilder.sort(meta.sort());
            }
            fieldsBuilder.sortable(meta.sortable());
        }
        return fieldsBuilder.build();
    }

    public static List<MetaData.SearchOption> getOptions(Class<?> type, Meta meta) {
        if (meta.options().length > 0) {
            List<MetaData.SearchOption> options = new ArrayList<>();
            for (int i = 0; i < meta.options().length; i++) {
                String item = meta.options()[i];
                MetaData.SearchOption.SearchOptionBuilder optionBuilder = MetaData.SearchOption.builder();
                if (item.contains(":")) {
                    String[] a = item.split(":");
                    optionBuilder.key(a[0]);
                    optionBuilder.name(a[1]);
                } else {
                    optionBuilder.key(i == 0 ? "true" : "false");
                    optionBuilder.name(item);
                }
                options.add(optionBuilder.build());
            }
            return options;

        } else if (type.equals(Boolean.class)) {
            List<MetaData.SearchOption> options = new ArrayList<>();
            options.add(MetaData.SearchOption.builder().key("true").name("是").build());
            options.add(MetaData.SearchOption.builder().key("false").name("否").build());
            return options;
        }
        return null;
    }

    //从枚举中获取元信息
    public static List<MetaData.SearchOption> getSearchOptionFromEnum(Class<?> type) {
        List<MetaData.SearchOption> options = new ArrayList<>();
        Object[] enums = type.getEnumConstants();
        for (Object object : enums) {
            try {
                MetaData.SearchOption.SearchOptionBuilder optionBuilder = MetaData.SearchOption.builder();
                Field enumField = type.getField(((Enum) object).name());
                optionBuilder.key(object.toString());
//                if (enumField.isAnnotationPresent(ApiModelProperty.class)) {
//                    ApiModelProperty prop = enumField.getAnnotation(ApiModelProperty.class);
//                    optionBuilder.name(prop.value());
//                }
                options.add(optionBuilder.build());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return options;
    }

    public static List<MetaData> getSearchableMeta(Class clazz) {
        return getMeta(clazz).getSubs().stream().filter(MetaData::isSearchable).collect(Collectors.toList());
    }


//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        MetaData meta = getMeta(AppVersion.class);
//
//        System.out.println((System.currentTimeMillis() - start));
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            System.out.println(objectMapper.writeValueAsString(meta));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }


}
