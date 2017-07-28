package org.rapid.util.common;

import java.nio.charset.Charset;
import java.util.Collections;

import org.rapid.util.common.consts.code.Code;
import org.rapid.util.common.message.Result;

public interface Consts {

	final Charset UTF_8						= Charset.forName("UTF-8");
	final String SYMBOL_EQUAL				= "=";
	final String SYMBOL_AND					= "&";
	final String SYMBOL_UNDERLINE			= "_";
	final String SYMBOL_DOC					= ".";
	final String SYMBOL_PLUS				= "+";
	final int REGION_ID_CH					= 100000;
	
	interface MimeType {
		
		final String APPLICATION_FORM_URLENCODED_VALUE 		= "application/x-www-form-urlencoded";

		final String TEXT_HTML_UTF_8 						= "text/html;charset=UTF-8";
		
		final String TEXT_JSON_UTF_8 						= "text/json;charset=UTF-8";
		
		final String TEXT_PLAIN_UTF_8 						= "text/plain;charset=UTF-8";
	}
	
	interface RESULT {
		Result OK 								= Result.result(Code.OK);
		Result FORBID							= Result.result(Code.FORBID);
		Result FAILURE							= Result.result(Code.FAILURE);
		Result TOKEN_INVALID					= Result.result(Code.TOKEN_INVALID);
		Result API_NOT_EXIST					= Result.result(Code.API_NOT_EXIST);
		Result CAPTCHA_ERROR					= Result.result(Code.CAPTCHA_ERROR);
		Result USER_NOT_EXIST					= Result.result(Code.USER_NOT_EXIST);
		Result KEY_DUPLICATED					= Result.result(Code.KEY_DUPLICATED);
		Result USER_STATUS_CHANGED				= Result.result(Code.USER_STATUS_CHANGED);
		Result UNSUPPORTED_SERVICE				= Result.result(Code.UNSUPPORTED_SERVICE);
		Result SORT_FIELD_NOT_EXIST	 			= Result.result(Code.SORT_FIELD_NOT_EXIST);
		Result REGION_OUT_OF_BOUNDARY			= Result.result(Code.REGION_OUT_OF_BOUNDARY);
		
		Result EMPTY_LIST						= Result.result(Collections.EMPTY_LIST);
	}
	
	interface REDIS {
		final String OK														= "OK";
		final String OPTION_ZREVRANGE 										= "ZREVRANGE";
		final String OPTION_ZRANGE											= "ZRANGE";
	}
	
	enum RegionLevel {
		COUNTRY(1),
		PROVINCE(2),
		CITY(3),
		COUNTY(4);
		private int mark;
		private RegionLevel(int mark) {
			this.mark = mark;
		}
		public int mark() {
			return mark;
		}
	}
}
