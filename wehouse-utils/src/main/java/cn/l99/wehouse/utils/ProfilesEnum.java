package cn.l99.wehouse.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 环境枚举类
 *
 * @author L99
 */
public enum ProfilesEnum {

    DEV("dev"),
    UAT("uat");

    private String desc;

    private static Map<String, ProfilesEnum> lookup = new HashMap<>(2);

    static {
        for (ProfilesEnum profilesEnum : ProfilesEnum.values()) {
            lookup.put(profilesEnum.getDesc(), profilesEnum);
        }
    }

    ProfilesEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public static ProfilesEnum get(String desc) {
        return lookup.get(desc);
    }

}
