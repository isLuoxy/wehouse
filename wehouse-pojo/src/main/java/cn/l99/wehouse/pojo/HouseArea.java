package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.HouseAreaLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房源所属地区
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseArea {

    private String id;

    private String adcode;

    private String name;

    private HouseAreaLevel level;

    private String belong;

    private String enNameAbbreviation;
}
