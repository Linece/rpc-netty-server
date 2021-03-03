package com.zdc.netty;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RpcServer implements ApplicationContextAware,InitializingBean {

	//存储自定义注解bean
	private Map<String,Object> handlerMap = new HashMap<String, Object>();

	private int port;

	public RpcServer(int port){
		this.port = port;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//接收客户端的链接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//处理已经被接收的链接
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup,workGroup ).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline()
							.addLast(new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)))
							//编码
							.addLast(new ObjectEncoder())
							//约为处理
							.addLast(new ProcessonHandler(handlerMap));

				}
			});
			serverBootstrap.bind(port).sync();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String,Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RPCService.class);
		if(!serviceBeanMap.isEmpty()){
			for(Object serviceBean:serviceBeanMap.values()){
				RPCService rpcService = serviceBean.getClass().getAnnotation(RPCService.class);
				//获取接口类定义
				String serviceName = rpcService.value().getName();
				//拿到版本号
				String version = rpcService.version();
				if(!StringUtils.isEmpty(version)){
					serviceName+="-"+version;
				}
				handlerMap.put(serviceName,serviceBean );
			}
		}
	}
}
