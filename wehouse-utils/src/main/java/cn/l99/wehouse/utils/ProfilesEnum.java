package cn.l99.wehouse.utils;

/**
 * 环境枚举类
 *
 * @author L99
 */
public enum ProfilesEnum {

    DEV("dev"),
    UAT("uat");

    private String desc;

    ProfilesEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
