package org.rapid.util.common.enums;

public enum REGION_TYPE {

	COUNTRY(1),
	
	PROVINCE(2),
	
	CITY(3),
	
	COUNTY(4);
	
	private int mark;
	
	private REGION_TYPE(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final REGION_TYPE match(int level) {
		for (REGION_TYPE type : REGION_TYPE.values()) {
			if (type.mark == level)
				return type;
		}
		return null;
	}
}
