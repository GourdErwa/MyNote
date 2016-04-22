package esper.epl;

import esper.javabean.Apple;
import esper.javabean.Banana;
import esper.javabean.Yieldly;

/**
 * Subqueries子查询
 * <p>
 * 基本上与SQL 的写法类似。
 * <p>
 * API - 5.11. Subqueries
 *
 * @author wei.Li by 14-8-12.
 */
class EPL_5_Subqueries {


    /**
     * 以下限制适用于子查询：
     * <p>
     * 1.子查询流定义必须定义一个数据窗口或其它视图，以限制子查询的结果，减少了保持子查询执行的事件数
     * 2.子查询只能从子句包含一个select子句，一个where子句和GROUP BY子句。 having子句，以及连接，外连接和输出速率限制在子查询中是不允许的。
     * 3.如果使用聚合函数在子查询时，请注意以下限制：
     * 3.1没有一个相关流（次）的性质可以在聚合函数中使用。
     * 3.2子选择流的特性都必须在聚合函数。
     *
     * @return epl[]
     */
    protected static String subqueries() {

        /**
         * 子查询结果作为外部事件的属性
         * Apple事件 的 id 与 当前窗口中的最后一个Banana 的price 作为结果集输出
         */
        String epl1 = "select id, (select price from " + Banana.CLASSNAME + ".std:lastevent()) as lastBanana_price from " + Apple.CLASSNAME;

        /**
         * 子查询关联外部事件的属性
         */
        String epl2 = "select * from " + Apple.CLASSNAME + " as Apple where 3 = " +
                "  (select price from " + Banana.CLASSNAME + ".std:unique(id) where id = Apple.id)";
        //每进入一个Apple 事件。查询 id 与当前窗口中Banana 按照std:unique(id)保留的所有唯一值，无一个匹配则输出 null
        String epl3 = "select id, (select price from " + Banana.CLASSNAME + ".std:unique(id) where id = apple.id) as price from " + Apple.CLASSNAME + " as apple";

        /**
         * 子查询内部事件作为外部事件的属性
         */
        String epl4 = "select (select * from " + Banana.CLASSNAME + ".std:lastevent()) as banana from " + Apple.CLASSNAME;

        /**
         * 子查询中应用聚合函数
         */
        String epl5 = "select * from " + Apple.CLASSNAME + " where price > (select max(price) from " + Banana.CLASSNAME + "(id='Banana_Id').std:lastevent())";
        return epl4;
    }


    /**
     * The 'exists' Keyword
     *
     * @return epl
     */
    protected static String exists() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where exists (select * from " + Banana.CLASSNAME + ".std:unique(id) where id = RFID.id)";
        return epl1;
    }


    /**
     * The 'in' and 'not in' Keywords
     *
     * @return epl
     */
    protected static String in_notin() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where RFID.id in (select id from " + Banana.CLASSNAME + ".std:unique(id))";
        return epl1;
    }


    /**
     * The 'any' and 'some' Keywords
     * any :至少大于结果集的一个 or 连接
     * some:至少等于结果集的一个 or 连接
     *
     * @return epl
     */
    protected static String any_some() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where RFID.id > any(select id from " + Banana.CLASSNAME + ".win:keepall())";
        return epl1;
    }


    /**
     * The 'all' Keyword
     * all:必须小于所有结果集的值才满足 and 连接
     *
     * @return epl
     */
    protected static String all() {
        String epl1 = "select * from " + Apple.CLASSNAME + " as RFID " +
                "  where RFID.id < all(select id from " + Banana.CLASSNAME + ".win:keepall())";
        return epl1;
    }


    /**
     * EPL也同样支持join，并且包含了full outer join / left outer join / right outer join / inner join等。
     * 和sql基本无差别。
     *
     * @return epl
     */
    protected static String join() {
        /**
         * inner join
         在没有任何关键字的修饰下，即为默认join方式，也就是inner join。
         必须等到所有join的事件都到了才可能输出，因为要是有where关联两个事件，得满足where条件了才能输出
         如果只有Apple到或者Banana到都不会输出内容。std:lastevent是一种data window。如果不加特殊修饰的话（特殊修饰？下面会告诉你），
         事件必须有data window或者view修饰，否则会出现语法错误。
         当然，不同的事件可以用不同的data window修饰，并没有规定要一样。
         */
        String epl1 = "select * from " + Apple.CLASSNAME + ".std:lastevent(), " + Banana.CLASSNAME + ".std:lastevent()";

        /**
         * full outer join
         上面看到的默认join方式是要求所有join的事件都必须到达引擎才会输出，并且join的事件之间通过where子句设置了条件判断，
         还得到达的两个事件满足条件了才能输出，而full outer join正好解决了这个问题，不管哪个事件到达，不管是否符合条件，都会输出。

         输出结果有4种可能：
         a.当只有Apple事件到达，没有满足join条件，会输出Apple事件，且Banana事件为null。
         b.当只有Banana事件到达，没有满足join条件，会输出Banana事件，且Apple事件为null。
         c.当两个事件都到达了，且没有满足join条件，即price不相等，则a，b情况各出现一次。
         d.当两个事件都到达了，且满足join条件，即price相等，即可输出满足条件的事件。
         所以说不管什么情况下，当前进入的事件都会输出，至于join的那个事件，满足即输出事件，不满足即输出null。
         */
        String epl2 = "select * from " + Apple.CLASSNAME + ".std:lastevent() as o full outer join " + Banana.CLASSNAME + ".std:lastevent() as b on o.price = b.price";

        /**
         * left outer join
         full outer join输出了所进入的所有事件，不满足join条件的就输出null，
         而left outer join则规定关键字左边的事件可以即刻输出，而关键字右边的事件必须满足join条件才可输出。

         因为Apple事件在left outer join的左边，所以他的输出不受join条件的限制，即事件到来该怎么输出怎么输出。
         但是Banana就不同，由于有join条件限制，即两个事件的price要相等，所以如果Banana事件到达的时候，
         如果没有满足条件的Apple事件，则Banana事件是不会输出的。（注意：输出null也算输出，这里是null都不会输出，即不触发listener）
         */
        String epl3 = "select * from " + Apple.CLASSNAME + ".std:lastevent() as pi left outer join " + Banana.CLASSNAME + ".std:lastevent() as pe on pi.price = pe.price";

        /**
         * right outer join
         和left outer join相反，在关键字右边的事件不受join条件约束，而左边的事件必须满足join条件才可输出。具体例子我就不举了，大家可以写两个句子试试。

         此外，在使用以上4种join的时候，可以多种join混用。
         on后面的表达式是join的限制条件，且只能用“=”，如果想用其他操作符，则必须放到where子句中，这点尤其要注意。
         多个限制条件只能用and连接，不能用逗号，且限制的事件也要一样。
         */
        String epl4 = "select * from " + Yieldly.CLASSNAME + ".std:lastevent() as a " +
                "     left outer join " + Banana.CLASSNAME + ".std:lastevent() as b on a.price = b.price " +
                "     full outer join " + Apple.CLASSNAME + ".std:lastevent() as o on o.price = a.price";
        /**
         * // a，b分别是两个事件的别名

         // 正确写法
         ……on a.price = b.price and a.size = b.size……

         // 错误写法1：不能用逗号连接
         ……on a.price = b.price, a.size = b.size……

         // 错误写法2：必须针对同样的事件进行限制（c是另一个事件的别名）
         ……on a.price = b.price and a.size = c.size……

         */


        /**
         * Unidirectional Join
         之前说到，如果不加特殊修饰，则join的事件都需要data window或者view修饰，目的是为了暂存事件以便等待满足条件的事件并执行join。
         如果想让某个事件到来时直接触发join，不需要暂存，也就是不需要data window或者view修饰，则需要加上一个特殊关键字——unidirectional。

         epl5的意思是：维持最新的Banana事件，直到一个和Banana的price相等的Apple事件到来时输出两者。
         由于有unidirectional的修饰，表明Apple事件是即时触发join操作，也就是说进入此EPL的Apple事件是无状态的。
         所以当Apple事件到来时，如果没有price相等的Banana，则什么输出也没有，即使下一个Banana事件的price和之前来的Apple的price相等也不会有输出，
         因为那个Apple事件已经从这个句子的上下文中移除了。
         */
        String epl5 = "select * from " + Apple.CLASSNAME + " as a unidirectional, " + Banana.CLASSNAME + ".std:lastevent() as b where a.price = b.price";
        /**
         * unidirectional使用很简单，但是也有其限制：
         1.在一个join句子中，unidirectional关键字只能用于一个事件流。
         2.用unidirectional修饰的事件流，不能通过esper的查询api查出来，因为该事件流是无状态的，不会暂存在引擎中，所以就没法查了。（关于查询api，后面的章节会详说）
         3.使用了unidirectional修饰的事件流就不能再用data window或者view修饰了，也就是说他们是互斥的。
         */

        return epl5;
    }

}
