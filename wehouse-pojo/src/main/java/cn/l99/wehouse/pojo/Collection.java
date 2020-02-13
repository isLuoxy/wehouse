package cn.l99.wehouse.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户房屋收藏表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection implements Serializable {

    private int id;

    private int userId;

    private int houseId;

    private Date collectionTime;
}
