package esper.epl;

import esper.javabean.Apple;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-31
 * <p>
 * API - 5.7. Stabilizing and Controlling Output: the Output Clause
 * Output 用来控制Esper对事件流计算结果的输出时间和形式,可以以固定频率，也可以是某个时间点输出
 */
class EPL_3_Output {


    /**
     * Output 用来控制Esper对事件流计算结果的输出时间和形式,可以以固定频率，也可以是某个时间点输出
     * <p>
     * 简单语法如下：
     * output [after suppression_def]
     * [[all | first | last | snapshot] every time_period | output_rate events]
     * <p>
     * after suppression_def 是可选参数，表示先满足一定的条件再输出。
     * all | first | last | snapshot 表明输出结果的形式，默认值为all。
     * every output_rate    表示输出频率，即每达到规定的频率就进行输出。
     * time_period          表示时间频率
     * output_rate events   表示事件数量
     *
     * @return epl
     */
    protected static String outPut() {
        // 30分钟内，每进入一个AppleEvent，统计一次sum price，并且每60秒输出一次统计结果。snapshot:快照
        String epl1 = " select sum(price) from " + Apple.CLASSNAME + ".win:time(30 min) output snapshot every 10 seconds";
        return epl1;
    }


    /**
     * after
     * <p>
     * 语法：
     * output after time_period | number events [...]
     *
     * @return epl
     */
    protected static String after() {
        /**
         * time_period表示时间段，number events表示事件数量。表示从EPL可用开始，经过一段时间或者接收到一定数量的事件再进行输出。
         */
        // 统计20个Apple事件的sum price，并且在有5个Apple事件进入后才开始输出统计结果
        String epl1 = "select sum(price) from " + Apple.CLASSNAME + ".win:length(20) output after 5 events";

        /**
         * 从第一个进入的事件进行统计，直到进入了5个事件以后才输出统计结果，之后每进入一个事件输出一次（这是win:length的特性）。
         * 但是要注意的是，after之后的时间长度和事件数量会影响之后的时间或者事件数量。
         * 因为after之后的every子句要等到after后面的表达式满足后才生效
         */
        // 从EPL可用开始计时，经过1分钟后，每5秒输出一次当前100秒内的所有Apple的avg price（即：第一次输出在65秒时）
        String epl2 = "select avg(price) from " + Apple.CLASSNAME + ".win:time(100 sec) after 1 min snapshot every 5 sec";

        return epl1;
    }


    /**
     * first - last
     * <p>
     * first表示每一批可输出的内容中的第一个事件计算结果
     * last相反
     *
     * @return epl
     */
    protected static String first() {
        String epl1 = "select * from " + Apple.CLASSNAME + " output first every 2 events";
        return epl1;
    }

    /**
     * snapshot - all
     * <p>
     * snapshot表示输出EPL所保持的所有事件计算结果，通常用来查看view或者window中现存的事件计算结果
     * all和snapshot类似，也是输出所有的事件，但是不同的是，snapshot相当于对计算结果拍了一张照片，把结果复制出来并输出，而all是把计算结果直接输出，不会复制
     *
     * @return epl
     */
    protected static String snapshot() {

        /**
         * 表示每进入两个事件输出5 sec内的所有事件.
         * 且 snapshot 不会将这些事件从5 sec范围内移除,而 all 输出的事件不再保留于5 sec范围内
         */

        String epl1 = "select * from " + Apple.CLASSNAME + ".win:time(5 sec) output snapshot every 2 events";
        return epl1;
    }


    /**
     * Crontab Output 定时输出
     * <p>
     * output的另一个语法可以建立定时输出，关键字是at。
     * 语法如下
     * <p>
     * output [after suppression_def]
     * [[all | first | last | snapshot] at
     * (minutes, hours, days of month, months, days of week [, seconds])]这些都是时间单位，语法后面再细说。
     *
     * @return epl
     */
    protected static String crontabOutput() {
        // 在8点到17点这段时间内，每15分钟输出一次
        String epl1 = "select * from " + Apple.CLASSNAME + " output at (*/15,8:17,*,*,*)";
        return epl1;
    }


    /**
     * when 实现达到某个固定条件再输出的效果
     * <p>
     * Output还可以使用when来实现达到某个固定条件再输出的效果，一般通过变量，用户自定义的 函数以及output内置的属性来实现。
     * 基本语法如下：
     * <p>
     * output [after suppression_def]
     * [[all | first | last | snapshot] when trigger_expression
     * [then set variable_name = assign_expression [, variable_name = assign_expression [,...]]]
     * <p>
     * trigger_expression返回true或者false，表示输出或者不输出
     * then set variable_name=assign_expression表示是当trigger_expression被触发时，可对变量重新赋值。
     *
     * @return epl
     */
    protected static String when() {
        // 当exceed为true时，输出所有进入EPL的事件，然后设置exceed为false
        String epl1 = "select price  from " + Apple.CLASSNAME + " output when exceed then set exceed=false";

        /**
         对于when关键字，Esper提供了一些内置的属性帮助我们实现更复杂的输出约束
         Built-In Property Name	        Description
         ==============================================================================
         last_output_timestamp	    Timestamp when the last output occurred for the statement; Initially set to time of statement creation
         count_insert	            Number of insert stream events
         count_insert_total	        Number of insert stream events in total (not reset when output occurs).
         count_remove	            Number of remove stream events
         count_remove_total	        Number of remove stream events in total (not reset when output occurs).

         例如：

         进入的Apple事件总数达到5个时才输出，且不清零count_insert_total属性，继续累加事件总数
         select * from Apple output when count_insert_total=5

         移除的Apple事件总数达到4个时才输出，并清零count_remove属性
         select * from Apple output when count_remove=4

         另外，在使用when的时候，需要注意：
         1.当trigger_expression返回true时，Esper会输出从上一次输出之后到这次输出之间所有的insert stream和remove stream。
         2.若trigger_expression不断被触发并返回true时，则Esper最短的输出间隔为100毫秒。
         3.expression不能包含事件流的属性，聚合函数以及prev函数和prior函数
         */
        return epl1;
    }

    /**
     * when terminated
     * <p>
     * Output还针对Context专门设计了一个输出条件，即在Context终止时输出Context中的内容
     * 具体语法如下：
     * <p>
     * output when terminated [and termination_expression]
     * [then set variable_name = assign_expression [, variable_name = assign_expression [,...]]]]
     *
     * @return epl
     */
    protected static String contextTerminated() {
        // 在MyContext下，查询context的id并计算Apple的sum price，当Context结束且输入的事件总数大于10时，输出。然后设置FinishCompute变量为true
        String epl1 = " context MyContext select context.id, sum(price) from " + Apple.CLASSNAME + " output when terminated and count_insert_total > 10 then set FinishCompute = true";

        // 在MyContext下，计算Apple的avg size，并每1分钟输出第一个进入的事件计算结果，当context结束时也输出一次计算结果
        String epl2 = "context MyContext select avg (size) from " + Apple.CLASSNAME + " output first every 1 min and when terminated";

        return "";
    }

    /**
     * Limit子句通常一起使用的顺序和OUTPUT子句将查询结果限制为那些落在指定的范围内的。
     * 可以用它来接收结果行的第一个定数，或获得一系列的结果行
     * <p>
     * limit row_count [offset offset_count]
     * <p>
     * limit 8 offset 2
     * ...equivalent to
     * limit 2, 8
     * <p>
     * ow_count为负数，则无限制输出，若为0，则不输出。当row_count是变量表示并且变量为null，则无限制输出。
     * offset _count是不允许负数的，如果是变量表示，并且变量值为null或者负数，则EPL会把他假设为0。
     *
     * @return epl
     */
    protected static String Limit() {

        String epl1 = "select price, count(*) from " + Apple.CLASSNAME +
                " group by price " +
                " output snapshot every 5 sec" +
                " order by count(*) desc " +
                " limit 4 offset 2";

        return epl1;
    }

    /**
     Output和Aggregation，Group by一起使用时，first，last，all，snapshot四个关键字产生的效果会比较特别。
     Esper的官方文档 【5.7. Stabilizing and Controlling Output: the Output Clause】，有相当完整的例子做说明.
     另外针对first，last，all，snapshot四个关键字，只有使用snapshot是不会缓存计算结果。
     其他的关键字会缓存事件直到触发了输出条件才会释放，所以如果输入的数据量比较大，就要注意输出条件被触发前的内存使用量。

     关于Output的内容比较多，使用起来也比较灵活。
     Group by和Aggregation和SQL的类似，所以使用起来很容易。
     */
}
