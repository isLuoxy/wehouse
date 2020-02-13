package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地区表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region implements Serializable {
    private String id;

    private String regionName;

    private String cityId;
}
