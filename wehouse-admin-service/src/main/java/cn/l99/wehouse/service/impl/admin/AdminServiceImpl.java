package cn.l99.wehouse.service.impl.admin;

import cn.l99.wehouse.dao.AdminDao;
import cn.l99.wehouse.dao.HouseDao;
import cn.l99.wehouse.dao.OwnerDao;
import cn.l99.wehouse.dao.UserDao;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.Page;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.admin.Admin;
import cn.l99.wehouse.pojo.admin.HouseOwner;
import cn.l99.wehouse.pojo.baseEnum.ErrorCode;
import cn.l99.wehouse.pojo.baseEnum.Status;
import cn.l99.wehouse.pojo.dto.HouseMsgDto;
import cn.l99.wehouse.pojo.dto.UserMsgDto;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.pojo.vo.AdminVo;
import cn.l99.wehouse.pojo.vo.HouseMsgVo;
import cn.l99.wehouse.pojo.vo.UserMsgVo;
import cn.l99.wehouse.service.admin.IAdminService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(version = "${wehouse.service.version}")
@Component
@Slf4j
public class AdminServiceImpl implements IAdminService {

    final AdminDao adminDao;

    final UserDao userDao;

    final HouseDao houseDao;

    final OwnerDao ownerDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, UserDao userDao, HouseDao houseDao, OwnerDao ownerDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.houseDao = houseDao;
        this.ownerDao = ownerDao;
    }

    @Override
    public CommonResult getAdminByAdminName(AdminVo adminVo) {
        Admin admin = adminDao.selectAdminByAdminNameAndAdminPassword(adminVo);
        if (admin == null) {
            return CommonResult.failure(ErrorCode.MISMATCH);
        }
        return CommonResult.success();
    }


    @Override
    public CommonResult updateUser(UserMsgVo userMsgVo) {
        boolean result = userDao.updateUserByUserId(userMsgVo.convert2User());
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.failure(400, "管理员更新用户失败");
    }

    @Override
    public CommonResult getAllUser(Page page) {
        List<User> user = userDao.getUser(page.getPageStart(), page.getPageSize());
        List<UserMsgDto> userMsgDtos = user.stream().map(UserMsgDto::convert2UserMsgDto).collect(Collectors.toList());
        int total = userDao.getTotal();
        return CommonResult.success(0, generatorPageResult(total, userMsgDtos));
    }

    @Override
    public CommonResult deleteUser(UserMsgVo userMsgVo) {
        User user = new User();
        user.setId(Integer.valueOf(userMsgVo.getUserId()));
        user.setStatus(Status.P);
        boolean result = userDao.updateUserByUserId(user);
        return CommonResult.success();
    }


    @Override
    public CommonResult getAllHouse(Page page) {
        List<HouseOwner> houseList = adminDao.getAllHouse(page.getPageStart(), page.getPageSize());
        List<HouseMsgDto> houseMsgDtoList = new ArrayList<>();
        for (HouseOwner houseOwner : houseList) {
            HouseMsgDto houseMsgDto = HouseMsgDto.convert2HouseMsgDto(houseOwner);
            houseMsgDtoList.add(houseMsgDto);
        }
        int total = houseDao.getTotal();
        return CommonResult.success(0, generatorPageResult(total, houseMsgDtoList));
    }

    @Override
    public CommonResult updateHouse(HouseMsgVo houseMsgVo) {
        House house = houseMsgVo.convertToHouse();
        boolean result = houseDao.updateHouse(house);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.failure(400, "管理员更新房源失败");
    }


    /**
     * 用于组装才有分页条件的响应
     *
     * @param total
     * @param data
     * @return
     */
    private Map<String, Object> generatorPageResult(int total, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("data", data);
        return result;
    }
}
