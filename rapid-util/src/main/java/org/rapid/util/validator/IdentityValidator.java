package org.rapid.util.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IdentityValidator {
	
	private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; 
	
	/**
	 * 根据身份证号获取年龄
	 * 
	 * @param identity
	 * @return
	 */
	public static int getAge(String identity) {
		if (15 == identity.length()) {
			try {
				identity = convertIdcarBy15bit(identity);
			} catch (ParseException e) {
				return 0;
			}
		}
        String birthday = identity.substring(6, 14);    
        Date birthdate;
		try {
			birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
		} catch (ParseException e) {
			return 0;
		}    
        GregorianCalendar currentDay = new GregorianCalendar();    
        currentDay.setTime(birthdate);    
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");  
        String year = simpleDateFormat.format(new Date());  
        return Integer.parseInt(year) - currentDay.get(Calendar.YEAR);  
	}
	
	public static boolean isMale(String identity) {
		if (15 == identity.length()) {
			try {
				identity = convertIdcarBy15bit(identity);
			} catch (ParseException e) {
				return true;
			}
		}
        String id17 = identity.substring(16, 17);    
        return Integer.parseInt(id17) % 2 != 0;
	}

	public static boolean isValidate18Idcard(String idcard) {
		if (null == idcard || idcard.length() != 18)
			return false;
		String idcard17 = idcard.substring(0, 17);
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		if (Validator.isDigital(idcard17))
			c = idcard17.toCharArray();
		else
			return false;
		if (null != c) {
			int bit[] = new int[idcard17.length()];
			bit = _converCharToInt(c);
			int sum17 = 0;
			sum17 = _getPowerSum(bit);
			checkCode = _getCheckCodeBySum(sum17);
			if (null == checkCode)
				return false;
			// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
			if (!idcard18Code.equalsIgnoreCase(checkCode))
				return false;
		}
		return true;
	}
	
	/** 
     * 将15位的身份证转成18位身份证 
     *  
     * @param idcard 
     * @return 
	 * @throws ParseException 
     */  
    private static String convertIdcarBy15bit(String idcard) throws ParseException {  
        if (null == idcard || idcard.length() != 15)
            return null;  
        String idcard17 = null;  
        if (Validator.isDigital(idcard)) {  
            String birthday = idcard.substring(6, 12);  
            Date birthdate  = new SimpleDateFormat("yyMMdd").parse(birthday);  
            Calendar cday = Calendar.getInstance();  
            cday.setTime(birthdate);  
            String year = String.valueOf(cday.get(Calendar.YEAR));  
            idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);  
            char c[] = idcard17.toCharArray();  
            String checkCode = "";  
            if (null != c) {  
                int bit[] = new int[idcard17.length()];  
                bit = _converCharToInt(c);  
                int sum17 = 0;  
                sum17 = _getPowerSum(bit);  
                checkCode = _getCheckCodeBySum(sum17);  
                if (null != checkCode)
                    return null;  
                idcard17 += checkCode;  
            }  
        } else
            return null;  
        return idcard17;  
    }  

	private static int[] _converCharToInt(char[] c) {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c)
			a[k++] = Integer.parseInt(String.valueOf(temp));
		return a;
	}

	private static int _getPowerSum(int[] bit) {
		int sum = 0;
		if (power.length != bit.length)
			return sum;
		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}
	
	private static String _getCheckCodeBySum(int sum17) {  
        String checkCode = null;  
        switch (sum17 % 11) {  
        case 10:  
            checkCode = "2";  
            break;  
        case 9:  
            checkCode = "3";  
            break;  
        case 8:  
            checkCode = "4";  
            break;  
        case 7:  
            checkCode = "5";  
            break;  
        case 6:  
            checkCode = "6";  
            break;  
        case 5:  
            checkCode = "7";  
            break;  
        case 4:  
            checkCode = "8";  
            break;  
        case 3:  
            checkCode = "9";  
            break;  
        case 2:  
            checkCode = "x";  
            break;  
        case 1:  
            checkCode = "0";  
            break;  
        case 0:  
            checkCode = "1";  
            break;  
        }  
        return checkCode;  
    }  
}
