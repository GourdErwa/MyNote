package esper.views;

import document.Undigested;
import document.Unfinished;
import document.Unsolved;
import esper.javabean.Apple;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-8
 * <p>
 * 视图 Views窗口语法
 * <p>
 * esper/doc/reference - Chapter 12. EPL Reference: Views
 */
public class View {


    /**
     * Table 12.1. Built-in Data Window Views
     * <p>
     * View	Syntax	                                             Description
     * ==========================================================================================================
     * win:length(size)	                                滑动窗口，存最近的 size 条数据
     * win:length_batch(size)	                        批处理-间隔窗口，存满 size 条触发查询
     * win:time(time period)	                        滑动窗口，存最近 size 秒内的数据
     * win:ext_timed(timestamp expression, time period)	滑动窗口，存最近 time period 内的数据，不再基于EPL引擎，而是系统时间戳[timestamp]
     * win:time_batch(time period[,optional reference point] [, flow control])	            批次事件和释放他们指定的时间间隔,与流控制选项
     * win:ext_timed_batch(timestamp expression, time period[,optional reference point])	批处理事件并释放它们的基础上由表达式提供的毫秒值每隔指定的时间间隔
     * win:time_length_batch(time period, size [, flow control])	多策略的时间和长度的批处理窗口,当时间或数量任意一个满足条件时，触发查询
     * win:time_accum(time period)	                                阻塞输出，直到 period 内没有新进入的数据时，才输出并触发查询. 这些数据视为已移出窗口
     * win:keepall()	                                            keep-all 无参数，记录所有进入的数据，除非使用delete操作，才能从窗口移出数据
     * ext:sort(size, sort criteria)	                            排序的排序标准表达式返回的值,仅保留事件到给定的大小。 Q:select sum(amount) from userbuy.ext:sort(3, amount desc) 将3个最高金额求和
     * ext:rank(unique criteria(s), size, sort criteria(s))	        只保留最近的事件中有相同的值为标准表达式(s)按某种标准表达式和仅保留顶部事件到给定的大小。 Q:select * from userbuy.ext:sort(3,amount desc,id asc) 找出最高金额的3条事件，并按id排序
     * ext:time_order(timestamp expression, time period)	        Orders events that arrive out-of-order, using an expression providing timestamps to be ordered.
     * std:unique(unique criteria(s))	        对不同的unique[例如:id]保留其最近的一条事件
     * std:groupwin(grouping criteria(s))	    一般与其它窗口组合使用，将进入的数据按表达式 id 分组
     * 关于分组的时间窗口:当使用grouped-window时间窗口,注意,是否保留5分钟的事件或每组保留5分钟的事件,
     * 结果都是一样的从保留的角度事件既保留政策,考虑所有组,同一组的事件。因此请单独指定时间窗
     * std:lastevent()	                        保留最后一个事件，长度为1
     * std:firstevent()	                        保留第一个事件，不顾后续的事件
     * std:firstunique(unique criteria(s))	    Retains only the very first among events having the same value for the criteria expression(s), disregarding all subsequent events for same value(s).
     * win:firstlength(size)	                保留一批数据的前 size 条，需配合delete操作
     * win:firsttime(time period)	            保留窗口初始化后 period 内的所有数据
     *
     * @return epl
     */
    protected static String dataWindowViews() {
        //每进入3个事件后统计输出一次newEvents[3]
        String epl1 = "select price from " + Apple.CLASSNAME + ".win:length_batch(3)";

        //每进入3个事件后，按price分组统计3个事件中每组 price 出现的次数
        // TODO 默认记录了以前的分组统计信息，当前10个事件中不存在以前分组统计信息，则 count(price)为0
        String epl2 = "select rstream price , count(price) from " + Apple.CLASSNAME + ".win:length_batch(3) group by price";

        //[istream | rstream]的规则，在我们插入一批数据后，istream在3秒后输出进入窗口的数据，3秒过后，rstream会输出同样的内容
        String epl3 = "select rstream price , count(price) from " + Apple.CLASSNAME + ".win:time(5 seconds) group by price";

        @Unfinished(Description = "ERROR-> Caused by: com.espertech.esper.view.ViewParameterException: Invalid parameter expression 0: Property named 'timestamp' is not valid in any stream")
        String epl4 = "select price from " + Apple.CLASSNAME + ".win:ext_timed(timestamp, 10 sec)";

        //win:time_batch(10 sec,"FORCE_UPDATE, START_EAGER") 加上这两个参数后，会在窗口初始化时就执行查询，并且在没有数据进入和移出时，强制查询出结果
        String epl5 = "select * from " + Apple.CLASSNAME + ".win:time_batch(10 sec, \"FORCE_UPDATE, START_EAGER\")";

        // 时间和数量满足任意一个则输出
        String epl6 = "select price from " + Apple.CLASSNAME + ".win:time_length_batch(3 sec, 5, \"FORCE_UPDATE, START_EAGER\")";

        //3s 内没有数据进入则输出结果，并移除数据
        String epl7 = " select rstream price from " + Apple.CLASSNAME + ".win:time_accum(3 sec)";

        //只输出前10个事件
        String epl8 = "select price from " + Apple.CLASSNAME + ".win:firstlength(10)";

        //对 id 和 price 分组，保留最后一条事件记录 等同于std:groupwin(id, price).win:length(1)
        String epl9 = "select price from " + Apple.CLASSNAME + ".std:unique(id, price)";

        //进入的数据按id分组，相同id的数据条目数不大于3
        @Unfinished(Description = "输出结果不明确")
        String epl10 = "select sum(price) from " + Apple.CLASSNAME + ".std:groupwin(id).win:length(3)";

        //统计每组id最近3次消费的总price
        String epl11 = "select sum(price) from " + Apple.CLASSNAME + ".std:groupwin(id).win:length(3) group by id";

        //当前窗口中 最大的3个 price 的sum 值
        String epl12 = "select sum(price) from " + Apple.CLASSNAME + ".ext:sort(3, price desc)";

        //当前窗口中 按 price 升序 id 降序 的price sum 值
        String epl13 = "select sum(price) from " + Apple.CLASSNAME + ".ext:sort(3, price desc, id asc)";

        //按 size 分组，只保留最后一个分组的 size 数据，取前3个 price 的 sum 值
        String epl14 = "select sum(price) from " + Apple.CLASSNAME + ".ext:rank(size, 3, price desc)";

        //create_time 时间排序，保留4s 的数据
        String epl15 = "select rstream * from " + Apple.CLASSNAME + ".ext:time_order(create_time, 4 sec)";

        return epl15;
    }

    /**
     * win:expr(expiry expression)	    窗口的事件是否保留取决于过期表达式expression的结果。
     * expression相当于开关，如果一直是true，那么时间进入view不移除，直到expression为false，将之前保留的事件从view中全部删掉
     * <p>
     * Table 12.3. Built-in Properties of the Expiry Expression Data Window View
     * <p>
     * Name	            Type	                        Description
     * ================================================================================
     * current_count	int	            当前数据窗口包括到达的事件的数量
     * expired_count	int	            评估过期的事件数量
     * newest_event	    (same event type as arriving events)	最后一个到达的事件
     * newest_timestamp	long	        引擎的时间戳与last-arriving事件有关。
     * oldest_event	    (same event type as arriving events)	currently-evaluated事件本身。
     * oldest_timestamp	long            引擎的时间戳与currently-evaluated事件有关。
     * view_reference	Object	        当前视图处理的对象
     *
     * @return epl
     */
    protected static String expr() {

        //保留最后进入的2个事件
        String epl1 = "select rstream price from " + Apple.CLASSNAME + ".win:expr(current_count <= 2)";

        //已过期事件数量
        String epl2 = "select rstream price from " + Apple.CLASSNAME + ".win:expr(expired_count = 2)";

        //保留最后5秒进入的事件
        String epl3 = "select rstream price from " + Apple.CLASSNAME + ".win:expr(oldest_timestamp > newest_timestamp - 5000)";

        // id 相同标志的事件被保留，如果 id 值变化则移除保留的事件
        String epl4 = "select rstream price from " + Apple.CLASSNAME + ".win:expr(newest_event.id = oldest_event.id)";

        return epl2;
    }

    /**
     * win:expr_batch(expiry expression) 事件窗口,批次处理，基于过期表达式的结果作为一个参数传递判断是否移除他们
     * 参考{@link #expr()}
     * <p>
     * Table 12.4. Built-in Properties of the Expiry Expression Data Window View
     * <p>
     * Name	            Type	                        Description
     * ================================================================================
     * current_count	int	            在数据窗口包括当前到达的事件的事件的数量
     * newest_event	    (same event type as arriving events)	最后一个到达的事件
     * newest_timestamp	long	        引擎的时间戳与last-arriving事件有关。
     * oldest_event	    (same event type as arriving events)	currently-evaluated事件本身。
     * oldest_timestamp	long            引擎的时间戳与currently-evaluated事件有关。
     * view_reference	Object	        当前视图处理的对象
     *
     * @return epl
     */
    protected static String expr_batch() {

        //按current_count保留的事件数量分组，批量处理输出
        String epl1 = "select rstream price from " + Apple.CLASSNAME + ".win:expr_batch(current_count > 2)";

        //保留最后5秒进入的事件分组- 5秒内的事件，批量输出
        String epl2 = "select rstream price from " + Apple.CLASSNAME + ".win:expr_batch(oldest_timestamp > newest_timestamp - 5000)";

        return epl1;
    }

    /**
     * 以下为数据公式计算
     */

    /**
     * View	                    Syntax	                                                                Description
     * =============================================================================================================================
     * Size	                    std:size([expression, ...])	                                            Derives a count of the number of events in a data window, or in an insert stream if used without a data window, and optionally provides additional event properties as listed in parameters.
     *
     * @return epl
     */
    @Unsolved
    protected static String size_Views() {
        //统计按price分组 事件总数
        String epl1 = "select size from " + Apple.CLASSNAME + ".std:groupwin(price).std:size()";

        @Unfinished
        String epl2 = "select  size ,id ,price from " + Apple.CLASSNAME + ".win:time(3 sec).std:size(id, price)";

        @Unfinished
        String epl3 = "select size from " + Apple.CLASSNAME + ".win:time(3 sec).std:size()";

        return epl3;
    }

    /**
     * stat:uni属性
     * <p>
     * View	                    Syntax	                                 Description
     * ============================================================================================================
     * 单变量统计数据    stat:uni(value expression [,expression, ...])	     统计由单变量统计出的值
     * <p>
     * <p>
     * Property Name	Description
     * =========================================================
     * datapoints	值得数量, 相当于  count(*)
     * total	    总计  ,  相当于sum()
     * average	    平均值
     * variance	    方差
     * stddev	    样本的标准偏差(方差的平方根)
     * stddevpa	    总体标准偏差
     *
     * @return epl
     */
    protected static String stat_uni_Views() {

        String epl1 = "select stddev from " + Apple.CLASSNAME + ".win:length(3).stat:uni(price)";
        String epl2 = "select total from " + Apple.CLASSNAME + ".win:length(3).stat:uni(price)";
        String epl3 = "select average from " + Apple.CLASSNAME + ".win:length(3).stat:uni(price)";
        String epl4 = "select datapoints from " + Apple.CLASSNAME + ".win:length(3).stat:uni(price)";
        return epl3;
    }

    /**
     * stat:linest  计算两个表达式的返回值的回归和相关的中间结果。
     * <p>
     * View	                    Syntax	                                 Description
     * ============================================================================================================
     * Regression	            stat:linest(value expression, value expression [,expression, ...])	    Calculates regression on the values returned by two expressions.
     * <p>
     * 参考文档 12.4.2. Regression (stat:linest) 部分
     *
     * @return epl
     */
    @Undigested
    protected static String stat_linest_Views() {

        //下面的示例选择所有的派生值加上所有事件属性：
        String epl3 = "select * from " + Apple.CLASSNAME + ".win:time(10 seconds).stat:linest(price, offer, *)";

        return epl3;
    }

    /**
     * stat:correl 计算两个事件的相关性
     * <p>
     * View	                    Syntax	                                                                Description
     * ============================================================================================================
     * Correlation	            stat:correl(value expression, value expression [,expression, ...])	    计算2个表达式返回的值的相关值
     * <p>
     * Property Name	Description
     * =========================================================
     * correlation	    两个事件的相关性
     *
     * @return epl
     */
    @Undigested
    protected static String stat_correl_Views() {

        //计算价格的相关性
        String epl1 = "select correlation from " + Apple.CLASSNAME + ".stat:correl(size,price)";

        String epl2 = "select * from " + Apple.CLASSNAME + ".stat:correl(price, size, *)";

        return epl2;
    }


    /**
     * stat:weighted_avg 加权平均数
     * <p>
     * View	                    Syntax	                                                                 Description
     * ============================================================================================================
     * Weighted average	        stat:weighted_avg(value expression, value expression [,expression, ...]) 返回给返回值来计算的平均值和表达式返回重量表达式的加权平均值
     * <p>
     * Property Name	Description
     * =========================================================
     * average	        加权平均数
     *
     * @return epl
     */
    @Undigested
    protected static String stat_weighted_avg_Views() {

        //计算价格的相关性
        String epl1 = "select average " +
                "from " + Apple.CLASSNAME + ".win:time(3 seconds).stat:weighted_avg(price, size)";

        return epl1;
    }

}
