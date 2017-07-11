package org.rapid.util.math.compare;

public enum IntComparable implements Comparable<Integer> {
	
	SINGLETON;
	
	@Override
	public boolean compare(ComparisonSymbol symbol, Integer src, Integer... targets) {
		int len = targets.length;
		if (0 == len)
			throw new IllegalArgumentException("Comparable targets error");
		switch (symbol) {
		case EQUAL:
			return targets[0] == src;
		case GREATER_THAN:
			return src.intValue() > targets[0].intValue();
		case GREATER_THAN_OR_EQUAL:
			return src.intValue() >= targets[0].intValue();
		case LESS_THAN:
			return src.intValue() < targets[0].intValue();
		case LESS_THAN_OR_EQUAL:
			return src.intValue() <= targets[0].intValue();
		case BETWEEN:
		case LE_BETWEEN:
		case RE_BETWEEN:
			if (2 != len)
				throw new IllegalArgumentException("Comparable targets error");
			int max = Math.max(targets[0], targets[1]);
			int min = Math.min(targets[0], targets[1]);
			if (symbol == ComparisonSymbol.BETWEEN)
				return (src > min && src < max);
			else if (symbol == ComparisonSymbol.LE_BETWEEN) 
				return (src >= min && src < max);
			else
				return (src > min && src <= max);
		default:
			return false;
		}
	}
}
