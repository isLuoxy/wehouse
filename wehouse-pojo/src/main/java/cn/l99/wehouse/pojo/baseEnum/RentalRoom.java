package cn.l99.wehouse.pojo.baseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 出租房间
 * <p>
 * 当房源出租类型为合租时使用
 *
 * @author L99
 */
public enum RentalRoom {

    E("东卧"),
    S("南卧"),
    W("西卧"),
    N("北卧"),
    SE("东南卧"),
    NE("东北卧"),
    SW("西南卧"),
    NW("西北卧");

    private String value;

    RentalRoom(String value) {
        this.value = value;
    }

    private static Map<String, RentalRoom> lookup = new HashMap<>();

    static {
        for (RentalRoom rentalRoom : RentalRoom.values()) {
            lookup.put(rentalRoom.getValue(), rentalRoom);
        }
    }

    public String getValue() {
        return value;
    }

    public static RentalRoom get(String value) {
        return lookup.get(value);
    }

}
