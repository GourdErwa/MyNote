package commons.apache.bean;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lw on 14-4-18.
 * Bean Copy，也就是copy bean的属性。
 * 如果做分层架构开发的话就会用到,比如从PO（Persistent Object）拷贝数据到VO（Value Object）
 */
public class Commons_BeanUtils {

    /**
     * Map设置到bean
     */
    public static void setPeopleForMap() {

        People people = new People();

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "demo");
        map.put("sex", "女");

        try {
            //The class[people] must be public...
            BeanUtils.populate(people, map);

            System.out.println(people.toString());

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态取值-Map、List...
     */
    public static void dynamicPeople() {
        People people = new People();
        people.setName("demo");
        String address;
        try {

            BeanUtils.getProperty(people, "name");

            Map<String, String> addressMap = new HashMap<String, String>();
            addressMap.put("1", "中南海");
            addressMap.put("2", "天安门");
            BeanUtils.setProperty(people, "addressMap", addressMap);
            address = BeanUtils.getProperty(people, "addressMap(2)");//括号下标1开始

        } catch (IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * strust 返回封装的表单对象转换后直接赋值
     * 此处用class Form模拟表单
     *
     * @param form form
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    public static void getPeopleForForm(Form form)
            throws InvocationTargetException, IllegalAccessException {
        People people = form;
        BeanUtils.copyProperties(people, form);
    }

    class Form extends People {

    }
}



