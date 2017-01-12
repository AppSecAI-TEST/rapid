package org.rapid.util.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {
	
	/**
	 * 将 13 位 unix 时间戳转换为指定格式的时间表示
	 * 
	 * @param timestamp
	 * @param format
	 * @param zone
	 * @return
	 */
	public static final String getDate(long timestamp, String format, TimeZone zone) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(zone);
		return df.format(new Date(timestamp));
	}
}
