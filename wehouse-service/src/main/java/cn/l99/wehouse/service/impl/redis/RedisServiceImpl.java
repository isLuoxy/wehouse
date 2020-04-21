package cn.l99.wehouse.service.impl.redis;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.redis.RedisUtils;
import cn.l99.wehouse.service.redis.IRedisService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service(version = "${wehouse.service.version}")
@Component
public class RedisServiceImpl implements IRedisService {

    final RedisUtils redisUtils;

    @Autowired
    public RedisServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
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
     * 修改过期时间，修改成功返回{@link CommonResult},否则返回 null
     *
     * @param key
     * @param time
     * @return
     */
    @Override
    public CommonResult expire(String key, long time) {
        boolean expire = redisUtils.expire(key, time);
        return expire ? CommonResult.success() : null;
    }

    @Override
    public CommonResult delete(String key) {
        redisUtils.del(key);
        return CommonResult.success();
    }
}
