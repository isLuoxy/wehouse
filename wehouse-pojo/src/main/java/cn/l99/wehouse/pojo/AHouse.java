package cn.l99.wehouse.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * House+HouseExt
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AHouse implements Serializable {

    private static final long serialVersionUID = 5295786311150742134L;

    private House house;

    private HouseExt houseExt;

    // 记录用户页面停留时间的埋点，如果不为空，说明需要记录用户离开页面的时间，此时前端可以根据该标识发送用户离开页面时间；为空则不需要
    private int usId;
}
