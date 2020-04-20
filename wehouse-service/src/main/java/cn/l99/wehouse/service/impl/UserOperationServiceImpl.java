package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.UserOperationDao;
import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IUserOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserOperationServiceImpl implements IUserOperationService {


    private final UserOperationDao userOperationDao;

    @Autowired
    public UserOperationServiceImpl(UserOperationDao userOperationDao) {
        this.userOperationDao = userOperationDao;
    }

    @Override
    public List<UserOperation> findUserOperationByUserIdAndGtOperationTime(String userId, Date operationTime) {

        List<UserOperation> userOperations = userOperationDao.findUserOperationByUserIdAndOperationTime(userId, operationTime);
        return userOperations;
    }

    @Override
    @Async
    public int addUserOperation(UserOperation userOperation) {
        userOperationDao.insertUserOperation(userOperation);
        return userOperation.getId();
    }

    @Override
    public CommonResult updateUserOperation(UserOperation userOperation) {
        int line = userOperationDao.updateUserOperation(userOperation);
        if (line == 1) {
            return CommonResult.success();
        }
        log.warn("更新用户操作记录失败，{}", userOperation);
        return CommonResult.failure(ErrorCode.UPDATE_ERROR);
    }
}
