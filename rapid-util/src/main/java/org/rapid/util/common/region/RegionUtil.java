package org.rapid.util.common.region;

/**
 * 行政区划工具类
 * 
 * @author ahab
 *
 */
public class RegionUtil {
	
	public static final int CH_REGION_ID				= 100000;
	
	/**
	 * 判断 code2  是否是 code1 的子级行政区划
	 * 
	 * @param code1
	 * @param code2
	 * @return
	 */
	public static final boolean isSubRegion(int code1, int code2) {
		if (code1 == CH_REGION_ID)
			return true;
		RegionType type1 = RegionType.match(code1);
		RegionType type2 = RegionType.match(code2);
		int level1 = type1.level;
		int level2 = type2.level;
		while (level1 <= level2) {
			if (code1 == code2)
				return true;
			if (type2 == RegionType.COUNTRY)
				return false;
			code2 = RegionType.parent(code2);
			type2 = RegionType.match(code2);
			level2--;
		}
		return false;
	}
}
