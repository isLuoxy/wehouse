package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户请求通用对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {

    private String userPhone;

    private String userName;

    private String userPassword;

    private String code;

    /**
     * 在进行注册时使用对象转换，将 UserVo 转换成 User 用于持久化操作
     *
     * @return 转换后的 {@link User} 对象
     */
    public User convertToUserWhenRegister() {
        User user = new User();
        user.setUserName(userName);
        user.setUserNickname(userName);
        user.setUserPhone(userPhone);
        user.setUserPassword(userPassword);
        user.setCreateTime(new Date());
        return user;
    }
}
