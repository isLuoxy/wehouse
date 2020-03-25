package cn.l99.wehouse.pojo;

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
}
