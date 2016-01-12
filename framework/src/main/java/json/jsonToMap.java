package json;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lw on 14-7-23.
 * JSON字符串转为MAP
 */
public class jsonToMap {

    public static Map json_To_Map(String jsonStr) {
        try {
            return new ObjectMapper().readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
       /* String jsonStr = "{ \"name\" : \"liw\", \"sex\" : \"男\", \"age\" : \"23\",\"address\" : \"BeiJing\"}";
        json_To_Map(jsonStr);*/
    }
}