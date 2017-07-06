package org.rapid.util.common.enums;

/**
 * 时间类型
 * 
 * @author ahab
 */
public enum TIME_TYPE {

	YEAR(1),
	
	MONTH(2),
	
	DAY(3),
	
	WEEK(4),
	
	SEASON(5),
	
	HOUR(6),
	
	MINUTES(7),
	
	SECOND(8),
	
	MILLISECOND(9),
	
	NANOSECOND(10);
	
	private int mark;
	
	private TIME_TYPE(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final TIME_TYPE match(int mark) {
		for (TIME_TYPE type : TIME_TYPE.values()) {
			if (type.mark == mark)
				return type;
		}
		return null;
	}
}
