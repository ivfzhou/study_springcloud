package cn.ivfzhou.reserve_platform.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获得next天之后的日期。
     */
    public static Date getNextDate(int next) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, next);
        return calendar.getTime();
    }

    /**
     * 计算两个时间相差的天数。
     */
    public static int days(Date beginTime, Date endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DDD");
        Integer beginDays = Integer.parseInt(simpleDateFormat.format(beginTime));
        Integer endDays = Integer.parseInt(simpleDateFormat.format(endTime));
        return endDays - beginDays;
    }

    /**
     * 日期对象转换成指定格式的字符串。
     */
    public static String date2Str(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
