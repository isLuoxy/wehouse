package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.Region;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 区域信息响应类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto implements Serializable {

    // 区域中文名称
    private String regionCnName;

    @JSONField(serialize=false)
    // 区域英文名称（一般为拼音）
    private String regionEnName;

    public void convertToRegionDtoFromRegion(Region region) {
        this.regionCnName = region.getRegionName();
    }
}
