package org.rapid.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class Validator {
	
	private static final Pattern UNIX_TIMESTAMP	= Pattern.compile("[1-9]\\d{9}");
	
	/**
     * 正则表达式：验证身份证
     */
    private static final Pattern IDENTITY = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
    
    /**
     * 正则表达式：车牌号
     */
    private static final Pattern VEHICLE_LICENSE = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");
    
    public static boolean isUnixTimestamp(String value) {
    	return _matches(UNIX_TIMESTAMP, value);
    }
    
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIdentity(String identity) {
    	return _matches(IDENTITY, identity);
    }
    
    /**
     * 校验车牌号
     * 
     * @param license
     * @return
     */
    public static boolean isVehicleLisense(String license) {
    	return _matches(VEHICLE_LICENSE, license);
    }
    
    /**
     * 检验手机号
     * 
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile, String countryCode) {
    	PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		try {
			PhoneNumber number = util.parse(mobile, countryCode);
			return util.isValidNumber(number);
		} catch (NumberParseException e) {
			return false;
		}
    }
    
    public static boolean isDigital(String str) {  
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");  
    } 
    
    private static boolean _matches(Pattern pattern, CharSequence input) {
    	if (null == input)
    		return false;
    	Matcher m = pattern.matcher(input);
    	return m.matches();
    }
}
