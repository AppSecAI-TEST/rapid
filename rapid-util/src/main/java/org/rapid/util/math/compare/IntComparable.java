package org.rapid.util.math.compare;

public enum IntComparable implements Comparable<Integer> {
	
	SINGLETON;
	
	@Override
	public boolean compare(ComparisonSymbol symbol, Integer src, Integer... targets) {
		int len = targets.length;
		if (0 == len)
			throw new IllegalArgumentException("Comparable targets error");
		switch (symbol) {
		case eq:
			return targets[0] == src;
		case gt:
			return src.intValue() > targets[0].intValue();
		case gte:
			return src.intValue() >= targets[0].intValue();
		case lt:
			return src.intValue() < targets[0].intValue();
		case lte:
			return src.intValue() <= targets[0].intValue();
		case bteween:
		case lbteween:
		case rbteween:
			if (2 != len)
				throw new IllegalArgumentException("Comparable targets error");
			int max = Math.max(targets[0], targets[1]);
			int min = Math.min(targets[0], targets[1]);
			if (symbol == ComparisonSymbol.bteween)
				return (src > min && src < max);
			else if (symbol == ComparisonSymbol.lbteween) 
				return (src >= min && src < max);
			else
				return (src > min && src <= max);
		default:
			return false;
		}
	}
}
