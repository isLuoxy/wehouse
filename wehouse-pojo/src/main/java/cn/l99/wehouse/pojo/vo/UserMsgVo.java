package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.CommonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息操作对象
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMsgVo implements Serializable {
    private static final long serialVersionUID = -2336736904320543811L;

    private String userId;

//    不支持改动
//    private String userName;

    private String userNickName;

    private String userPhone;

    private String studentAuthentication;

    private String status;

    public User convert2User() {
        User user = new User();
        user.setId(Integer.valueOf(userId));
        user.setUserNickname(userNickName);
        user.setStudentAuthentication(CommonType.get(studentAuthentication));
        user.setUserPhone(userPhone);
        return user;
    }
}
