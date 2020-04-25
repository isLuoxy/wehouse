package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.AHouse;
import cn.l99.wehouse.pojo.HouseExt;
import cn.l99.wehouse.pojo.SubwayLines;
import cn.l99.wehouse.pojo.SubwayStations;
import cn.l99.wehouse.utils.SubwayUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 二级房屋扩展信息响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHouseExtDto implements Serializable {
    private static final long serialVersionUID = -8779928166188571126L;

    private String television;

    private String fridge;

    private String washingMachine;

    private String airConditioning;

    private String heater;

    private String bed;

    private String broadband;

    private String wardrobe;

    private String subwayLine;

    private String subwayStation;

    private String description;

    public void convert2SecondHouseExtDtoByAHouse(AHouse aHouse) {
        HouseExt houseExt = aHouse.getHouseExt();
        this.television = houseExt.getTelevision().getValue();
        this.fridge = houseExt.getFridge().getValue();
        this.washingMachine = houseExt.getWashingMachine().getValue();
        this.airConditioning = houseExt.getAirConditioning().getValue();
        this.heater = houseExt.getHeater().getValue();
        this.bed = houseExt.getBed().getValue();
        this.broadband = houseExt.getBroadband().getValue();
        this.wardrobe = houseExt.getWardrobe().getValue();
        this.description = houseExt.getDescription();

        SubwayLines subwayLines = aHouse.getSubwayLines();
        SubwayStations subwayStations = aHouse.getSubwayStations();

        this.subwayLine = StringUtils.isEmpty(subwayLines) ? null : SubwayUtils.SubwayNameFormat(subwayLines.getName());
        this.subwayStation = StringUtils.isEmpty(subwayStations) ? null : subwayStations.getName();
    }
}
