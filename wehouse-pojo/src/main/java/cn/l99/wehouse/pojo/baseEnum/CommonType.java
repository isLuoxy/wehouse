package cn.l99.wehouse.pojo.baseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用枚举
 * Y-是； N-否
 *
 * @author l99
 */
public enum CommonType {
    Y("是"),
    N("否");

    private String value;

    CommonType(String value) {
        this.value = value;
    }

    private static final Map<String, CommonType> lookup = new HashMap<String, CommonType>();

    static {
        for (CommonType commonType : CommonType.values()) {
            lookup.put(commonType.getValue(), commonType);
        }
    }

    public String getValue() {
        return this.value;
    }

    public static CommonType get(String value) {
        return lookup.get(value);
    }
}
