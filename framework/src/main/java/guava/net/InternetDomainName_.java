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

package guava.net;

import com.google.common.net.InternetDomainName;

/**
 * Guava还有一个很酷的功能就是它的InternetDomainName，用它来解析及修改域名简直是得心应手。
 * 如果你自己写过类似的功能的话，你就会知道它提供的方式是多高效优雅了。
 * 它是Mozilla基金会发起的项目，遵循最新的RFC规范，它采用的是公共后缀列表（Public Suffix List， PSL）中的域名列表。
 * 与apache-common库中的竞争者相比，它还提供了许多专门的方法。
 * <p>
 * 我们来看一个简单的例子：
 * <p>
 * InternetDomainName owner =
 * InternetDomainName.from("blog.takipi.com").topPrivateDomain(); // returns takipi.com
 * <p>
 * InternetDomainName.isValid(“takipi.monsters"); // returns false
 * <p>
 * 关于域名有几个概念是比较容易混淆的：
 * <p>
 * <p>
 * publicSuffix()返回的是对应着公共后缀列表中的独立实体的顶级域名。
 * 因此返回的可能会有co.uk, .com, .cool这样的结果（没错，.cool是一个真实的后缀，比如javais.cool, scalais.cool以及cppis.cool）。
 * <p>
 * 而topPrivateDomain()，这是对应公共后缀列表的一个独立实体的私有域名。
 * 在blog.takipi.com上调用这个方法会返回takipi.com，但如果你把它用于某个github主页，
 * 比如username.github.io的话则会返回username.github.io，因为这在PSL上是一个单独的实体。
 * <p>
 * 当你需要校验域名的时候这个功能就派上用场了，比如我们最近给将JIRA集成进Takipi的时候，首先我们要检查你的JIRA域名，然后才能连接到Takipi的生产环境的错误分析工具中。
 */

/**
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
public class InternetDomainName_ {

    public static void main(String[] args) {
        InternetDomainName owner =
                InternetDomainName.from("java.gourderwa.com").topPrivateDomain(); // returns gourderwa.com

        final boolean valid = InternetDomainName.isValid("takipi.monsters1");   // returns true

        System.out.println(owner);
        System.out.println(valid);
    }
}
