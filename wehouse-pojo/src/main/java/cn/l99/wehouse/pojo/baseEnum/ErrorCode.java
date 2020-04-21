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

    // 学生验证成功
    STU_AUTH_SUCCESS(4401, "学生验证通过，自动跳转首页"),

    // 学生验证失败
    STU_AUTH_FAILED(4402, "学生验证失败，自动跳转首页"),

    // 邮件发送失败
    EMAIL_SEND_FAILED(4403, "验证邮件发送失败"),

    // 房源信息不存在
    HOUSE_NOT_EXIST(4301, "房源信息不存在"),

    // 该房源已出租
    HOUSE_RENTED(4302, "房源已出租"),

    // 编码错误
    CODING_ERROR(5000, "编码出错"),

    // 数据库统一修改错误
    UPDATE_ERROR(400, "更新错误"),

    // 邮箱和学校不匹配
    EMAIL_NO_MATCH_SCHOOL(6000, "学校和邮箱不匹配");

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
