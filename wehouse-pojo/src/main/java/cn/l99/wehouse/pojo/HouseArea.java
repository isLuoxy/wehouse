package cn.l99.wehouse.pojo;

import cn.l99.wehouse.pojo.baseEnum.HouseAreaLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房源所属地区
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseArea implements Serializable {

    private static final long serialVersionUID = 2284953428019197910L;

    private Integer id;

    private String adcode;

    private String name;

    private HouseAreaLevel level;

    private String belong;

    private String enNameAbbreviation;
}
