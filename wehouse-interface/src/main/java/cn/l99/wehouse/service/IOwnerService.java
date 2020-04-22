package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.OwnerVo;

public interface IOwnerService {

    CommonResult checkOwnerName(String ownerName);

    CommonResult checkOwnerPhone(String ownerPhone);

    CommonResult register(OwnerVo ownerVo);

    CommonResult login(OwnerVo ownerVo);

    CommonResult logout();


}
