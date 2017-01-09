package org.rapid.soa.service;

import java.util.ArrayList;
import java.util.List;

import org.rapid.soa.common.api.DemoService;
import org.rapid.soa.common.model.User;

public class DemoServiceImpl implements DemoService {
	
	@Override
	public User getUserById(int id) {
		System.out.println("invoke getUserById " + id);
		User user = new User();
		user.setAge(12);
		user.setAddress("是行ss航走々");
		user.setName("sss");
		return user;
	}

	@Override
	public List<User> getUsers(List<String> list) {
		System.out.println("invoke getUsers");
		System.out.println(list);
		List<User> l = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setAge(i);
			user.setAddress("是行ss航走々" + i);
			user.setName("sss" + i);
			l.add(user);
		}
		return l;
	}

	@Override
	public List<String> getIds(User user) {
		System.out.println("invoke getIds");
		return null;
	}
}
