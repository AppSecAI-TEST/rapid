package org.rapid.util.lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {

	@SuppressWarnings("unchecked")
	public static final Set<Integer> toInt(Collection<String> set) {
		if (null == set || set.isEmpty())
			return Collections.EMPTY_SET;
		Set<Integer> temp = new HashSet<Integer>();
		for (String str : set)
			temp.add(Integer.valueOf(str));
		return temp;
	}
}
