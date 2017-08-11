package org.rapid.util.math.compare;

public enum StrComparable implements Comparable<String> {
	
	SINGLETON;

	@Override
	public boolean compare(ComparisonSymbol symbol, String src, String... targets) {
		int len = targets.length;
		if (0 == len)
			throw new IllegalArgumentException("Comparable targets error");
		switch (symbol) {
		case eq:
			return targets[0].equals(src);
		case gt:
		case gte:
		case lt:
		case lte:
		case bteween:
		case lbteween:
		case rbteween:
			throw new UnsupportedOperationException("String can not compare whith range symbol");
		default:
			return false;
		}
	}
}
