package org.rapid.util.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.rapid.util.common.enums.SEASON;

public final class DateUtil {
	
	public static final String YYYYMM					= "yyyyMM";
	public static final String YYYYMMDD					= "yyyyMMdd";
	public static final String YYYY_MM_DD_HH_MM_SS		= "yyyy-MM-dd HH:mm:ss";
	public static final String ISO8601_UTC 				= "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final TimeZone TIMEZONE_UTC			= TimeZone.getTimeZone("UTC");
	public static final TimeZone TIMEZONE_GMT_8			= TimeZone.getTimeZone("GMT+:08:00");
	
	/**
	 * 一天的毫秒数
	 */
	public static final int MILLIS_DAY			= 24 * 3600 * 1000;
	
	/**
	 * 半个小时的毫秒数
	 */
	public static final int MILLIS_HALF_HOUR	= 30 * 60 * 1000;
	
	/**
	 * 半个小时的毫秒数
	 */
	public static final int MILLIS_FIVE_MINUTES	= 60 * 1000;
	
	public static int currentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static String UTCDate() { 
		return getDate(ISO8601_UTC, System.currentTimeMillis(), TIMEZONE_UTC);
	}
	
	public static String getDate(String format, int timestamp) {
		return getDate(format, timestamp * 1000l, TimeZone.getDefault());
	}
	
	public static String getDate(String format, int timestamp, TimeZone timeZone) {
		return getDate(format, timestamp * 1000l, timeZone);
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
	
	public static long getTimeGap(String date1, String date2, String format) {
		return getTimeGap(date1, date2, format, TimeZone.getDefault());
	}
	
	public static long getTime(String date, String format) { 
		return getTime(date, format, TimeZone.getDefault());
	}
	
	public static int getSecondTime(String date, String format) { 
		return (int) (getTime(date, format, TimeZone.getDefault()) / 1000);
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
	
	/**
	 * 根据传入的时间获取该时间下一年的终点
	 * 
	 * @param date
	 * @param fomat
	 * @param toFormat
	 * @return
	 */
	public static String dateOfYearTail(String date, String format, String toFormat) {
		long timestamp = getTime(date, format);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp);
		return null;
	}
	public static void main(String[] args) {
		String date = "2017-08-10 15:09:10";
		System.out.println(getTime(date, YYYY_MM_DD_HH_MM_SS));
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(getTime(date, YYYY_MM_DD_HH_MM_SS));
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println(calendar.get(Calendar.MINUTE));
		System.out.println(calendar.get(Calendar.SECOND));
		System.out.println(calendar.get(Calendar.MILLISECOND));
	}
	
	public static int year(TimeZone timeZone, Locale locale, long time) {
		GregorianCalendar calendar = new GregorianCalendar(timeZone, locale);
		calendar.setTime(new Date(time));
		return calendar.get(Calendar.YEAR);
	}
	
	public static int month(TimeZone timeZone, Locale locale, long time) {
		GregorianCalendar calendar = new GregorianCalendar(timeZone, locale);
		calendar.setTime(new Date(time));
		return calendar.get(Calendar.MONTH);
	}
	
	public static int weekOfYear(TimeZone timeZone, Locale locale, long time) {
		GregorianCalendar calendar = new GregorianCalendar(timeZone, locale);
		calendar.setTime(new Date(time));
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int dayOfYear(TimeZone timeZone, Locale locale, long time) {
		GregorianCalendar calendar = new GregorianCalendar(timeZone, locale);
		calendar.setTime(new Date(time));
		return calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	public static int season(TimeZone timeZone, Locale locale, long time) {
		switch (month(timeZone, locale, time)) {
		case 2:
		case 3:
		case 4:
			return SEASON.SPRING.mark();
		case 5:
		case 6:
		case 7:
			return SEASON.SUMMER.mark();
		case 8:
		case 9:
		case 10:
			return SEASON.AUTUMN.mark();
		default:
			return SEASON.WINTER.mark();
		}
	}
	
	/**
	 * 获取某一年某一月的临界时间：第一天的第一秒(00:00:00) 或者 最后一天的最后一秒(23:59:59)
	 * 
	 * @return
	 */
	public static final int boundaryTimeOfMonth(int year, int month, boolean first) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, first ? 0 : 23);
		calendar.set(Calendar.MINUTE, first ? 0 : 59);
		calendar.set(Calendar.SECOND, first ? 0 : 59);
		return (int) TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
	}
}
