package cn.l99.wehouse.pojo.baseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 房屋状态枚举
 * <h1>
 * Unaudited:未审核
 * AuditPass: 审核通过
 * Rented： 已出租
 * LogicalDeletion：逻辑删除
 * </h1>
 *
 * @author l99
 */
public enum HouseStatus {

    U("未审核"),
    A("审核通过"),
    R("已出租"),
    L("逻辑删除");

    private static Map<String, HouseStatus> lookup = new HashMap<>();

    static {
        for (HouseStatus houseStatus : HouseStatus.values()) {
            lookup.put(houseStatus.getValue(), houseStatus);
        }
    }

    private String value;

    HouseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HouseStatus get(String value) {
        return lookup.get(value);
    }

}
