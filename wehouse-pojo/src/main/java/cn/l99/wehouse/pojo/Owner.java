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

    private int id;

    private String ownerName;

    private String ownerPassword;

    private String ownerPhone;
}
