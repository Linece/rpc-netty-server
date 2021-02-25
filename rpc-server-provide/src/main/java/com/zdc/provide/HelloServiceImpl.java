package com.zdc.provide;

import com.zdc.api.IHelloService;
import com.zdc.api.User;

public class HelloServiceImpl implements IHelloService {
	public String sayHello(String content) {
		System.out.println("sayHello:"+content);
		return content;
	}

	public String saveUser(User user) {
		System.out.println("saveUser:"+user);
		return "saveUser";
	}
}
