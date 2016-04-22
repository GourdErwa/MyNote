package esper.epl;

import document.Undigested;
import esper.javabean.Apple;
import esper.javabean.Banana;

/**
 * API - 5.15. Creating and Using Named Windows
 * <p>
 * 创建 NamedWindow
 * 插入数据
 * 查询数据、按条件触发查询数据
 * 修改数据
 * 删除数据
 * 合并事件NamedWindow
 * 对NamedWindow建立索引
 *
 * @author wei.Li by 14-8-21.
 */
class EPL_8_NamedWindow {

    /**
     * 创建 window  - ①在现有的数据类型上创建列
     * <p>
     * 语法：
     * [context context_name]
     * create window window_name.view_specifications
     * [as] [select list_of_properties from] event_type_or_windowname
     * [insert [where filter_expression]]
     * <p>
     * 1）context是关键字，后面跟着之前定义的context的名称。
     * 关于context的内容，{@link EPL_2_Context_1,EPL_2_Context_2,EPL_2_Context_3}
     * 2）create window后跟着要创建的named window的名字，且具有唯一性。名字后面紧跟着的“.”是用来连接事件过期策略的，即view。
     * 常用的view有win:length，win:length_batch，win:time，win:time_batch，std:unique，std:groupwin及自定义view等等，并且特定的view可以连用。
     * PS：view的相关内容{@link esper.views.View}
     * 3）select子句表示将某个事件定义中的全部或者某些属性作为named window所维护的事件属性。
     * 如果将某个事件的所有属性都加入到named window中，则可以通过select子句前的as连接事件名称，并且省略select子句。
     *
     * @return epl
     */
    protected static String createWindowExistingType() {
        String epl1 = "create window AppleWindow.win:keepall() as " + Apple.CLASSNAME;
        String epl2 = "create window AppleWindow.win:time(30 sec) as " +
                "  select id as appleId, price as applePrice from OrderEvent";
        return epl1;
    }

    /**
     * 创建 window  - ②自定义列的数据类型
     * <p>
     * 语法：
     * [context context_name]
     * create window window_name.view_specifications [as] (column_name column_type
     * [,column_name column_type [,...])
     *
     * @return epl
     */
    protected static String createWindowDefiningColumnsNamesAndTypes() {
        String epl1 = "create window SecurityEvent.win:time(30 sec) " +
                "(ipAddress string, userId String, numAttempts int, properties String[])";

        /**
         * @see EPL_9_Schema
         */
        @Undigested(Description = "what is schema ?")
        String epl2 = "create schema SecurityData (name String, roles String[])";
        String epl3 = "create window SecurityEvent.win:time(30 sec) " +
                " (ipAddress string, userId String, secData SecurityData, historySecData SecurityData[])";

        return epl1;
    }


    /**
     * 创建 window  - ③将存在的 window 数据插入到新创建的 window 中
     * <p>
     * 语法：[context context_name] create window window_name.view_specifications as windowname insert [where filter_expression]
     * <p>
     * windowname后面紧跟insert，表示将该window中的事件插入到新建的named window中。where filter_expression表示过滤插入的事件。
     *
     * @return epl
     */
    protected static String populatingExistingNamedWindow() {

        String epl1 = "create window BananaEventWindow.win:time(10) as AppleEventWindow insert where price > 100";

        return epl1;
    }


    /**
     * 销毁 window
     */
    protected static void dropNamedWindow() {
        //EPStatement epStatement = epAdministrator.createEPL("epl . . .");
        //epStatement.destroy();
    }

    /**
     * 插入数据到 window
     * <p>
     * 语法：insert into window_name [(property_names)] [values (value_expressions) 或者查询方式 select value_expressions]
     * <p>
     * 查询 window 的数据，与普通查询相同
     *
     * @return epl[]
     */
    protected static String[] insertIntoDate() {

        String query_1 =
                "insert into OrdersWindow(orderId, symbol, price) values ('001', 'GE', 100)";
        //epService.getEPRuntime().executeQuery(query);

        String query_2 =
                "insert into OrdersWindow(orderId, symbol, price) select '001', 'GE', 100";
        //epService.getEPRuntime().executeQuery(query);

        //新建 window - 插入数据 - 查询数据
        String epl1 = "create window AppleWindow.win:keepall() as select id ,price from " + Apple.CLASSNAME;
        String epl2 = "insert into AppleWindow select id,price from " + Apple.CLASSNAME;
        String epl3 = "on " + Apple.CLASSNAME + " select count(*) from AppleWindow ";//查询方式与查询 javabean 方式相同
        //String epl3 = "on " + Banana.CLASSNAME + " as b select win.* from AppleWindow as win ";
        return new String[]{epl1, epl2, epl3};
    }


    /**
     * 按照一定条件触发查询 window 的数据
     * <p>
     * 语法：
     * on event_type[(filter_criteria)] [as stream_name]
     * [insert into insert_into_def]
     * select select_list
     * from window_name [as stream_name]
     * [where criteria_expression]
     * [group by grouping_expression_list]
     * [having grouping_search_conditions]
     * [order by order_by_expression_list]
     *
     * @return EPL[]
     */
    protected static String[] triggeredOnSelect() {

        //新建 window - 插入数据
        String epl1 = "create window AppleWindow.win:keepall() as select id ,price from " + Apple.CLASSNAME;
        String epl2 = "insert into AppleWindow select id,price from " + Apple.CLASSNAME;

        //触发条件查询
        String epl3 = "on " + Banana.CLASSNAME + " as b select win.* from AppleWindow as win ";

        //将查询结果插入AppleWindow 中
        // "where a.id ='1' group by win.price having win.price >1 order by win.price";

        String epl4 = "on " + Banana.CLASSNAME + " as b insert into AppleWindow select * from AppleWindow as win ";


        return new String[]{epl1, epl2, epl3};
    }


    /**
     * 按照条件触发查询并删除 window 的数据
     * on trigger
     * select [and] delete select_list...
     * ... (please see on-select for insert into, from, group by, having, order by)...
     *
     * @return epl[]
     */
    protected static String[] triggeredOnSelectDelete() {

        //新建 window - 插入数据
        String epl1 = "create window AppleWindow.win:keepall() as select id ,price from " + Apple.CLASSNAME;
        String epl2 = "insert into AppleWindow select id,price from " + Apple.CLASSNAME;

        //触发条件查询
        String epl3 = "on " + Apple.CLASSNAME + " as a select and delete window(win.*)  from AppleWindow as win ";
        //"where a.id ='1' group by win.price having win.price >1 order by win.price";

        return new String[]{epl1, epl2, epl3};
    }


    /**
     * 更新 Update window 数据
     * <p>
     * on event_type[(filter_criteria)] [as stream_name]
     * update window_name [as stream_name]
     * set mutation_expression [, mutation_expression [,...]]
     * [where criteria_expression]
     *
     * @return epl[]
     */
    protected static String[] updateNamedWindow() {
        //simple example
        String epl1 = "on UpdateOrderEvent update AllOrdersNamedWindow set price = 0";
        String epl2 = "on ZeroVolumeEvent update AllOrdersNamedWindow set price = 0 where volume <= 0";

        //对于OrderUpdateEvent或者FlushOrderEvent事件进入后触发修改 win set 的条件用 if else 匹配
        String epl3 = "on pattern [every ord=OrderUpdateEvent(volume>0) or every flush=FlushOrderEvent] " +
                "update AllOrdersNamedWindow as win" +
                "set price = case when ord.price is null then flush.price else ord.price end" +
                "where ord.id = win.id or flush.id = win.id";


        String epl4 = "on UpdateEvent as upd" +
                "update MyWindow as win" +
                "set field_a = 1," +
                "field_b = win.field_a," + // 把 a修改后的值 1 赋给 b
                "field_c = initial.field_a ";// 把 a 更新前的值赋值给 c（关键字 initial）
        /**
         * 针对 epl4 ：
         * update更新属性前会复制一份同样的事件暂存，比如initial这种操作就需要更新前的值，所以就需要我们实现序列化接口。
         * 如果不想通过代码完成这个序列化要求，也可以通过配置完成。
         * 另外还有以下几点需要注意：
         a）需要更新的属性一定要是可写的
         b）XML格式的事件不能通过此语句更新
         c）嵌套属性不支持更新
         */

        //Query 方式更新
        String query = "update AllOrdersNamedWindow set volume = 0 where volumne = 0";
        //epService.getEPRuntime().executeQuery(query);
        return new String[]{epl1, epl2};
    }


    /**
     * 删除 delete window 的数据
     * <p>
     * 语法：
     * on event_type[(filter_criteria)] [as stream_name] (Pattern 匹配->on pattern [pattern_expression] [as stream_name])
     * delete from window_name [as stream_name]
     * [where criteria_expression]
     *
     * @return epl[]
     */
    protected static String[] deleteNamedWindow() {

        //事件触发
        String epl1 = "on ZeroVolumeEvent delete from AllOrdersNamedWindow where volume <= 0";

        String epl2 = "on NewOrderEvent(volume>0) as myNewOrders" +
                "delete from AllOrdersNamedWindow as myNamedWindow " +
                "where myNamedWindow.symbol = myNewOrders.symbol";

        //Pattern 模式匹配
        String epl3 = "on pattern [every timer:interval(10 sec)] delete from MyNamedWindow";

        String epl4 = "on pattern [every ord=OrderEvent(volume>0) or every flush=FlushOrderEvent] " +
                "delete from OrderWindow as win" +
                "where ord.id = win.id or flush.id = win.id";

        //Query 方式删除
        String query = "delete from AllOrdersNamedWindow where volume <= 0";
        //epService.getEPRuntime().executeQuery(query);
        return new String[]{epl2, epl2};

    }

    /**
     * 合并事件
     * <p>
     *
     * @return epl[]
     */
    protected static String[] MergeWindow() {
    /*语法：
      on event_type[(filter_criteria)] [as stream_name]
      merge [into] window_name [as stream_name]
      [where criteria_expression]
       when [not] matched [and search_condition]
           then [
               insert [into streamname]
                   [ (property_name [, property_name] [,...]) ]
                   select select_expression [, select_expression[,...]]
                   [where filter_expression]
               |
               update set mutation_expression [, mutation_expression [,...]]
                   [where filter_expression]
               |
               delete
                   [where filter_expression]
           ]
           [then [insert|update|delete]] [,then ...]
       [when ...  then ... [...]]
     */

        /*
         a.第一行和前面的用法都一样。
         b.第二行的where语句将事件分为了matched（满足where条件）和not matched（不满足where条件）两类
         c.第三行的when配合matched或者not matched表示“window中满足where条件的事件，执行下面的操作/window中不满足where条件的事件，执行下面的操作”。
            search_condition为可选字段，表示再次过滤matched或not matched中的事件，只有没被过滤掉的事件才可以被then后面的语句操作。
         d.第四行的insert语句和之前说的insert into不太一样。
            虽然都表示插入事件，但是由于into streamname是可选，所以在只有insert关键字的情况下，会将触发的事件插入到当前的named window中。
            如果要指明插入到别的named window中就要在insert之后带上into及window的名字。
            再之后的圆括号中的内容表示要插入的事件的属性，一般情况是在将事件插入到别的window中时，用它来重命名第五行中列出的属性。
         e.第五行实际是配合第四行一起使用的。select子句不可少，不然引擎就不知道要往window中插入什么内容了。
            select的内容可以是*，也可以是属性列表。where语句再一次限制可插入的触发事件。注意select后面没有from，因为事件来源就是当时的触发事件。
         f.第七行用来更新符合条件的事件，可更新单个或多个属性，where条件判断是否可进行更新操作。
         g.第九行用来删除符合条件的事件，只包含关键字delete以及可选的where语句。
         h.最后两行表示on merge中可以有多个when，每个when可以有多个then及insert或updata或delete语句，这样就能组成一个非常复杂的merge操作了。
         */
        //在下面的例子中每一个匹配子句包含两个动作，一个动作中插入一个日志事件和第二动作插入、删除或更新
        String epl1 = "on OrderEvent oe" +
                "  merge OrderWindow pw" +
                "  where pw.orderId = oe.orderId" +
                "  when not matched " +
                "    then insert into LogEvent select 'this is an insert example' as name" +
                "    then insert select *" +
                "  when matched and oe.deletedFlag=true" +
                "    then insert into LogEvent select 'this is a delete example' as name" +
                "    then delete" +
                "  when matched" +
                "    then insert into LogEvent select 'this is a update example' as name" +
                "    then update set pw.quantity = oe.quantity, pw.price = oe.price";

        //根据条件判断，进行2此修改
        String epl2 = "on OrderEvent oe" +
                "  merge OrderWindow pw" +
                "  where pw.orderId = oe.orderId" +
                "  when matched" +
                "    then update set clearorder(pw) where oe.price < 0" +
                "    then update set pw.quantity = oe.quantity, pw.price = oe.price where oe.price >= 0";
        return new String[]{epl1, epl2};
    }


    /**
     * 对named window中存放的事件的属性建立索引
     * <p>
     * 语法：
     * create [unique] index index_name on named_window_name (property [hash| btree] [, property] [hash|btree] [,...] )
     * <p>
     * unique代表建立唯一索引，如果插入了重复的行，则会抛出异常并阻止重复行插入。
     * 如果不使用此关键字，则表示可以插入重复行。
     * index_name为索引的名称，named_window_name是要建立索引的named window。
     * 后面的括号中包含named window中的属性以及索引类型。
     * 索引类型分两种，hash索引不会排序，如果有=操作，建议使用此类型索引。
     * btree索引基于排序二叉树，适合<, >, >=, <=, between, in等操作。
     * 如果不显式声明hash或者btree，则默认为hash索引。
     *
     * @return epl[]
     */
    protected static String[] IndexingNamedWindows() {
        String epl1 = "create unique index UserProfileIndex on UserProfileWindow(userId, profileId) ";
        String epl2 = "create index idx1 on TickEventWindow(symbol hash, buyPrice btree)  ";
        return new String[]{epl1, epl2};
    }
}

