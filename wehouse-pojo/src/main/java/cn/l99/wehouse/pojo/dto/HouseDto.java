package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.House;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房屋一级简略展示封装数据
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class HouseDto implements Serializable {

    private String id;

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

    public void convertByHouse(House house) {
        this.id = house.getId();
        this.name = house.getName();
        this.rentalType = house.getRentalType().getValue();
        this.orientation = house.getOrientation().getValue();
        this.regionCnName = house.getRegionCnName();
        this.placeCnName = house.getPlaceCnName();
        this.village = house.getVillage();
    }
}
