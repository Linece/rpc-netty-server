package com.zdc.provide;

import com.zdc.api.IHelloService;

public class App {
  public static void main(String[] args) {

	  IHelloService service = new HelloServiceImpl();
	  RpcPxoyServer server = new RpcPxoyServer();
	  server.publisher(service, 8080);

  }
}
