package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.baseEnum.CommonType;
import cn.l99.wehouse.pojo.baseEnum.Orientation;
import cn.l99.wehouse.pojo.baseEnum.RentalRoom;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import cn.l99.wehouse.utils.BaseUnitUtils;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 二级房屋基础信息响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHouseDto implements Serializable {

    private static final long serialVersionUID = 8274396778362772806L;

    private String id;

    private String name;

    private String rentalType;

    private String orientation;

    private String area;

    private String floor;

    private String price;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;

    private String elevator;

    private String houseType;

    private String rentalRoom;


    private String regionCnName;

    private String streetCnName;

    private String village;

    private String address;

    private int ownerId;

    private String pictureUrl;

    public void convert2SecondHouseDtoByHouse(House house) {
        this.id = house.getId();
        if (house.getRentalType().equals(RentalType.H)) {
            this.name = house.getRentalType().getValue() + "-" + house.getName() + house.getHouseType() + "-" + house.getRentalRoom().getValue();
        } else {
            this.name = house.getRentalType().getValue() + "-" + house.getName() + house.getHouseType();
        }
        this.rentalType = house.getRentalType().getValue();
        this.orientation = house.getOrientation().getValue();
        this.regionCnName = house.getRegionCnName();
        this.elevator = house.getElevator().name();
        this.streetCnName = house.getStreetCnName();
        this.village = house.getVillage();
        this.floor = house.getFloor();
        this.area = house.getArea() + BaseUnitUtils.SQUARE_METER;
        this.price = house.getPrice().toString() + BaseUnitUtils.PER_MONTH;
        this.pictureUrl = house.getPictureUrl();
        this.address = house.getAddress();
        this.ownerId = house.getOwnerId();
    }
}
