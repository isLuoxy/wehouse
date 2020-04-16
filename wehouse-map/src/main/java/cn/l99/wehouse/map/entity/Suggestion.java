package cn.l99.wehouse.map.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {
    private List<String> keywords;
    private List<String> cities;
}
