package cn.l99.wehouse.map.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * 地图工具类
 *
 * @author L99
 */
public class MapUtils {


    /**
     * 根据经纬度获取距离
     *
     * @param longitude1 来源经度
     * @param latitude1  来源维度
     * @param longitude2 目标经度
     * @param latitude2  目标维度
     * @return 距离
     */
    public static double getDistanceBasedOnLongAndLat(String longitude1, String latitude1, String longitude2, String latitude2) {
        GlobalCoordinates source = new GlobalCoordinates(Double.valueOf(latitude1), Double.valueOf(longitude1));
        GlobalCoordinates target = new GlobalCoordinates(Double.valueOf(latitude2), Double.valueOf(longitude2));
        return getSphereDistanceMeter(source, target);
    }

    /**
     * 使用 Sphere坐标系计算距离
     *
     * @param gpsFrom {@link GlobalCoordinates} 来源经纬度
     * @param gpsTo   {@link GlobalCoordinates} 目标经纬度
     * @return 距离
     */
    private static double getSphereDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo) {
        return getDistanceMeter(gpsFrom, gpsTo, Ellipsoid.Sphere);
    }

    private static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {

        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }
}
