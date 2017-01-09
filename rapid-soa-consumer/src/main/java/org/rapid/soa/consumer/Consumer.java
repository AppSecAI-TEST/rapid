package org.rapid.soa.consumer;

import java.util.List;

import org.rapid.soa.common.api.DemoService;
import org.rapid.soa.common.model.User;

public class Consumer {

	private DemoService demoService;
	
	public void init() { 
		User user = demoService.getUserById(1);
		System.out.println(user.getAge() + " " + user.getName() + " " + user.getAddress());
		List<User> list = demoService.getUsers(null);
		System.out.println(list.size());
		List<String> l = demoService.getIds(user);
		System.out.println(l);
	}
	
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}
}
