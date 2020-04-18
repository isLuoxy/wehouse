package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.UserOperationDao;
import cn.l99.wehouse.pojo.UserOperation;
import cn.l99.wehouse.pojo.baseEnum.OperationType;
import cn.l99.wehouse.service.IUserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
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
    public void addUserOperation(UserOperation userOperation) {
        userOperationDao.insertUserOperation(userOperation);
    }
}
