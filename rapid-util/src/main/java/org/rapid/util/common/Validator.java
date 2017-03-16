package org.rapid.util.common;

import java.util.regex.Pattern;

public class Validator {
	
	/**
     * 正则表达式：验证身份证
     */
    private static final String IDENTITY = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
    
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIdentity(String identity) {
        return Pattern.matches(IDENTITY, identity);
    }
}
