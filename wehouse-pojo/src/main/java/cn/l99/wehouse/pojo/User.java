package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.CommonType;
import cn.l99.wehouse.pojo.baseEnum.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private int id;

    private String userName;

    private String userNickname;

    private String userPassword;

    private CommonType studentAuthentication;

    private String userPhone;

    private UserStatus userStatus;

    private Date createTime;

    private Date lastLoginTime;

    private String userHead;

}
