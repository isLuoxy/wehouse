package cn.l99.wehouse.service.admin;

import cn.l99.wehouse.pojo.Page;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.AdminVo;
import cn.l99.wehouse.pojo.vo.HouseMsgVo;
import cn.l99.wehouse.pojo.vo.HouseVo;
import cn.l99.wehouse.pojo.vo.UserMsgVo;

public interface IAdminService {

    CommonResult getAdminByAdminName(AdminVo adminVo);

    CommonResult updateUser(UserMsgVo userMsgVo);

    CommonResult getAllUser(Page page);

    CommonResult deleteUser(UserMsgVo userMsgVo);

    // -- 房源操作 --
    CommonResult getAllHouse(Page page);

    CommonResult updateHouse(HouseMsgVo houseMsgVo);
    // -- 房源操作 --
}
