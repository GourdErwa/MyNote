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

package commons.apache.lang;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wei.Li by 14/9/30.
 */
public class DateUtils_ {

    private static Date date = new Date();

    private static void simpleExample() {

        DateUtils.addHours(date, 10);//add 各个时间属性

        //打印时间 为 ：2014-09-30T14:15:13
        DateFormatUtils.ISO_DATETIME_FORMAT.format(date);

        DateUtils.getFragmentInDays(date, Calendar.MONTH);//这个月里的天数为 30
        DateUtils.getFragmentInHours(date, Calendar.MONTH);//这个月里的小时为 29*24+14 = 710  API 计算结果为734 ??

        DateUtils.isSameDay(date, date);//是否是同一天

        DateUtils.isSameInstant(date, date);//比较时间戳是否相等


        //ceiling 取上限,14:15:13 按小时 ceiling 后为15:00:00
        DateUtils.ceiling(date, Calendar.HOUR);
        DateUtils.ceiling(date, Calendar.MINUTE);

        DateUtils.round(date, Calendar.HOUR);//小时四舍五入

        //truncate 类似Oracle SQL语句中的truncate函数
        //2014-09-30T14:15:13 按小时truncate后为14:00:00
        DateUtils.truncate(date, Calendar.HOUR);

        //truncatedEquals truncate之后进行比较是否相等
        DateUtils.truncatedEquals(date, date, Calendar.HOUR);

        //toCalendar方法将Date装换成Calendar (java.util.GregorianCalendar)
        (DateUtils.toCalendar(date)).getClass();

        //parseDate方法
        try {
            //尝试不同的表达式进行解析,按数组内容优先匹配10为月
            DateUtils.parseDateStrictly("10-11-2010", new String[]{"yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy"});

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //DateUtils 是对Calendar和SimpleDateFormat方法的补充
    }


    public static void main(String[] args) {
        //simpleExample();


        StringBuffer sb = new StringBuffer();
        sb.append("                   _ooOoo_\n");
        sb.append("                  o8888888o\n");
        sb.append("                  88\" . \"88\n");
        sb.append("                  (| -_- |)\n");
        sb.append("                  O\\  =  /O\n");
        sb.append("               ____/`---'\\____\n");
        sb.append("             .'  \\\\|     |//  `.\n");
        sb.append("            /  \\\\|||  :  |||//  \\ \n");
        sb.append("           /  _||||| -:- |||||-  \\ \n");
        sb.append("           |   | \\\\\\  -  /// |   |\n");
        sb.append("           | \\_|  ''\\---/''  |   |\n");
        sb.append("           \\  .-\\__  `-`  ___/-. /\n");
        sb.append("         ___`. .'  /--.--\\  `. . __\n");
        sb.append("      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n");
        sb.append("     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n");
        sb.append("     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n");
        sb.append("======`-.____`-.___\\_____/___.-`____.-'======\n");
        sb.append("                   `=---='\n");
        sb.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
        sb.append("\t\t\t佛祖保佑       永无BUG\n");
        System.out.println(sb.toString());
    }
}
