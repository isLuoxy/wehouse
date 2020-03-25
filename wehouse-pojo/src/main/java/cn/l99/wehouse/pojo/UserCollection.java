package cn.l99.wehouse.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户房屋收藏表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollection implements Serializable {

    private static final long serialVersionUID = 377910918410271086L;

    private Integer id;

    private Integer userId;

    private Long houseId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectionTime;
}
