package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.UserCollection;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseCollection;
import cn.l99.wehouse.utils.BaseUnitUtils;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人收藏响应类
 *
 * @author L99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto implements Serializable {

    private static final long serialVersionUID = 2108463470169074849L;

    private Long houseId;

    private String name;

    private String rentalTpye;

    private String floor;

    private String area;

    private String price;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectionTime;

    private String regionCnName;

    private String placeCnName;

    private String pictureUrl;

    private String houseType;

    public void ConvertToCollectionDto(HouseCollection houseCollection) {
        House house = houseCollection.getHouse();
        UserCollection userCollection = houseCollection.getUserCollection();
        this.houseId = house.getId();
        this.name = house.getName();
        this.rentalTpye = house.getRentalType().getValue();
        this.floor = house.getFloor();
        this.area = house.getArea() + BaseUnitUtils.SQUARE_METER;
        this.price = house.getPrice() + BaseUnitUtils.PER_MONTH;
        this.collectionTime = userCollection.getCollectionTime();
        this.regionCnName = house.getRegionCnName();
        this.placeCnName = house.getPlaceCnName();
        this.pictureUrl = house.getPictureUrl();
        this.houseType = house.getHouseType();
    }

}
