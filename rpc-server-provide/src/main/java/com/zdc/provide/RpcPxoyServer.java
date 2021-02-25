package com.zdc.provide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcPxoyServer {

	private ExecutorService executorService = Executors.newCachedThreadPool();


	public void publisher(Object service,int port){
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(port);
			while (true){
				Socket socket = serverSocket.accept();
				//ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				executorService.execute(new ProcessorHandler(socket,service));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
