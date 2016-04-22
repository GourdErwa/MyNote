package esper.epl;

import esper.javabean.Apple;
import esper.javabean.Banana;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-31
 * Time: 13:58
 * <p>
 * Context基本语法
 * <p>
 * create context context_name partition [by] event_property [and event_property [and ...]] from stream_def
 * [, event_property [...] from stream_def] [, ...]
 * <p>
 * 说明：
 * context_name为context的名字，并且唯一。如果重复，会说明已存在。
 * event_property为事件的属性名，多个属性名之间用and连接，也可以用逗号连接。
 * stream_def为事件流的定义，简单的定义可以是一个事件的名称，比如之前定义了一个Map结构的事件为User，那么这里就可以写User。复杂的流定义后面会说到
 */
class EPL_2_Context_1 {


    /**
     * 简单的实例
     *
     * @return epl
     */
    protected static String simple() {

        //id , price 是Apple 的属性
        String epl1 = "create context NewUser partition by id and price from " + Apple.CLASSNAME;

        // 多个事件流，price是Banana的属性，price是Apple的属性
        String epl2 = "create context Person partition by price from " + Banana.CLASSNAME + ", price from " + Apple.CLASSNAME;

        return epl1;
    }

    /**
     *多个流一定要注意，每个流的中用于context的属性的数量要一样，数据类型也要一致。比如下面这几个就是错误的：
     *
     create context Person partition by sid from Student, tname from Teacher
     // 错误：sid是int，tname是String，数据类型不一致

     create context Person partition by sid from Student, tid,tname from Teacher
     // 错误：Student有一个属性，Teacher有两个属性，属性数量不一致

     create context Person partition by sid,sname from Student, tname,tid from Teacher
     // 错误：sid对应tname，sname对应tid，并且sname和tname是String，sid和tid是int，属性数量一样，但是对应的数据类型不一致

     *
     */


    /**
     * 对进入context的事件增加过滤条件，不符合条件的就被过滤掉
     *
     * @return epl
     */
    public static String filter() {

        // age大于20的Student事件才能建立或者进入context
        String epl = "create context Apple partition by price from " + Apple.CLASSNAME + "(price > 20)";
        return epl;
    }

    /**
     partition by后面的属性，就是作为context的一个约束，比如说id，如果id相等的则进入同一个context里，如果id不同，那就新建一个context。
     好比根据id分组，id相同的会被分到一个组里，不同的会新建一个组并等待相同的进入。

     如果parition by后面跟着同一个流的两个属性，那么必须两个属性值一样才能进入context。
     比如说A事件id=1,name=a，那么会以1和a两个值建立context，有点像数据库里的联合主键。
     然后B事件id=1,name=b，则又会新建一个context。接着C事件id=1,name=a，那么会进入A事件建立的context。
     如果partition by后面跟着两个流的一个属性，那么两个属性值一样才能进入context。
     比如说Student事件sid=1，那么会新建一个context，然后来了个Teacher事件tid=1，则会进入sid=1的那个context。
     多个流也一样，不用关心是什么事件，只用关心事件的属性值一样即可进入同一个context。
     要是说了这么多还是不懂，可以看看下面要讲的context自带属性也许就能明白一些了。

     Name	Description
     ---------------------------------------------------------------------------------------------
     name	The string-type context name.
     id	    The integer-type internal context id that the engine assigns to the context partition.
     key1	The event property value for the first key.
     keyN	The event property value for the Nth key.

     name表示context的名称，这个是不会变的。
     id是每个context的唯一标识，从0开始。
     key1和keyN表示context定义时所选择的属性的值，1和N表示属性的位置。

     EPL: create context Person partition by sid, sname from Student
     // key1为sid，key2为sname

     */


    /**
     * 完整的例子
     *
     * @return epl
     */
    protected static String[] contextProperties() {

        // 创建context
        String epl1 = "create context apple_context_test partition by id,price from " + Apple.CLASSNAME;
        // context.id针对不同的esb的id,price建立一个context，如果事件的id和price相同，则context.id也相同，即表明事件进入了同一个context
        String epl2 = "context apple_context_test select context.id,context.name,context.key1,context.key2 from " + Apple.CLASSNAME;

        /**
         * 针对不同的id和price，都会新建一个context，并context.id会从0开始增加作为其标识。
         * 如果id和price一样，事件就会进入之前已经存在的context，所以e3这个事件就会和e1一样存在于context.id=0的context里面。
         */
        return new String[]{epl1, epl2};
    }
}
