package org.rapid.util.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {
	
	public static final String ISO8601_UTC 		= "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final TimeZone TIMEZONE_UTC	= TimeZone.getTimeZone("UTC");

	/**
	 * 一天的毫秒数
	 */
	public static final int MILLIS_DAY			= 24 * 3600 * 1000;
	
	/**
	 * 半个小时的毫秒数
	 */
	public static final int MILLIS_HALF_HOUR	= 30 * 60 * 1000;
	
	public static int currentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static String UTCDate() { 
		return getDate(ISO8601_UTC, System.currentTimeMillis(), TIMEZONE_UTC);
	}
	
	/**
	 * 将 unix 时间戳转换为指定格式的时间字符串
	 * 
	 * @param format
	 * @param timestamp
	 * @param timeZone
	 * @return
	 */
	public static String getDate(String format, long timestamp, TimeZone timeZone) {
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(timeZone);
		return df.format(new Date(timestamp));
	}
	
	/**
	 * 获取 date1 和 date2 之间的时间差值:毫秒单位
	 * 
	 * @param date1
	 * @param date2
	 * @param format
	 * @param zone
	 * @return
	 */
	public static long getTimeGap(String date1, String date2, String format, TimeZone zone) {
		return getTime(date1, format, zone) - getTime(date2, format, zone);
	}
	
	public static long getTime(String date, String format, TimeZone zone) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(zone);
		try {
			return df.parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}
}
