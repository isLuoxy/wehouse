package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 地铁站台基础信息
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubwayStations {

    private int id;

    private String name;

    private String uid;

    private String lat;

    private String lng;

    private String isPractical;

    private String lineId;

    private Date createdAt;

    private Date updatedAt;
}
