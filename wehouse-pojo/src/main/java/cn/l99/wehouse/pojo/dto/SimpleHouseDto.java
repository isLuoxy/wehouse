package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import cn.l99.wehouse.utils.BaseUnitUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 一级房屋基础信息响应
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleHouseDto implements Serializable {
    private static final long serialVersionUID = -983183819204453244L;

    private String id;

    private String name;

    private String rentalType;

    private String orientation;

    private String regionCnName;

    private String streetCnName;

    private String village;

    private String floor;

    private String area;

    private String price;

    private String pictureUrl;

    public void convertToSimpleHouseDtoFromHouse(House house) {
        this.id = house.getId();
        if (house.getRentalType().equals(RentalType.H) && !StringUtils.isEmpty(house.getRentalRoom())) {
            this.name = house.getRentalType().getValue() + "-" + house.getName() + house.getHouseType() + "-" + house.getRentalRoom();
        } else {
            this.name = house.getRentalType().getValue() + "-" + house.getName() + house.getHouseType();
        }
        this.rentalType = house.getRentalType().getValue();
        this.orientation = house.getOrientation().getValue();
        this.regionCnName = house.getRegionCnName();
        this.streetCnName = house.getStreetCnName();
        this.village = house.getVillage();
        this.floor = house.getFloor();
        this.area = house.getArea() + BaseUnitUtils.SQUARE_METER;
        this.price = house.getPrice().toString() + BaseUnitUtils.PER_MONTH;
        this.pictureUrl = house.getPictureUrl();
    }
}
