package esper.epl;


import document.Keyword;

/**
 * API - 5.16. Declaring an Event Type: Create Schema
 *
 * @author wei.Li by 14-8-21.
 */
class EPL_9_Schema {

    /**
     * 声明一个提供事件类型的名称和数据类型
     * <p>
     * 语法：
     * <p>
     * create [map | objectarray] schema schema_name [as]
     * (property_name property_type [,property_name property_type [,...])
     * [inherits inherited_event_type[, inherited_event_type] [,...]]
     * [starttimestamp timestamp_property_name]
     * [endtimestamp timestamp_property_name]
     * [copyfrom copy_type_name [, copy_type_name] [,...]]
     * <p>
     * 1.任何Java类名，完全合格或引入简单的类名配置。
     * 2.添加左右方括号[]到任何类型来表示一个数组类型的事件属性。
     * 3.使用事件类型名称作为属性类型。
     * <p>
     *
     * @return epl[]
     */
    protected static String declareSchema() {
        // Declare type SecurityEvent
        String epl1 = "create schema SecurityEvent as (ipAddress string, userId String, numAttempts int)";

        // 声明指定字段名称和数据类型 与 POJO[hostinfo]
        String epl2 = "create schema AuthorizationEvent(group String, roles String[], hostinfo com.mycompany.HostNameInfo)";

        // 声明一个有其他 schema 数组类型的字段innerEvents
        String epl3 = "create schema CompositeEvent(group String, innerEvents SecurityEvent[])";

        // 声明类型WebPageVisitEvent继承自PageHitEvent所有属性
        @Keyword(keyWord = "inherits", Description = "继承")
        String epl4 = "create schema WebPageVisitEvent(userId String) inherits PageHitEvent";

        // 声明一个类型的起始和结束时间戳（即事件与持续时间）(i.e. event with duration)
        String epl5 = "create schema RoboticArmMovement (robotId string, startts long, endts long)" +
                "starttimestamp startts endtimestamp endts";

        //创建具有SecurityEvent的所有属性加一个用户名属性类型
        @Keyword(keyWord = "copyfrom", Description = "复制")
        String epl6 = "create schema ExtendedSecurityEvent (userName string) copyfrom SecurityEvent";

        //创建具有SecurityEvent的所有属性类型
        String epl7 = "create schema SimilarSecurityEvent () copyfrom SecurityEvent";

        //创建具有SecurityEvent和WebPageVisitEvent的所有属性加一个用户名属性类型
        String epl8 = "create schema WebSecurityEvent (userName string) copyfrom SecurityEvent, WebPageVisitEvent";

        // 显示完整的包名 LoginEvent event type
        String epl9 = "create schema LoginEvent as com.mycompany.LoginValue";

        // 当把包名配置后，无需加包名
        String epl10 = "create schema LogoutEvent as SignoffValue";

        return epl1;
    }

    /**
     * 使用实例
     *
     * @return epl
     */
    protected static String useSchema() {
        String epl1 = "create schema SecurityData (name String, roles String[])";
        String epl2 = "create window SecurityEvent.win:time(30 sec) " +
                " (ipAddress string, userId String, secData SecurityData, historySecData SecurityData[])";

        return epl1;
    }

}
