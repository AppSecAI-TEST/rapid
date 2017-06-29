package org.rapid.util.math.compare;

public enum StrComparable implements Comparable<String> {
	
	SINGLETON;

	@Override
	public boolean compare(ComparisonSymbol symbol, String src, String... targets) {
		int len = targets.length;
		if (0 == len)
			throw new IllegalArgumentException("Comparable targets error");
		switch (symbol) {
		case EQUAL:
			return targets[0].equals(src);
		case NOT_EQUAL:
			return !targets[0].equals(src);
		case GREATER_THAN:
		case GREATER_THAN_OR_EQUAL:
		case LESS_THAN:
		case LESS_THAN_OR_EQUAL:
		case BETWEEN:
		case LE_BETWEEN:
		case RE_BETWEEN:
			throw new UnsupportedOperationException("String can not compare whith range symbol");
		case IN:
			for (String str : targets) {
				if (!src.equals(str)) 
					continue;
				return true;
			}
			return false;
		case NOT_IN:
			for (String str : targets) {
				if (!src.equals(str)) 
					continue;
				return false;
			}
			return true;
		default:
			return false;
		}
	}
}
