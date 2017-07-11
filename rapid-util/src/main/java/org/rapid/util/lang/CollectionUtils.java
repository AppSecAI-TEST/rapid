package org.rapid.util.lang;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionUtils {
	
	public static final Set<Long> splitToLongSet(String param, String regex) {
		String[] arr = param.split(regex);
		Set<Long> set = new HashSet<Long>(arr.length);
		for (int i = 0, len = arr.length; i < len; i++)
			set.add(Long.valueOf(arr[i]));
		return set;
	}
	
	public static final List<Long> splitToLongList(String param, String regex) {
		String[] arr = param.split(regex);
		List<Long> list = new ArrayList<Long>(arr.length);
		for (int i = 0, len = arr.length; i < len; i++)
			list.add(Long.valueOf(arr[i]));
		return list;
	}
	
	public static final Integer[] toIntegerArray(String ...params) { 
		Integer[] arr = new Integer[params.length];
		for (int i = 0, len = params.length; i < len; i++)
			arr[i] = Integer.valueOf(params[i]);
		return arr;
	}
	
	public static final List<BigDecimal> toDecimalList(String ...params) { 
		List<BigDecimal> decimals = new ArrayList<BigDecimal>();
		for (int i = 0, len = params.length; i < len; i++)
			decimals.add(new BigDecimal(params[i]));
		return decimals;
	}
	
	public static final int[] toIntArray(String ...params) { 
		int[] arr = new int[params.length];
		for (int i = 0, len = params.length; i < len; i++)
			arr[i] = Integer.valueOf(params[i]);
		return arr;
	}

	public static final Set<Integer> toIntSet(Collection<String> collection) {
		if (null == collection || collection.isEmpty())
			return Collections.EMPTY_SET;
		Set<Integer> temp = new HashSet<Integer>();
		for (String str : collection)
			temp.add(Integer.valueOf(str));
		return temp;
	}
	
	public static final List<Integer> toIntList(Collection<String> collection) {
		if (null == collection || collection.isEmpty())
			return Collections.EMPTY_LIST;
		List<Integer> temp = new ArrayList<Integer>();
		for (String str : collection)
			temp.add(Integer.valueOf(str));
		return temp;
	}
	
	public static final LinkedList<String> toStrLinkedList(String... params) {
		if (null == params || 0 == params.length)
			return null;
		LinkedList<String> temp = new LinkedList<String>();
		for (int i = 0, len = params.length; i < len; i++)
			temp.addLast(params[i]);
		return temp;
	}
	
	/**
	 * null 或者没有元素都为true
	 * 
	 * @param collection
	 * @return
	 */
	public static final boolean isEmpty(Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}
	
	/**
	 * null 或者没有元素都为0
	 * 
	 * @param collection
	 * @return
	 */
	public static final int size(Collection<?> collection) {
		return isEmpty(collection) ? 0 : collection.size();
	}
	
	/**
	 * 判断 src2 是否是 src1 的子集
	 * 
	 * @param src1
	 * @param src2
	 * @return
	 */
	public static final boolean isSubSet(Collection<?> src1, Collection<?> src2) {
		if (null == src1)
			return null == src2;
		if (null != src2) {
			for (Object obj : src2) {
				if (!src1.contains(obj))
					return false;
			}
		}
		return true;
	}
}
