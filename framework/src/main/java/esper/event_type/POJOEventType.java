package esper.event_type;

import com.espertech.esper.client.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-28
 * Time: 14:41
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 目标1：当Person类型的事件中name为xiaohulu时，Esper能得到对应的age,children和address
 * 目标2：当Person类型的事件中name为xiaohulu时，Esper能得到对应的第二个孩子,家里的电话和家庭住址在哪条路上
 */
class Person {
    public static final String CLASSNAME = Person.class.getName();

    private String name;
    private int age;
    private List<Child> children;
    private Map<String, Integer> phones;
    private Address address;

    Person(String name, int age, List<Child> children, Map<String, Integer> phones, Address address) {
        this.name = name;
        this.age = age;
        this.children = children;
        this.phones = phones;
        this.address = address;
    }

    /**
     * @return 随机获取Person 对象
     */
    public static Person getRandomPerson() {
        Random random = new Random();
        String name = UUID.randomUUID().toString();
        int age = random.nextInt(100);
        List<Child> children = Child.getRandomChild(10);
        Map<String, Integer> phones = Maps.newHashMap();
        phones.put("home", 110);
        phones.put("iphone", 120);
        Address address = Address.getRandomAddress();
        return new Person(name, age, children, phones, address);
    }

    /**
     * @param name 对象的 name
     * @return 随机获取Person 对象
     */
    public static Person getRandomPerson(String name) {
        Person person = getRandomPerson();
        person.setName(name);
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public List<Child> getChildren() {
        return children;
    }

    public Child getChild(int index) {
        return children.get(index);
    }

    public int getPhones(String name) {
        return phones.get(name);
    }

    public Map<String, Integer> getPhones() {
        return phones;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", children=" + children +
                ", phones=" + phones +
                ", address=" + address +
                '}';
    }
}

class Address {
    private String road;
    private String street;
    private int houseNo;

    Address(String road, String street, int houseNo) {
        this.road = road;
        this.street = street;
        this.houseNo = houseNo;
    }

    /**
     * @return 随机获取一个Address 对象
     */
    public static Address getRandomAddress() {

        return new Address(UUID.randomUUID().toString(), UUID.randomUUID().toString(), (int) (Math.random() * 1000));
    }

    public String getRoad() {
        return road;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNo() {
        return houseNo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "road='" + road + '\'' +
                ", street='" + street + '\'' +
                ", houseNo=" + houseNo +
                '}';
    }
}

class Child {
    private String name;
    private int sex;

    Child(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }

    /**
     * @param num 获取对象的个数
     * @return 随机获取num个Child 对象
     */
    public static List<Child> getRandomChild(int num) {

        List<Child> childList = Lists.newArrayList();
        num = num < 1 ? 10 : num;
        for (int i = 0; i < num; i++) {
            childList.add(new Child("child" + i, i));
        }
        return childList;
    }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "Child{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}


/**
 * 执行测试
 */
public class POJOEventType implements Runnable {

    private static final EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
    private static final EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();
    private static final EPRuntime epRuntime = defaultProvider.getEPRuntime();

    public static void main(String[] args) {


        POJOEventType pojoEventType = new POJOEventType();
        //当Person类型的事件中name为xiaohulu时，Esper能得到对应的age,children和address
        //pojoEventType.getFiledByName();

        //当Person类型的事件中name为xiaohulu时，Esper能得到对应的第二个孩子,家里的电话和家庭住址在哪条路上
        pojoEventType.getFiledAndArrayIndexByName();

    }

    /**
     * 当Person类型的事件中name为xiaohulu时，Esper能得到对应的age,children和address
     * get...方法必须有
     */
    private void getFiledByName() {

        final String epl = "select age,children,address from " + Person.CLASSNAME + " where name=\"xiaohulu\"";

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

        //默认添加2个事件
        Person person = Person.getRandomPerson();
        epRuntime.sendEvent(person);

        Person randomPerson = Person.getRandomPerson("xiaohulu");
        epRuntime.sendEvent(randomPerson);
    }

    /**
     * 当Person类型的事件中name为xiaohulu时，Esper能得到对应的第二个孩子,家里的电话和家庭住址在哪条路上
     * Person 应该有以下方法对应
     * {@link Person#getChild(int)}     对应的第二个孩子
     * {@link Person#getPhones(String)} 家里的电话
     */
    private void getFiledAndArrayIndexByName() {

        final String epl = "select children[1], phones('home'), address.road from " + Person.CLASSNAME + " where name=\"xiaohulu\"";

        EPStatement epStatement = epAdministrator.createEPL(epl);

        //注册的事件类型 bean
        EventType[] eventBeans = epAdministrator.getConfiguration().getEventTypes();
        for (EventType eventBean : eventBeans) {
            System.out.println(eventBean.getName());
        }

        UpdateListener updateListener = new UpdateListener() {

            @Override
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                if (newEvents != null) {
                    System.out.println("~~~~~~~~~~~~~newEvents write~~~~~~~~~~~~~~");
                    System.out.println("Person's children[1]        is \t" + newEvents[0].get("children[1]"));
                    System.out.println("Person's phones('home')     is \t" + newEvents[0].get("phones('home')"));
                    System.out.println("Person's address.road       is \t" + newEvents[0].get("address.road"));
                }
                if (oldEvents != null) {
                    System.out.println("~~~~~~~~~~~~~oldEvents write~~~~~~~~~~~~~~");
                    System.out.println("Person's children[1]        is \t" + oldEvents[0].get("children[1]"));
                    System.out.println("Person's phones('home')     is \t" + oldEvents[0].get("phones('home')"));
                    System.out.println("Person's address.road       is \t" + oldEvents[0].get("address.road"));
                } else
                    System.out.println("~~~~~~~~~~~~~oldEvents not find ~~~~~~~~~~~~~~");
            }
        };
        //注册修改事件监听
        epStatement.addListener(updateListener);

        //启动线程添加事件
        new POJOEventType().run();

    }

    @Override
    public void run() {
        int i = 0;
        while (i < 100) {
            i++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i % 3 == 0)
                epRuntime.sendEvent(Person.getRandomPerson());
            else
                epRuntime.sendEvent(Person.getRandomPerson("xiaohulu"));
        }

    }
}
