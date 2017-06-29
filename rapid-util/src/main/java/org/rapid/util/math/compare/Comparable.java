package org.rapid.util.math.compare;

public interface Comparable<TYPE> {

	/**
	 * 比较接口：符合条件则返回 true，否则返回 false
	 * 
	 * @param src
	 * @param targets
	 * @return
	 */
	boolean compare(ComparisonSymbol symbol, TYPE src, TYPE... targets);
}
