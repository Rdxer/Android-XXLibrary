package com.rdxer.xxlibrary.OtherUtils;

import android.util.Pair;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by LXF on 16/6/2.
 */

public class DateUtils {
    private final static String[] CN_Digits = {"〇", "一", "二", "三", "四", "五",
            "六", "七", "八", "九", "十"};

    public static final long daySpan = 24 * 60 * 60 * 1000;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
    /**
     * yyyy.MM.dd
     */
    public static final String DATE_FORMAT_DOT = "yyyy.MM.dd";
    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_NO_MINUS = "yyyyMMdd";
    /**
     * yyMMdd
     */
    public static final String DATE_FORMAT_NO_MINUS_SHORT = "yyMMdd";
    /**
     * yyyy-MM
     */
    public static final String MONTH_FORMAT_NORMAL = "yyyy-MM";
    /**
     * MM-dd
     */
    public static final String MONTH_DAY_FORMAT = "MM-dd";
    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    /**
     * yyyyMM
     */
    public static final String MONTH_FORMAT = "yyyyMM";
    /**
     * yyyy.MM
     */
    public static final String MONTH_FORMAT_DOT = "yyyy.MM";
    /**
     * yyyyMMddHHmm
     */
    public static final String DATE_FORMAT_MINUTE = "yyyyMMddHHmm";
    /**
     * yyyyMMddHHmmss
     */
    private static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
    /**
     * MM/dd/yyyy HH:mm:ss
     **/
    public static final String MONTH_DAY_YEAR_HOUR_MINUTE = "MM/dd/yyyy HH:mm:ss";

    /**
     * 判断参数year、month、day能否组成一个合法的日期。
     *
     * @param month 月份，合法月份范围是 1-12
     * @param day   日数
     * @param year  年份，必须大于1900
     * @return
     */
    public static boolean isDate(int month, int day, int year) {
        if (year < 1900) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }

        // 判断年份是否为闰年
        @SuppressWarnings("unused")
        boolean leapYear = isLeapYear(year);
        // 获得该年该月的最大日期
        int maxD = getMaxDay(year, month);
        if (day > maxD) {
            return false;
        }

        return true;
    }

    /**
     * 给定一个年份和月份，可以得到该月的最大日期。 例如 2009年1月，最大日期为31。
     *
     * @param year  年份，必须大于1900
     * @param month 月份，合法月份范围是 1-12
     * @return
     */
    public static int getMaxDay(int year, int month) {
        if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 31;
    }

    /**
     * 判断年份是否为闰年。
     *
     * @param year 年份，必须大于1900
     * @return
     */
    public static boolean isLeapYear(int year) {
        boolean leapYear = ((year % 400) == 0);
        if (!leapYear) {
            leapYear = ((year % 4) == 0) && ((year % 100) != 0);
        }
        return leapYear;
    }


    public static String getCurrentStringTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String getCurrentTimeSecond() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = format.format(date);
        return formatDate;
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式串转换为日期
     *
     * @param formatDate yyyy-MM-dd HH:mm:ss 格式日期
     * @return Date日期
     */
    public static Date paseDate(String formatDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentTimeMilliSecond() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String getCurrentMonth() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String formatDate = format.format(date);
        return formatDate;
    }

    /**
     * 获取当前日期（格式为20110802）
     *
     * @return
     */
    public static String getCurrentDay() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String formatDate = format.format(date);
        return formatDate;
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式，例如：yyyy-MM-dd
     * @param count  增加或减少的天数
     * @return
     */
    public static String getCurrentDate(String format, Integer count) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, count);//增加或减少的天数
        String currentDate = df.format(cal.getTime());
        return currentDate;
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static String getCurrentDateTimestamp(String format, Integer count) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, count);//增加或减少的天数
        String currentDate = df.format(cal.getTime());
        return currentDate;
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static long getCurrentDateTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的时间戳字符串
     *
     * @return
     */
    public static String getCurrentDateTimestampString() {
        return getCurrentStringTime()+"";
    }

}
