package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 城市表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class City implements Serializable {

    private String id;

    private String cityName;

    private String provinceId;
}
