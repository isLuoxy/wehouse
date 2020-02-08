package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 地铁线路基础信息
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayLines {

    private int id;

    private String name;

    private String uid;

    private String pairUid;

    private int cityId;

    private Date createdAt;

    private Date updatedAt;
}
