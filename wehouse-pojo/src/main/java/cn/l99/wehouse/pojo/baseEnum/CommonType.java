package cn.l99.wehouse.pojo.baseEnum;

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

    //private String name;

    private CommonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
//
//    public String getName() {
//        return this.name();
//    }
}
