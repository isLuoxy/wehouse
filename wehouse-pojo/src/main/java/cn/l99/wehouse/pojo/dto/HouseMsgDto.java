package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.admin.HouseOwner;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import cn.l99.wehouse.utils.BaseUnitUtils;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseMsgDto implements Serializable {
    private static final long serialVersionUID = 9075255151512012252L;

    private String houseId;

    private String houseName;

    private String ownerName;

    private String ownerId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String houseType;

    private String rentalType;

    private String price;

    private String area;

    private String status;

    public static HouseMsgDto convert2HouseMsgDto(HouseOwner houseOwner) {
        HouseMsgDto houseMsgDto = new HouseMsgDto();
        houseMsgDto.houseId = houseOwner.getHouseId();
        if (houseOwner.getRentalType().equals(RentalType.H) && !StringUtils.isEmpty(houseOwner.getRentalRoom())) {
            houseMsgDto.houseName = houseOwner.getRentalType().getValue() + "-" + houseOwner.getHouseName() + houseOwner.getHouseType() + "-" + houseOwner.getRentalRoom();
        } else {
            houseMsgDto.houseName = houseOwner.getRentalType().getValue() + "-" + houseOwner.getHouseName() + houseOwner.getHouseType();
        }
        houseMsgDto.rentalType = houseOwner.getRentalType().getValue();
        houseMsgDto.area = houseOwner.getArea() + BaseUnitUtils.SQUARE_METER;
        houseMsgDto.price = houseOwner.getPrice() + BaseUnitUtils.PER_MONTH;
        houseMsgDto.ownerName = houseOwner.getOwnerName();
        houseMsgDto.ownerId = String.valueOf(houseOwner.getOwnerId());
        houseMsgDto.status = houseOwner.getStatus().getValue();
        houseMsgDto.setCreateTime(houseOwner.getCreateTime());
        return houseMsgDto;
    }
}
