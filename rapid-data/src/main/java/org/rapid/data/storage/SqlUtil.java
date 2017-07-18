package org.rapid.data.storage;

import java.util.Map;
import java.util.Map.Entry;

import org.rapid.util.common.Consts;
import org.rapid.util.reflect.ClassUtil;

public class SqlUtil {
	
	private static final String AND					= "AND";
	private static final String WHERE				= "WHERE";
	
	private static final String SPACE				= " ";
	private static final String REV_QUOTE			= "`";
	private static final String SINGLE_QUOTE		= "'";

	public static final void appendWithWhere(StringBuilder sql, Map<String, Object> params) {
		sql.append(SPACE).append(WHERE).append(SPACE);
		boolean first = true;
		for (Entry<String, Object> entry : params.entrySet()) {
			if (!first)
				sql.append(SPACE).append(AND).append(SPACE);
			first = false;
			sql.append(REV_QUOTE).append(entry.getKey()).append(REV_QUOTE).append(Consts.SYMBOL_EQUAL);
			if (ClassUtil.isNumber(entry.getValue())) 
				sql.append(entry.getValue());
			else
				sql.append(SINGLE_QUOTE).append(entry.getValue()).append(SINGLE_QUOTE);
		}
	}
}
