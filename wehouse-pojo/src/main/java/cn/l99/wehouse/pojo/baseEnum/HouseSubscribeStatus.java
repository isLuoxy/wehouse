package cn.l99.wehouse.pojo.baseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 预约看房状态枚举
 * <h1>
 * Wait：待确认
 * verify：已确认
 * Cancel: 已取消
 * Finish: 已完成
 * </h1>
 */
public enum HouseSubscribeStatus {

    W("待确认"),
    V("已确认"),
    C("已取消"),
    F("已完成");

    private String value;
    private static final Map<String, HouseSubscribeStatus> lookup = new HashMap<String, HouseSubscribeStatus>();

    static {
        for (HouseSubscribeStatus houseSubscribeStatus : HouseSubscribeStatus.values()) {
            lookup.put(houseSubscribeStatus.getValue(), houseSubscribeStatus);
        }
    }

    HouseSubscribeStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static HouseSubscribeStatus get(String value) {
        return lookup.get(value);
    }


}
