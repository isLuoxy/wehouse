package cn.l99.wehouse.service.redis;

import cn.l99.wehouse.pojo.response.CommonResult;

/**
 * redis 的服务层接口
 *
 * @author L99
 */
public interface IRedisService {

    CommonResult getValueIfExist(String key);

    CommonResult expire(String key, long time);

    CommonResult delete(String key);
}
