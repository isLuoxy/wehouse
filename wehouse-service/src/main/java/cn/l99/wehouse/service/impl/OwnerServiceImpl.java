package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.OwnerDao;
import cn.l99.wehouse.pojo.Owner;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.OwnerVo;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.IOwnerService;
import cn.l99.wehouse.utils.SmsUtils;
import cn.l99.wehouse.utils.UserUtils;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service(version = "${wehouse.service.version}")
@Component
@Slf4j
public class OwnerServiceImpl implements IOwnerService {

    final OwnerDao ownerDao;

    final RedisUtils redisUtils;

    public OwnerServiceImpl(OwnerDao ownerDao, RedisUtils redisUtils) {
        this.ownerDao = ownerDao;
        this.redisUtils = redisUtils;
    }

    @Override
    public CommonResult checkOwnerName(String ownerName) {
        Owner owner = ownerDao.selectOwnerByOwnerName(ownerName);
        return owner == null ? CommonResult.success() : CommonResult.failure(ErrorCode.DUPLICATE_USERNAME);
    }

    @Override
    public CommonResult checkOwnerPhone(String ownerPhone) {
        Owner owner = ownerDao.selectOwnerByOwnerPhone(ownerPhone);
        return owner == null ? CommonResult.success() : CommonResult.failure(ErrorCode.DUPLICATE_PHONE);
    }

    @Override
    public CommonResult register(OwnerVo ownerVo) {
        log.info("ownerName:{},code:{},ownerPhone:{}", ownerVo.getOwnerName(), ownerVo.getCode(), ownerVo.getOwnerPhone());
        // 判断验证码是否正确
        String code = ownerVo.getCode();
        if (StringUtils.isEmpty(code) || !code.equals(redisUtils.get(ownerVo.getOwnerPhone()))) {
            return CommonResult.failure(ErrorCode.ERROR_CODE);
        }
        Owner owner = ownerVo.convertToOwnerWhenRegister();
        return ownerDao.insertOwner(owner) ? CommonResult.success() : CommonResult.failure(ErrorCode.REGISTER_FAIL);
    }

    @Override
    public CommonResult login(OwnerVo ownerVo) {
        // 判断用户名类型，是手机号还是用户名
        boolean usePhone = Pattern.matches(UserUtils.PHONE_PATTERN, ownerVo.getOwnerName());
        Owner owner;
        if (usePhone) {
            // 验证手机号是否存在
            owner = ownerDao.selectOwnerByOwnerPhone(ownerVo.getOwnerName());
        } else {
            owner = ownerDao.selectOwnerByOwnerName(ownerVo.getOwnerName());
        }
        if (StringUtils.isEmpty(owner)) {
            return CommonResult.failure(ErrorCode.NOT_EXIST);
        }

        if (!owner.getOwnerPassword().equals(ownerVo.getOwnerPassword())) {
            return CommonResult.failure(ErrorCode.MISMATCH);
        }
        String token = UserUtils.generateToken(ownerVo.getOwnerName(), ownerVo.getOwnerPassword());
        // 过期时间30分钟
        redisUtils.set(token, String.valueOf(owner.getId()), 1800);
        Map<String, String> map = new HashMap<>(2);
        map.put("token", token);
        return CommonResult.success(map);
    }

    @Override
    public CommonResult logout() {
        return null;
    }

}
