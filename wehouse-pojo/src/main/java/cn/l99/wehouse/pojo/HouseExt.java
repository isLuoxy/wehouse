package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.CommonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房屋信息扩展表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseExt implements Serializable {

    private int id;

    private int houseId;

    private CommonType television;

    private CommonType fridge;

    private CommonType washingMachine;

    private CommonType airConditioning;

    private CommonType heater;

    private CommonType bed;

    private CommonType broadband;

    private CommonType wardrobe;

    private int subwayLineId;

    private int subwayStationId;

    private String description;
}
