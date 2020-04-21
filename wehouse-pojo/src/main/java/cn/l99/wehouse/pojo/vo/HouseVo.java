package cn.l99.wehouse.pojo.vo;

import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseExt;
import cn.l99.wehouse.pojo.baseEnum.*;
import cn.l99.wehouse.utils.id.IdGenerator;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房屋请求通用对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseVo implements Serializable {

    private static final long serialVersionUID = -5691072120033033873L;

    private String name;

    private String rentalType;

    private String orientation;

    private String area;

    private String floor;

    private Double price;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;

    private String elevator;

    private String houseType;

    private String rentalRoom;

    private String provinceCnName;

    private String cityCnName;

    private String regionCnName;

    private String streetCnName;

    private String village;

    private String address;

    private Integer ownerId;

    private String television;

    private String fridge;

    private String washingMachine;

    private String airConditioning;

    private String heater;

    private String bed;

    private String broadband;

    private String wardrobe;

    private Integer subwayLineId;

    private Integer subwayStationId;

    private String description;

    private String longitude;

    private String latitude;

    /**
     * 将请求对象转换成 {@link House} 对象
     *
     * @return
     */
    public House convertToHouse() {
        House house = new House();
        house.setId(String.valueOf(IdGenerator.nextId()));
        house.setName(this.name);
        house.setRentalType(RentalType.get(this.rentalType));
        house.setOrientation(Orientation.get(this.orientation));
        house.setArea(Double.valueOf(this.area));
        house.setFloor(this.floor);
        house.setPrice(this.price);
        house.setCheckInTime(this.checkInTime);
        house.setElevator(CommonType.valueOf(this.elevator));
        house.setHouseType(this.getHouseType());
        house.setRentalRoom(RentalRoom.get(this.rentalRoom));
        house.setProvinceCnName(this.getProvinceCnName());
        house.setCityCnName(this.getCityCnName());
        house.setRegionCnName(this.getRegionCnName());
        house.setStreetCnName(this.streetCnName);
        house.setVillage(this.village);
        house.setAddress(this.address);
        house.setHouseStatus(HouseStatus.U);
        house.setDistanceToSubway(-1);
        house.setOwnerId(this.ownerId);
        house.setPictureUrl(null);
        house.setLongitude(longitude);
        house.setLatitude(latitude);

        HouseExt houseExt = new HouseExt();
        houseExt.setHouseId(house.getId());
        houseExt.setTelevision(CommonType.valueOf(this.elevator));
        houseExt.setFridge(CommonType.valueOf(this.fridge));
        houseExt.setWashingMachine(CommonType.valueOf(this.washingMachine));
        houseExt.setAirConditioning(CommonType.valueOf(this.airConditioning));
        houseExt.setHeater(CommonType.valueOf(this.heater));
        houseExt.setBed(CommonType.valueOf(this.bed));
        houseExt.setBroadband(CommonType.valueOf(this.broadband));
        houseExt.setWardrobe(CommonType.valueOf(this.wardrobe));
        houseExt.setSubwayLineId(this.subwayLineId);
        houseExt.setSubwayStationId(this.subwayStationId);
        houseExt.setDescription(this.description);

        house.setHouseExt(houseExt);
        return house;
    }

}
