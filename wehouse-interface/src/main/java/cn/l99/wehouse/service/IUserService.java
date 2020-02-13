package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.Collection;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.UserVo;

/**
 * 用户服务层接口
 *
 * @author L99
 */
public interface IUserService {

    CommonResult checkUserName(String userName);

    CommonResult checkUserPhone(String userPhone);

    CommonResult register(UserVo userVo);

    CommonResult login(UserVo userVo);

    CommonResult sendCode(String phone);

    CommonResult getPersonalCenterByUserId(String userId);

    CommonResult getValueIfExist(String key);

    CommonResult getPersonalCollectionByUserId(String userId);

    CommonResult postPersonalCollection(Collection collection);

    CommonResult sendStuAuthEmail(String userId, String address);

    CommonResult updateUserStudentAuthentication(String uid, String email, String token);
}
