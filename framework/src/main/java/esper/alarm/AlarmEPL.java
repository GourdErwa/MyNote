package esper.alarm;

/**
 * @author wei.Li
 */
class AlarmEPL {

    public static final String EPL = "select avg(real_value) as real_value, " +
            "max(real_value) as max_value, min(real_value) as min_value, " +
            "max(type) as type, min(start_time) as start_time, max(end_time) as end_time, " +
            "max(alarmHandleSetting) as alarmHandleSetting " +
            "from esper.alarm.EventTest ";
    //"group by alarmHandleSetting.id1 having count(*) >= 1 output snapshot every 1 minutes";


}
