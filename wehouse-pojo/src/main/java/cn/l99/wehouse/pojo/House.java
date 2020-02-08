package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.CommonType;
import cn.l99.wehouse.pojo.baseEnum.HouseStatus;
import cn.l99.wehouse.pojo.baseEnum.Orientation;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 房屋基础类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {

    private int id;

    private String name;

    private RentalType rentalType;

    private Orientation orientation;

    private Double area;

    private String floor;

    private Integer price;

    private Date checkInTime;

    private CommonType elevator;

    private String houseType;

    private String provinceCnName;

    private String cityCnName;

    private String regionCnName;

    private String placeCnName;

    private String village;

    private String address;

    private HouseStatus houseStatus;

    private int distanceToSubway;

    private int ownerId;

    private String pictureUrl;
}
