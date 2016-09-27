package com.gourd.erwa.util.date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;

import java.util.Locale;

/**
 * 时间格式化相关常量
 *
 * @author wei.Li by 15/8/28
 */
public interface DateFormatConstant {

    /**
     * yyyy-MM-dd
     */
    FastDateFormat DATE_FORMAT = DateFormatUtils.ISO_DATE_FORMAT;

    /**
     * yyyyMMdd
     */
    FastDateFormat DATE_NO_CONNECTOR_FORMAT = FastDateFormat.getInstance("yyyyMMdd", Locale.CHINA);


    /**
     * HH:mm:ss
     */
    FastDateFormat TIME_FORMAT = DateFormatUtils.ISO_TIME_NO_T_FORMAT;

    /**
     * HH:mm
     */
    FastDateFormat TIME_NO_SS_FORMAT = FastDateFormat.getInstance("HH:mm", Locale.CHINA);

    /**
     * MM:dd
     */
    FastDateFormat TIME_MM_DD_FORMAT = FastDateFormat.getInstance("MM-dd", Locale.CHINA);

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    FastDateFormat DATE_TIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * yyyy-MM-dd HH:mm
     */
    FastDateFormat DATE_TIME_NO_SS_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm", Locale.CHINA);

    /**
     * yyyy-MM
     */
    FastDateFormat DATE_NO_DD_FORMATTER = FastDateFormat.getInstance("yyyy-MM", Locale.CHINA);

}
