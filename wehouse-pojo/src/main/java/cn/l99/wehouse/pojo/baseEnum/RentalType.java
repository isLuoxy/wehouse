package cn.l99.wehouse.pojo.baseEnum;

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

    private RentalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
