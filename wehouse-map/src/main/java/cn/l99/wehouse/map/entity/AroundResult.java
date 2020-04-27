package cn.l99.wehouse.map.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AroundResult {

    private String status;

    private String count;

    private String info;

    private String infocode;

    private List<Around> pois;
}
