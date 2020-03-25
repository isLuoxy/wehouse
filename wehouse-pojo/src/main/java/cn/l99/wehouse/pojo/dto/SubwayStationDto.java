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

    private static final long serialVersionUID = -5157609022867527343L;
    // 地铁站台 id
    private Integer stationId;
    // 地铁站台中文名称
    private String stationCnName;
}
