package com.zdc.netty;

import com.zdc.api.IHelloService;
import com.zdc.api.User;

@RPCService(value = IHelloService.class,version = "V2.0")
public class HelloServiceImpl2 implements IHelloService {
	@Override
	public String sayHello(String content) {
		System.out.println("V2.0-->sayHello:"+content);
		return "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊："+content;
	}

	@Override
	public String saveUser(User user) {
		System.out.println("V2.0-->saveUser:"+user);
		return "saveUser";
	}
}
