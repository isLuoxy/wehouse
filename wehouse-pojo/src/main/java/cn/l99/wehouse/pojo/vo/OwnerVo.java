package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.Owner;
import cn.l99.wehouse.pojo.User;
import cn.l99.wehouse.pojo.baseEnum.Status;
import cn.l99.wehouse.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerVo implements Serializable {

    private static final long serialVersionUID = 3189687394282543670L;

    private String ownerName;

    private String ownerPassword;

    private String ownerPhone;

    private String code;

    public Owner convertToOwnerWhenRegister() {
        Owner owner = new Owner();
        owner.setOwnerName(ownerName);
        owner.setOwnerPassword(ownerPassword);
        owner.setOwnerPhone(ownerPhone);
        owner.setCreateTime(DateUtils.now());
        owner.setStatus(Status.N);
        return owner;
    }
}
