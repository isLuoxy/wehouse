package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.HouseArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseAreaDao {

    List<HouseArea> getRegionByCityName(String cityName);

    /**
     * 添加城市，后台管理员使用
     *
     * @param houseAreas
     * @return
     */
    boolean insertCity(@Param("houseAreaList") List<HouseArea> houseAreas);
}
