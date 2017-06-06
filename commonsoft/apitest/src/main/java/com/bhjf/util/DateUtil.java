package com.bhjf.util;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_SHORT_FORMAT = "yyyyMMdd";
    public static final String DATE_SLASH_FORMAT = "yyyy/MM/dd";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_SHORT_FORMAT = "yyyyMMddHHmmss";
    public static final String TIME_LONG_FORMAT = "yyyyMMddHHmmssSSS";

    protected static Logger logger = Logger.getLogger(DateUtil.class);


    private static Long getTimeValue(Object time) {
        Long value;

        if (time instanceof Double) {
            value = ((Double) time).longValue();
        } else if (time instanceof Integer) {
            value = ((Integer) time).longValue();
        } else if (time instanceof BigInteger) {
            value = ((BigInteger) time).longValue();
        } else {
            value = (Long) time;
        }

        return value;
    }

    /**
     * Date --> String(yyyy-MM-dd)
     *
     * @param date 日期对象
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateFormat(Date date) {
        if (date == null)
            return "";

        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    /**
     * Double/Integer/Long --> String(yyyy-MM-dd)
     *
     * @param time 时间戳
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateFormat(Object time) {
        if (time == null)
            return "";

        Long value = getTimeValue(time);
        if (value.toString().length() == 10) {
            value = value * 1000;
        }

        return new SimpleDateFormat(DATE_FORMAT).format(new Date(value));
    }

    /**
     * Double/Integer/Long --> String(yyyyMMdd)
     *
     * @param time 时间戳
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateShortFormat(Object time) {
        if (time == null)
            return "";

        Long value = getTimeValue(time);
        if (value.toString().length() == 10) {
            value = value * 1000;
        }

        return new SimpleDateFormat(DATE_SHORT_FORMAT).format(new Date(value));
    }

    /**
     * Double/Integer/Long --> String(yyyy/MM/dd)
     *
     * @param time 时间戳
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateFormatSlash(Object time) {
        if (time == null)
            return "";

        Long value = getTimeValue(time);
        if (value.toString().length() == 10) {
            value = value * 1000;
        }

        return new SimpleDateFormat(DATE_SLASH_FORMAT).format(new Date(value));
    }

    /**
     * Double/Integer/Long --> String(yyyy-MM-dd)
     *
     * @param time 时间戳
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateFormatStr(Object time) {
        if (time == null)
            return "";

        Long value = getTimeValue(time);
        if (value.toString().length() == 10) {
            value = value * 1000;
        }

        return new SimpleDateFormat(DATE_FORMAT).format(new Date(value));
    }

    /**
     * Date --> String(yyyy-MM-dd HH:mm:ss)
     *
     * @param date 时间对象
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateTimeFormat(Date date) {
        if (date == null)
            return "";

        return new SimpleDateFormat(TIME_FORMAT).format(date);
    }

    /**
     * Double/Integer/Long --> String(yyyy-MM-dd HH:mm:ss)
     *
     * @param time 时间对象
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unused")
    public static String dateTimeFormat(Object time) {
        if (time == null)
            return "";

        Long value = getTimeValue(time);
        if (value.toString().length() == 10) {
            value = value * 1000;
        }

        return new SimpleDateFormat(TIME_FORMAT).format(new Date(value));
    }

    /**
     * Long --> Date
     *
     * @param timestamp 时间戳
     * @return 日期对象
     */
    @SuppressWarnings("unused")
    public static Date dateParse(Long timestamp) {
        if (timestamp == null)
            return null;

        Date newDate = new Date(timestamp * 1000);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * String(YYYY-MM-DD) --> Date
     *
     * @param date 日期字符串
     * @return 日期对象
     */
    @SuppressWarnings("unused")
    public static Date dateParse(String date) {
        if (StringUtil.isBlank(date)) {
            return null;
        }

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            logger.error("dateParse", e);
        }

        return null;
    }

    /**
     * String(YYYY-MM-DD HH:II:SS) --> Date
     *
     * @param date 日期字符串
     * @return 日期对象
     */
    @SuppressWarnings("unused")
    public static Date timeParse(String date) {
        if (StringUtil.isBlank(date)) {
            return null;
        }

        try {
            return new SimpleDateFormat(TIME_FORMAT).parse(date);
        } catch (ParseException e) {
            logger.error("timeParse", e);
        }

        return null;
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    @SuppressWarnings("unused")
    public static Long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间字符串(YYYY-MM-DD)
     *
     * @return 日期对象
     */
    @SuppressWarnings("unused")
    public static Date getCurrentDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            logger.error("getCurrentDate", e);
        }
        return null;
    }

    /**
     * 获取当前时间字符串(YYYY-MM-DD)
     *
     * @return 当前时间字符串
     */
    @SuppressWarnings("unused")
    public static String getCurrentDateformatStr() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    /**
     * 获取当前时间字符串(YYYYMMDD)
     *
     * @return 当前时间字符串
     */
    @SuppressWarnings("unused")
    public static String getCurrentDateStr() {
        return new SimpleDateFormat(DATE_SHORT_FORMAT).format(new Date());
    }

    /**
     * 获取当前时间字符串(YYYYMMDDHHIISS)
     *
     * @return 当前时间字符串
     */
    public static String getCurrentTimeStr() {
        return new SimpleDateFormat(TIME_SHORT_FORMAT).format(new Date());
    }

    /**
     * 获取当前时间字符串(YYYYMMDDHHIISSSSS)
     *
     * @return 当前时间字符串
     */
    public static String getCurrentLongTimeStr() {
        return new SimpleDateFormat(TIME_LONG_FORMAT).format(new Date());
    }

    /**
     * 日期 --> long
     *
     * @param date 日期对象
     * @return 时间戳
     */
    @SuppressWarnings("unused")
    public static Long convert(Date date) {
        if (date == null) return null;

        return date.getTime() / 1000;
    }

    /**
     * 日期字符串(YYYY-MM-DD 或 YYYY-MM-DD HH:II:SS)转Long
     *
     * @param date 日期字符串
     * @return 时间戳
     */
    @SuppressWarnings("unused")
    public static Long convert(String date) {
        if (date == null)
            return null;

        try {
            if (date.length() == TIME_FORMAT.length())
                return (new SimpleDateFormat(TIME_FORMAT).parse(date)).getTime() / 1000;

            if (date.length() == DATE_FORMAT.length())
                return (new SimpleDateFormat(DATE_FORMAT).parse(date)).getTime() / 1000;

            if (date.length() == DATE_SHORT_FORMAT.length())
                return (new SimpleDateFormat(DATE_SHORT_FORMAT).parse(date)).getTime() / 1000;
            
 			if(date.length() == TIME_SHORT_FORMAT.length())
 				return (new SimpleDateFormat(TIME_SHORT_FORMAT).parse(date)).getTime() / 1000;
        } catch (ParseException e) {
            logger.error("convert", e);
        }

        return null;
    }

    /**
     * 在给定的时间上加/减XX天
     *
     * @param date 当前日期对象
     * @param days 要加/减的天数
     * @return 新日期对象
     */
    @SuppressWarnings("unused")
    public static Date addDay(Date date, Integer days) {
        if (date == null || days == null || days == 0)
            return date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

    /**
     * 在给定的时间上加/减XX天
     *
     * @param date 当前时间戳
     * @param days 要加/减的天数
     * @return 新时间戳
     */
    @SuppressWarnings("unused")
    public static Long addDay(Long date, Integer days) {
        if (date == null || days == null || days == 0) return date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date * 1000);
        calendar.add(Calendar.DATE, days);

        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 在给定的时间上加/减XX个月
     *
     * @param date   当前时间对象
     * @param months 要加/减的月数
     * @return 新日期对象
     */
    @SuppressWarnings("unused")
    public static Date addMonth(Date date, Integer months) {
        if (date == null || months == null || months == 0)
            return date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);

        return calendar.getTime();
    }

    /**
     * 在给定的时间上加/减XX个月
     *
     * @param date   当前时间戳
     * @param months 要加/减的月数
     * @return 新时间戳
     */
    @SuppressWarnings("unused")
    public static Long addMonth(Long date, Integer months) {
        if (date == null || months == null || months == 0) return date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date * 1000);
        calendar.add(Calendar.MONTH, months);

        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return 天数
     */
    @SuppressWarnings("unused")
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数,超过30天的月份按30天算(计算利息用)
     *
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return 天数
     */
    @SuppressWarnings("unused")
    public static int daysBetweenMore(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int date1Year = cal.get(Calendar.YEAR);//得到年
        int date1Month = cal.get(Calendar.MONTH) + 1;//得到月
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        int date2Year = cal.get(Calendar.YEAR);//得到年
        int date2Month = cal.get(Calendar.MONTH) + 1;//得到月
        int date2Day = cal.get(Calendar.DAY_OF_MONTH);//得到日
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int days = 0;
        if (date1Year == date2Year) {//相同年份的时候
            for (int i = date1Month; i <= date2Month; i++) {
                if ("1,3,5,7,8,10,12".contains(String.valueOf(i))) {
                    days = days + 1;
                }
            }
            if (date2Day <= 30) {
                days = days - 1;
            }
        } else {//不同年份的时候，如2015-6-1 至2016-5-1
            //计算两个时间相差月数
            int months = (date2Year - date1Year) * 12 + (date2Month - date1Month);
            for (int i = date1Month; i <= (date1Month + months); i++) {
                int j = 0;
                if (i / 12 > 0) {
                    j = i - 12 * (i / 12);
                }
                if ("1,3,5,7,8,10,12".contains(String.valueOf(j == 0 ? i : j))) {
                    days = days + 1;
                }
            }
            if (date2Day <= 30) {
                days = days - 1;
            }
        }
        return Integer.parseInt(String.valueOf(between_days - days));
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return 月数
     */
    @SuppressWarnings("unused")
    public static int monthBetween(Date date1, Date date2) {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(date1);
        aft.setTime(date2);
        return aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
    }

    /**
     * 计算两个日期之间相差几分钟
     *
     * @param date1 时间戳1
     * @param date2 时间戳2
     * @return 分钟数
     */
    @SuppressWarnings("unused")
    public static long minuteBetween(Long date1, Long date2) {
        return (date2 - date1) / 60;
    }

    /**
     * 获取当前月份的第一天
     *
     * @param date 日期对象
     * @return 时间戳
     */
    @SuppressWarnings("unused")
    public static Long getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取指定时间(0点0分0秒)
     *
     * @param date 日期对象
     * @param type -1：上一天 0：当天 1：下一天
     * @return 时间戳
     */
    @SuppressWarnings("unused")
    public static Long getDateTime(Date date, int type) {
        if (date == null) date = new Date();
        date = addDay(date, type);

        String strDate = new SimpleDateFormat(DATE_FORMAT).format(date) + " 00:00:00";
        try {
            return new SimpleDateFormat(TIME_FORMAT).parse(strDate).getTime() / 1000;
        } catch (ParseException e) {
            logger.error("getDateTime", e);
        }

        return null;
    }

    /**
     * 获取当天00：00：00
     *
     * @param time 当前时间戳
     * @return 新时间戳
     */
    @SuppressWarnings("unused")
    public static Long getDateBeginTime(Long time) {
        String strTime = dateTimeFormat(time).substring(0, 10);
        return convert(strTime);
    }

    /**
     * 获取当天23：59：59
     *
     * @param time 当前时间戳
     * @return 新时间戳
     */
    @SuppressWarnings("unused")
    public static Long getDateEndTime(Long time) {
        String strTime = dateTimeFormat(time).substring(0, 10);
        return convert(strTime) + (3600 * 24L) - 1L;
    }

    /**
     * 获取当天23：00：00
     *
     * @param time 当前时间戳
     * @return 新时间戳
     */
    @SuppressWarnings("unused")
    public static Long getDateLastHourTime(Long time) {
        String strTime = dateTimeFormat(time).substring(0, 10);
        return convert(strTime) + (3600 * 24L) - 60 * 60L;
    }

    /**
     * Long转换成Calendar
     *
     * @param date 时间戳
     * @return 日历对象
     */
    @SuppressWarnings("unused")
    public static Calendar getCalender(Long date) {
        date = date * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal;
    }

    /**
     * 计算 2 个日期之间的相隔工作日天数
     *
     * @param date1 时间戳1
     * @param date2 时间戳2
     * @return 天数
     */
    @SuppressWarnings("unused")
    public static int getWorkingDay(Long date1, Long date2) {
        Calendar d1 = getCalender(date1);
        Calendar d2 = getCalender(date2);
        int result;
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int charge_start_date = 0; // 开始日期的日期偏移量
        int charge_end_date = 0; // 结束日期的日期偏移量
        // 日期不在同一个日期内
        int stmp;
        int etmp;
        stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
        etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
        if (stmp != 0 && stmp != 6) {
            charge_start_date = stmp - 1; // 开始日期为星期六和星期日时偏移量为 0
        }
        if (etmp != 0 && etmp != 6) {
            charge_end_date = etmp - 1; // 结束日期为星期六和星期日时偏移量为 0
        }
        result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7)
                * 5 + charge_start_date - charge_end_date;
        return result;
    }

    /**
     * 获取两个日期之间周末天数(包含开始当天和结束当天)
     *
     * @param date1 时间戳1
     * @param date2 时间戳2
     * @return 天数
     */
    @SuppressWarnings("unused")
    public static int getHolidays(Long date1, Long date2) {
        Calendar d1 = getCalender(date1);
        Calendar d2 = getCalender(date2);
        if (d1.after(d2)) {
            Long swap = date1;
            date1 = date2;
            date2 = swap;
        }
        int week1 = getWeek(date1);
        int week2 = getWeek(date2);
        if (week2 == 6 || week2 == 7) {
            d2 = getNextMonday(d2);
            date2 = d2.getTimeInMillis() / 1000;
        }
        int day;
        if (week1 == 6 || week1 == 7) {
            day = getDaysBetween(d1, d2) - getWorkingDay(date1, date2) + 1;
        } else {
            day = getDaysBetween(d1, d2) - getWorkingDay(date1, date2);
        }
        return day;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param d1 日历对象1
     * @param d2 日历对象2
     * @return 天数
     */
    @SuppressWarnings("unused")
    public static int getDaysBetween(Calendar d1, Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR)
                - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }


    /**
     * 获得日期的下一个星期一的日期
     *
     * @param date 日历对象
     * @return 新日历对象
     */
    @SuppressWarnings("unused")
    public static Calendar getNextMonday(Calendar date) {
        Calendar result = null;
        result = date;
        do {
            result = (Calendar) result.clone();
            result.add(Calendar.DATE, 1);
        } while (result.get(Calendar.DAY_OF_WEEK) != 2);
        return result;
    }

    /**
     * 获取当前星期几
     *
     * @param date 时间戳
     * @return 星期几
     */
    @SuppressWarnings("unused")
    public static int getWeek(Long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.dateParse(date));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return w == 0 ? 7 : w;
    }

    /**
     * 根据yyyy-MM-dd获取Long
     *
     * @param day 时间字符串
     * @throws ParseException
     */
    @SuppressWarnings("unused")
    public static Long getMilSec(String day) throws ParseException {
        DateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = fmt1.parse(day);
        return date1.getTime() / 1000;
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthDay 生日
     * @return 年龄
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public static int getAge(String birthDay) throws Exception {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(new Date(getMilSec(birthDay) * 1000));
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }
    //获取昨天开始的最近七日的YYYYMMDD格式列表
    public static List<Map<String,Object>> getSevenDays(){
    	String[] weeks = new String[]{"周一","周二","周三","周四","周五","周六","周日"};
    	List sevenDays = new LinkedList();
    	Long date = new Date().getTime()/1000;
    	for(int i=0;i<7;i++){
    		Long temp_date = addDay(date,-(i+1));
    		DateFormat fmt1 = new SimpleDateFormat(DATE_SHORT_FORMAT);
    		String format_date = fmt1.format(new Date(temp_date*1000));
    		Map temp_map = new HashMap();
    		temp_map.put("week",weeks[getWeek(temp_date)-1]);
    		temp_map.put("date", format_date);
    		temp_map.put("Weight", 6-i);
    		sevenDays.add(temp_map);
    	}
    	return sevenDays;
    }
    /**
     * 获取收益开始时间
     * 周一16:00--周二16:00   周三收益
     * 周二16:00--周三16:00   周四收益
     * 周三16:00--周四16:00   周五收益
     * 周四16:00--周五16:00   下周一收益
     * 周五16:00--周一16:00   下周二收益
     * @param now
     * @return
     */
    
    public static Long getIncomeDate(Date now){
    	Long nowTime = now.getTime()/1000;
    	//如果是五一
    	if(nowTime>=1493276400L && nowTime<1493362800L){
    		return 1493697600L;
    	} 
    	if(nowTime >=1493362800L && nowTime<1493708400L){
    		return 1493784000L;
    	}
    	//如果是端午节
    	if(nowTime>=1495695600L && nowTime<1495782000L){
    		return 1496203200L;
    	} 
    	if(nowTime >=1495782000L && nowTime<1496214000L){
    		return 1496289600L;
    	}
    	int nowweek = getWeek(nowTime);
		Calendar nextMonday = DateUtil.getNextMonday(DateUtil.getCalender(nowTime));
    	//如果今天是周六日则为下周二开始计算收益
    	if(nowweek == 6 || nowweek == 7){
    		return  (nextMonday.getTimeInMillis()+24*60*60*1000)/1000;
    	}else{
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd 15:00:00");
            String dataDateStr = sf.format(now);
            Long fourthTime = DateUtil.convert(dataDateStr);
            if(nowTime.compareTo(fourthTime)<0){
            	if(nowweek == 5){
            		return  nextMonday.getTimeInMillis()/1000;
            	}else{
                	return nowTime+24*60*60;
            	}
            }else{
            	if(nowweek == 4){
            		return  nextMonday.getTimeInMillis()/1000;
            	}else if(nowweek == 5){
            		return  (nextMonday.getTimeInMillis()/1000)+24*60*60;
            	}else{
            		return nowTime+24*60*60*2;
            	}
            }
    	}
    }
    //获取收益到账时间
    public static Long getIncomeReciveDate(Date now){
    	return getIncomeDate(now)+24*60*60;
    }
    public static String convertWeek(int week){
    	String[] weeks = new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    	return weeks[week-1];
    }
}