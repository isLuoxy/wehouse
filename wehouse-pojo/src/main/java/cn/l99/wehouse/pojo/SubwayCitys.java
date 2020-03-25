package cn.l99.wehouse.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 地铁对应的城市
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayCitys implements Serializable {

    private static final long serialVersionUID = -6852739552162111212L;

    private Integer id;

    private String cnName;

    private String enName;

    private Integer code;

    private String pre;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
