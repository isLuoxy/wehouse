package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.admin.Admin;
import cn.l99.wehouse.pojo.vo.AdminVo;
import org.springframework.stereotype.Repository;

/**
 * 管理员持久层
 *
 * @author L99
 */
@Repository
public interface AdminDao {

    Admin selectAdminByAdminNameAndAdminPassword(AdminVo adminVo);

}
