package org.rapid.util.lang;

public final class DateUtils {
	
	/**
	 * 一天的毫秒数
	 */
	public static final int MILLIS_DAY			= 24 * 3600 * 1000;
	
	public static int currentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}
}
