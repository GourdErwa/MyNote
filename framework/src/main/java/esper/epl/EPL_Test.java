package esper.epl;

import esper.javabean.Apple;

/**
 * @author wei.Li by 15/11/19
 */
class EPL_Test {

    public static String time() {
        return "select count(*), 'a' as a from " + Apple.CLASSNAME + "(id = '1').win:time(3 minutes)  group by id having count(*) >= 2 output snapshot every 1 minutes";
    }
}
