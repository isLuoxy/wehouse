package cn.l99.wehouse.utils.condition;

/**
 * 房屋条件类
 *
 * @author L99
 */
public class HouseCondition {

    // 区域名
    private String regionCnName;

    // 地铁线路id
    private String subwayLineId;

    // 页面开始
    private String pageStart = "0";

    // 页面大小
    private String pageSize = "30";

    // 房屋类型
    private String houseType;

    // 租金 <
    private String rentLessThan;

    // 租金 >=
    private String rentGreaterThanOrEqual;

    // 朝向
    private String orientation;


    public String getRegionCnName() {
        return regionCnName;
    }

    public void setRegionCnName(String regionCnName) {
        this.regionCnName = regionCnName;
    }

    public String getSubwayLineId() {
        return subwayLineId;
    }

    public void setSubwayLineId(String subwayLineId) {
        this.subwayLineId = subwayLineId;
    }


    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }


    public String getRentLessThan() {
        return rentLessThan;
    }

    public void setRentLessThan(String rentLessThan) {
        this.rentLessThan = rentLessThan;
    }


    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }


    public String getRentGreaterThanOrEqual() {
        return rentGreaterThanOrEqual;
    }

    public void setRentGreaterThanOrEqual(String rentGreaterThanOrEqual) {
        this.rentGreaterThanOrEqual = rentGreaterThanOrEqual;
    }

    public String getPageStart() {
        return pageStart;
    }

    public void setPageStart(String pageStart) {
        this.pageStart = pageStart;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
