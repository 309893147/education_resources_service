package com.education.resources.annotation;


import com.education.resources.bean.config.ConfigContentType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigDes {

    String title() default "";

    String description() default "";


    ConfigContentType type() default ConfigContentType.TEXT;

    String  test() default "";

    String  defaultValue();

    boolean  require() default false;



}
