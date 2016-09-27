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

package com.gourd.erwa.util.date;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wei.Li by 15/1/5 (gourderwa@163.com).
 */
public class CalculateDate {


    private static final long CURR_TIME = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2;

    private static void calculate(statistically statistically) {

        Date date = new Date(CURR_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


        switch (statistically) {
            case BASIC:
                break;
            case HOUR:
                break;
            case DAY:
                break;
            case WEEK:
                break;
            case MONTH:
                break;
            case QUARTER:
                break;
            case YEAR:
                break;

        }

    }

    public static void main(String[] args) {
    }

    enum statistically {
        BASIC,
        HOUR,
        DAY,
        WEEK,
        MONTH,
        QUARTER,//1,4,7,10  ！！！ CronExpression '0 7 0 1 0,3,6,9 ?' is invalid,.
        YEAR //CronExpression '0 10 0 1 0 ? 0/1' is invalid,.
    }


}
