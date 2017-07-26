package org.rapid.util.concurrent;

public class ThreadLocalUtil {

	public static final ThreadLocal<Integer> INT_HOLDER				= new ThreadLocal<Integer>();
	
	public static final <T> T getAndRemove(ThreadLocal<T> threadLocal) {
		T t = threadLocal.get();
		threadLocal.remove();
		return t;
	}
}
