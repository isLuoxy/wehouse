package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.utils.BaseUnitUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 简单房屋信息响应类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleHouseDto implements Serializable {
    private static final long serialVersionUID = -983183819204453244L;

    private Long id;

    private String name;

    private String rentalType;

    private String orientation;

    private String regionCnName;

    private String placeCnName;

    private String village;

    private String floor;

    private String area;

    private String price;

    private String pictureUrl;

    public void convertToSimpleHouseDtoFromHouse(House house) {
        this.id = house.getId();
        this.name = house.getName();
        this.rentalType = house.getRentalType().getValue();
        this.orientation = house.getOrientation().getValue();
        this.regionCnName = house.getRegionCnName();
        this.placeCnName = house.getStreetCnName();
        this.village = house.getVillage();
        this.floor = house.getFloor();
        this.area = house.getArea() + BaseUnitUtils.SQUARE_METER;
        this.price = house.getPrice().toString() + BaseUnitUtils.PER_MONTH;
        this.pictureUrl = house.getPictureUrl();
    }
}
