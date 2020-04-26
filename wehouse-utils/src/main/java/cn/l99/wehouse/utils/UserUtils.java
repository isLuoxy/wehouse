package cn.l99.wehouse.utils;

import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户工具类
 *
 * @author L99
 */
public class UserUtils {

    // 手机正则验证
    public static final String PHONE_PATTERN = "^1[3456789]\\d{9}$";

    public static final String SALT = "wehouse";

    public static final String DEV_LINK_TEMPLATE = "http://localhost:9999/verifyEmailByUrl/uid/%s/email/%s/token/%s";

    public static final String UAT_LINK_TEMPLATE = "http://121.36.29.50:9999/verifyEmailByUrl/uid/%s/email/%s/token/%s";

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
        stringBuilder.append(System.currentTimeMillis());
        return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
    }

    /**
     * 生成学生认证邮箱内容认证链接
     *
     * @param userId   用户id
     * @param address  用户邮箱
     * @param profiles 当前环境
     * @return 认证链接
     */
    public static Map<String, String> generatorCertificationLink(String userId, String address, ProfilesEnum profiles) {
        String link = "";
        switch (profiles) {
            case DEV:
                link = DEV_LINK_TEMPLATE;
                break;
            case UAT:
                link = UAT_LINK_TEMPLATE;
                break;
            default:
                break;
        }
        String uid = generatorCertificationLinkUid();
        String email = address;
        String token = generatorCertificationLinkToken(uid, email, userId);

        Map<String, String> map = new HashMap<>();
        String resultLink = String.format(link, uid, email, token);
        map.put("uid", uid);
        map.put("token", token);
        map.put("link", resultLink);
        return map;
    }

    /**
     * TODO:优化，可能在有效期内出现重复的数字
     *
     * @return
     */
    private static String generatorCertificationLinkUid() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

    /**
     * 生成学生身份验证的token
     *
     * @param uid
     * @param email
     * @param userId
     * @return
     */
    private static String generatorCertificationLinkToken(String uid, String email, String userId) {
        return AESUtils.parseByte2HexStr(AESUtils.encrypt(userId, uid + email + SALT));
    }

    /**
     * 解析token
     *
     * @param uid
     * @param email
     * @param token
     * @return
     */
    public static String parseCertificationLinkToken(String uid, String email, String token) {
        return new String(AESUtils.decrypt(AESUtils.parseHexStr2Byte(token), uid + email + SALT));
    }
}
