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

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author wei.Li by 14/9/30.
 */
public class StringEscapeUtils_ {


    private static void simleExample_Sql() {
        String userName = "1' or '1'='1";
        String password = "123456";
        userName = StringEscapeUtils.escapeSql(userName);
        password = StringEscapeUtils.escapeSql(password);
        String sql = "SELECT COUNT(userId) FROM t_user WHERE userName='" + userName + "' AND password ='" + password + "'";
        System.out.println(sql);
        //SELECT COUNT(userId) FROM t_user WHERE userName='1'' or ''1''=''1' AND password ='123456'
    }

    public static void simleExample_Other() {
        String str = "中国";

        System.out.println("用escapeJava方法转义之后的字符串为      :"
                + StringEscapeUtils.escapeJava(str));
        System.out.println("用unescapeJava方法反转义之后的字符串为   :"
                + StringEscapeUtils.unescapeJava(StringEscapeUtils.escapeJava(str)));

        System.out.println("用escapeHtml方法转义之后的字符串为      :"
                + StringEscapeUtils.escapeHtml(str));
        System.out.println("用unescapeHtml方法反转义之后的字符串为   :"
                + StringEscapeUtils.unescapeHtml(StringEscapeUtils.escapeHtml(str)));

        System.out.println("用escapeXml方法转义之后的字符串为        :"
                + StringEscapeUtils.escapeXml(str));
        System.out.println("用unescapeXml方法反转义之后的字符串为    :"
                + StringEscapeUtils.unescapeXml(StringEscapeUtils.escapeXml(str)));

        System.out.println("用escapeJavaScript方法转义之后的字符串为:"
                + StringEscapeUtils.escapeJavaScript(str));
        System.out.println("用unescapeJavaScript方法反转义之后的字符串为:"
                + StringEscapeUtils.unescapeJavaScript(StringEscapeUtils.escapeJavaScript(str)));
    }

    public static void main(String[] args) {
        simleExample_Sql();
        simleExample_Other();
    }
}
