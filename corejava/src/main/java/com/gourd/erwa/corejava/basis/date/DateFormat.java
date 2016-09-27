package com.gourd.erwa.util.corejava.basis.date;


import java.util.Calendar;
import java.util.Date;

/**
 * @author wei.Li by 14-8-26.
 */
public class DateFormat {

    /**
     * 日期操作
     */
    private static void dateFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // 如何获取当前的月份？
        calendar.get(Calendar.MONTH);


        //如何获取指定日期所在的周从哪一天开始？
        int minimalDaysInFirstWeek
                = calendar.getMinimalDaysInFirstWeek();//获取一年中第一个星期所需天数
        final int i //获取这天在一年中的天数做差
                = calendar.get(Calendar.DAY_OF_YEAR) - minimalDaysInFirstWeek;


        // 如何知道现在距离指定的时间还有多久？
        final long l
                = (calendar.getTimeInMillis() + 1000L) - calendar.getTimeInMillis();


        long before_strat = 1408982446558L;//1
        long before_end = 1408993446558L;

        long after_start = 1408983446558L;//2
        long after_end = 1408992446558L;
        //如果2的开始时间大于1的结束时间，没有交集
        if (after_start - before_end > 0) {
            return;//not have
        } else
            //如果1的结束时间大于2的结束时间，2的所有时间段为交集
            if (before_end - after_end > 0) {
                return;
            } else
                return;//为2的结束时间与1的结束时间为交集

    }

}
