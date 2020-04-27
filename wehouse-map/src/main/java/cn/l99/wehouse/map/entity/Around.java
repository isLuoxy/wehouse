package cn.l99.wehouse.map.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 详细字段见 https://lbs.amap.com/api/webservice/guide/api/search
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Around {

    private String id;

    private String name;

    private String address;

    private String location;

    private String distance;
}
