package cn.l99.wehouse.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房屋一级简略展示封装数据
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto implements Serializable {

    private int id;

    private String name;

    private String rentalType;

    private String orientation;

    private String area;

    private String price;

    private String pictureUrl;
}
