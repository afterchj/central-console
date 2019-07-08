package com.example.blt.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author hongjian.chen
 * @date 2019/7/4 19:03
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
        pipeline.addLast("idleStateHandler", new IdleStateHandler(5, 0, 0, TimeUnit.MINUTES));
        //字符串编解码器
        pipeline.addLast(new StringEncoder());
        //修改了这里
        pipeline.addLast("decoder", new MyDecoder());
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 4, -8, 0));
//        pipeline.addLast("byteArrayEncoder", new ByteArrayEncoder());

        //自定义Handler
        pipeline.addLast("serverChannelHandler", new ChatServerHandler());
    }

}
