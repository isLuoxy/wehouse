package cn.l99.wehouse.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 地铁线路基础信息
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayLines implements Serializable {

    private static final long serialVersionUID = -7954872276282547614L;

    private Integer id;

    private String name;

    private String uid;

    private String pairUid;

    private Integer cityId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
