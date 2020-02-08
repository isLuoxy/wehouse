package cn.l99.wehouse.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;

import java.util.HashMap;
import java.util.Map;


/**
 * 短信工具类
 *
 * @author L99
 */
public class SmsUtils {

    private static final String API_URL = "https://sms_developer.zhenzikj.com";
    private static final String API_ID = "104382";
    private static final String APP_SECRET = "2cb5347e-acd8-4765-89c8-d90df7ae6893";
    private static final String registerMsg = "验证码：%d，10分钟内有效，为了保障您的账户安全，请勿向他人泄露验证码信息";

    /**
     * 发送验证码到用户手机
     *
     * @param phone 用户手机号
     * @param code 验证码
     * @return 发送状态
     */
    public static boolean sendCode(String phone, int code) {
        String msg = String.format(registerMsg, code);
        ZhenziSmsClient client = new ZhenziSmsClient(API_URL, API_ID, APP_SECRET);
        Map<String,String> params = new HashMap<>();
        params.put("message",msg);
        params.put("number",phone);
        try {
            String result = client.send(params);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getIntValue("code") != 0){
                // 发送短信失败 TODO: 这里暂不处理短信发送失败的情况
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
