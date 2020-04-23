package cn.l99.wehouse.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页情况
 *
 * @author L99
 */
@Data
@NoArgsConstructor
public class Page implements Serializable {

    private static final long serialVersionUID = 3356074732500082159L;
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
