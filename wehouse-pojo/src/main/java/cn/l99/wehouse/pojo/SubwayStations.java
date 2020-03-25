package cn.l99.wehouse.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 地铁站台基础信息
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayStations implements Serializable {

    private static final long serialVersionUID = -5329863179700820076L;

    private Integer id;

    private String name;

    private String uid;

    private String lat;

    private String lng;

    private String isPractical;

    private Integer lineId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
