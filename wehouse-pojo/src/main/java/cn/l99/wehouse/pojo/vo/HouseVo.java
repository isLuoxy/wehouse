package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseExt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 房屋请求通用对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseVo implements Serializable {

    private static final long serialVersionUID = -5691072120033033873L;

    private String name;

    private String rentalType;

    private String orientation;

    private String area;

    private String floor;

    private Integer price;

    private Date checkInTime;

    private String elevator;

    private String houseType;

    private String provinceCnName;

    private String cityCnName;

    private String regionCnName;

    private String placeCnName;

    private String village;

    private String address;

    private String ownerId;

    private String television;

    private String fridge;

    private String washingMachine;

    private String airConditioning;

    private String heater;

    private String bed;

    private String broadband;

    private String wardrobe;

    private String subwayLineId;

    private String subwayStationId;

    private String description;


    private House convertToHouse() {
        House house = new House();
        return house;
    }

}
