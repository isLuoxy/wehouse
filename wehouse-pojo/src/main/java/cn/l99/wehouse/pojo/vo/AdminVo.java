package cn.l99.wehouse.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVo implements Serializable {
    private static final long serialVersionUID = -2443763134905807918L;

    private String adminName;

    private String adminPassword;
}
