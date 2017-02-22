package org.rapid.util.common;

/**
 * 环境
 * 
 * @author ahab
 */
public enum Env {

	/**
	 * 本地环境
	 */
	LOCAL,
	
	/**
	 * 测试环境
	 */
	TEST,
	
	/**
	 * 线上环境
	 */
	ONLINE;
	
	public static final Env match(String env) {
		for (Env temp : Env.values()) {
			if (temp.name().equalsIgnoreCase(env))
				return temp;
		}
		throw new RuntimeException("No environment found!");
	}
}
