package org.rapid.util.lang;

import java.util.Locale;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneUtil {

	/**
	 * 获取手机号码：去掉国家编号
	 * 
	 * @param mobile
	 * @return
	 */
	public static long getNationalNumber(String mobile) {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		PhoneNumber number;
		try {
			number = util.parse(mobile, Locale.CHINA.getCountry());
		} catch (NumberParseException e) {
			throw new RuntimeException("mobile parse error!", e);
		}
		return number.getNationalNumber();
	}
	
	/**
	 * 获取手机区号：默认为中国
	 * 
	 * @param mobile
	 * @return
	 */
	public static long getCountryCode(String mobile) {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		PhoneNumber number;
		try {
			number = util.parse(mobile, Locale.CHINA.getCountry());
		} catch (NumberParseException e) {
			throw new RuntimeException("mobile parse error!", e);
		}
		return number.getCountryCode();
	}
	
	/**
	 * 判断是否是有效的手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		PhoneNumber number;
		try {
			number = util.parse(mobile, Locale.CHINA.getCountry());
		} catch (NumberParseException e) {
			return false;
		}
		return util.isValidNumber(number);
	}
}
