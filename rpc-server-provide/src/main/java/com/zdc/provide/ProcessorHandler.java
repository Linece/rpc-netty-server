package com.zdc.provide;

import com.zdc.api.RpcRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessorHandler implements Runnable {

  private Object service;

  private Socket socket;

  public ProcessorHandler(Socket socket, Object service) {
    this.service = service;
    this.socket = socket;
  }



  public void run() {

    ObjectInputStream objectInputStream = null;

    ObjectOutputStream objectOutputStream = null;

    try {
      objectInputStream = new ObjectInputStream(socket.getInputStream());
      RpcRequest request = (RpcRequest)objectInputStream.readObject();
      Object result = invoke(request);

      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectOutputStream.writeObject(result);
      objectOutputStream.flush();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Object[] args = rpcRequest.getParams();
    Class[] types = new Class[args.length];
    for(int i=0;i<args.length;i++){
      types[i] = args[i].getClass();
    }
    Class clazz = Class.forName(rpcRequest.getClassName());
    Method method = clazz.getMethod(rpcRequest.getMethodName(),types);
    Object result = method.invoke(service,args);
    return result;
  }

  public Object getService() {
    return service;
  }

  public void setService(Object service) {
    this.service = service;
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }
}
