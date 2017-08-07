package org.rapid.util.common.key;

import java.io.Serializable;

public class Pair<FIRST extends Serializable, SECOND extends Serializable> implements Serializable {

	private static final long serialVersionUID = -5689699323038629005L;
	
	private FIRST first;
	private SECOND second;
	
	public Pair() {}
	
	public Pair(FIRST first, SECOND second) {
		this.first = first;
		this.second = second;
	}
	
	public FIRST getFirst() {
		return first;
	}
	
	public void setFirst(FIRST first) {
		this.first = first;
	}
	
	public SECOND getSecond() {
		return second;
	}
	
	public void setSecond(SECOND second) {
		this.second = second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return first.toString() + "_" + second.toString();
	}
}
