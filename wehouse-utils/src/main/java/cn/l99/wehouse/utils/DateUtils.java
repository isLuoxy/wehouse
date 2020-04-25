package cn.l99.wehouse.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author L99
 */
public class DateUtils {

    /**
     * 增加特定天数
     *
     * @param currDate
     * @param dayToAdd
     * @return
     */
    public static Date plusDays(Date currDate, int dayToAdd) {
        LocalDateTime localDateTime = date2LocalDateTime(currDate);
        return localDateTime2Date(localDateTime.plusDays(dayToAdd));
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 回退特定天数
     *
     * @param currDate
     * @param dayToMinus
     * @return
     */
    public static Date minusDays(Date currDate, int dayToMinus) {
        LocalDateTime localDateTime = date2LocalDateTime(currDate);
        return localDateTime2Date(localDateTime.minusDays(dayToMinus));
    }

    /**
     * 回退特定月数
     *
     * @param currDate
     * @param monthsToMinus
     * @return
     */
    public static Date minusMonths(Date currDate, int monthsToMinus) {
        LocalDateTime localDateTime = date2LocalDateTime(currDate);
        return localDateTime2Date(localDateTime.minusMonths(monthsToMinus));
    }

    /**
     * 两个日期间隔的秒数
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static long secondsBetweenSecondes(Date firstDate, Date secondDate) {
        return Math.abs(ChronoUnit.SECONDS.between(date2LocalDateTime(firstDate), date2LocalDateTime(secondDate)));
    }

    public static long millsBetweenMills(Date firstDate, Date secondDate) {
        return Math.abs(ChronoUnit.MILLIS.between(date2LocalDateTime(firstDate), date2LocalDateTime(secondDate)));
    }

    /**
     * @param firstDate
     * @param secondDate
     * @param intervalThreshold 以毫秒为单位
     * @return
     */
    public static boolean intervalsExceeding(Date firstDate, Date secondDate, long intervalThreshold) {
        long mills = millsBetweenMills(firstDate, secondDate);
        return mills > intervalThreshold;
    }

    public static long secondes2Mills(long seconds) {
        return seconds * 60 * 1000;
    }

    public static long minutes2Mills(long minutes) {
        return secondes2Mills(minutes * 60);
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    /**
     * 获取当天0点的时间
     *
     * @return
     */
    public static Date get0Am() {
        long current = System.currentTimeMillis();
        long zero = current - (current + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        return new Date(zero);
    }

    /**
     * 获取某个日期的零点
     *
     * @param date
     * @return
     */
    public static Date get0Am(Date date) {
        long current = date.getTime();
        long zero = current - (current + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        return new Date(zero);
    }
}
