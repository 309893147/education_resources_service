package com.education.resources.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Meta {

    //是否可搜索，针对字段
    boolean  searchable() default false;

    //是否可显示在列表
    boolean  displayInList() default false;

    //显示的名称
    String displayName() default "";

    String[]  values() default {};

    //删除用的key
    String deleteKey() default "id";

    //是否可以插入，针对类
    boolean  insertable() default true;

    //删除选项
    DeleteType  delete() default DeleteType.DELETE;

    //编辑选项
    EditType  edit() default EditType.EDIT;


    //导出选项
    ExportType  export() default ExportType.NONE;

    //打印选项
    PrintType   print() default PrintType.NONE;

    boolean  sortable() default false;

    boolean   clickable() default false;



    int sort() default   -1;

    ContentType  type() default ContentType.TEXT;

    /**
     * 可选项，比如
     * options = {"是","否"},
     * options={"true:打开","false:关闭"}
     * options={"1:打开","2:关闭","3:禁用"}
     * @return
     */
    String[] options()  default {};


}
