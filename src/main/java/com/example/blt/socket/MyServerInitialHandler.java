package com.example.blt.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class MyServerInitialHandler extends  ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0){
		ChannelPipeline pipeline = arg0.pipeline();
		pipeline.addLast("dec",new StringDecoder());
		pipeline.addLast("enc",new StringEncoder());
//		pipeline.addLast("bink" ,new ByteArrayEncoder());
		pipeline.addLast("cmd",new NettyServerHandler());
	}

}
