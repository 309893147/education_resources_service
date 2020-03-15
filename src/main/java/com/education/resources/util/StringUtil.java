package com.education.resources.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * transform comma separated string to int array
     * @param ids
     * @return Integer array
     */
    public static Integer[]  toIntArray(String ids){
        String[] idStringArray = ids.split(",");
        Integer[] idArray = new Integer[idStringArray.length];
        for (int i = 0; i < idStringArray.length; i++) {
            idArray[i] = Integer.parseInt(idStringArray[i]);
        }
        return idArray;
    }

    public static<T> List<T> toDeleteBatch(String ids, Class<T> clazz){
        Integer[] array = toIntArray(ids);
        List<T> list = new ArrayList<>();
        for (Integer id: array){
            try {
                T instance = clazz.newInstance();
                Field idField = instance.getClass().getSuperclass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(instance,id);
                list.add(instance);
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
