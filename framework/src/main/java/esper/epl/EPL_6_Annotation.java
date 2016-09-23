package esper.epl;

import esper.javabean.Apple;

/**
 * AIP - 5.2.7. Annotation
 *
 * @author wei.Li by 14-8-12.
 */
class EPL_6_Annotation {

    /**
     * Table 5.2. Built-In EPL Statement Annotations
     =====================================================
     Name	指定一个EPL名称.
     属性是:
     value : Statement name.

     @Name("MyStatementName")

     ------------------------------------------------------

     Description  对EPL进行描述.
     属性是:
     value : Statement description.

     @Description("Place statement description here.")

     ------------------------------------------------------

     Tag	对EPL进行额外的说明.
     属性是:
     name : Tag name.
     value : Tag value.

     @Tag(name="MyTagName",value="MyTagValue")

     ------------------------------------------------------
     Priority    指定EPL的优先级，参数只有一个，并且整数（可负可正）.
     属性是:
     value : priority value.

     @Priority(10)

     ------------------------------------------------------

     Drop    指定事件经过此EPL后不再参与其他的EPL计算
     No attributes.

     @Drop

     ------------------------------------------------------

     Hint    为EPL加上某些标记，让引擎对此EPL产生其他的操作，会改变EPL实例的内存占用，但通常不会改变输出.
     用法参考 API-20.2.23. Consider using Hints
     属性是:
     value : 由Esper 提供关键字，不区分大小写，多个以逗号隔开.

     @Hint('iterate_only')

     ------------------------------------------------------

     Hook    Use this com.gourd.erwa.annotation to register one or more statement-specific hooks providing a hook type for each individual hook,
     such as for SQL parameter, column or row conversion.
     属性是 the hook type and the hook itself (usually a import or class name):

     @Hook(type=HookType.SQLCOL,hook='MyDBTypeConvertor')

     ------------------------------------------------------

     Audit    EPL添加此注解后，可以额外输出EPL运行情况，有点类似日志
     API - 16.3.1. @Audit Annotation
     optional value : 由Esper 提供关键字，不区分大小写，多个以逗号隔开.

     Category	        Description
     ==============================================================
     Dataflow-Source	Each data flow source operator providing an event.
     Dataflow-Op	    Each data flow operator processing an event.
     Dataflow-Transition	Each data flow instance state transition.
     Exprdef	        Each expression declaration name and return value.
     Expression	        Each top-level expression and its return value.
     Expression-nested	Each expression including child or nested expressions and their return value.
     Insert	            Each event inserted via insert-into.
     Pattern	        Each pattern sub-expression and its change in truth-value.
     Pattern-instances	Each pattern sub-expression and its count of active instances.
     Property	        Each property name and the event's property value.
     Schedule	        Each schedule modification and trigger received by a statement.
     Stream	            Each new event received by a statement.
     View	            Each view name and its insert and remove stream.

     @Audit

     ------------------------------------------------------

     EventRepresentation    这是用来指定EPL产生的计算结果事件包含的数据形式
     参数只有一个，即array=true或array=false。false为默认值，代表数据形式为Map，若为true，则数据形式为数组。

     @EventRepresentation(array=true)

     ------------------------------------------------------

     IterableUnbound    对于迭代与未绑定的数据流报表时使用，指示引擎保留了最后一个事件进行迭代。

     @IterableUnbound

     */


    /**
     * simple example
     * <p>
     * epAdministrator.getStatementNames() 获取到 epl 名称
     * 不用注解声明默认EPL-name格式为：467a8cc7-59a0-448e-bf68-f2f81b5738a4
     *
     * @return epl
     */
    protected static String example() {

        String epl1 = "@Name('AppleFilter')" +
                "@Description('filter id = 1')" +
                "@Tag(name='author',value='GourdErwa')" +
                "@Priority(2)" +
                "@Audit" +
                "@EventRepresentation(array=false)" +
                "select * from " + Apple.CLASSNAME + "(id='1')";

        return epl1;
    }

}
