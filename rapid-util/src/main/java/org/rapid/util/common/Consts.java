package org.rapid.util.common;

import java.nio.charset.Charset;

import org.rapid.util.common.consts.code.Code;
import org.rapid.util.common.message.Result;

public interface Consts {

	final Charset UTF_8						= Charset.forName("UTF-8");
	final String SYMBOL_EQUAL				= "=";
	final String SYMBOL_AND					= "&";
	final String SYMBOL_UNDERLINE			= "_";
	final String SYMBOL_DOC					= ".";
	
	interface MimeType {
		
		final String APPLICATION_FORM_URLENCODED_VALUE 		= "application/x-www-form-urlencoded";

		final String TEXT_HTML_UTF_8 						= "text/html;charset=UTF-8";
		
		final String TEXT_JSON_UTF_8 						= "text/json;charset=UTF-8";
		
		final String TEXT_PLAIN_UTF_8 						= "text/plain;charset=UTF-8";
	}
	
	interface RESULT {
		Result<?> OK = Result.result(Code.OK);
	}
}
