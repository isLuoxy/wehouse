package cn.l99.wehouse.map.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCode {

    private String formatted_address;
    private String country;
    private String province;
    private String citycode;
    private String city;
    private String district;
    // private String township;
    private String street;
    private String adcode;
    private String number;
    private String location;
    private String level;
}
