package esper.epl;

import esper.javabean.Apple;
import esper.javabean.Banana;

/**
 * @author wei.Li by 14-8-18.
 */
class EPL_7_Patterns_3 {

    /**
     * And
     *
     * @return epl
     */
    protected static String every() {
        String epl1 = "select a.*  from pattern[every a=" + Apple.CLASSNAME + " -> b=" + Banana.CLASSNAME + "]";
        return epl1;
    }
}
