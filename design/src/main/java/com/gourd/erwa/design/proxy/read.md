动态代理模式

JDK、Cglib实现两种方式

参考资料：

http://hi.baidu.com/malecu/item/9e0edc115cb597a1feded5a0

java 动态代理深度学习(Proxy,InvocationHandler),含$Proxy0源码

java 动态代理深度学习,
一.相关类及其方法:
java.lang.reflect.Proxy,
Proxy 提供用于创建动态代理类和实例的静态方法.
newProxyInstance()
返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序
(详见api文档)

java.lang.reflect.InvocationHandler,
InvocationHandler 是代理实例的调用处理程序 实现的接口。
invoke()
在代理实例上处理方法调用并返回结果。在与方法关联的代理实例上调用方法时，将在调用处理程序上调用此方法。
(详见api文档)
二.源代码:
被代理对象的接口及实现类:
package com.ml.test;
public interface Manager {
public void modify();
}
package com.ml.test;
public class ManagerImpl implements Manager {
@Override
public void modify() {
   System.out.println("*******modify()方法被调用");
}
}
业务代理类:
package com.ml.test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
public class BusinessHandler implements InvocationHandler {
private Object object = null;
public BusinessHandler(Object object) {
   this.object = object;
}
@Override
public Object invoke(Object proxy, Method method, Object[] args)
    throws Throwable {
   System.out.println("do something before method");
   Object ret = method.invoke(this.object, args);
   System.out.println("do something after method");
   return ret;
}
}


客户端类:
package com.ml.test;
import java.lang.reflect.Proxy;
public class Client {
public static void main(String[] args) {
   // 元对象(被代理对象)
   ManagerImpl managerImpl = new ManagerImpl();
   // 业务代理类
   BusinessHandler securityHandler = new BusinessHandler(managerImpl);
   // 获得代理类($Proxy0 extends Proxy implements Manager)的实例.
   Manager managerProxy = (Manager) Proxy.newProxyInstance(managerImpl
     .getClass().getClassLoader(), managerImpl.getClass()
     .getInterfaces(), securityHandler);
   managerProxy.modify();
}
}
三.执行结果:
do something before method
*******modify()方法被调用
do something after method
四.机制分析:
Proxy.(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)做了以下几件事.
(1)根据参数loader和interfaces调用方法 getProxyClass(loader, interfaces)创建代理类$Proxy.
$Proxy0类实现了interfaces的接口,并继承了Proxy类.
(2)实例化$Proxy0并在构造方法中把BusinessHandler传过去,接着$Proxy0调用父类Proxy的构造器,为h赋值,如下:
class Proxy{
   InvocationHandler h=null;
   protected Proxy(InvocationHandler h) {
    this.h = h;
   }
   ...
}



下面是本例的$Proxy0类的源码(好不容易才把它提出来):

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
public final class $Proxy0 extends Proxy implements Manager {
private static Method m1;
private static Method m0;
private static Method m3;
private static Method m2;

static {
   try {
    m1 = Class.forName("java.lang.Object").getMethod("equals",
      new Class[] { Class.forName("java.lang.Object") });
    m0 = Class.forName("java.lang.Object").getMethod("hashCode",
      new Class[0]);
    m3 = Class.forName("com.ml.test.Manager").getMethod("modify",
      new Class[0]);
    m2 = Class.forName("java.lang.Object").getMethod("toString",
      new Class[0]);
   } catch (NoSuchMethodException nosuchmethodexception) {
    throw new NoSuchMethodError(nosuchmethodexception.getMessage());
   } catch (ClassNotFoundException classnotfoundexception) {
    throw new NoClassDefFoundError(classnotfoundexception.getMessage());
   }
}

public $Proxy0(InvocationHandler invocationhandler) {
   super(invocationhandler);
}
@Override
public final boolean equals(Object obj) {
   try {
    return ((Boolean) super.h.invoke(this, m1, new Object[] { obj }))
      .booleanValue();
   } catch (Throwable throwable) {
    throw new UndeclaredThrowableException(throwable);
   }
}
@Override
public final int hashCode() {
   try {
    return ((Integer) super.h.invoke(this, m0, null)).intValue();
   } catch (Throwable throwable) {
    throw new UndeclaredThrowableException(throwable);
   }
}
public final void modify() {
   try {
    super.h.invoke(this, m3, null);
    return;
   } catch (Error e) {
   } catch (Throwable throwable) {
    throw new UndeclaredThrowableException(throwable);
   }
}
@Override
public final String toString() {
   try {
    return (String) super.h.invoke(this, m2, null);
   } catch (Throwable throwable) {
    throw new UndeclaredThrowableException(throwable);
   }
}
}
接着把得到的$Proxy0实例强制转换成Manager.
当执行managerProxy.modify()方法时,就调用了$Proxy0类中的modify()方法.
在modify方法中,调用父类Proxy中的h的invoke()方法.
即InvocationHandler.invoke();

提取源码方式：
java.lang.reflect.Proxy.class501行:byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces);
修改rt.jar中java.lang.reflect.Proxy类. 在这行下面把 proxyClassFile 写出到一个指定的文件中. 然后重新编译Proxy类,
并把它放rt.jar中替换掉原有的Proxy类,然后调用方法时就会打印出proxyClassFile 到你指定的文件中,然后再反编译这个文件,就可以啦.
