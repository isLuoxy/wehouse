package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房东信息表
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner implements Serializable  {

    private static final long serialVersionUID = 5541657115316019756L;

    private Integer id;

    private String ownerName;

    private String ownerPassword;

    private String ownerPhone;
}
