package jodatime;

import com.google.common.collect.Lists;
import org.joda.time.*;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lw on 14-4-18.
 * <p>
 * 参考博客 <a>http://blog.csdn.net/xiaohulunb/article/details/21102055</a>
 */
public class Joda_Time {

    //定义日期-时间-周 的格式化字符串,lssues——月份为MM大写，小写默认为分钟数
    private static final String DATE = "yyyy-MM-dd";
    private static final String TIME = "HH:mm:ss";
    private static final String WEEK = "E";

    /**
     * 自定义DateTime
     *
     * @param year         year
     * @param month        month
     * @param day          day
     * @param hour         hour
     * @param minute       minute
     * @param second       second
     * @param milliseconds milliseconds
     * @return DateTime
     */
    public static DateTime getDareTime(
            int year, int month, int day, int hour, int minute, int second, int milliseconds
    ) {
        return new DateTime(year, month, day, hour, minute, second, milliseconds);
    }

    /**
     * 获取当前日期，Date
     *
     * @return String
     */
    public static String getNowDate() {
        return DateTime.now().toString(DATE);
    }

    /**
     * 获取当前时间，Time
     *
     * @return String
     */
    public static String getNowTime() {
        return DateTime.now().toString(DATE + " " + TIME);
    }

    /**
     * 获取当前时间，Time
     *
     * @param dateTime dateTime
     * @return String
     */
    public static String getDateTime(DateTime dateTime) {
        return dateTime.toString(DATE + " " + TIME);
    }


    /**
     * 字符串转Date日期
     *
     * @param dataStr 默认格式为 yyyy-mm-dd
     * @return Date
     */
    public static Date str2Date(String dataStr) {

        DateTime dateTime = new DateTime(dataStr);
        return dateTime.toDate();
    }

    /**
     * 计算五年后的第二个月的最后一天-demo
     * monthOfYear()     // get monthOfYear property
     *
     * @param dateTime dateTime
     * @return String
     */
    public static String computationDateDemo(DateTime dateTime) {
        return dateTime.plusYears(5)
                .monthOfYear()
                .setCopy(2)
                .dayOfMonth()
                .withMaximumValue()
                .toString();
    }

    /**
     * Property属性
     */
    private static void property() {

        //计算 11 月中第一个星期一之后的第一个星期二
        DateTime.now()
                .monthOfYear()  //获取属性
                .setCopy(11)    //重新设值
                .dayOfMonth()
                .withMinimumValue()
                .plus(6)
                .dayOfWeek()
                .setCopy("Monday")
                .plus(1);
    }

    /**
     * 与JDK 的互通性
     */
    private static void tojdkDate() {
        //与 jdk日期 交互
        Date jdkDate = new Date();
        DateTime dateTime = new DateTime(jdkDate);
        Date dateJDK = dateTime.toDate();
    }

    /**
     * 以 Joda 方式格式化时间
     */
    private static void timeFormat() {


        DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));

        dateTime.toString(ISODateTimeFormat.date());//yyyy-MM-dd
        dateTime.toString(ISODateTimeFormat.yearMonthDay());//yyyy-MM-dd

        dateTime.toString(ISODateTimeFormat.dateHourMinuteSecond());//2014-08-26T13:49:46


        dateTime.toString(ISODateTimeFormat.basicDateTime());           //20140826T114706.257+0800
        dateTime.toString(ISODateTimeFormat.basicDateTimeNoMillis());   //20140826T114706+0800
        dateTime.toString(ISODateTimeFormat.basicOrdinalDateTime());    //2014238T114706.257+0800
        dateTime.toString(ISODateTimeFormat.basicOrdinalDate());    //2014238T114706.257+0800
        dateTime.toString(ISODateTimeFormat.basicWeekDateTime());       //2014W352T114843.391+0800
        dateTime.toString(ISODateTimeFormat.basicTime());       //2014W352T114843.391+0800

        final String string =
                dateTime.toString(ISODateTimeFormat.forFields(
                        Lists.newArrayList(DateTimeFieldType.year()
                                , DateTimeFieldType.monthOfYear()
                                , DateTimeFieldType.dayOfMonth()
                                , DateTimeFieldType.hourOfDay()
                                , DateTimeFieldType.minuteOfHour()
                                , DateTimeFieldType.millisOfSecond())
                        , false  //true to use the extended format (with separators)
                        , true //true ISO8601, false to include additional formats
                ));

    }

    /**
     * plus
     *
     * @return String
     */
    public static String computationDate() {
        DateTime dateTime = new DateTime();
        //日期 +-
        dateTime = dateTime.plusDays(1);

        //日期比较
        dateTime.isAfterNow();
        dateTime.isBefore(dateTime.getMillis());

        //获取当前分钟数of这个小时
        dateTime.getMinuteOfHour();

        return getDateTime(dateTime);
    }

    /**
     * 本地时间
     */
    private static void readablePartial() {
        ReadablePartial localDate
                = new LocalDate(1990, 01, 01);
        localDate.get(DateTimeFieldType.dayOfMonth());

        LocalTime localTime = new LocalTime(13, 30, 26, 0);// 1:30:26PM

    }

    public static void main(String[] args) {
        timeFormat();
    }
}
