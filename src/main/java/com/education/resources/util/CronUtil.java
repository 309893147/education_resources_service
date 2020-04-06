package com.education.resources.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CronUtil {
    //"ss mm HH dd MM ? yyyy"
    private static final SimpleDateFormat sdf = new SimpleDateFormat("0 mm HH * * *");

    /***
     *  功能描述：日期转换cron表达式
     * @param date
     * @return
     */
    public static String formatDateByPattern(Date date) {
        String formatTimeStr = null;
        if (Objects.nonNull(date)) {
            formatTimeStr = sdf.format(date);
            System.out.println(formatTimeStr);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron, eg "0 07 10 15 1 ?"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date);
    }

    public static String getStr(String dateStr) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            //使用SimpleDateFormat的parse()方法生成Date
        Date date = null;
        String str = null;
        try {
            date = sf.parse(dateStr);
            System.out.println(date);
            str= getCron(date);
           return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) {
        //获得一个时间格式的字符串
        String dateStr = "10:21";
        getStr(dateStr);
    }

}
