package com.zdc.netty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.zdc.netty")
public class SpringConfig {

	@Bean
	public RpcServer rpcServer(){
		return new RpcServer(8080);
	}
}
