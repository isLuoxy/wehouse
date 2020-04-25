package cn.l99.wehouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistory implements Serializable {
    private static final long serialVersionUID = -4015903037149464617L;

    private int id;

    private int userId;

    private String condition;

    private String keyWords;

    private Date searchTime;
}
