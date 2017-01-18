package com.dutycode.rpc.server.core.commuication.tcp;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.dutycode.rpc.server.contract.context.DCRpcContext;
import com.dutycode.rpc.server.contract.server.IServerHandler;

public class SocketHandler extends SimpleChannelUpstreamHandler implements IServerHandler {

	private Logger log = Logger.getLogger(SocketHandler.class);
	
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		super.handleUpstream(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		SocketServer.allChannels.add(e.getChannel());
		log.info("new channel open: " + e.getChannel().getRemoteAddress().toString());
	}



	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		e.getChannel().close();
		log.info("channel close: " + e.getChannel().getRemoteAddress().toString());
	}

	
	public void writeResponse(DCRpcContext context) {

	}
	
	
	

}
