package com.lss.education_resources_service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigDes {

    String title() default "";

    String description() default "";


    String  type() default "string";

    boolean  require() default false;

}
