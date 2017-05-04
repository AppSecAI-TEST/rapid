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
}
