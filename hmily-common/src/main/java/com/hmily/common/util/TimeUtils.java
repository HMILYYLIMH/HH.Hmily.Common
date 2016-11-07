package com.hmily.common.util;

import com.hmily.common.enums.TimeType;
import com.hmily.common.log.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类。
 *
 * @author hehui
 */
public class TimeUtils {

    private static final String TAG = "TimeUtils";

    /**
     * 获取传入日期的凌晨时间点和当天结束点。
     *
     * @param date       ：时间对象。
     * @param isDayBreak ：{@code true}：获取当天的凌晨时间点；{@code false}：获取当天的结束点（即第二天凌晨时间点）。
     * @return ：返回时间点对象。
     */
    public static Date getDate(Date date, boolean isDayBreak) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int milliSecond = cal.get(Calendar.MILLISECOND);

        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + milliSecond;
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (isDayBreak) {
            return cal.getTime();
        } else {
            cal.setTimeInMillis(cal.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }
        return cal.getTime();
    }

    /**
     * 根据类型判断日期是否完全相等。
     *
     * @param args1    ：时间参数1；
     * @param args2    ：时间参数2；
     * @param timeType ：需要精确的时间类型。
     * @return ：是否相等。
     */
    public static boolean equals(Date args1, Date args2, TimeType timeType) {
        if (timeType == null) {
            return false;
        }
        if (args1 == args2) {
            return true;
        }
        if ((args1 == null && args2 != null) || (args1 != null && args2 == null)) {
            return false;
        }

        try {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(args1);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(args2);

            switch (timeType) {
                case ALL:
                    return calendar1.equals(calendar2);

                case YEAR:
                    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);

                case MONTH:
                    return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                            && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));

                case DAY:
                    return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                            && (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));

                case HOUR:
                    return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                            && (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR))
                            && (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY));

                case MINUTE:
                    return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                            && (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR))
                            && (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY))
                            && (calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE));

                case SECOND:
                    return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                            && (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR))
                            && (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY))
                            && (calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE))
                            && (calendar1.get(Calendar.SECOND) == calendar2.get(Calendar.SECOND));

                case MILLISECOND:
                    return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                            && (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR))
                            && (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY))
                            && (calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE))
                            && (calendar1.get(Calendar.SECOND) == calendar2.get(Calendar.SECOND))
                            && (calendar1.get(Calendar.MILLISECOND) == calendar2.get(Calendar.MILLISECOND));

                default:
                    return false;
            }
        } catch (Exception e) {
            Logger.e(TAG, "", e);
        }

        return false;
    }
}
