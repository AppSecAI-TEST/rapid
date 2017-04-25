package org.rapid.data;

public class TestPojo {

	private long expire;
	private String name;
	private int age;
	
	public TestPojo() {}
	
	public TestPojo(long expire, String name, int age) {
		this.expire = expire;
		this.name = name;
		this.age = age;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
