package com.example.blt.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class MyServerIniterHandler extends  ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		ChannelPipeline pipeline = arg0.pipeline();
		pipeline.addLast("dec",new StringDecoder());
		pipeline.addLast("enc",new StringEncoder());
		pipeline.addLast("cmd",new MyServerIniterHandler());
	}

}
