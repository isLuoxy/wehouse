package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseSubscribe;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 房源预定响应对象
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSubscribeDto implements Serializable {
    private static final long serialVersionUID = 2978425038994568300L;

    private Integer id;

    private String houseId;

    private String houseName;

    private Integer userId;

    private String userName;

    private String subscribeStatus;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private String telephone;

    public static HouseSubscribeDto convert2HouseSubScribeDtoByHouseSubscribe(HouseSubscribe houseSubscribe, House house, User user) {
        HouseSubscribeDto houseSubscribeDto = new HouseSubscribeDto();
        houseSubscribeDto.setId(houseSubscribe.getId());
        houseSubscribeDto.setHouseId(houseSubscribe.getHouseId());
        String name;
        if (house.getRentalType().equals(RentalType.H)) {
            name = house.getRentalType().getValue() + "-" + house.getName() + house.getHouseType() + "-" + house.getRentalRoom().getValue();
        } else {
            name = house.getRentalType().getValue() + "-" + house.getName() + house.getHouseType();
        }
        houseSubscribeDto.setHouseName(name);
        houseSubscribeDto.setUserId(user.getId());
        houseSubscribeDto.setUserName(user.getUserName());
        houseSubscribeDto.setSubscribeStatus(houseSubscribe.getHouseSubscribeStatus().getValue());
        houseSubscribeDto.setOrderTime(houseSubscribe.getOrderTime());
        houseSubscribeDto.setTelephone(houseSubscribe.getTelephone());

        return houseSubscribeDto;
    }
}
