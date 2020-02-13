package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.HouseCollection;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.CommonType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息持久层
 *
 * @author L99
 */
@Repository
public interface UserDao {

    boolean insertUser(User user);

    User selectUserByUserName(String userName);

    User selectUserByUserPhone(String userPhone);

    User selectUserByUserId(String userId);

    List<HouseCollection> selectHouseAndCollectionByUserId(String userId);

    boolean updateUserStudentAuthentication(String userId, CommonType commonType);

}
