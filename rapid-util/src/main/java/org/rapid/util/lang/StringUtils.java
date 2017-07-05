package org.rapid.util.lang;

public class StringUtils {

	public static final String EMPTY = "";

	/**
	 * 所有省、直辖市的简称
	 */
	public static String[] PROVINCES = { "京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫",
			"鄂", "湘", "粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "港", "澳", "台" };
	/**
	 * 所有省、直辖市的行政区划代码
	 */
	public static int[] PROVINCES_CODE = { 110000, 120000, 130000, 140000, 150000, 210000, 220000, 230000, 310000,
			320000, 330000, 340000, 350000, 360000, 370000, 410000, 420000, 430000, 440000, 450000, 460000, 500000,
			510000, 520000, 530000, 540000, 610000, 620000, 630000, 640000, 650000, 710000, 810000, 820000 };
	/**
	 * 字母表
	 */
	public static String[] ALPHABET = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
	public static final String provinceAbbreviation(int region) {
		for (int idx = 0, len = PROVINCES_CODE.length; idx < len; idx++) {
			if (PROVINCES_CODE[idx] != region)
				continue;
			return PROVINCES[idx];
		}
		return null;
	}

	/**
	 * 获取字符串的长度，如果汉子则为2个字符长度
	 */
	public static int getLength(String string) {
		int length = 0;
		for (int i = 0; i < string.length(); i++) {
			int ascii = Character.codePointAt(string, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	/**
	 * 去掉字符串的头尾空格，任何空格都算，比如制表符，换行符，回车，全角空格等
	 * 
	 * @param str
	 * @return
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str))
			return str;
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0)))
			sb.deleteCharAt(0);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1)))
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * @see #hasLength(CharSequence)
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * 任何空格都算在内
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str))
			return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i)))
				return true;
		}
		return false;
	}

	/**
	 * 只要有一个字符串是 null 或者空就返回false
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean hasText(String... strs) {
		for (String str : strs) {
			if (!hasText(str))
				return false;
		}
		return true;
	}

	/**
	 * 替换字符串
	 * 
	 * @param string
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String replace(String string, String oldPattern, String newPattern) {
		if (!hasLength(string) || !hasLength(oldPattern) || newPattern == null)
			return string;
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = string.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(string.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = string.indexOf(oldPattern, pos);
		}
		sb.append(string.substring(pos));
		return sb.toString();
	}
}
