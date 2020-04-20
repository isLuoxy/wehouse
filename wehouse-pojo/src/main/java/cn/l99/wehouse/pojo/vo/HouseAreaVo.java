package cn.l99.wehouse.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房源地区请求对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseAreaVo implements Serializable {

    private static final long serialVersionUID = 5892477180760239031L;

    // 名称
    private String name;

    // 拼音首字母缩写
    private String enName;
}
