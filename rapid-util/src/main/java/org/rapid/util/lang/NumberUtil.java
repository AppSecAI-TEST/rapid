package org.rapid.util.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumberUtil {

	/**
	 * 检查是否是 2 的整数次幂，负数也算
	 * 
	 * @param val
	 * @return
	 */
	public static final boolean isPowerOfTwo(int val) { 
		if (val < 0)
			val = ~(val - 0x01);
		return Integer.bitCount(val) == 1;
    }  
	
	/**
	 * 将一个整数拆分成多个 2 的整数次幂的集合
	 * 
	 * @return
	 */
	public static final Set<Integer> splitIntoPowerOfTwoSet(int val) {
		int cval = val >= 0 ? val : ~(val - 0x01);
		if (0 == cval)
			return null;
		Set<Integer> set = new HashSet<Integer>();
		_splitIntoPowerOfTwo(set, cval, 0, val > 0);
		return set;
	}
	
	public static final List<Integer> splitIntoPowerOfTwoList(int val) {
		int cval = val >= 0 ? val : ~(val - 0x01);
		if (0 == cval)
			return null;
		List<Integer> list = new ArrayList<Integer>();
		_splitIntoPowerOfTwo(list, cval, 0, val > 0);
		return list;
	}
	
	private static final void _splitIntoPowerOfTwo(Collection<Integer> set, int val, int depth, boolean positive) {
		int pre = Integer.bitCount(val);
		val >>= 1;
		int aft = Integer.bitCount(val);
		if (aft + 1 == pre)
			set.add(positive ? (int) Math.pow(2, depth) : -(int) Math.pow(2, depth));
		if (0 == aft)
			return;
		_splitIntoPowerOfTwo(set, val, ++depth, positive);
	}
}
