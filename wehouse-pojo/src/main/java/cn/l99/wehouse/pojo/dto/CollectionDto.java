package cn.l99.wehouse.pojo.dto;

import cn.l99.wehouse.pojo.Collection;
import cn.l99.wehouse.pojo.House;
import cn.l99.wehouse.pojo.HouseCollection;
import cn.l99.wehouse.utils.BaseUnitUtils;
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

    private String id;

    private String name;

    private String rentalTpye;

    private String floor;

    private String area;

    private String price;

    private Date collectionTime;

    private String regionCnName;

    private String placeCnName;

    private String pictureUrl;

    private String houseType;

    public void ConvertToCollectionDto(HouseCollection houseCollection) {
        House house = houseCollection.getHouse();
        Collection collection = houseCollection.getCollection();
        this.id = house.getId();
        this.name = house.getName();
        this.rentalTpye = house.getRentalType().getValue();
        this.floor = house.getFloor();
        this.area = house.getArea() + BaseUnitUtils.SQUARE_METER;
        this.price = house.getPrice() + BaseUnitUtils.PER_MONTH;
        this.collectionTime = collection.getCollectionTime();
        this.regionCnName = house.getRegionCnName();
        this.placeCnName = house.getPlaceCnName();
        this.pictureUrl = house.getPictureUrl();
        this.houseType = house.getHouseType();
    }

}
