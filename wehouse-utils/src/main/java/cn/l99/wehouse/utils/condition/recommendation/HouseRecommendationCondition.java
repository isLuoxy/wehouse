package cn.l99.wehouse.utils.condition.recommendation;

import java.math.BigDecimal;

/**
 * 房源推荐条件类
 * <p>
 * 用于新增房源时查找相似房源的条件
 */
public class HouseRecommendationCondition {

    // 租金 <
    private Double rentLessThan;

    // 租金 >=
    private Double rentGreaterThanOrEqual;

    // 出租方式
    private String rentalType;

    // 房屋类型(当出租方式是整租时使用)
    private String houseType;

    // 位置
    private String address;

    // 开始
    private Integer start;

    // 查找相似房源的大小
    private Integer size;


    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRentLessThan() {
        return rentLessThan;
    }

    public void setRentLessThan(Double rentLessThan) {
        this.rentLessThan = rentLessThan;
    }

    public Double getRentGreaterThanOrEqual() {
        return rentGreaterThanOrEqual;
    }

    public void setRentGreaterThanOrEqual(Double rentGreaterThanOrEqual) {
        this.rentGreaterThanOrEqual = rentGreaterThanOrEqual;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
