package com.ktcs.kafka.streams;

/**
 * @author wei.Li by 2017/8/3
 */
abstract class Utils {

    private Utils() {
    }


    static String pars(String rStr) {

        final String result;
        switch (rStr) {
            case "l": {
                result = "r_l";
                break;
            }
            case "s": {
                result = "r_s";
                break;
            }
            case "b": {
                result = "r_b";
                break;
            }
            default: {
                result = null;
                break;
            }
        }

        return result;

    }

}
