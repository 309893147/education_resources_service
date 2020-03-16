package com.education.resources.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceFileDes {
    ResourceFileSaveMode  saveMode() default ResourceFileSaveMode.REPLACE;

    String prefix() default "";

    String[] keys() default {};
}
