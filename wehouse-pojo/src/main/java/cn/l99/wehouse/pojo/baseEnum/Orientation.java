package cn.l99.wehouse.pojo.baseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 房屋朝向枚举
 * <h1>
 * East: 东
 * South：南
 * West：西
 * North：北
 * </h1>
 *
 * @author l99
 */
public enum Orientation {

    E("东"),
    S("南"),
    W("西"),
    N("北");

    private static Map<String, Orientation> lookup = new HashMap<>();

    static {
        for (Orientation orientation : Orientation.values()) {
            lookup.put(orientation.getValue(), orientation);
        }
    }

    private String value;

    private Orientation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Orientation get(String value) {
        return lookup.get(value);
    }
}
