package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.HouseSubscribe;
import cn.l99.wehouse.pojo.HouseSubscribeExt;
import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import cn.l99.wehouse.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 房源预定请求对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSubscribeVo implements Serializable {

    private static final long serialVersionUID = -8823484505672339049L;

    private String id;

    private String houseId;

    private String userId;

    private String telephone;

    private Date orderTime;

    private String status;

    private String comment;

    public HouseSubscribe convert2HouseSubscribe() {
        HouseSubscribe houseSubscribe = new HouseSubscribe();
        if (!StringUtils.isEmpty(id)) {
            houseSubscribe.setId(Integer.valueOf(id));
        }
        houseSubscribe.setHouseId(houseId);
        if (!StringUtils.isEmpty(userId)) {
            houseSubscribe.setUserId(Integer.valueOf(userId));
        }
        houseSubscribe.setOrderTime(orderTime);
        houseSubscribe.setTelephone(telephone);
        houseSubscribe.setCreateTime(DateUtils.now());
        houseSubscribe.setLastUpdateTime(DateUtils.now());
        if (StringUtils.isEmpty(status)) {
            houseSubscribe.setHouseSubscribeStatus(HouseSubscribeStatus.W);
        } else {
            houseSubscribe.setHouseSubscribeStatus(HouseSubscribeStatus.get(status));
        }
        return houseSubscribe;
    }

    public HouseSubscribeExt convert2HouseSubscribeExt() {
        HouseSubscribeExt houseSubscribeExt = new HouseSubscribeExt();
        houseSubscribeExt.setComment(this.comment);
        houseSubscribeExt.setHouseSubscribeId(Integer.valueOf(id));
        return houseSubscribeExt;
    }
}
