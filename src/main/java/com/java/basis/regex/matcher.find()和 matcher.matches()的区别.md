JAVA正则表达式,matcher.find()和 matcher.matches()的区别
======================================================
#1.find()方法是部分匹配，是查找输入串中与模式匹配的子串，如果该匹配的串有组还可以使用group()函数。

matches()是全部匹配，是将整个输入串与模式匹配，如果要验证一个输入的数据是否为数字类型或其他类型，一般要用matches()。

#2.Pattern pattern= Pattern.compile(".*?,(.*)");

    Matcher matcher = pattern.matcher(result);

  if (matcher.find()) {
   return matcher.group(1);
  }


#3.详解：

matches
public static boolean matches(String regex,  CharSequence input)

编译给定正则表达式并尝试将给定输入与其匹配。<br>  
调用此便捷方法的形式<br>  
`Pattern.matches(regex, input);
 Pattern.compile(regex).matcher(input).matches() ;`
 
 如果要多次使用一种模式，编译一次后重用此模式比每次都调用此方法效率更高。

参数：
regex - 要编译的表达式
input - 要匹配的字符序列  
抛出：  
PatternSyntaxException - 如果表达式的语法无效

find
public boolean find()尝试查找与该模式匹配的输入序列的下一个子序列。  
此方法从匹配器区域的开头开始，如果该方法的前一次调用成功了并且从那时开始匹配器没有被重置，则从以前匹配操作没有匹配的第一个字符开始。

如果匹配成功，则可以通过 start、end 和 group 方法获取更多信息。

matcher.start() 返回匹配到的子字符串在字符串中的索引位置. 

matcher.end()返回匹配到的子字符串的最后一个字符在字符串中的索引位置. 

matcher.group()返回匹配到的子字符串 

返回：
当且仅当输入序列的子序列匹配此匹配器的模式时才返回 true。


#4.部分JAVA正则表达式实例

   ①字符匹配
   `Pattern p = Pattern.compile(expression); // 正则表达式 
   Matcher m = p.matcher(str); // 操作的字符串 
   boolean b = m.matches(); //返回是否匹配的结果 
   System.out.println(b);` 

   `Pattern p = Pattern.compile(expression); // 正则表达式 
   Matcher m = p.matcher(str); // 操作的字符串 
   boolean b = m. lookingAt (); //返回是否匹配的结果 
   System.out.println(b);` 

   `Pattern p = Pattern.compile(expression); // 正则表达式 
   Matcher m = p.matcher(str); // 操作的字符串 
   boolean b = m..find (); //返回是否匹配的结果 
   System.out.println(b);`
    
    ②分割字符串 
    `Pattern pattern = Pattern.compile(expression); //正则表达式 
    String[] strs = pattern.split(str); //操作字符串 得到返回的字符串数组` 
    
    ③替换字符串 
    `Pattern p = Pattern.compile(expression); // 正则表达式 
    Matcher m = p.matcher(text); // 操作的字符串 
    String s = m.replaceAll(str); //替换后的字符串` 


   ④查找替换指定字符串 
   `Pattern p = Pattern.compile(expression); // 正则表达式 
   Matcher m = p.matcher(text); // 操作的字符串 
   StringBuffer sb = new StringBuffer(); 
   int i = 0; 
   while (m.find()) { 
    m.appendReplacement(sb, str); 
    i++;    //字符串出现次数 
   } 
   m.appendTail(sb);//从截取点将后面的字符串接上 
   String s = sb.toString(); 
   `
   ⑤查找输出字符串 
   `Pattern p = Pattern.compile(expression); // 正则表达式 
   Matcher m = p.matcher(text); // 操作的字符串 
   while (m.find()) { 
    matcher.start() ;
    matcher.end();
    matcher.group(1);
  }`