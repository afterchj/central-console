package com.example.blt.netty;

import com.whalin.MemCached.MemCachedClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 服务器主要的业务逻辑
 */
@Component
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);
    //保存所有活动的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
        MemCachedClient memCachedClient = new MemCachedClient();
        Channel channel = arg0.channel();
//        //当有用户发送消息的时候，对其他用户发送信息
        for (Channel ch : group) {
            if (ch.remoteAddress() != null) {
                String address1 = String.valueOf(ch.remoteAddress());
                address1 = address1.substring(1, address1.lastIndexOf(":"));
                if (arg1.contains(":")) {
                    String address = arg1.substring(0, arg1.lastIndexOf(":"));
                    String value = arg1.substring(arg1.lastIndexOf(":") + 1);
                    if (address.equals(address1)) {
                        ch.writeAndFlush(value);
                        System.out.println("发送成功");
                        break;
                    }
                } else {
                    ch.writeAndFlush(address1);
                    logger.info("[" + channel.remoteAddress() + "]: " + arg1);
                }
            }
        }

        if ("0".equals(arg1)) {
            String address = String.valueOf(channel.remoteAddress());
            address = address.substring(1, address.lastIndexOf(":"));
            address = "central-console" + address;
            memCachedClient.set(address, arg1, new Date(21000));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.remove(channel);
    }

    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        logger.info("[" + ctx.channel().remoteAddress() + "]" + cause.getMessage());
        ctx.close().sync();
    }

}
