package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * House+HouseExt
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AHouse {

    private House house;

    private HouseExt houseExt;
}
