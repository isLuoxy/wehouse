package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.response.CommonResult;

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
     * @return 数据库生成的主键值
     */
    int addUserOperation(UserOperation userOperation);

    /**
     * 更新用户操作记录
     *
     * @param userOperation {@link UserOperation}
     * @return 更新成功行数
     */
    CommonResult updateUserOperation(UserOperation userOperation);
}
