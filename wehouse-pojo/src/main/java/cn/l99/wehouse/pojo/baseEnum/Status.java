package cn.l99.wehouse.pojo.baseEnum;

/**
 * 用户、房东状态枚举
 *<h1>
 *     Normal: 正常
 *     Prohibition：封禁
 *</h1>
 * @author L99
 */
public enum Status {
    N("正常"),
    P("封禁");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
