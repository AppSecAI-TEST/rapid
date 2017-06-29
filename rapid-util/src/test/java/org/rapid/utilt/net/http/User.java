package org.rapid.utilt.net.http;

import java.util.Arrays;
import java.util.List;

import org.rapid.util.common.serializer.SerializeUtil;

public class User {

	private int age;
	private String name;
	private List<User> users;
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setAge(100);
		byte[] bytes = SerializeUtil.ProtostuffUtil.serial(user);
		System.out.println(Arrays.toString(bytes));
		byte[] nmew = new byte[]{8, 100};
		user = SerializeUtil.ProtostuffUtil.deserial(nmew, User.class);
		System.out.println(user.getAge() + " " + user.getName() + " " + user.getUsers());
	}
}
