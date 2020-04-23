package cn.l99.wehouse.service.impl;


import cn.l99.wehouse.dao.CollectionDao;
import cn.l99.wehouse.dao.UserDao;
import cn.l99.wehouse.dao.UserStudentAuthenticationDao;
import cn.l99.wehouse.mail.Mail;
import cn.l99.wehouse.mail.MailTemplate;
import cn.l99.wehouse.mail.StudentCertificationMailTemplate;
import cn.l99.wehouse.pojo.UserCollection;
import cn.l99.wehouse.pojo.HouseCollection;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.UserStudentAuthentication;
import cn.l99.wehouse.pojo.baseEnum.CommonType;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.dto.CollectionDto;
import cn.l99.wehouse.pojo.dto.UserDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.UserStudentAuthenticationVo;
import cn.l99.wehouse.pojo.vo.UserVo;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.IUserService;
import cn.l99.wehouse.utils.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户服务层接口实现类
 *
 * @author L99
 */
@Service(version = "${wehouse.service.version}")
@Component
@Slf4j
public class UserServiceImpl implements IUserService {

    final UserDao userDao;

    final CollectionDao collectionDao;

    final RedisUtils redisUtils;

    final Mail mail;

    final EnvironmentProfiles environment;

    final UserStudentAuthenticationDao userStudentAuthenticationDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, CollectionDao collectionDao, RedisUtils redisUtils, Mail mail, EnvironmentProfiles environment, UserStudentAuthenticationDao userStudentAuthenticationDao) {
        this.userDao = userDao;
        this.collectionDao = collectionDao;
        this.redisUtils = redisUtils;
        this.mail = mail;
        this.environment = environment;
        this.userStudentAuthenticationDao = userStudentAuthenticationDao;
    }

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
        log.info("userName:{},code:{},userPhone:{}", userVo.getUserName(), userVo.getCode(), userVo.getUserPhone());
        // 判断验证码是否正确
        String code = userVo.getCode();
        if (StringUtils.isEmpty(code) || !code.equals(redisUtils.get(userVo.getUserPhone()))) {
            return CommonResult.failure(ErrorCode.ERROR_CODE);
        }
        User user = userVo.convertToUserWhenRegister();
        return userDao.insertUser(user) ? CommonResult.success() : CommonResult.failure(ErrorCode.REGISTER_FAIL);
    }


    @Override
    public CommonResult logout() {
        // TODO：将用户登出时间进行记录
        return null;
    }


    /**
     * 用户登陆
     *
     * @param userVo {@link UserVo}
     * @return {@link CommonResult} 通用结果封装
     */
    @Override
    public CommonResult login(UserVo userVo) {
        // 判断用户名类型，是手机号还是用户名
        boolean usePhone = Pattern.matches(UserUtils.PHONE_PATTERN, userVo.getUserName());
        User user;
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
        redisUtils.set(token, String.valueOf(user.getId()), 1800);
        Map<String, String> map = new HashMap<>(2);
        map.put("token", token);
        return CommonResult.success(map);
    }

    /**
     * 发送手机验证码，这里还对手机号做了一个重复验证，如果手机号已存在的话则不发送验证码
     *
     * @param phone 手机号
     * @return {@link CommonResult} 通用结果封装
     */
    @Override
    public CommonResult sendCode(String phone) {
//        if (StringUtils.isEmpty(phone) && userDao.selectUserByUserPhone(phone) != null) {
//            // 手机号存在的话直接返回
//            return CommonResult.failure(ErrorCode.DUPLICATE_PHONE);
//        }
        // 生成6位数字
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        // 调用短信平台 api 发送短信
        boolean result = SmsUtils.sendCode(phone, code);
        if (!result) {
            return CommonResult.failure(ErrorCode.CODE_SEND_FAILED);
        }
        // 将验证码存到 redis 中，并设置过期时间 10分钟
        redisUtils.set(phone, String.valueOf(code), 600);
        return CommonResult.success();
    }


    @Override
    public CommonResult getPersonalCenterByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return CommonResult.failure(ErrorCode.NOT_LOGIN);
        }
        User user = userDao.selectUserByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.userConvertToUserDto(user);
        return CommonResult.success(userDto);
    }

    @Override
    public Map<Integer, User> getUserForHouseSubscribe(List<Integer> userId) {
        List<User> userByUserId = userDao.getUserByUserId(userId);
        return userByUserId == null ? null : userByUserId.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    /**
     * 判断 redis 中的 key 是否存在，如果存在则返回对应的值，不存在则返回null
     *
     * @param key redis 中的 key 值
     * @return {@link CommonResult} 通用结果封装
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
     * @param userId 用户的 id
     * @return {@link CommonResult} 通用结果封装
     */
    @Override
    public CommonResult getPersonalCollectionByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return CommonResult.failure(ErrorCode.NOT_LOGIN);
        }
        List<HouseCollection> houseCollections = userDao.selectHouseAndCollectionByUserId(userId);
        if (houseCollections == null) {
            return CommonResult.success();
        }
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
     * @return {@link CommonResult} 通用结果封装
     */
    @Override
    public CommonResult postPersonalCollection(UserCollection userCollection) {
        userCollection.setCollectionTime(DateUtils.now());
        boolean result = collectionDao.insertCollection(userCollection);
        if (result) {
            return CommonResult.success();
        } else {
            return CommonResult.failure(ErrorCode.COLLECTION_FAILED);
        }
    }

    /**
     * 发送学生认证邮件
     *
     * @param userId                      用户 id
     * @param userStudentAuthenticationVo
     * @return {@link CommonResult} 通用结果封装
     */
    @Override
    public CommonResult sendStuAuthEmail(String userId, UserStudentAuthenticationVo userStudentAuthenticationVo) {
        // 验证学校和邮箱是否匹配
        if (!verifyEmailAndSchool(userStudentAuthenticationVo)) {
            return CommonResult.failure(ErrorCode.EMAIL_NO_MATCH_SCHOOL);
        }
        MailTemplate mailTemplate = new StudentCertificationMailTemplate();
        String address = userStudentAuthenticationVo.getSchoolEmail();
        log.info("当前发送短信的环境为：{}", environment.getActive());
        Map<String, String> map = UserUtils.generatorCertificationLink(userId, address, ProfilesEnum.get(environment.getActive()));
        String content = mailTemplate.generatorMailContent(map.get("link"));
        boolean result = mail.sendMail(address, "wehouse 用户学生身份邮箱验证", content);
        if (result) {
            userStudentAuthenticationVo.setUid(map.get("uid"));
            String redisValue = JSONObject.toJSONString(userStudentAuthenticationVo);
            // 将验证信息存入 redis 中，并设置对应的过期时间(48小时)
            redisUtils.set(map.get("token"), redisValue, 48 * 60 * 60);
            return CommonResult.success();
        }
        return CommonResult.failure(ErrorCode.EMAIL_SEND_FAILED);
    }

    private boolean verifyEmailAndSchool(UserStudentAuthenticationVo userStudentAuthenticationVo) {
        return true;
    }

    /**
     * 学生认证验证
     *
     * @return {@link CommonResult} 通用结果封装
     */
    @Override
    public CommonResult updateUserStudentAuthentication(String uid, String email, String token) {
        return verifyStuAtu(uid, email, token);
    }

    private CommonResult verifyStuAtu(String uid, String email, String token) {
        String userStudentAuthenticationVoJsonString = (String) redisUtils.get(token);
        UserStudentAuthenticationVo userStudentAuthenticationVo = JSONObject.parseObject(userStudentAuthenticationVoJsonString, UserStudentAuthenticationVo.class);
        log.info("学生邮箱验证基础信息：{}", userStudentAuthenticationVo);
        String uidTemp = userStudentAuthenticationVo.getUid();
        if (uidTemp == null || !uidTemp.equals(uid)) {
            // 用户传进来跟之前存储的进行比较
            return CommonResult.failure(ErrorCode.STU_AUTH_FAILED);
        }
        String userId = UserUtils.parseCertificationLinkToken(uid, email, token);
        UserStudentAuthentication userStudentAuthentication = userStudentAuthenticationVo.conver2UserStudentAuthentication();
        userStudentAuthentication.setUserId(Integer.valueOf(userId));
        boolean result1 = userStudentAuthenticationDao.insertUserStudentAuthentication(userStudentAuthentication);
        boolean result2 = userDao.updateUserStudentAuthentication(userId, CommonType.Y);
        if (result1 && result2) {
            // 删除之前存储的的 uid 和 验证token
            redisUtils.del(token);
            return CommonResult.success(ErrorCode.STU_AUTH_SUCCESS);
        }
        return CommonResult.failure(ErrorCode.STU_AUTH_FAILED);
    }
}
