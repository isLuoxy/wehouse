package cn.l99.pojo.test;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.baseEnum.HouseStatus;
import cn.l99.wehouse.pojo.baseEnum.Orientation;
import cn.l99.wehouse.pojo.baseEnum.RentalType;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonTest {
    public static void main(String[] args) {
        House house = new House();
        house.setId(123l);
        house.setCheckInTime(new Date());
        house.setOrientation(Orientation.N);
        house.setHouseStatus(HouseStatus.A);
        house.setRentalType(RentalType.H);
        List<House> list = new ArrayList<>();
        list.add(house);
        list.add(house);
        System.out.println(JSONObject.toJSONString(list));
    }
}
