package cn.l99.wehouse.pojo.baseEnum;

/**
 * 预约看房状态枚举
 * <h1>
 *     Process：已预约看房时间
 *     Finish：看房结束
 * </h1>
 */
public enum HouseSubscribeStatus {

    P("已预约看房时间"),
    F("看房结束");

    private String value;

    private HouseSubscribeStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
