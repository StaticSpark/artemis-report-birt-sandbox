package com.funshion.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: zhangyong
 * Date: 13-12-23
 * Time: 下午2:08
 * Description:
 */
public class Utils {

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    /**
     * 获得一个包含起止日期与截止日期的数组
     *
     * @param from
     * @param to
     * @return
     */
    public static List<String> getDateListInRange(String from, String to) {
//      获取起止日期中间的所有日期
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Calendar fromCal = Calendar.getInstance();
            fromCal.setTime(sdf.parse(from));
            Calendar toCal = Calendar.getInstance();
            toCal.setTime(sdf.parse(to));
            toCal.add(Calendar.DAY_OF_MONTH, 1);

            while (fromCal.before(toCal)) {
                dates.add(sdf.format(fromCal.getTime()));
                fromCal.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            return null;
        }
        return dates;
    }

    public static String dateToString(Date date, String format) {
        sdf.applyPattern(format);
        return sdf.format(date);
    }

}
