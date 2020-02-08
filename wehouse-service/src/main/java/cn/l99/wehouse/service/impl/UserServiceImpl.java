package cn.l99.wehouse.service.impl;


import cn.l99.wehouse.dao.CollectionDao;
import cn.l99.wehouse.dao.UserDao;
import cn.l99.wehouse.pojo.Collection;
import cn.l99.wehouse.pojo.HouseCollection;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.dto.CollectionDto;
import cn.l99.wehouse.pojo.dto.UserDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.UserVo;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.IUserService;
import cn.l99.wehouse.utils.SmsUtils;
import cn.l99.wehouse.utils.UserUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.regex.Pattern;

/**
 * 用户服务层接口实现类
 *
 * @author L99
 */
@Service(version = "1.0")
@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDao userDao;

    @Autowired
    CollectionDao collectionDao;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public CommonResult checkUserPhone(String userPhone) {
        User user = userDao.selectUserByUserPhone(userPhone);
        return user == null ? CommonResult.success() : CommonResult.failure(ErrorCode.DUPLICATE_PHONE);
    }

    @Override
    public CommonResult checkUserName(String userName) {
        User user = userDao.selectUserByUserName(userName);
        return user == null ? CommonResult.success() : CommonResult.failure(ErrorCode.DUPLICATE_USERNAME);
    }

    @Override
    public CommonResult register(UserVo userVo) {
        // 判断验证码是否正确
        String code = userVo.getCode();
        if (StringUtils.isEmpty(code) || !code.equals(redisUtils.get(userVo.getUserPhone()))) {
            return CommonResult.failure(ErrorCode.ERROR_CODE);
        }
        User user = userVo.convertToUserWhenRegister();
        return userDao.insertUser(user) ? CommonResult.success() : CommonResult.failure(ErrorCode.REGISTER_FAIL);
    }


    /**
     * 用户登陆
     *
     * @param userVo
     * @return
     */
    @Override
    public CommonResult login(UserVo userVo) {
        // 判断用户名类型，是手机号还是用户名
        boolean usePhone = Pattern.matches(UserUtils.PHONE_PATTERN, userVo.getUserName());
        User user = null;
        if (usePhone) {
            // 验证手机号是否存在
            user = userDao.selectUserByUserPhone(userVo.getUserName());
        } else {
            user = userDao.selectUserByUserName(userVo.getUserName());
        }
        if (StringUtils.isEmpty(user)) {
            return CommonResult.failure(ErrorCode.NOT_EXIST);
        }

        if (!user.getUserPassword().equals(userVo.getUserPassword())) {
            return CommonResult.failure(ErrorCode.MISMATCH);
        }
        String token = UserUtils.generateToken(userVo.getUserName(), userVo.getUserPassword());
        // 过期时间30分钟
        redisUtils.set(token, user.getId(), 3600);
        Map<String, String> map = new HashMap<>(2);
        map.put("token", token);
        return CommonResult.success(map);
    }

    /**
     * 发送手机验证码，这里还对手机号做了一个重复验证，如果手机号已存在的话则不发送验证码
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public CommonResult sendCode(String phone) {
        if (StringUtils.isEmpty(phone) && userDao.selectUserByUserPhone(phone) != null) {
            // 手机号存在的话直接返回
            return CommonResult.failure(ErrorCode.DUPLICATE_PHONE);
        }
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        // 调用短信平台 api 发送短信
        boolean result = SmsUtils.sendCode(phone, code);
        if (!result) {
            return CommonResult.failure(ErrorCode.CODE_SEND_FAILED);
        }
        // 将验证码存到 redis 中，并设置过期时间 10分钟
        redisUtils.set(phone, code, 600);
        return CommonResult.success();
    }


    @Override
    public CommonResult getPersonalCenterByUserId(String userId) {
        User user = userDao.selectUserByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.userConvertToUserDto(user);
        return CommonResult.success(userDto);
    }

    /**
     * 判断 redis 中的 key 是否存在，如果存在则返回对应的值，不存在则返回null
     *
     * @param key
     * @return
     */
    @Override
    public CommonResult getValueIfExist(String key) {
        boolean hasKey = redisUtils.hasKey(key);
        if (hasKey) {
            return CommonResult.success(redisUtils.get(key));
        }
        return null;
    }

    /**
     * 个人中心收藏
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult getPersonalCollectionByUserId(String userId) {
        List<HouseCollection> houseCollections = userDao.selectHouseAndCollectionByUserId(userId);
        List<CollectionDto> collectionDtoList = new ArrayList<>(houseCollections.size());
        houseCollections.forEach(houseCollection -> {
            CollectionDto collectionDto = new CollectionDto();
            collectionDto.ConvertToCollectionDto(houseCollection);
            collectionDtoList.add(collectionDto);
        });
        return CommonResult.success(collectionDtoList);
    }

    /**
     * 房源收藏
     *
     * @return
     */
    @Override
    public CommonResult postPersonalCollection(Collection collection) {
        collection.setCollectionTime(new Date());
        boolean result = collectionDao.insertCollection(collection);
        if (result) {
            return CommonResult.success();
        } else {
            return CommonResult.failure(ErrorCode.COLLECTION_FAILED);
        }
    }
}
