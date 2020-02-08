package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地区表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {
    private String id;

    private String regionName;

    private String cityId;
}
