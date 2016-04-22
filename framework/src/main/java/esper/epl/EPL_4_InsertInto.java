package esper.epl;

import esper.javabean.Apple;
import esper.javabean.Banana;

/**
 * Insert into
 * 把一个事件流的计算结果放入另一个事件流，然后可以对这个事件流进行别的计算
 * <p>
 * API-5.10. Merging Streams and Continuous Insertion: the Insert Into Clause
 *
 * @author wei.Li by 14-8-12.
 */
class EPL_4_InsertInto {


    /**
     * Insert into
     * 把一个事件流的计算结果放入另一个事件流，然后可以对这个事件流进行别的计算
     * <p>
     * insert [istream | irstream | rstream] into event_stream_name [ (property_name [, property_name] ) ]
     * <p>
     * event_stream_name            定义了事件流的名称，在执行完insert的定义之后，我们可以使用select对这个事件流进行别的计算。
     * istream | irstream | rstream 表示该事件流允许另一个事件的输入/输入和输出/输出数据能够进入。{@link #insertInto() epl3、epl4}
     * property_name                表示该事件流里包含的属性名称，多个属性名之间用逗号分割，并且用小括号括起来。
     *
     * @return epl[]
     */
    protected static String[] insertInto() {
        /**
         * 属性名称不一致的写法：
         * 一、insert into Banana(id,price) select id,size from Apple
         * 二、insert into Banana select id as id, size as size from Apple
         */
        //将Apple 的事件属性放入 Banana 事件流，在对Banana 进行操作
        String epl1 = "insert  into " + Banana.CLASSNAME + " select id , price from " + Apple.CLASSNAME;
        String epl2 = "select * from " + Banana.CLASSNAME + ".win:length_batch(2)";

        //将Apple 移除的事件放入Banana 事件流
        String epl3 = "insert rstream into " + Banana.CLASSNAME + " select id , price from " + Apple.CLASSNAME + ".win:length_batch(2)";
        String epl4 = "select * from " + Banana.CLASSNAME + ".win:length_batch(2)";
        return new String[]{epl3, epl4};
    }

    /**
     * 置换一个属性到流
     * <p>
     * insert into stream_name select property_name.* from ...
     *
     * @return epl[]
     */
    protected static String[] transposingAPropertyToAStream() {

        /**
         将Apple中的Yieldly 对象属性置换到Banana 中(Banana 无此对象属性)
         @see esper.javabean.Apple#getYieldly()
         @see esper.javabean.Banana
         */
        String epl1 = "insert into " + Banana.CLASSNAME + " select id , price , yieldly.* from " + Apple.CLASSNAME;
        String epl2 = "select * from " + Banana.CLASSNAME;
        return new String[]{epl1, epl2};
    }

    /**
     * 根据流类型进行合并
     *
     * @return epl[]
     */
    protected static String[] MergingStreamsByEventType() {

        //只让OrderEvent的事件进入MergedStream中
        String epl1 = "insert into MergedStream select ord.* from ItemScanEvent, OrderEvent as ord";
        //由自定义函数MyLib.convert(item)方法转换为OrderEvent 事件 进入MergedStream中
        String epl2 = "insert into MergedStream select MyLib.convert(item) from ItemScanEvent as item";
        return null;
    }


    /**
     * 装饰事件的属性
     * <p>
     * 可以将事件流整体和事件流属性组成的复杂表达式一起放入insert
     * 如果说别的事件流想进入此insert，那么事件流属性一定要和第一个*表示的所有属性相同。
     *
     * @return elp[]
     */
    protected static String[] DecoratedEvents() {
        String epl1 = "insert into " + Banana.CLASSNAME + " select id , price*discount as discount2price, yieldly.* from " + Apple.CLASSNAME;
        //第一个*表示Apple ，第二个表示乘法
        String epl2 = "insert into " + Banana.CLASSNAME + " select *, price*discount as discount2price from " + Apple.CLASSNAME;
        String epl3 = "select * from " + Banana.CLASSNAME;
        return new String[]{epl2, epl3};
    }
}
