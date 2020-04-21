package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStudentAuthentication implements Serializable {

    private static final long serialVersionUID = 6974685532206685934L;

    private Integer id;

    private Integer userId;

    private String school;

    private String schoolEmail;

    private Date certificationTime;
}
