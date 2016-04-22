package esper.epl;


/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-7
 * <p>
 * Context 过滤条件   {@link #contextFilterCondition()}
 * <p>
 * 模式过滤             {@link #contextPatternCondition()}
 * 定时任务过滤           {@link #contextCrontabCondition()}
 * 时间段条件过滤          {@link #contextTimePeriodCondition()}
 * 嵌套的context过滤         {@link #contextNesting()}
 * 查看 context 的内容       {@link #ContextEndsOutPut()}
 */
class EPL_2_Context_3 {


    /**
     * Context 过滤条件
     * <p>
     * event_stream_name [(filter_criteria)] [as stream_name]
     * <p>
     * event_stream_name是事件类型的名称或名称的事件流填充一个插入语句。
     * filter_criteria是可选的,由一系列事件流的表达式过滤事件,事件流后括号内的名字。
     *
     * @return epl[]
     */
    protected static String[] contextFilterCondition() {

        // 不重叠的情况下,当MyStartEvent到达时开始，MyEndEvent到达结尾的
        String epl1 = "create context MyContext start MyStartEvent end MyEndEvent";

        // 重叠情况下，当进入的MyEvent事件的 level>0 则新建一个 context ，10秒后停止
        String epl2 = "create context MyContext initiated MyEvent(level > 0) terminated after 10 seconds";

        //不重叠情况下，当MyEvent到达开始，到某个MyEvent的 id 与新建 context 时相等则结束
        String epl3 = "create context MyContext " +
                "start MyEvent as myevent" +
                "end MyEvent(id=myevent.id)";

        // 重叠情况下，MyInitEvent进入则新建 context， 进入的MyTermEvent的id=e1.id且level 不等于 e1.level则结束这个 context
        String epl4 = "create context MyContext " +
                "initiated by MyInitEvent as e1 " +
                "terminated by MyTermEvent(id=e1.id, level <> e1.level)";
        return new String[]{epl1};
    }


    /**
     * 模式条件过滤
     * <p>
     * pattern [pattern_expression] [@inclusive]
     * <p>
     * 指定后@inclusive模式有相同的事件,构成了模式匹配也计入任何语句相关联的上下文。您还必须提供一个标签为每个事件的模式应包括在内。
     *
     * @return epl
     */
    protected static String[] contextPatternCondition() {

        //非重叠的背景下，当StartEventOne或StartEventTwo到达启动
        //并且在5秒后结束。
        //这里StartEventOne或StartEventTwo没有计入任何语句
        //指的是上下文。
        String epl1 = "create context MyContext " +
                "  start pattern [StartEventOne or StartEventTwo] " +
                "  end after 5 seconds";

        //这里StartEventOne或StartEventTwo计入任何语句
        String epl2 = "create context MyContext " +
                "  start pattern [a=StartEventOne or b=StartEventTwo] @inclusive " +
                "  end after 5 seconds";

        //重叠的情况下，每个不同的MyInitEvent启动一个新的上下文
        //20秒后，每个上下文分区终止

        String epl3 = "create context MyContext" +
                "  initiated by pattern [every-distinct(a.id, 20 sec) a=MyInitEvent]@inclusive" +
                "  terminated after 20 sec";


        //一个重叠的上下文,每个模式匹配发起一个新的上下文
        //所有上下文分区MyTermEvent到来时终止。
        // MyInitEvent和MyOtherEvent触发模式本身不包括在内
        //在任何语句相关联的上下文。
        String epl4 = "create context MyContext" +
                "  initiated by pattern [every MyInitEvent -> MyOtherEvent where timer:within(5)]" +
                "  terminated by MyTermEvent";

        //非重叠的背景下，当任StartEventOne或StartEventTwo到达启动
        //而当任何一个匹配EndEventOne或EndEventTwo到达结束
        String epl5 = "create context MyContext " +
                "  start pattern [a=StartEventOne or b=StartEventTwo]@inclusive" +
                "  end pattern [EndEventOne(id=a.id) or EndEventTwo(id=b.id)]";
        return null;
    }


    /**
     * 定时任务条件过滤
     *
     * @return epl[]
     */
    protected static String[] contextCrontabCondition() {

        //非重叠的情况下开始每天上午9时至下午5时
        String epl1 = "create context NineToFive start (0, 9, *, *, *) end (0, 17, *, *, *)";

        //重叠的背景下的crontab启动一个新的上下文每1分钟
        //和10秒后的每个上下文分区终止：
        String epl2 = "create context MyContext initiated (*, *, *, *, *) terminated after 10 seconds";
        return null;
    }

    /**
     * 时间段条件过滤
     * <p>
     * after time_period_expression
     * <p>
     * -------------指定时间段-----------------
     * time-period : [year-part] [month-part] [week-part] [day-part] [hour-part] [minute-part] [seconds-part] [milliseconds-part]
     * year-part :      (number|variable_name) ("years" | "year")
     * month-part :     (number|variable_name) ("months" | "month")
     * week-part :      (number|variable_name) ("weeks" | "week")
     * day-part :       (number|variable_name) ("days" | "day")
     * hour-part :      (number|variable_name) ("hours" | "hour")
     * minute-part :    (number|variable_name) ("minutes" | "minute" | "min")
     * seconds-part :   (number|variable_name) ("seconds" | "second" | "sec")
     * milliseconds-part : (number|variable_name) ("milliseconds" | "millisecond" | "msec")
     * ---------------一些事例---------------------
     * 10 seconds
     * 10 minutes 30 seconds
     * 20 sec 100 msec
     * 1 day 2 hours 20 minutes 15 seconds 110 milliseconds
     * 0.5 minutes
     * 1 year
     * 1 year 1 month
     *
     * @return elp[]
     */
    protected static String[] contextTimePeriodCondition() {

        //非重叠上下文10秒后开始
        //开始1分钟结束,之后10秒又开始启动。
        String epl1 = "create context NonOverlap10SecFor1Min start after 10 seconds end after 1 minute";

        //重叠的情况下，每5秒启动一个新的上下文分区
        //每个分区持续1分钟
        String epl2 = "create context Overlap5SecFor1Min initiated after 5 seconds terminated after 1 minute";

        return null;
    }


    /**
     * 嵌套的context过滤
     * <p>
     * create context context_name
     * context nested_context_name [as] nested_context_definition ,
     * context nested_context_name [as] nested_context_definition [, ...]
     * <p>
     * 注意语境顺序，减少资源消耗
     * 例如将下方法中EPL1 的SegmentedByCustomer放于NineToFive前面
     *
     * @return epl[]
     */
    protected static String[] contextNesting() {

        //在嵌套的上下文来计算每个账户共提款金额为每一个客户，但只在上午9点到下午5点：
        String epl1 = "create context NineToFiveSegmented" +
                "  context NineToFive start (0, 9, *, *, *) end (0, 17, *, *, *)," +
                "  context SegmentedByCustomer partition by custId from BankTxn";

        //属性获取
        String elp2 = "context NineToFiveSegmented" +
                "select context.NineToFive.startTime, context.SegmentedByCustomer.key1 from BankTxn";


        //TODO unsolved
        String epl3 = "create context CtxNestedTrainEnter" +
                "  context InitCtx initiated by TrainEnterEvent as te terminated after 5 minutes," +
                "  context HashCtx coalesce by consistent_hash_crc32(tagId) from PassengerScanEvent" +
                "    granularity 16 preallocate";
        //属性获取
        String epl4 = "context CtxNestedTrainEnter" +
                "select context.InitCtx.te.trainId, context.HashCtx.id," +
                "  tagId, count(*) from PassengerScanEvent group by tagId";
        return null;
    }

    /**
     * 查看Context的内容
     * Output When Context Partition Ends
     * context 的内容输出参考 {@link EPL_3_Output}
     *
     * @return epl[]
     */
    protected static String[] ContextEndsOutPut() {
        String epl1 = " create context OverLapping initiated InitialEvent terminated TerminateEvent";
        String epl2 = "context OverLapping select * from User output snapshot when terminated";

        // 每两分钟输出OverLapping的事件
        String epl3 = "context OverLapping select * from User output snapshot every 2 minute";
        return null;
    }

}
