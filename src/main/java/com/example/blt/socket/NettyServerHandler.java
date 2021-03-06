package com.example.blt.socket;

import com.example.blt.task.ExecuteTask;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * 服务器主要的业务逻辑
 *
 * @author hongjian.chen
 * @version time：2018/2/28.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
//    private static Set<Map> lightSet = new CopyOnWriteArraySet<>();


    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String msg) {
//        ConsoleUtil.cleanSet(lightSet);
        Channel channel = arg0.channel();
//        channel.writeAndFlush(msg);
        SocketAddress address = channel.remoteAddress();
        String str = address.toString();
        String ip = str.substring(1, str.indexOf(":"));
        ExecuteTask.pingInfo(ip, msg);
//        ExecuteTask.saveInfo(msg, map, lightSet, true);
    }


    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        Channel channel = ctx.channel();
//        logger.warn("[" + channel.remoteAddress().toString() + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
//        Channel channel = ctx.channel();
//        logger.warn("[" + channel.remoteAddress().toString() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        ctx.close().sync();
    }
}
