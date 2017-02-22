package org.rapid.util.common;

import java.util.Random;

public class KeyUtil {

	/**
	 * 生成指定位数的 数字验证码
	 * 
	 * @param bitNum
	 * @return
	 */
	public static String randomCaptcha(int bitNum) { 
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		while (sb.length() < bitNum) 
			sb.append(random.nextInt(10));
		return sb.toString();
	}
}
