package cn.l99.wehouse.utils;

import org.springframework.util.DigestUtils;

import java.util.regex.Pattern;

/**
 * 用户工具类
 *
 * @author L99
 */
public class UserUtils {

    // 手机正则验证
    public static final String PHONE_PATTERN = "^1[3456789]\\d{9}$";

    public static final String SALT = "wehouse";

    /**
     * 令牌生成，用于标识登录状态
     * <h1>
     * 使用 MD5 对字符串进行加密
     * </h1>
     *
     * @return 生成的令牌
     */
    public static String generateToken(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str);
        }
        return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
    }

}
