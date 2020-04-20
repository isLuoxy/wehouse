package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.UserOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOperationVo implements Serializable {

    private static final long serialVersionUID = 5446166178427752637L;

    private int usId;

    private Date startTime;

    private Date endTime;

    private int pageOnTime;

    public UserOperation convertToUserOperation() {
        UserOperation userOperation = new UserOperation();
        userOperation.setId(usId);
        userOperation.setOperationStartTime(startTime);
        userOperation.setOperationEndTime(endTime);
        userOperation.setPageOnTime(pageOnTime);
        return userOperation;
    }
}
