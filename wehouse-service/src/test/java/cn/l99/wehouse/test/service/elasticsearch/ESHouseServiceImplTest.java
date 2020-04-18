package cn.l99.wehouse.test.service.elasticsearch;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseExt;
import cn.l99.wehouse.pojo.baseEnum.CommonType;
import cn.l99.wehouse.pojo.baseEnum.HouseStatus;
import cn.l99.wehouse.pojo.baseEnum.Orientation;
import cn.l99.wehouse.service.WeHouseApplication;
import cn.l99.wehouse.service.elasticsearch.ESIHouseService;
import cn.l99.wehouse.test.service.BashTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {WeHouseApplication.class, BashTest.class})
@RunWith(SpringRunner.class)
@Slf4j
public class ESHouseServiceImplTest {

    @Before
    public void before() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Autowired
    ESIHouseService houseService;

    @Test
    public void addHouse() {
        House house = new House();
        house.setId(198199L);
        house.setHouseStatus(HouseStatus.A);
        house.setOrientation(Orientation.N);
        house.setCityCnName("深圳");
        house.setArea(40d);
        house.setName("夜半湾花园");
        house.setRegionCnName("宝安区");
        house.setStreetCnName("翻身");
        HouseExt houseExt = new HouseExt();
        houseExt.setHouseId(house.getId());
        houseExt.setBed(CommonType.Y);
        houseExt.setSubwayLineId(12);
        house.setHouseExt(houseExt);
        houseService.addHouseToEs(house);
    }
}
