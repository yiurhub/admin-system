package com.yiur.admin.utils;

import org.func.spring.boot.utils.StringUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * corn表达式工具类
 * @author Yiur
 */
public final class CornUtil {

    /**
     * 根据date解析corn表达式
     * @param date 时间日期
     * @return String
     */
    public static String corn(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) == 12 ? 1 : calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        return String.format("%d %d %d %d %d ? %d", seconds, minutes, hours, day, month, year);
    }

}
