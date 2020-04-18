package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOperation implements Serializable {
    
    private static final long serialVersionUID = -5244043896342383526L;

    private int id;

    private int userId;

    private long houseId;

    private OperationType operationType;

    private Date operationTime;
}
