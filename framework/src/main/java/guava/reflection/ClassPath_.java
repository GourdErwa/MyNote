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

package guava.reflection;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;

/**
 * 这段代码会遍历你指定包中的所有类并打印出它们的名字。这里要说明的是它只会扫描我们指定的包的物理路径下的类。
 * 如果类是从其它地方加载进来的则不在此列，因此使用它的时候请务必小心，不然你得到的结果就是错误的了。
 */

/**
 * @author wei.Li by 15/3/31 (gourderwa@163.com).
 */
public class ClassPath_ {

    public static void main(String[] args) {
        try {
            ClassPath classPath = ClassPath.from(ClassPath_.class.getClassLoader());

            final ImmutableSet<ClassPath.ClassInfo> topLevelClasses = classPath.getTopLevelClasses("com.google.common");

            for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
                System.out.println(topLevelClass.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
