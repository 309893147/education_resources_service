package com.education.resources.util.jpa;

import com.education.resources.bean.entity.meta.MetaData;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.from.TimestampFilterItem;
import com.education.resources.util.MetaUtils;
import com.education.resources.util.rest.APIError;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SpecificationUtil {


    public static <T>  PredicateBuilder<T> expire(){
        return expire("expire");
    }


    public static <T>  PredicateBuilder<T> expire(String key){
        return Specifications.<T>and().gt(key,new Timestamp(System.currentTimeMillis()));
    }

    public static <T> PredicateBuilder<T> filter(Object object){
        return filter(object,null);
    }
    /**
     * 通过meta data 构造搜索条件
     * @param object
     */
    public static <T> PredicateBuilder<T> filter(Object object, PageForm pageForm){
        Class<?> clazz = object.getClass();
        PredicateBuilder<T> spec = Specifications.<T>and();
        spec.eq("presenceStatus",1);
        List<MetaData> meta = MetaUtils.getSearchableMeta(clazz);
        meta.forEach(it ->{
            try {
                if ("createTime".equals(it.getKey())){
                    return;
                }
                Field field = clazz.getDeclaredField(it.getKey());
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null){
                    return;
                }
                if (field.getType().equals(String.class)){
                    spec.like(!StringUtils.isEmpty((String) value),it.getKey(),buildLike((String) value));
                }else {
                    spec.eq(it.getKey(),value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        if (pageForm != null){
            if(!StringUtils.isEmpty(pageForm.getTimes())){
                try {
                    parseTimestampList(pageForm.getTimes()).forEach(item -> {
                        spec.between(item.getName(),new Timestamp(item.getStart()),new Timestamp(item.getEnd()));
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    APIError.e(400,"格式错误！正确格式为：name,start,end;name,start,end");
                }
            }
        }
        return spec;
    }


    public static String buildLike(String value){
        return String.format("%%%s%%",value);
    }


    public static List<TimestampFilterItem> parseTimestampList(String times){
        String[] timeArr = times.split(";");
        List<TimestampFilterItem> timestampFilterItems=new ArrayList<>();
        for(String time:timeArr){
            timestampFilterItems.add(parseTimestampFilterItem(time));
        }
        return timestampFilterItems;
    }

    public static TimestampFilterItem parseTimestampFilterItem(String time){
        String[] split = time.split(",");
        return TimestampFilterItem.builder().name(split[0]).start(Long.parseLong(split[1])).end(Long.parseLong(split[2])).build();
    }



}
