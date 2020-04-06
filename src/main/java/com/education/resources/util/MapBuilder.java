package com.education.resources.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder<T,K> {

    private Map<T,K>  map;

    public MapBuilder() {
        this.map = new LinkedHashMap<>();
    }

    public MapBuilder<T,K>  put(T key,K value){
        map.put(key,value);
        return  this;
    }

    public Map<T,K> build(){
        return  this.map;
    }
}
