package cn.l99.wehouse.service.admin;

import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.AdminVo;
import cn.l99.wehouse.pojo.vo.UserMsgVo;

public interface IAdminService {

    CommonResult getAdminByAdminName(AdminVo adminVo);

    CommonResult updateUser(UserMsgVo userMsgVo);
}
