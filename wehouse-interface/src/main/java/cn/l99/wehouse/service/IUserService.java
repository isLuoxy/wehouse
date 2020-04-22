package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.UserCollection;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.UserStudentAuthenticationVo;
import cn.l99.wehouse.pojo.vo.UserVo;

import java.util.List;
import java.util.Map;

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

    CommonResult logout();

    CommonResult sendCode(String phone);

    CommonResult getPersonalCenterByUserId(String userId);

    Map<Integer, User> getUserForHouseSubscribe(List<Integer> userId);

    CommonResult getValueIfExist(String key);

    CommonResult getPersonalCollectionByUserId(String userId);

    CommonResult postPersonalCollection(UserCollection userCollection);

    CommonResult sendStuAuthEmail(String userId, UserStudentAuthenticationVo userStudentAuthenticationVo);

    CommonResult updateUserStudentAuthentication(String uid, String email, String token);
}
