package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.HouseSubscribeStatus;
import com.alibaba.fastjson.annotation.JSONField;
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

    private static final long serialVersionUID = -4137258040887736585L;

    private Integer id;

    private String houseId;

    private Integer userId;

    /**
     * 数据创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 状态
     */
    private HouseSubscribeStatus status;

    /**
     * 记录更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /**
     * 预约时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /**
     * 联系电话
     */
    private String telephone;
}
