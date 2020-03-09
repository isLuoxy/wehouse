package cn.l99.wehouse.pojo.baseEnum;

/**
 * 房屋地区等级枚举
 * <h1>
 * City: 城市
 * District: 区域
 * Street： 街道
 * </h1>
 *
 * @author l99
 */
public enum HouseAreaLevel {
    C("城市"),
    D("区域"),
    S("街道");

    private String value;

    HouseAreaLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
