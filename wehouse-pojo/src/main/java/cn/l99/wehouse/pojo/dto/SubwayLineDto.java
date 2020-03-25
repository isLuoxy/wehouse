package cn.l99.wehouse.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地铁线路信息响应类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayLineDto implements Serializable {

    private static final long serialVersionUID = -8501792353821191773L;
    // 地铁线路 id
    private Integer lineId;
    // 地铁线路中文名称
    private String lineCnName;

    @Override
    public int hashCode() {
        return lineCnName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubwayLineDto) {
            return lineCnName.equals(((SubwayLineDto) obj).getLineCnName());
        }
        return false;
    }
}
