package cn.l99.wehouse.map.lbs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 高德地图区信息
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {
    private String citycode;
    private String adcode;
    private String name;
    private String polyline;
    private String center;
    private String level;
    private List<District> districts;
}
