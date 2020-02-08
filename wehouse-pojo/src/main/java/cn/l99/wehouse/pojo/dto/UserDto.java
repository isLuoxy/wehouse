package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息响应封装
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private String userHead;
    private String userName;
    private String userPhone;
    private String studentAuthentication;

    /**
     * 将 user 转换成 userDTO
     * @param user
     */
    public void userConvertToUserDto(User user) {
        this.userHead = user.getUserHead();
        this.userName = user.getUserName();
        this.userPhone = user.getUserPhone();
        this.studentAuthentication = user.getStudentAuthentication().getValue();
    }
}
