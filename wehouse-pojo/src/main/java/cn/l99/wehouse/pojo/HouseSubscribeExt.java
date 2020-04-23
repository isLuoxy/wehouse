package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.OperatorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 房源预定扩展
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSubscribeExt implements Serializable {

    private static final long serialVersionUID = -2333532472882363396L;

    private int id;

    private int houseSubscribeId;

    private String comment;

    private int operatorId;

    private OperatorType operatorType;

    private Date lastUpdateTime;
}
