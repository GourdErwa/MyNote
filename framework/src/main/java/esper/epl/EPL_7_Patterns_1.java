package esper.epl;

import document.Faulty;
import document.Unfinished;
import esper.javabean.Apple;
import esper.javabean.Banana;

/**
 * api - Chapter 6. EPL Reference: Patterns
 * <p>
 * 模式表现形式包括原子模式和运算模式：
 * <p>
 * 1.原子模式的模式的基本构建块。原子是过滤表达式，观察员基于时间的事件和插件定制观察家认为没有观察引擎的控制下，外部事件。
 * Pattern Atom            Example
 * ====================================================================================================================
 * 过滤器表达式              StockTick(symbol='ABC', price > 100)
 * 基于时间的间隔或者安排      timer:interval(10 seconds) timer:at(*, 16, *, *, *)
 * 自定义插件                myapplication:myobserver("http://someResource")
 * <p>
 * 2.运算模式控制表达的生命周期，并结合原子逻辑或时间
 * <p>
 * 　　有4种模式操作符:
 * <p>
 * 1.运算模式控制子表达式重复: every, every-distinct, [num] and until
 * 2.逻辑运算符: and, or, not
 * 3.时态操作,操作事件顺序: -> (followed-by)
 * 4.守护where-conditions控制子表达式的生命周期。例子是 timer:within, timer:withinmax and while-expression.自定义插件也可以使用。
 *
 * @author wei.Li by 14-8-13.
 */
class EPL_7_Patterns_1 {


    /**
     * simple example
     *
     * @return epl1
     */
    protected static String inEPL() {

        //每进入一个Banana事件则输出跟随前面的Apple 事件
        String epl1 = "select a.*  from pattern[every a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + "]";

        //查询 (每进入一个Banana事件则输出跟随的Apple 事件1分钟内id相等的数据)在过去的5分钟内id在 ('0' , '1' , '2')中
        String epl2 = "select a.*  from " +
                "pattern[every a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + "(a.id=b.id) where timer:within(1 min)]"
                + ".win:time(5 min) where a.id in ('0' , '1' , '2')";

        @Unfinished(Description = "语句解析错误")
        String epl3 = "select * from pattern [every (" + Apple.CLASSNAME + " or " + Banana.CLASSNAME + ").win:length(10)]";

        return epl2;
    }


    /**
     * 抑制相同的事件 API - 6.2.6. Suppressing Same-Event Matches
     * <p>
     * Patterns:[every a=A -> B]
     * <p>
     * 事件进入顺序 A1   A2   B1  :
     * 当事件B1到达模式为两者的组合{ A1，B1 }和组合{ A2，B1 }匹配。
     * <p>
     * 使用@SuppressOverlappingMatches格局级别标注，指示引擎匹配到第一个后放弃后面所有。
     * <p>
     * 该引擎只考虑用于检测重叠标记的事件。
     * 禁止以多个同时发生的事件中发生的单一事件或抵达时间推进的结果。
     * 部分完成的模式不会受到影响和现有模式状态不会改变，因为抑制的结果。
     * 限制：注释不能用有模式的加入​​。
     *
     * @return epl1
     */
    @Faulty(Description = "注解解析错误，API 6.2.6. Suppressing Same-Event Matches")
    protected static String SuppressingSame_EventMatches() {
        String epl1 = " select * from " +
                "pattern @SuppressOverlappingMatches [every a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + "]";
        return epl1;
    }


    /**
     * 丢弃部分的匹配 API - 6.2.7. Discarding Partially Completed Patterns
     * <p>
     * Patterns:[every a=A -> B and C(id=a.id)]
     * <p>
     * 事件进入顺序 A1{id='id1'}   A2{id='id2'}   B1
     * <p>
     * 根据上面的序列没有匹配。C模式部分完成等待事件。
     * 正在等待一个C { id ="id1"}事件之后的模式匹配完成组合{ A1,B1 }。
     * 正在等待一个C { id ="id2"}事件之后的模式匹配完成组合{ A2,B1 }。
     * <p>
     * 假设事件C1 { id ="id1")到达模式输出组合{ A1、B1、C1 }。
     * 假设事件C2 { id ="id2")到达模式输出组合{ A2、B1、C2 }。
     * 请注意,事件B1 partially-completed模式的一部分。
     * <p>
     * 使用@DiscardPartialsOnMatch pattern-level注释指示引擎,当任何匹配发生后丢弃部分完成模式的重叠事件的匹配(或匹配如果有多个匹配)。
     *
     * @return epl
     */
    protected static String discardingPartiallyCompletedPatterns() {
        /**
         * 当事件C1 { id =“id1”)到达模式输出匹配组合{ A1、B1、C1 }。
         * 匹配引擎丢弃所有的指示partially-completed模式指的A1,B1和C1事件。
         * 由于事件B1 partially-completed模式的一部分,等待C { id =“id2”},引擎丢弃partially-completed模式。
         * 因此,当C2 { id =“id2”}到引擎输出不匹配。
         */
        String epl1 = "select * from pattern @DiscardPartialsOnMatch [every a=A -> B and C(id=a.id)]";
        return epl1;
    }


    /**
     * 当指定@DiscardPartialsOnMatch和@SuppressOverlappingMatches引擎丢弃partially-completed重叠所有匹配包括抑制匹配的模式。
     * @see #SuppressingSame_EventMatches()   抑制匹配
     * @see #discardingPartiallyCompletedPatterns() 丢弃部分的匹配
     */


    /**
     * 匹配操作的优先级
     * API - 6.3. Operator Precedence
     * 见图片
     * 图1 有优先级的模式匹配
     * 图2 级别相等的模式匹配
     * 查考文档的 table
     */


    /**
     * 模式条件中的过滤操作
     *
     * @return epl
     */
    protected static String filterExpressions() {
        /**
         * 每进入一个Banana事件则输出跟随前面的Apple 事件
         * 多个 and 条件可以用,隔开
         * 静态方法的调用
         */

        String epl1 = "select a.*  from " +
                "pattern[every a=" + Apple.CLASSNAME + "(a.price > 5 , id in ('0','1','2'),a.getPriceByDiscount(price,discount) > 3) -> " +
                "b=com.framework_technology.esper.javabean.Banana(" + Banana.CLASSNAME + ".getPriceByDiscount2StaticMethod(price,discount) > 3)]";
        return epl1;
    }


    /**
     * 控制事件的消耗匹配 API - 6.4.1. Controlling Event Consumption
     * <p>
     * Patterns:[ a=RfidEvent(zone='Z1') and b=RfidEvent(assetId='0001')]
     * 在这种模式，一个RfidEvent事件到达具有区"Z1"和assetId “0001” 。
     * 模式也匹配，当两个RfidEvent事件到达，以任何顺序，其中，一个具有带"Z1" ，而另一个具有assetId “0001” 。
     * <p>
     * 用@consume过滤器表达式来表示，如果一个到达的事件相匹配的引擎，匹配明显的过滤器表达式，不匹配任何其他过滤器表达式多重过滤表达式。
     * 该@consume包括括号中的级别数。默认级别数是与同级别的数字1。多个过滤表达式@consume匹配所有的事件。
     * <p>
     * Patterns修改为:[ a=RfidEvent(zone='Z1')@consume and b=RfidEvent(assetId='0001')]
     * 这种模式当单个RfidEvent到来,带Z1,assetId‘0001’,因为当第一个过滤器表达式匹配模式引擎使用事件。
     * 在任何顺序模式只匹配两个RfidEvent事件到达时。一个事件必须带Z1,然后其他事件必须有一个带“Z1”和一个assetId‘0001’。
     *
     * @return epl
     */
    protected static String ControllingEventConsumption() {

        /**
         * 当RfidEvent zone="Z1"模式到达 。
         * 在这种情况下，输出事件填充属性' A' ，但不是'B '和' C'内容 。
         * 当RfidEvent zone="Z1"和“0001”=assetId的模式到达。
         * 在这种情况下，输出事件填充属性' B' ，但不是'a'和'C'内容 。
         * 当RfidEvent zone="Z1"和assetId=“0001”和类=“perishable”的模式到达。
         * 在这种情况下，输出事件填充属性' C' ，但不是'A'和'B' 。
         */
        String epl1 = "a=RfidEvent(zone='Z1')@consume(2)" +
                "  or b=RfidEvent(assetId='0001')@consume(1)" +
                "  or c=RfidEvent(category='perishable'))";
        return epl1;
    }


    /**
     * Use With Named Windows
     * API - 6.4.2. Use With Named Windows
     */
    @Unfinished
    protected static String namedWindows() {
        /**
         * @see EPL_8_NamedWindow
         */
        return null;
    }
}
