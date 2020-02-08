package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页情况
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    // 默认一页30条房源数据
    private int pageSize = 30;

    // 默认起始页为 o 页
    private int pageStart = 0;

}
