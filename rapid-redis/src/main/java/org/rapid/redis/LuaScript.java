package org.rapid.redis;

/**
 * Lua 脚本
 * 
 * @author ahab
 */
public class LuaScript {

	/**
	 * lua 脚本的 sha1 值
	 */
	private String sha1Key;
	
	/**
	 * 脚本内容
	 * 
	 */
	private String content;
	
	/**
	 * 是否已经缓存在远程 redis 中了，如果为 true 则表示远程 redis 服务器已经有该脚本了
	 * 
	 */
	private boolean stored;
	
	public LuaScript(String sha1Key, String content) {
		this.sha1Key = sha1Key;
		this.content = content;
	}
	
	public String getSha1Key() {
		return sha1Key;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean isStored() {
		return stored;
	}
	
	public void setStored(boolean stored) {
		this.stored = stored;
	}
}
