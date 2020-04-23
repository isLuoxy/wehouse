package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.dto.HouseSubscribeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中间类，房源+预定房源+用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseAndSubscribeAndUser implements Serializable {

    private static final long serialVersionUID = -3410900366824555364L;

    private House house;

    private User user;

    private HouseSubscribe houseSubscribe;

    /**
     * 将中间类转换成房源预定的响应类
     * @return
     */
    public HouseSubscribeDto convert2HouseSubscribeDto() {
        return HouseSubscribeDto.convert2HouseSubScribeDtoByHouseSubscribe(houseSubscribe, house, user);
    }
}
