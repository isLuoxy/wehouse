package cn.l99.wehouse.utils;

import java.sql.Timestamp;
import java.time.*;
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
        return new Date(new Timestamp(zero).getTime());
    }
}
