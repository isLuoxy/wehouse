package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.baseEnum.OperationType;

import java.util.Date;
import java.util.List;

/**
 * 用户操作服务层接口
 *
 * @author L99
 */
public interface IUserOperationService {

    /**
     * 查找用户过去的时间范围内的操作房源
     *
     * @param userId
     * @param operationTime
     * @return
     */
    List<UserOperation> findUserOperationByUserIdAndGtOperationTime(String userId, Date operationTime);

    /**
     * 添加新的操作记录
     *
     * @param userOperation {@link UserOperation}
     */
    void addUserOperation(UserOperation userOperation);
}
