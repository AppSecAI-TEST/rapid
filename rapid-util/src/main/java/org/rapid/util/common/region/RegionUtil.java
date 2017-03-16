package org.rapid.util.common.region;

/**
 * 行政区划工具类
 * 
 * @author ahab
 *
 */
public class RegionUtil {
	
	private static final String PROVINCE_LAST_STR			= "0000";
	private static final String CITY_LAST_STR				= "00";

	/**
	 * 获取行政区划类型
	 * 
	 * @return
	 */
	public static final RegionType type(int code) {
		String str = String.valueOf(code).substring(2);
		if (str.equals(PROVINCE_LAST_STR))
			return RegionType.PROVINCE;
		str = String.valueOf(code).substring(4);
		if (str.equals(CITY_LAST_STR))
			return RegionType.CITY;
		return RegionType.DISTRICT;
	}
}
