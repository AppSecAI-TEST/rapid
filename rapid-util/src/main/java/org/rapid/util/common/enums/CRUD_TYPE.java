package org.rapid.util.common.enums;

public enum CRUD_TYPE {

	CREATE(1),
	
	RETRIEVE(2),
	
	UPDATE(4),
	
	DELETE(8);
	
	private int mark;
	
	private CRUD_TYPE(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final CRUD_TYPE match(int type) { 
		for (CRUD_TYPE temp : CRUD_TYPE.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
