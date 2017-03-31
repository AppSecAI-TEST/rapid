package org.rapid.util.lang;

import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {

	public static final Set<Integer> toInt(Set<String> set) {
		if (null == set || set.isEmpty())
			return null;
		Set<Integer> temp = new HashSet<Integer>();
		for (String str : set)
			temp.add(Integer.valueOf(str));
		return temp;
	}
}
