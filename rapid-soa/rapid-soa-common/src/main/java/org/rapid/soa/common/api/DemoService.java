package org.rapid.soa.common.api;

import java.util.List;

import org.rapid.soa.common.model.User;

public interface DemoService {

	User getUserById(int id); 
	
	List<User> getUsers(List<String> list);
	
	List<String> getIds(User user);
}
