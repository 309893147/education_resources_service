package com.education.resources.bean.config;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class ConfigProperty extends Config {


    public int intValue(){

        return Integer.parseInt(getValue());
    }

    public float floatValue(){

        return Float.parseFloat(getValue());
    }

    public long longValue(){

        return Long.parseLong(getValue());
    }

    public double doubleValue(){
        return Double.parseDouble(getValue());
    }

    public String stringValue(){
        return getValue();
    }

    public BigDecimal bigDecimalValue(){
        return new BigDecimal(getValue());
    }

    public Timestamp  expireValue(TimeUnit timeUnit){
        return new Timestamp(System.currentTimeMillis()+timeUnit.toMillis(longValue()));
    }


    @Override
    public String getValue() {
        if (StringUtils.isEmpty(super.getValue())){
            return getDefaultValue();
        }
        return super.getValue();
    }
}
