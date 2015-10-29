/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 * Email :	gourderwa@163.com
 *
 *
 *
 *
 *
 *
 */

package performance;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

/**
 * @author wei.Li by 15/8/28
 */
public class MapTraversal {


    private static final Map<String, String> MAP = Maps.newHashMap();

    static {
        MAP.put("1", "1");
        MAP.put("2", "2");
    }

    public static void main(String[] args) {

        final Set<String> keySet = MAP.keySet();

        final Set<Map.Entry<String, String>> entrySet = MAP.entrySet();

    }

}
