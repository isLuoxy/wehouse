package cn.l99.wehouse.pojo.baseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 房屋出租方式枚举
 * Z-整租； H-合租
 *
 * @author l99
 */
public enum RentalType {

    Z("整租"),
    H("合租");

    private String value;

    private static final Map<String, RentalType> lookup = new HashMap<String, RentalType>();

    static {
        for (RentalType rentalType : RentalType.values()) {
            lookup.put(rentalType.getValue(), rentalType);
        }
    }

    private RentalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RentalType get(String value) {
        return lookup.get(value);
    }

}
