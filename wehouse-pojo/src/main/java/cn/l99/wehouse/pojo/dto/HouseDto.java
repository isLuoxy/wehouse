package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseExt;
import cn.l99.wehouse.pojo.baseEnum.*;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房屋二级展示封装数据
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto implements Serializable {

    private SecondHouseDto house;

    private SecondHouseExtDto houseExt;

    // 记录用户页面停留时间的埋点，如果不为空，说明需要记录用户离开页面的时间，此时前端可以根据该标识发送用户离开页面时间；为空则不需要
    private Integer usId;

    /**
     * 组装响应数据
     *
     * @param ahouse
     */
    public void convertByAHouse(AHouse ahouse) {
        if (house == null) {
            house = new SecondHouseDto();
        }
        house.convert2SecondHouseDtoByHouse(ahouse.getHouse());

        if (houseExt == null) {
            houseExt = new SecondHouseExtDto();
        }
        houseExt.convert2SecondHouseExtDtoByAHouse(ahouse);
    }
}
