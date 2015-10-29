package esper.javabean;

/**
 * 生产地
 *
 * @author wei.Li by 14-8-12.
 */
public class Yieldly {

    public static final String CLASSNAME = Yieldly.class.getName();

    private String address;

    public Yieldly(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}
