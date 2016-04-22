package esper.event_type;

import com.espertech.esper.client.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-28
 * Time: 16:27
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Esper支持原生Java Map结构的事件。相对于POJO来说，Map的结构更利于事件类型的热加载，毕竟不是class，所以不需要重启JVM。
 * 所以如果系统对重启比较敏感，建议使用Map来定义事件的结构。Map的结构很简单，主要分为事件定义名和事件属性列表。
 * <p>
 * 1.Person在定义Address属性时，map的value不是Address.class，而是Address字符串，而这就代表引擎里的Address对应的Map结构定义
 * 2.事件定义注册必须是Address先于Person，因为Person用到了Address，而引擎是根据Address注册时用的名字去查找Address定义的，所以如果名字写错，引擎就找不到Address了
 * 如果Person有多个Address，则以数组方式定义Person的多个Address时，代码又变成下面的样子了
 * person.put("addresses", "Address[]");
 * <p>
 * 目标： 当Person类型的事件中name为luonanqin时，Esper能得到对应的age,children
 * TODO PropertyAccessException : Property named 'address' is not a valid property name for this type
 */
public class MapEventType {
    static EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
    static EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();

    public static void main(String[] args) {

        // Address定义
        Map<String, Object> address = new HashMap<String, Object>();
        address.put("road", String.class);
        address.put("street", String.class);
        address.put("houseNo", int.class);

        // Person定义
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("name", String.class);
        person.put("age", int.class);
        person.put("children", List.class);
        person.put("phones", Map.class);
        person.put("address", "Address");

        // 注册Address到Esper
        epAdministrator.getConfiguration().addEventType("Address", address);
        // 注册Person到Esper
        epAdministrator.getConfiguration().addEventType("Person", person);

        /**
         * 另外对于Map，Esper只支持增量更新，也就是说只能增加Map中的属性定义，
         * 而不能修改或者删除某个属性（实际上属性增多并不影响其处理性能，所以没有删除在我看来也没什么。至于修改，也只能是先注销再注册了）。
         * 我们为Person增加一个sex属性
         */
        person.put("sex", int.class);
        epAdministrator.getConfiguration().updateMapEventType("Person", person);

        /** 输出结果：
         * Person props: [address, age, name, children, phones, gender]
         */
        EventType person_EventType = epAdministrator.getConfiguration().getEventType("Person");
        System.out.println("Person  props: " + Arrays.asList(person_EventType.getPropertyNames()));
        EventType address_EventType = epAdministrator.getConfiguration().getEventType("Address");
        System.out.println("Address props: " + Arrays.asList(address_EventType.getPropertyNames()));


        //目标： 当Person类型的事件中name为luonanqin时，Esper能得到对应的age,children
        String epl = "select age,children from Person where name=\"xiaohulu\"";
        EPStatement epStatement = epAdministrator.createEPL(epl);
        //注册修改事件监听
        UpdateListener updateListener = new MyUpdateListener() {
            @Override
            public void update_Event(EventBean[] newEvents) {
                if (newEvents != null) {
                    System.out.println("~~~~~~~~~~~~~newEvents write~~~~~~~~~~~~~~");
                    System.out.println("Person's age        is \t" + newEvents[0].get("age"));
                    System.out.println("Person's children   is \t" + newEvents[0].get("children"));
                    System.out.println("Person's address    is \t" + newEvents[0].get("address"));
                }
            }
        };
        epStatement.addListener(updateListener);
        final EPRuntime epRuntime = defaultProvider.getEPRuntime();

        Map<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put("road", "BeiJing.100");
        Map<String, Object> personMap = new HashMap<String, Object>();
        personMap.put("name", "xiaohulu");
        personMap.put("address", addressMap);

        epRuntime.sendEvent(personMap, "Person");
    }
}
