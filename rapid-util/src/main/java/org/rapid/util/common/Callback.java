package org.rapid.util.common;

public interface Callback<P, V> {

	V invoke(P param) throws Exception;
}
