package org.rapid.util.common.region;

/**
 * 行政区划工具类
 * 
 * @author ahab
 *
 */
public class RegionUtil {
	
	/**
	 * 判断 code2  是否是 code1 的子级行政区划
	 * 
	 * @param code1
	 * @param code2
	 * @return
	 */
	public static final boolean isSubRegion(int code1, int code2) {
		RegionType type1 = RegionType.match(code1);
		RegionType type2 = RegionType.match(code2);
		int level1 = type1.level;
		int level2 = type2.level;
		while (level1 < level2) {
			code2 = RegionType.parent(code2);
			if (code1 == code2)
				return true;
			level2 ++;
		}
		return false;
	}
}
