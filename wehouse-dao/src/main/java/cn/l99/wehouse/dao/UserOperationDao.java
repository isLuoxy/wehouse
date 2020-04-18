package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.UserOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户操作持久层
 *
 * @author L99
 */
@Repository
public interface UserOperationDao {

    boolean insertUserOperation(UserOperation userOperation);

    List<UserOperation> findUserOperationByUserIdAndOperationTime(@Param("userId") String userId, @Param("data") Date date);
}
