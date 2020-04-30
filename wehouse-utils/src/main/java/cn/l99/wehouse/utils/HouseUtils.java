package cn.l99.wehouse.utils;

import cn.l99.wehouse.utils.condition.HouseCondition;
import cn.l99.wehouse.utils.condition.recommendation.HouseRecommendationCondition;

import java.math.BigDecimal;


/**
 * 房屋工具类
 *
 * @author L99
 */
public class HouseUtils {

    private static final int DEFAULT_PAGE_SIZE = 30;

    /**
     * 房屋类型枚举
     */
    public enum RentalType {
        z1("整租", "Z"),
        z2("合租", "H");

        private String desc;
        private String data;

        RentalType(String desc, String data) {
            this.desc = desc;
            this.data = data;
        }

    }

    /**
     * 租金类型枚举
     */
    public enum Rent {

        r1("&-1000", BigDecimal.valueOf(Integer.MIN_VALUE), BigDecimal.valueOf(1000)),
        r2("1000-1500", BigDecimal.valueOf(1000), BigDecimal.valueOf(1500)),
        r3("1500-2000", BigDecimal.valueOf(1500), BigDecimal.valueOf(2000)),
        r4("2000-3000", BigDecimal.valueOf(2000), BigDecimal.valueOf(3000)),
        r5("3000-5000", BigDecimal.valueOf(3000), BigDecimal.valueOf(5000)),
        r6("5000-6000", BigDecimal.valueOf(5000), BigDecimal.valueOf(6000)),
        r7("6000-8000", BigDecimal.valueOf(6000), BigDecimal.valueOf(8000)),
        r8("8000-9000", BigDecimal.valueOf(8000), BigDecimal.valueOf(9000)),
        r9("9000-&", BigDecimal.valueOf(9000), BigDecimal.valueOf(Integer.MAX_VALUE));

        // 租金 >=
        private BigDecimal rentGreaterThanOrEqual;

        // 租金 <
        private BigDecimal rentLessThan;
        private String desc;

        Rent(String desc, BigDecimal rentGreaterThanOrEqual, BigDecimal rentLessThan) {
            this.desc = desc;
            this.rentLessThan = rentLessThan;
            this.rentGreaterThanOrEqual = rentGreaterThanOrEqual;
        }

        public static Rent judgingRangeByRent(BigDecimal bigDecimal) {
            if (bigDecimal == null) {
                return null;
            }
            for (Rent rent : Rent.values()) {
                if (bigDecimal.compareTo(rent.rentGreaterThanOrEqual) > -1 && bigDecimal.compareTo(rent.rentLessThan) == -1) {
                    return rent;
                }
            }
            return null;
        }

        public BigDecimal getRentGreaterThanOrEqual() {
            return rentGreaterThanOrEqual;
        }

        public BigDecimal getRentLessThan() {
            return rentLessThan;
        }

        public String getDesc() {
            return desc;
        }
    }


    /**
     * 朝向类型枚举
     */
    public enum Orientation {
        g1("东", "E"),
        g2("南", "S"),
        g3("西", "W"),
        g4("北", "N");

        private String desc;
        private String data;

        Orientation(String desc, String data) {
            this.desc = desc;
            this.data = data;
        }
    }

    /**
     * 通过提取str中的值去构造房源查找条件
     *
     * @param condition
     * @return
     */
    public static HouseCondition acqConditions(String condition) {
        if (condition == null || "".equals(condition)) {
            return null;
        }
        HouseCondition houseCondition = new HouseCondition();
        String[] temp = condition.split("-");
        for (String s : temp) {
            acqConditionsHelp(houseCondition, s);
        }
        return houseCondition;
    }


    /**
     * @param houseCondition 房屋条件类
     * @param conditionStr   条件字符串
     */
    private static void acqConditionsHelp(HouseCondition houseCondition, String conditionStr) {
        String prefix = conditionStr.substring(0, 1);
        switch (prefix) {
            case "a":
                houseCondition.setRegionCnName(conditionStr.substring(1));
                break;
            case "b":
                houseCondition.setSubwayLineId(conditionStr.substring(1));
                break;
            case "p":
                int page = Integer.valueOf(conditionStr.substring(1));
                int[] ints = constructPage(page);
                houseCondition.setPageStart(String.valueOf(ints[0]));
                houseCondition.setPageSize(String.valueOf(ints[1]));
                break;
            case "z":
                RentalType rentalType = RentalType.valueOf(conditionStr);
                houseCondition.setRentalType(rentalType.data);
                break;
            case "r":
                String rent = Rent.valueOf(conditionStr).desc;
                String[] strings = constructRent(rent);
                if (!"&".equals(strings[0])) {
                    houseCondition.setRentGreaterThanOrEqual(strings[0]);
                }
                if (!"&".equals(strings[1])) {
                    houseCondition.setRentLessThan(strings[1]);
                }
                break;
            case "g":
                String orientation = Orientation.valueOf(conditionStr).data;
                houseCondition.setOrientation(orientation);
                break;
            default:
                break;
        }
    }

    private static int[] constructPage(int currentPage) {
        int pageStart = ((currentPage - 1) * DEFAULT_PAGE_SIZE);
        int[] page = new int[2];
        page[0] = pageStart;
        page[1] = DEFAULT_PAGE_SIZE;
        return page;
    }

    private static String[] constructRent(String rent) {
        String[] split = rent.split("-");
        return split;
    }
}
