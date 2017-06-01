package com.zh;

import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.security.MD5Util;
import com.zh.dubbo.untils.security.SHAUtil;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 70214 on 2017/4/24.
 */
public class DateTest {
    @Test
    public void testDate(){
        System.out.println("/////////////////////////////////////////////////");
        System.out.println("当前时间是+====="+ DateUtil.getDateTime());
        System.out.println("当前时间是+====="+DateUtil.getDate());
        System.out.println("当前时间是+====="+DateUtil.getTime());
        System.out.println("统计开始时间+===="+DateUtil.getStartDate());
        System.out.println("统计结束时间+===="+DateUtil.getEndDate());
        System.out.println("获取当前服务器的年+=="+DateUtil.getYear());
        System.out.println("获取当前服务器的月+=="+DateUtil.getMonth());
        System.out.println("获取当前服务器的天+=="+DateUtil.getDay());
        System.out.println("////////////////////////////////////////////////");
    }
    @Test
    public void getMargin(){
        String oldDayStr = "2017-04-20 00:00:00'";
        String tomDayStr = "2017-05-22 23:59:59";
        Date now  = new Date();
        System.out.println("///////////////////////////");
        System.out.println("两日期相差天数+"+DateUtil.getMargin(tomDayStr,oldDayStr));
        System.out.println("两日期相差月份+"+DateUtil.getMonthMargin(tomDayStr,oldDayStr));
        System.out.println("增加天数+10"+DateUtil.addDay(oldDayStr,10));
        System.out.println("增加天数+11"+DateUtil.addDay(oldDayStr,11));
        System.out.println("增加月数+10"+DateUtil.addMonth(oldDayStr,10));
        System.out.println("增加月数+12"+DateUtil.addMonth(oldDayStr,12));
        System.out.println("增加年份+1"+DateUtil.addYear(oldDayStr,1));
        System.out.println("返回某月中最大的天数+2016-02"+DateUtil.getMaxDay(2016,2));
        System.out.println("返回某月中最大的天数+2017-02"+DateUtil.getMaxDay(2017,2));
        System.out.println("返回某月中最大的天数+2017-03"+DateUtil.getMaxDay(2017,3));
        System.out.println("///////////////////////////");

    }
    @Test
    public void md5()throws Exception{
        String ss = MD5Util.md5Encode("zhangsan");
        System.out.println(ss);
    }
    @Test
    public void SHA()throws Exception{
        String pwd = SHAUtil.getPwd("zhangweiguo","qw1t2s",5);
        System.out.println("pwd======"+pwd);
    }

}
