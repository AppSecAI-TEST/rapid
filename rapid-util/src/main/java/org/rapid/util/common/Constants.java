package org.rapid.util.common;

import java.nio.charset.Charset;

public interface Constants {

	public static final Charset UTF_8						= Charset.forName("UTF-8");
	
	interface MimeType {
		
		final String APPLICATION_FORM_URLENCODED_VALUE 		= "application/x-www-form-urlencoded";

		final String TEXT_HTML_UTF_8 						= "text/html;charset=UTF-8";
		
		final String TEXT_JSON_UTF_8 						= "text/json;charset=UTF-8";
		
		final String TEXT_PLAIN_UTF_8 						= "text/plain;charset=UTF-8";
	}
}
