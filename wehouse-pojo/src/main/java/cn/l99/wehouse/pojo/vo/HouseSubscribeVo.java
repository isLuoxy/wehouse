package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.HouseSubscribe;
import cn.l99.wehouse.pojo.HouseSubscribeExt;
import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import cn.l99.wehouse.utils.DateUtils;
import com.alibaba.fastjson.annotation.JSONField;
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

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private String status;

    private String comment;

    // 用于搜索房源预定时使用，用于房东端
    private String ownerId;

    // 用于房源预定搜索时使用
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;

    private String userName;

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
            houseSubscribe.setStatus(HouseSubscribeStatus.W);
        } else {
            houseSubscribe.setStatus(HouseSubscribeStatus.get(status));
        }
        return houseSubscribe;
    }

    public HouseSubscribeExt convert2HouseSubscribeExt() {
        HouseSubscribeExt houseSubscribeExt = new HouseSubscribeExt();
        houseSubscribeExt.setComment(this.comment);
        houseSubscribeExt.setHouseSubscribeId(Integer.valueOf(id));
        houseSubscribeExt.setLastUpdateTime(DateUtils.now());
        houseSubscribeExt.setHouseSubscribeId(Integer.valueOf(this.id));
        return houseSubscribeExt;
    }
}
