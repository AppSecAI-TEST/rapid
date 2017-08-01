package org.rapid.util.common.enums;

public enum SEASON {

	SPRING(1),
	
	SUMMER(2),
	
	AUTUMN(4),
	
	WINTER(8);
	
	private int mark;
	
	private SEASON(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
