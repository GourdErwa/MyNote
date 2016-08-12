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

package guava.base;

import com.google.common.base.CharMatcher;

/*
  ASCII：               用于匹配ASCII字符
  DIGIT：               匹配ASCII数字
  JAVA_DIGIT：          匹配unicode数字
  JAVA_LETTER：         匹配字母（含中文）
  JAVA_LETTER_OR_DIGIT：匹配字母（含中文）或数字
  JAVA_UPPER_CASE：     匹配所有大写字符
  JAVA_LOWER_CASE：     匹配所有小写字符
  JAVA_ISO_CONTROL：    匹配iso控制字符
  INVISIBLE：           匹配所有不可见字符
  SINGLE_WIDTH：        匹配单字宽字符
  ANY：                 用于匹配任意字符
  NONE：                不匹配所有字符
  WHITESPACE：          用于匹配所有空白字符


  CharMatcher同时也提供了许多的工厂方法，我们可以使用CharMatcher来调用这些方法获取Matcher实例，如下：

  is(final char match)：         返回匹配指定字符的Matcher
  isNot(final char match)：      返回不匹配指定字符的Matcher
  anyOf(final CharSequence sequence)：返回能够匹配sequence中任一字符的Matcher
  noneOf(CharSequence sequence)：返回能够过滤sequence中任一字符的Matcher
  inRange(final char startInclusive, final char endInclusive)：返回匹配范围内任意字符的Matcher
  forPredicate(final Predicate<? super Character> predicate)： 返回使用Predicate的apply()判断匹配的Matcher
  negate()：               返回与当前Matcher判断规则相反的Matcher
  and(CharMatcher other)： 返回与other匹配条件组合进行与运算的Matcher
  or(CharMatcher other)：  返回与other匹配条件组合进行或运算的Matcher
  precomputed()：          返回经过预先计算的，最优于当前运行环境的Matcher


  获取的符合规则的Matcher后，有以下常用方法来处理字符串并返回结果，如下：

  removeFrom(CharSequence sequence)：去除匹配到的字符
  retainFrom(CharSequence sequence)：筛选匹配到的字符
  replaceFrom(CharSequence sequence, char replacement)：         使用指定字符替换匹配到的字符
  replaceFrom(CharSequence sequence, CharSequence replacement)： 使用指定字符替换匹配到的字符
  trimFrom(CharSequence sequence)：        去除首尾匹配到的字符
  trimLeadingFrom(CharSequence sequence)： 去除首部匹配到的字符
  trimTrailingFrom(CharSequence sequence)：去除尾部匹配到的字符
  collapseFrom(CharSequence sequence, char replacement)：        将匹配到的字符组（多个字符）替换成指定字符
  trimAndCollapseFrom(CharSequence sequence, char replacement)： 去除首尾空格后进行字符替换


  另外，CharMatcher还提供了一些判断方法和获取字符索引的方法，如下：

  matchesAnyOf(CharSequence sequence)：  如果sequence中任一字符匹配，返回true
  matchesAllOf(CharSequence sequence)：  如果sequence中所有字符都匹配，返回true
  matchesNoneOf(CharSequence sequence)： 如果sequence中所有字符都不匹配，返回true
  indexIn(CharSequence sequence)：       返回匹配到的第一个字符的索引
  indexIn(CharSequence sequence, int start)：返回从指定索引开始，匹配到的第一个字符的索引
  lastIndexIn(CharSequence sequence)：   返回匹配到的最后一个字符的索引
  countIn(CharSequence sequence)：       返回匹配到的字符数量
 */

/**
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
public class CharMatcher_ {

    public static void main(String[] args) {
        CharMatcher charMatcher = CharMatcher.ANY;
        final int aa = charMatcher.countIn("aa");
        System.out.println(aa);

        testCharMatcher();
    }

    //打印方法
    private static void print(Object obj) {
        System.out.println(String.valueOf(obj));
    }

    public static void testCharMatcher() {

        //原始字符串
        String sequence = "HELLO   RealFighter ~!@#$%^&*() ，,.。   \n123(*&gS   你好\t234啊   abc  \n";

        print(sequence);
        //使用JAVA_ISO_CONTROL去除所有控制字符\n\t
        String str = CharMatcher.JAVA_ISO_CONTROL.removeFrom(sequence);
        print(str);

        //筛选出所有的字母(包含中文)或数字
        str = CharMatcher.JAVA_LETTER_OR_DIGIT.retainFrom(sequence);
        print(str);

        //匹配sequence中的数字并全部替换成*号
        str = CharMatcher.JAVA_DIGIT.replaceFrom(sequence, "*");
        print(str);

        //去除首尾连续匹配到的大写字符
        str = CharMatcher.JAVA_UPPER_CASE.trimFrom(sequence);
        print(str);

        //去除首部连续匹配到的大写字符
        str = CharMatcher.JAVA_UPPER_CASE.trimLeadingFrom(sequence);
        print(str);

        //去除尾部连续匹配到的大写字符
        str = CharMatcher.JAVA_UPPER_CASE.trimTrailingFrom(sequence);
        print(str);

        //将匹配到的大写字符替换成问号
        str = CharMatcher.JAVA_UPPER_CASE.collapseFrom(sequence, '?');
        print(str);

        //去除首尾空白符后将匹配到的小写字符替换为问号
        str = CharMatcher.JAVA_LOWER_CASE.trimAndCollapseFrom(sequence, '?');
        print(str);
    }

}
