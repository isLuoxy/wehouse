package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.baseEnum.HouseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseMsgVo implements Serializable {
    private static final long serialVersionUID = -6703173981876665446L;

    private String houseId;

    private String status;

    public House convertToHouse() {
        House house = new House();
        house.setId(houseId);
        house.setHouseStatus(HouseStatus.get(status));
        return house;
    }
}
