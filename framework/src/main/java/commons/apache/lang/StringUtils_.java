package commons.apache.lang;

import org.apache.commons.lang.StringUtils;

/**
 * @author lw by 14-5-18.
 */
public class StringUtils_ {

    private static void demo_1() {

        /*检查字符是否为空(" ")或null*/
        StringUtils.isEmpty(null);//true;
        StringUtils.isEmpty("");// true
        StringUtils.isEmpty(" ");// false
        StringUtils.isEmpty("bob");// false
        StringUtils.isEmpty("  bob  ");//;// false

        /*检查字符是否不为空(" ")或不为null*/
        StringUtils.isNotEmpty(null);//false;
        StringUtils.isNotEmpty("");// false
        StringUtils.isNotEmpty(" ");// true
        StringUtils.isNotEmpty("bob");// true
        StringUtils.isNotEmpty("  bob  ");//;// true

        /*检查字符串是否是空格、空(" ")或null*/
        StringUtils.isBlank(null);// true
        StringUtils.isBlank("");// true
        StringUtils.isBlank(" ");// true
        StringUtils.isBlank("bob");// false
        StringUtils.isBlank("  bob  ");// false

        /*检查字符串是否不是空格、空(" ")或null*/
        StringUtils.isNotBlank(null);// false
        StringUtils.isNotBlank("");// false
        StringUtils.isNotBlank(" ");// false
        StringUtils.isNotBlank("bob");// true
        StringUtils.isNotBlank("  bob  ");// true

        /*去除字符串中的空格，返回"" 或者 null*/
        StringUtils.trim(null);// null
        StringUtils.trim("");// ""
        StringUtils.trim("     ");// ""
        StringUtils.trim("abc");// "abc"
        StringUtils.trim("    abc    ");// "abc"

        StringUtils.trimToNull(null);// null
        StringUtils.trimToNull("");//null
        StringUtils.trimToNull("     ");//null

        StringUtils.trimToEmpty(null);//""
        StringUtils.trimToEmpty("");//""
        StringUtils.trimToEmpty("     ");//""

        /*去除的字符串开始和结束两边的空格，返回"" 或者 null*/
        StringUtils.strip(null);// null
        StringUtils.strip("");// ""
        StringUtils.strip("     ");// ""
        StringUtils.strip("abc");// "abc"
        StringUtils.strip("    ab c    ");// "ab c"
        //stripToNull、stripToEmpty同上trim方法

        //StringUtils.strip(null, *);// null
        //StringUtils.strip("", *);// ""
        StringUtils.strip("abc", null);// "abc"
        StringUtils.strip("  abc", null);// "abc"
        StringUtils.strip("abc  ", null);// "abc"
        StringUtils.strip(" abc ", null);// "abc"
        StringUtils.strip("  abcyx", "xyz");// "  abc"

    }

    public static void main(String[] args) {

        System.out.println(StringUtils.strip("  abcyx ", "xyz"));
    }

}
