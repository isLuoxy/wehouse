package cn.l99.wehouse.pojo.baseEnum;

/**
 * 错误码枚举定义
 *
 * @author L99
 */
public enum ErrorCode {

    // 用户名已存在
    DUPLICATE_USERNAME(4001, "用户名已存在"),

    // 手机号已存在
    DUPLICATE_PHONE(4002, "该手机号已被注册"),

    // 验证码错误
    ERROR_CODE(4003, "验证码错误"),

    // 注册失败
    REGISTER_FAIL(4004, "注册失败"),

    // 验证码发送失败
    CODE_SEND_FAILED(4005, "验证码发送失败"),

    // 该用户不存在
    NOT_EXIST(4101, "该用户不存在"),

    // 用户名与密码不匹配
    MISMATCH(4102, "账户名与密码不匹配，请重新输入"),

    // 用户未登录
    NOT_LOGIN(4103, "未登录，请登录"),

    // 收藏失败
    COLLECTION_FAILED(4201, "收藏失败"),

    // 编码错误
    CODING_ERROR(5000,"编码错误");
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
