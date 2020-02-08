package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 地铁对应的城市
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayCitys {

    private int id;

    private String cnName;

    private String enName;

    private int code;

    private String pre;

    private Date createdAt;

    private Date updatedAt;
}
