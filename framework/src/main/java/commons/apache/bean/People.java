package commons.apache.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by lw on 14-4-18.
 */
public class People {

    private String name;
    private String sex;
    private String[] sons;
    private ArrayList<String> cars;
    private Map<String, String> addressMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String[] getSons() {
        return sons;
    }

    public void setSons(String[] sons) {
        this.sons = sons;
    }

    public ArrayList<String> getCars() {
        return cars;
    }

    public void setCars(ArrayList<String> cars) {
        this.cars = cars;
    }

    public Map<String, String> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<String, String> addressMap) {
        this.addressMap = addressMap;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", sons=" + Arrays.toString(sons) +
                ", cars=" + cars +
                ", addressMap=" + addressMap +
                '}';
    }
}
