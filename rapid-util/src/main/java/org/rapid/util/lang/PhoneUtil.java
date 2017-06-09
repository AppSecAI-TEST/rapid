package org.rapid.util.lang;

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
			number = util.parse(mobile, null);
		} catch (NumberParseException e) {
			throw new RuntimeException(e);
		}
		return number.getNationalNumber();
	}
}
