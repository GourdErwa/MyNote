package esper.event_type;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-28
 * Time: 17:03
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 对象数组和Map很像，基本没有差别。只是定义方式不一样，Esper同样也只支持增量更新。
 */
public class ObjectArrayEventType {

    /**
     * @param args args
     */
    public static void main(String[] args) {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        EPAdministrator admin = epService.getEPAdministrator();

        // Address定义
        String[] addressPropNames = {"road", "street", "houseNo"};
        Object[] addressPropTypes = {String.class, String.class, int.class};

        // Child定义
        String[] childPropNames = {"name", "age"};
        Object[] childPropTypes = {String.class, int.class};

        // Person定义
        String[] personPropNames = {"name", "age", "children", "phones", "address"};
        Object[] personPropTypes = {String.class, int.class, "Child[]", Map.class, "Address"};

        // 注册Address到Esper
        admin.getConfiguration().addEventType("Address", addressPropNames, addressPropTypes);
        // 注册Child到Esper
        admin.getConfiguration().addEventType("Child", childPropNames, childPropTypes);
        // 注册Person到Esper
        admin.getConfiguration().addEventType("Person", personPropNames, personPropTypes);

        // 新增一个sex属性
        admin.getConfiguration().updateObjectArrayEventType("Person", new String[]{"sex"}, new Object[]{int.class});

        /** 输出结果：
         * Person props: [name, age, children, phones, address, sex]
         */
        EventType event = admin.getConfiguration().getEventType("Person");
        System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));
    }
}
