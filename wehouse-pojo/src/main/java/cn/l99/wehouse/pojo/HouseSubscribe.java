package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约看房基础信息类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSubscribe implements Serializable {

    private int id;

    private int houseId;

    private int userId;

    /**
     * 数据创建时间
     */
    private Date createTime;

    private HouseSubscribeStatus houseSubscribeStatus;
    /**
     * 记录更新时间
     */
    private Date lastUpdateTime;

    /**
     * 预约时间
     */
    private Date orderTime;
    /**
     * 联系电话
     */
    private String telephone;
}
