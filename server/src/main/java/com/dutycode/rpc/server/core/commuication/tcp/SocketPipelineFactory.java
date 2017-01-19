package com.dutycode.rpc.server.core.commuication.tcp;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;

import com.dutycode.rpc.protocal.ProtocolConst;

public class SocketPipelineFactory implements ChannelPipelineFactory {

	private final ChannelHandler handler ;
	private int frameMaxLength;
	
	public SocketPipelineFactory(ChannelHandler handler, int frameMaxLength){
		this.handler = handler;
		this.frameMaxLength = frameMaxLength;
	}
	
	
	public ChannelPipeline getPipeline() throws Exception {
		
		ChannelPipeline pipeline = Channels.pipeline();
//		ChannelBuffer buf = ChannelBuffers.directBuffer(capacity);
		
//		pipeline.addLast("encoder", handler);
//		pipeline.addLast("decoder", handler);
//		pipeline.addLast("executor", handler);
		
		ChannelBuffer buf = ChannelBuffers.directBuffer(ProtocolConst.P_END_TAG.length);
		buf.writeBytes(ProtocolConst.P_END_TAG);
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(this.frameMaxLength, true, buf));
		pipeline.addLast("handler", handler);
		return pipeline;
	}

}
