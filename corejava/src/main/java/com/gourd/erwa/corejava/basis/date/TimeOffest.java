/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package com.gourd.erwa.util.corejava.basis.date;

import java.util.Calendar;

/**
 * @author wei.Li by 14/10/13.
 */
public class TimeOffest {
    public static final int SECONDS_IN_MINUTE = 60,
            SECONDS_IN_5_MINUTE = SECONDS_IN_MINUTE * 5,
            SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60,
            SECONDS_IN_DAY = SECONDS_IN_HOUR * 24,
            DAYS_IN_WEEK = 7;

    public static final String DURATION_DAY = "day", DURATION_WEEK = "week";

    /**
     * 获取当前时刻在当天的分钟数
     *
     * @return minute of day.
     */
    public static int getMinuteOfDay(Calendar cal) {
        if (cal == null)
            cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
    }


    public static int anInt() {
        Calendar cal = Calendar.getInstance();
        long createdAt = 1413188802L;
        cal.setTimeInMillis(createdAt * 1000L);

        int seconds = getMinuteOfDay(cal) * 60 + cal.get(Calendar.SECOND);

        int calculate_frequency = 300;


        String calculate_duration = "day";
        if (calculate_duration.equalsIgnoreCase(DURATION_DAY)) {
            return seconds / calculate_frequency;
        } else if (calculate_duration.equalsIgnoreCase(DURATION_WEEK)) {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            int localDayOfWeek = (dayOfWeek - Calendar.SUNDAY);
            if (localDayOfWeek == 0)
                localDayOfWeek = 7;
            return (SECONDS_IN_DAY * (localDayOfWeek - 1) + seconds) / calculate_frequency;
        } else {
            // month, less used
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            return (SECONDS_IN_DAY * (dayOfMonth - 1) + seconds) / calculate_frequency;
        }


    }

    public static void main(String[] args) {
        System.out.println(anInt());
        final int i = SECONDS_IN_DAY / 300;
        System.out.println(i);
    }
}
