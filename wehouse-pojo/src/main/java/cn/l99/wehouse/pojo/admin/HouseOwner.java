package cn.l99.wehouse.pojo.admin;

import cn.l99.wehouse.pojo.baseEnum.HouseStatus;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import cn.l99.wehouse.pojo.baseEnum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 中间表，用于管理员管理房源
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseOwner {

    private String houseId;

    private String houseName;

    private String ownerName;

    private int ownerId;

    private Date createTime;

    private String houseType;

    private RentalType rentalType;

    private String rentalRoom;

    private double price;

    private double area;

    private HouseStatus status;
}
