package com.zdc.netty;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BootsTrap {
  public static void main(String[] args) {
	  ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
	  ((AnnotationConfigApplicationContext) context).start();
  }
}
