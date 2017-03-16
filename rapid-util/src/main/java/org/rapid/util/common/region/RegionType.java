package org.rapid.util.common.region;

public enum RegionType {

	PROVINCE(1),
	CITY(2),
	DISTRICT(4);
	
	private int mark;
	
	private RegionType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
