package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.UserStudentAuthentication;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStudentAuthenticationDao {

    boolean insertUserStudentAuthentication(UserStudentAuthentication userStudentAuthentication);
}
