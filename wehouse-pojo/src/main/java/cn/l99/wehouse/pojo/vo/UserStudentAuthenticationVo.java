package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.UserStudentAuthentication;
import cn.l99.wehouse.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生认证请求对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStudentAuthenticationVo implements Serializable {
    private static final long serialVersionUID = -6913703626606649910L;

    // 这个值不是数据库中的值，而是用来临时存储邮件的某个值
    private String uid;

    private String school;

    private String schoolEmail;

    public UserStudentAuthentication conver2UserStudentAuthentication() {
        UserStudentAuthentication userStudentAuthentication = new UserStudentAuthentication();
        userStudentAuthentication.setSchool(school);
        userStudentAuthentication.setSchoolEmail(schoolEmail);
        userStudentAuthentication.setCertificationTime(DateUtils.now());
        return userStudentAuthentication;
    }
}
