package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.vo.UserMsgVo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户操作信息响应对象
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMsgDto implements Serializable {

    private static final long serialVersionUID = -7848024702856657829L;

    private String userId;

    private String userName;

    private String userNickname;

    private String userPhone;

    private String studentAuthentication;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private String status;

    public static UserMsgDto convert2UserMsgDto(User user) {
        UserMsgDto userMsgDto = new UserMsgDto();
        userMsgDto.setUserId(String.valueOf(user.getId()));
        userMsgDto.setUserNickname(user.getUserNickname());
        userMsgDto.setUserName(user.getUserName());
        userMsgDto.setUserPhone(user.getUserPhone());
        userMsgDto.setStudentAuthentication(user.getStudentAuthentication().getValue());
        userMsgDto.setCreateTime(user.getCreateTime());
        userMsgDto.setLastLoginTime(user.getLastLoginTime());
        userMsgDto.setStatus(user.getStatus().getValue());
        return userMsgDto;
    }
}
