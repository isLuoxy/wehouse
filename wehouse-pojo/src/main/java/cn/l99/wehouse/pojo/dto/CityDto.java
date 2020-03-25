package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 城市名称响应类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto implements Serializable {

    private static final long serialVersionUID = -4339411447380683587L;
    // 城市 id
    private String cityId;

    // 城市中文名称
    private String cityCnName;

    // 城市拼音缩写
    private String cityPyName;

}
