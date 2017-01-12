package org.rapid.soa.consumer;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
		while (true) {
			user = demoService.getUserById(1);
			System.out.println(user.getAge() + " " + user.getName() + " " + user.getAddress());
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}
}
