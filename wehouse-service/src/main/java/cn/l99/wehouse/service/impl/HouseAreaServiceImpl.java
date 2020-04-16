package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.HouseAreaDao;
import cn.l99.wehouse.map.entity.District;
import cn.l99.wehouse.map.utils.DistrictUtils;
import cn.l99.wehouse.pojo.HouseArea;
import cn.l99.wehouse.pojo.baseEnum.HouseAreaLevel;
import cn.l99.wehouse.pojo.response.CommonResult;
import cn.l99.wehouse.service.IHouseAreaService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseAreaServiceImpl implements IHouseAreaService {

    @Autowired
    HouseAreaDao houseAreaDao;

    /**
     * 插入新的城市
     *
     * @param cityName   城市中文名称
     * @param cityPyName 城市拼音首字母缩写
     * @return
     */
    @Override
    public CommonResult insertCity(String cityName, String cityPyName) {

        District district = DistrictUtils.getRegionName(cityName);

        List<HouseArea> houseAreaList = new ArrayList<>();
        HouseArea houseArea = new HouseArea();
        houseArea.setAdcode(district.getAdcode());
        houseArea.setName(district.getName());
        houseArea.setEnNameAbbreviation(cityPyName);
        houseArea.setLevel(HouseAreaLevel.C);

        houseAreaList.add(houseArea);
        constructHouseAreaListByDistrict(houseAreaList, district.getDistricts());

        houseAreaDao.insertCity(houseAreaList);

        return CommonResult.success();
    }


    private void constructHouseAreaListByDistrict(List<HouseArea> houseAreas, List<District> districtList) {
        if (districtList == null) {
            return;
        }

        String cityAdcode = houseAreas.get(0).getAdcode();

        districtList.forEach(district -> {
            HouseArea houseArea = new HouseArea();
            houseArea.setLevel(HouseAreaLevel.D);
            houseArea.setAdcode(district.getAdcode());
            houseArea.setName(district.getName());
            houseArea.setBelong(cityAdcode);
            houseAreas.add(houseArea);
        });
    }
}
