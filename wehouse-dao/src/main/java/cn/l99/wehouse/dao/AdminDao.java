package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.HouseArea;
import cn.l99.wehouse.pojo.admin.Admin;
import cn.l99.wehouse.pojo.admin.HouseOwner;
import cn.l99.wehouse.pojo.vo.AdminVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员持久层
 *
 * @author L99
 */
@Repository
public interface AdminDao {

    Admin selectAdminByAdminNameAndAdminPassword(AdminVo adminVo);


    List<HouseOwner> getAllHouse(@Param("pageStart") int pageStart,
                                 @Param("pageSize") int pageSize);
}
