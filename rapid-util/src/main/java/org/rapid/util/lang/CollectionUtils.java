package org.rapid.util.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CollectionUtils {

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
