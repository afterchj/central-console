package com.example.blt.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientInitialHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel arg0){
		ChannelPipeline pipeline = arg0.pipeline();
		pipeline.addLast("stringD", new StringDecoder());
		pipeline.addLast("stringC", new StringEncoder());
		pipeline.addLast("http", new HttpClientCodec());
		pipeline.addLast("chat", new ChatClientHandler());
	}

}
