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
@NoArgsConstructor
public class Page {

    // 默认一页30条房源数据
    private int pageSize;

    // 默认起始页为 1 页
    private int pageNumber;

    private int pageStart;

    public Page(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.pageStart = (pageNumber - 1) * pageSize;
    }
}
