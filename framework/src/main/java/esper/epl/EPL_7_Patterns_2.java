package esper.epl;

import esper.javabean.Apple;
import esper.javabean.Banana;
import esper.javabean.Orange;

/**
 * API - 6.5. Pattern Operators
 *
 * @author wei.Li by 14-8-13.
 */
class EPL_7_Patterns_2 {

    /**
     *
     ####6.5.1. Every
     参考{@link #every()} 方法中epl1-epl4 语句
     ==========================
     事件的进入顺序如下:

     A1   B1   C1   B2   A2   D1   A3   B3   E1   A4   F1   B4

     Table 6.4. 'Every' operator examples

     #Example

     every ( A -> B )

     检测到的A事件后，通过B项。在当B出现的模式相匹配的时候。
     那么模式匹配重新启动，并期待在下一个的A事件。

     Matches on B1 for 组合 {A1, B1}
     Matches on B3 for 组合 {A2, B3}
     Matches on B4 for 组合 {A4, B4}


     every A -> B

     该模式每当进入一个B 事件就去匹配他前面的所有A 事件
     Matches on B1 for 组合 {A1, B1}
     Matches on B3 for 组合 {A2, B3} and {A3, B3}
     Matches on B4 for 组合 {A4, B4}


     A -> every B

     该模式触发A 事件后，每进入一个B 事件都触发
     Matches on B1 for 组合 {A1, B1}.
     Matches on B2 for 组合 {A1, B2}.
     Matches on B3 for 组合 {A1, B3}
     Matches on B4 for 组合 {A1, B4}


     every A -> every B

     通过每个B 事件触发每个A 事件
     Matches on B1 for 组合 {A1, B1}.
     Matches on B2 for 组合 {A1, B2}.
     Matches on B3 for 组合 {A1, B3} and {A2, B3} and {A3, B3}
     Matches on B4 for 组合 {A1, B4} and {A2, B4} and {A3, B4} and {A4, B4}




     #考虑三个事件互相跟随的表达式

     every (A -> B -> C)
     该模式首先查找A事件。当A事件到来时，它等待B事件。 B事件到达后，该模式寻找一个C事件。
     假设B事件以及C事件之间的第二A2事件到达。该模式将忽略A2事件因为它的再想找一个C事件。
     最后，当C事件到模式后激发匹配。然后，引擎将重新启动，开始寻找下一个A事件进入。


     every A -> B -> C
     这种模式为每个A之后，无论何时在A事件到达事件，通过A，B事件，然后一个C事件匹配成功。
     请注意，对于每一个到达的A 事件后，将启动一个新的子表达式寻找 B事件，然后是C，输出匹配事件的每一个相结合的表达式



     6.5.1.2. Limiting Subexpression Lifetime 限制子表达式
     =======================================================
     事件的进入顺序为
     A1   A2   B1

     这种模式对B1的到来匹配和输出两个事件（长度2 ，如果使用一个监听器数组） 。这两个事件是组合{ A1，B1 }和{ A2，B1 }
     every a=A -> b=B

     下一个模式的B1到达匹配仅输出最后一个A 事件，是结合{ A2，B1 }
     every a=A -> (b=B and not A)
     参考{@link #every()} 方法中epl5 语句




     ####6.5.1.3. Every Operator Example 操作实例
     =======================================================

     every A  -> (B -> C) where timer:within(1 hour)

     首先，该模式如上面从来没有停止寻找事件A 事件。
     当A1到达时，该模式启动一个新的子表达式A1保持在内存中，并查找任何B项。与此同时，它也一直在寻找更多的à事件。
     当A2到达时，该模式启动一个新的子表达式，保持A2在内存中，并查找任何B项。与此同时，它也一直在寻找更多的à事件。
     A2的到来后，有3子表达式活动：

     与A1的第一个有效的子表达式在内存中，寻找任何B项。
     与A2的第二个活跃的子表达式在内存中，寻找任何B项。
     第三个积极的子表达式，寻找下一个A的事件。

     在上面的图中，我们指定了一个1小时的使用寿命为子表达式找B和C的事件。
     因此，如果没有B和没有C事件A1后，在1小时内到达，第一个子表达式消失。
     如果没有B而没有C A2事件后1小时内到达，计算第二个子消失。
     然而，第三子表达式停留四处寻找多项的活动。
     如上图所示，从而在图案上的C1到来的组合{ A1，B1， C1 } ，并为组合{ A2 ，B1，C1 } ，只要A1和A2的一个小时之内使B1和C1到达匹配。
     现在你可能会问如何在{ A1  B1，C1 }和{ A2 ，B2，C2 } ，而不是匹配，
     因为你可能需要关联一个给定的属性。例如 id 的关联 every a=A -> (B(id=a.id -> C(id=a.id)) where timer:within(1 hour)


     ####6.5.2. Every-Distinct 字段值去重复

     语法规则:
     every-distinct(distinct_value_expr [, distinct_value_exp[...][, expiry_time_period])

     Example示例：
     every-distinct(s.sensor) s=Sample
     every-distinct(a.aprop, b.bprop) (a=A and b=B)
     every-distinct(a.aprop) a=A -> b=B(bprop = a.aprop)

     // 无效的，过滤性事件没有标签
     every-distinct(aprop) A
     // 无效的: property not from a sub-expression of every-distinct
     a=A -> every-distinct(a.aprop) b=B

     every-distinct(a.aprop) (a=A and not B)
     every-distinct(a.timestamp, 10 seconds) a=A

     //这个 a.timestamp 在这个语句是不会过期的，不推荐这种写法
     every-distinct(a.timestamp) a=A where timer:within(10 sec)




     ####6.5.3. Repeat 重复发生操作

     语法规则：
     [match_count] repeating_subexpr

     此模式触发最后5个到达的事件
     [5] A

     括号必须用于嵌套模式的子表达式。这种模式触发当最后任何五个(A或B)到达的事件：
     [5] (A or B)

     没有括号的模式语义变化,根据前面描述的运算符优先级。
     这种模式当最后一共有五事件或一个事件到达,哪个到达哪个最先发生:
     [5] A or B

     标签可以运用名字在sub-expressions或者事件过滤器表达式的模式中。
     下一个模式寻找A事件之后的B事件,和第二个A事件之后第二个B事件。
     输出事件提供索引和数组属性相同的名称:select a, a[0], a[0].id, a[1], a[1].id
     from pattern [ every [2] a=A ]
     [2] (a=A -> b=B)

     由于模式匹配返回多个事件,应该将输出使用下标标记为一个数组,例如:
     select a[0].id, a[1].id from pattern [every [2] (a=A and not C)]





     ####6.5.4.1. Unbound Repeat 释放重复发生的操作

     引擎匹配repeated_pattern_expr模式直到end_pattern_expr子表达式的求值结果为true停止执行。

     语法规则:
     repeated_pattern_expr until end_pattern_expr

     这个模式,不断寻找A事件,直到B事件到达:
     A until B

     嵌套模式sub-expressions必须放置在括号，操作 until 优先级大于模式。
     这个示例收集所有A或B事件10秒并将接收到的事件在索引属性' A '和' B ':
     (a=A or b=B) until timer:interval(10 sec)

     Bound Repeat - Bounded Range 绑定重复-限制范围

     下面的模式至少需要3个A 事件的匹配
     如果一个B 事件 在3个A 事件到达之前到达，表达式返回 false ，匹配停止
     [3:] A until B

     下面的模式当 C or D 事件到达时, 无论发生了多少个A 或者B 事件:
     如果A 或者B 事件到达数量>3 ，引擎模式停止增加 A 或者B 的事件
     标签也只可以匹配前3个
     [:3] (a=A or b=B) until (c=C or d=D)

     下面的模式匹配2个A 事件后的B 事件
     在B 事件中做过滤条件为仅当B 事件中的 beta 的值在第一个A 事件 id 值或者第二个A 事件 id 值中
     [2] A -> B(beta in (a[0].id, a[1].id))

     下面的select子句声明展示不同的方式访问标记的事件:
     select a, a[0], a[0].id, a[1], a[1].id from pattern [ every [2] a=A ]

     1.标签本身可以用来选择一组潜在的事件。例如,上面的“a”表达式返回一个数组的基本事件的事件类型。
     2.标签作为一个索引属性返回底层事件索引。例如,“[0]”表达式返回第一个基本事件,或null如果没有这样一个事件被重复操作符匹配。
     3.标记嵌套,索引属性返回底层事件在索引的属性。例如,“[1]。id的表达式返回id的属性值的第二个事件,或null如果没有这样的第二个事件被重复操作符匹配。

     你可能不会使用索引标记子表达式中定义的重复操作符相同的子表达式。
     例如,在以下模式重复操作符的表达式(a = b()- > =(id =[0].id))和标签不能使用索引形式在过滤事件b:
     // 无效的写法
     every [2] (a=A() -> b=B(id=a[0].id))

     // 有效的
     every [2] (a=A() -> b=B(id=a.id))

     */


    /**
     * every
     *
     * @return epl1
     */
    protected static String every() {

        //every A -> B
        String epl1 = "select a.*  from pattern[every a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + "]";

        //every ( A -> B )
        String epl2 = "select a.price  from pattern[every (a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + ")]";

        //every A -> every B
        String epl3 = "select a.*  from pattern[every a=" + Apple.CLASSNAME + " -> every b=" + Banana.CLASSNAME + "]";

        // A -> every B
        String epl4 = "select a.*  from pattern[ a=" + Apple.CLASSNAME + " -> every b=" + Banana.CLASSNAME + "]";

        // every a=A -> (b=B and not A)
        String epl5 = "select a.*  from " +
                "pattern[ every a=" + Apple.CLASSNAME + " -> ( b=" + Banana.CLASSNAME + " and not " + Apple.CLASSNAME + " )]";

        return epl5;
    }

    /**
     * @return epl
     */
    protected static String everyOperatorExample() {
        //every A  -> (B -> C) where timer:within(1 hour)
        String elp1 = "select a.price  from " +
                "pattern[ every a=" + Apple.CLASSNAME + " -> ( b=" + Banana.CLASSNAME + " -> o=" + Orange.CLASSNAME + ")" +
                " where timer:within(1 hour)]";
        return elp1;
    }


    /**
     * every-distinct
     *
     * @return epl
     */
    protected static String distinct() {
        //every A  -> (B -> C) where timer:within(1 hour)
        String elp1 = "select a.price from " +
                "pattern[ every-distinct(a.price , b.id) (a=" + Apple.CLASSNAME + " ->  b=" + Banana.CLASSNAME + ")]";
        return elp1;
    }

}
