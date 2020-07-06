package com.rb.fs.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilForDate
{
    /**
     * 格式化日期
     *
     * @param dateStr 字符型日期
     * @param format 格式
     * @return 返回日期
     */
    public static Date parseDate(String dateStr, String format)
    {
        Date date = null;
        try
        {
            java.text.DateFormat df = new java.text.SimpleDateFormat(format);
            date = (Date)df.parse(dateStr);
        }
        catch(java.text.ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Date currentTime_2 = null;
        try {
            currentTime_2 = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime_2;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println( StringToDate("2019-11-12 00:00:00"));

    }



    /**
     * @param dateStr，默认格式为：yyyy/MM/dd
     * @return Date
     */
    public static Date parseDate(String dateStr)
    {
        return parseDate(dateStr, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 格式化输出日期
     *
     * @param date 日期
     * @param format 格式
     * @return 返回字符型日期
     */
    public static String formatDate(Date date, String format)
    {
        String result = "";
        try
        {
            if(date != null)
            {
                java.text.DateFormat df = new java.text.SimpleDateFormat(format);
                result = df.format(date);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化日期，默认格式：yyyy/MM/dd
     *
     * @param date 日期
     * @return 返回字符型日期
     */
    public static String format(Date date)
    {
        return formatDate(date, "yyyy/MM/dd");
    }

    /**
     * 返回年份
     *
     * @param date 日期
     * @return 返回年份
     */
    public static int getYear(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     *
     * @param date 日期
     * @return 返回日份
     */
    public static int getDay(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(java.util.Calendar.SECOND);
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期
     */
    public static String getDate(Date date)
    {
        return formatDate(date, "yyyy/MM/dd");
    }

    /**
     * 返回字符型时间，格式默认为：HH:mm:ss
     *
     * @param date 日期
     * @return 返回字符型时间
     */
    public static String getTime(Date date)
    {
        return formatDate(date, "HH:mm:ss");
    }

    /**
     * 返回字符型日期时间，格式默认为：yyyy/MM/dd HH:mm:ss
     *
     * @param date 日期
     * @return 返回字符型日期时间
     */
    public static String getDateAndTime(Date date)
    {
        return formatDate(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day)
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(getMillis(date) + ((long)day) * 24 * 3600 * 1000);
        return calendar.getTime();
    }

    /**
     * 日期相减，返回相隔天数
     *
     * @param date 日期
     * @param date1 日期
     * @return 返回相减后的天数
     */
    public static int diffDate(Date date, Date date1)
    {
        return (int)((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 取得指定月份的第一天
     *
     * @param strdate String
     * @return String
     */
    public static String getMonthBegin(String strdate)
    {
        Date date = parseDate(strdate);
        return formatDate(date, "yyyy/MM") + "/01";
    }

    /**
     * 取得指定月份的最后一天
     *
     * @param strdate String
     * @return String
     */
    public static String getMonthEnd(String strdate)
    {
        Date date = parseDate(getMonthBegin(strdate));
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.MONTH, 1);
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -1);
        return format(calendar.getTime());
    }

    /**
     * 得到当前年份
     * @return
     */
    public static int getCurrYear()
    {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }

    /**
     * 得到一年的总周数
     *
     * @param year
     * @return
     */
    public static int getWeekCountInYear(int year)
    {
        java.util.Calendar c = new java.util.GregorianCalendar();
        c.set(year, java.util.Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date)
    {
        java.util.Calendar c = new java.util.GregorianCalendar();
        c.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(java.util.Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week)
    {
        java.util.Calendar c = new java.util.GregorianCalendar();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
        c.set(java.util.Calendar.DATE, 1);
        java.util.Calendar cal = (java.util.GregorianCalendar)c.clone();
        cal.add(java.util.Calendar.DATE, week * 7);
        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week)
    {
        java.util.Calendar c = new java.util.GregorianCalendar();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
        c.set(java.util.Calendar.DATE, 1);
        java.util.Calendar cal = (java.util.GregorianCalendar)c.clone();
        cal.add(java.util.Calendar.DATE, week * 7);
        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirestDayOfMonth(int year, int month)
    {
        month = month - 1;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        int day = c.getActualMinimum(java.util.Calendar.DAY_OF_MONTH);
        c.set(java.util.Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 提到某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month)
    {
        month = month - 1;
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        int day = c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        c.set(java.util.Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return 所在周的第一天的日期
     */
    public static Date getFirstDayOfWeek(Date date)
    {
        java.util.Calendar c = new java.util.GregorianCalendar();
        c.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        c.setTime(date);
        c.set(java.util.Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return 所在周最后一天的日期
     */
    public static Date getLastDayOfWeek(Date date)
    {
        java.util.Calendar c = new java.util.GregorianCalendar();
        c.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        c.setTime(date);
        c.set(java.util.Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    //比较两个时间
    public static int compaTime(Date d1, Date d2){
      return   d1.compareTo(d2);
    }

    public static Date[] StringToDate(String str) throws ParseException {
    Date dateArray[]= new Date[2];
    String dates[]=str.split(" ");
    String startdate=dates[0]+" 00:00:00";
    String enddate=dates[0]+" 23:59:59";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date1 = simpleDateFormat.parse(startdate);
    Date date2 = simpleDateFormat.parse(enddate);
    dateArray[0]=date1;
    dateArray[1]=date2;
    return dateArray;
}

    /**
     * 字符串转为Date
     * @return
     */
    public static Date StringToDate1(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = simpleDateFormat.parse(str);
        return date1;
    }



}
