package cn.l99.wehouse.map.lbs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 行政区域api返回结果封装
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictResult {
    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<Suggestion> suggestion;
    private List<District> districts;
}
