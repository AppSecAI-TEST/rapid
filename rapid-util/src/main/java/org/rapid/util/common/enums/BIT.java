package org.rapid.util.common.enums;

public enum BIT {

	LOW(0),
	
	HIGH(1);
	
	private int mark;
	
	private BIT(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
