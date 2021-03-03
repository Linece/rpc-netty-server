package com.zdc.netty;

import com.zdc.api.RpcRequest;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ProcessonHandler extends SimpleChannelInboundHandler<RpcRequest> {

	private Map<String,Object> handlerMap;

	public ProcessonHandler(Map<String,Object> handlerMap){
		this.handlerMap = handlerMap;
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
		Object result = invoke(msg);
		ctx.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);
	}

	public Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		//反射调用
		String serverName = rpcRequest.getClassName();
		String version = rpcRequest.getVersion();
		if(!StringUtils.isEmpty(version)){
			serverName += "-"+version;
		}
		Object service = handlerMap.get(serverName);
		if(service == null){
			throw new RuntimeException("service not found:"+serverName);
		}
		Object[] args = rpcRequest.getParams();
		Method method = null;
		Class clazz = Class.forName(rpcRequest.getClassName());
		method = clazz.getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
		Object result = method.invoke(service,args);
		return result;


	}
}
