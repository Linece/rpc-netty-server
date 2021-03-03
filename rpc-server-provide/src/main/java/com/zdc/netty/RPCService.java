package com.zdc.netty;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface RPCService {

	//服务接口类
	Class<?> value();

	//版本号
	String version() default "";
}
