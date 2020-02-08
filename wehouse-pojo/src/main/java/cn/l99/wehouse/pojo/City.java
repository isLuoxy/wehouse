package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 城市表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private String id;

    private String cityName;

    private String provinceId;
}
