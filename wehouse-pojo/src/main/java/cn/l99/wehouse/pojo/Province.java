package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 省份表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Province implements Serializable {

    private static final long serialVersionUID = 4393466665329384805L;

    private Integer id;

    private String provinceName;
}
