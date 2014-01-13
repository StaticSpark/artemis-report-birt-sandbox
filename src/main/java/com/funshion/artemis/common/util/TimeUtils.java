package com.funshion.artemis.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间相关操作
 * 
 * @author ?,jiangxu 
 * Reviewed-by weibl
 */
public class TimeUtils {
	private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);
	public static final String FormatWithHourMinuteSecond="yyyy-MM-dd HH:mm:ss";
	/**
	 * 返回当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}
 
	/**
	 * 根据指定格式解析时间
	 * 
	 * @param time
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTimeByFormatWithException(String time, String format) throws ParseException {
		SimpleDateFormat formate = new SimpleDateFormat(format);
		return formate.parse(time);
	}
	public static Date parseDateTimeByFormat(String dateStr, String format) {
		Date date=null;
		try {
			date = TimeUtils.parseDateTimeByFormatWithException(dateStr, format);
		} catch (ParseException e) {
			logger.error("parseDateTimeByFormat",e);	
		}
		return date;
	}
	/**
	 * 以格式(yyyy-MM-dd HH:mm:ss)解析时间
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTimeWithHourMinuteSecondFormat(String time) {
		Date back=null;
		try {
			back= parseDateTimeByFormatWithException(time,FormatWithHourMinuteSecond);
		} catch (ParseException e) {
			logger.error("parseDateTimeWithHourMinuteSecondFormat",e);
		}
		return back;
	}
	
	/**
	 * 生成不存在的小时sql
	 * @param monId
	 * @param areaId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getImpChartUnExistsHourSql(String monId, String areaId, String startDate, String endDate) {
		Date start = parseDateTimeByFormat(startDate, "yyyy-MM-dd");
		Date end = parseDateTimeByFormat(endDate, "yyyy-MM-dd");
		List<String> dateList = new ArrayList<String>();
		 while(start.before(end) || start.compareTo(end) == 0) {
			 int month = start.getMonth() + 1;
			 if(month >= 13) {
				 month = 1;
			 }
			 
			 String monthStr = "";
			 if(month < 10) {
				 monthStr = "0" + month;
			 }
			 dateList.add(formatDate(start, "yyyy-MM-dd"));
			 start.setDate(start.getDate() + 1);
		 }
		 String[] areaIds = areaId.split(",");
		 String sql = "";
		 for(int i = 0; i < dateList.size(); i++) {
			 for(int j = 0; j < areaIds.length; j++) {
				 for(int h = 0; h < 24; h++) {
					 sql += "SELECT "+h+" as mon_hour, 0 as path, "+monId+" as monId,'" + dateList.get(i) + "' as monDate, "+areaIds[j]+" as areaId,'' AS impFwd,	'' AS actImp," +
					        "'' AS impBlock,'' AS impPercent,'' AS clkFwd,'' AS actClk,'' AS clkBlock,'' AS impChokeSum,'' AS clkChokeSum,'' AS impChokeArea,'' AS clkChokeArea union ";
				 }
			 }
		 }
		 return sql;
	}
	
	/**
	 * 以格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat formate = new SimpleDateFormat(pattern);
		return formate.format(date);
	}
	/**
	 * 以格式(yyyy-MM-dd HH:mm:ss)格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateWithHourMinuteSecond(Date date){
		return formatDate(date,FormatWithHourMinuteSecond);
	}
	
	/**
	 * 得到昨天的开始时间
	 * @return
	 */
	public static String getYesterdayStartTime() {
		Date date = new Date();
		date.setDate(date.getDate() - 1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		String startDate = TimeUtils.formatDateWithHourMinuteSecond(date);
		return startDate;
	}
	
	/**
	 * 得到昨天的结束时间
	 * @return
	 */
	public static String getYesterdayEndTime() {
		Date date = new Date();
		date.setDate(date.getDate() - 1);
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		String endDate = TimeUtils.formatDateWithHourMinuteSecond(date);
		return endDate;
	}
}
