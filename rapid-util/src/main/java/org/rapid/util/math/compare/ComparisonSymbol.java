package org.rapid.util.math.compare;

/**
 * 比较符
 * 
 * @author ahab
 */
public enum ComparisonSymbol {

	GREATER_THAN(1),
	
	GREATER_THAN_OR_EQUAL(2),
	
	LESS_THAN(3),
	
	LESS_THAN_OR_EQUAL(4),
	
	EQUAL(5),
	
	BETWEEN(7),
	
	LE_BETWEEN(8),
	
	RE_BETWEEN(9);
	
	private int mark;
	
	private ComparisonSymbol(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final ComparisonSymbol match(int mark) {
		for (ComparisonSymbol symbol : ComparisonSymbol.values()) {
			if (symbol.mark != mark)
				continue;
			return symbol;
		}
		return null;
	}
}
