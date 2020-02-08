package cn.l99.wehouse.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地铁站台信息响应类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayStationDto implements Serializable {

    // 地铁站台 id
    private String stationId;
    // 地铁站台中文名称
    private String stationCnName;
}
