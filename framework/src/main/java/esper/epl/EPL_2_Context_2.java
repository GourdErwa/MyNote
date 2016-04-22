package esper.epl;

import esper.javabean.Apple;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-7
 * <p>
 * 1.类别分组新建 context        {@link #contextCategorySegmented()}
 * 2.通过开始和结束条件控制 context 的新建和停止重叠和非重叠     {@link #contextNon_Overlapping()}{@link #contextOverlapping()}
 * 3.不同的事件的起始条件         {@link #contextDistinctEventsForTheInitiatingCondition()}
 */
class EPL_2_Context_2 {


    /**
     * 类别分组 context
     * <p>
     * create context context_name
     * group [by] group_expression as category_label
     * [, group [by] group_expression as category_label]
     * [, ...]
     * from stream_def
     * <p>
     * group_expression表示分组策略的表达式，category_label为策略定义一个名字，一个context可以有多个策略同时存在，但是特殊的是之能有一个stream_def。
     * <p>
     * Name	    Description
     * ===================================================
     * name	    The string-type context name.
     * id	    The integer-type internal context id that the engine assigns to the context partition.
     * label	类别标签的字符串标识符值后所指定的每一组的作为关键字。
     *
     * @return epl[]
     */
    protected static String[] contextCategorySegmented() {
        String epl1 = "create context esbtest group by price<0 as low, group by price>0 and price<10 as middle,group by price>10 as high from " + Apple.CLASSNAME;
        String epl2 = "context esbtest select context.id,context.name,context.label, price from " + Apple.CLASSNAME;

        return new String[]{epl1, epl2};
    }


    /**
     * 一次或重复的规则的方式通过开始和结束条件控制。
     * 上下文的分区数始终为1或零：上下文分区不重叠。
     * <p>
     * create context context_name
     * start (@now | start_condition)
     * end end_condition
     *
     * @return epl[]
     */
    protected static String[] contextNon_Overlapping() {
        //上午9点-下午5点间启动 context，过滤价格大于100的事件
        String epl1 = "create context NineToFive start (0, 9, *, *, *) end (0, 17, *, *, *)";
        String epl2 = "context NineToFive select * from " + Apple.CLASSNAME + "(price >= 100)";

        //下面的语句在停电期间，并持续5秒输出温度的动力：
        String epl3 = "create context PowerOutage start PowerOutageEvent end pattern [PowerOnEvent -> timer:interval(5)]";
        String epl4 = "context PowerOutage select * from TemperatureEvent";

        //语句创建立即开始，持续15分钟，以15分钟的间隔一再分配一个新的上下文分区context Every15Minutes ：
        String epl5 = "create context Every15Minutes start @now end after 15 minutes";

        return new String[]{epl1, epl2};
    }


    /**
     * OverLapping和NoOverLapping一样都有两个条件限制，{@link EPL_2_Context_2#contextNon_Overlapping()}
     * 但是区别在于OverLapping的初始条件可以被触发多次，并且只要被触发就会新建一个context，但是当终结条件被触发时，之前建立的所有context都会被销毁。
     * <p>
     * create context context_name
     * initiated [by] [distinct (distinct_value_expr [,...])] [@now and] initiating_condition
     * terminated [by] terminating_condition
     *
     * @return epl[]
     */
    protected static String[] contextOverlapping() {
        // Apple进入 事件触发开始，5分钟后结束
        String epl1 = "create context CtxTrainEnter initiated by " + Apple.CLASSNAME + " as te  terminated after 5 minutes";
        String epl4 = "context CtxTrainEnter select context.name, context.startTime, context.endTime , a.* from " + Apple.CLASSNAME + "(price >= 10) as a";

        //TODO unsolved
        String epl2 = "context CtxTrainEnter" +
                "select t1 from pattern [" +
                "  t1=TrainEnterEvent -> timer:interval(5 min) and not TrainLeaveEvent(trainId = context.te.trainId)" +
                "  ]";
        //TODO unsolved 每隔一分钟新建一个，一分钟后停止
        String epl3 = "create context CtxEachMinute initiated @now and pattern [every timer:interval(1 minute)] terminated after 1 minutes";

        return new String[]{epl1, epl4};
    }


    /**
     * 不同的事件的起始条件
     * <p>
     * 请注意以下限制：
     * <p>
     * 1.distinct关键字需要事件流的初始条件(例如,而不是crontab或模式)和流名称必须分配使用关键字。
     * 2.子查询、聚合和特殊上一页和前函数是不允许在distinct-value表达式。
     *
     * @return EPL
     */
    protected static String[] contextDistinctEventsForTheInitiatingCondition() {
        // NewOrderEvent的不同的orderId进入 事件触发开始，CloseOrderEvent进入后根据orderId是否相等停止
        String epl1 = "create context OrderContext" +
                "  initiated by distinct(orderId) NewOrderEvent as newOrder " +
                "  terminated by CloseOrderEvent(closeOrderId = newOrder.orderId)";

        return new String[]{epl1};
    }

    /**
     *  上下文startTime/endTime

     Name	    Description
     =================================================
     name	    The string-type context name.
     startTime	分区的开始时间上下文。
     endTime	分区的结束时间上下文。分区的结束时间上下文。这个字段是唯一可用的情况下,它可以计算从crontab或提供时间表达式。

     */

}
