package com.dutycode.rpc.server.core.commuication.tcp;

import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.omg.CORBA.OMGVMCID;

import com.dutycode.rpc.server.contract.context.Global;
import com.dutycode.rpc.server.contract.context.ServerConfig;
import com.dutycode.rpc.server.contract.server.IServer;
import com.dutycode.rpc.server.core.proxy.IInvokeHandler;

public class SocketServer implements IServer {

	public SocketServer() {
	};

	private Logger log = Logger.getLogger(SocketServer.class);

	/**
	 * Netty serverboot
	 */
	static final ServerBootstrap serverBootstrap = new ServerBootstrap();

	/**
	 * Record all channel
	 */
	static final ChannelGroup allChannels = new DefaultChannelGroup("dutycode-rpc-channel-groups");

	/**
	 * invoker handler
	 */
	static IInvokeHandler invokeHandler = null;

	public void start() throws Exception {
		log.info("start dutycode rpc server, server config: ");
		log.info("service name : " + Global.getInstance().getServerConfig().getString(ServerConfig.SERVICE_NAME));
		log.info("tcp.listenPort : " + Global.getInstance().getServerConfig().getInt(ServerConfig.TCP_LISTENPORT));
		log.info("telnet.listenPort : " + Global.getInstance().getServerConfig().getInt(ServerConfig.TELNET_LISTENPORT));
		log.info("tcp.timeount : " + Global.getInstance().getServerConfig().getInt(ServerConfig.TCP_TIMEOUT));

		String invoker = Global.getInstance().getServerConfig().getString(ServerConfig.PROXY_INVOKER);
		invokeHandler = (IInvokeHandler)Class.forName(invoker).newInstance();
		
		serverBootstrap.setFactory(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors
				.newCachedThreadPool(), Global.getInstance().getServerConfig().getInt(ServerConfig.TCP_WORKERCOUNT)));
		
		
		SocketHandler sockerHandler = new SocketHandler();
		serverBootstrap.

	}

	public void stop() throws Exception {

	}

}
