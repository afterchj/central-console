package com.example.blt.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 服务器主要的业务逻辑
 *
 * @author hongjian.chen
 * @version time：2018/2/28.
 */
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);
    //保存所有活动的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Set<Map> lightSet = new CopyOnWriteArraySet<>();

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) {
        Channel channel = arg0.channel();
        String cmd;
        String to;
        try {
            JSONObject jsonObject = JSON.parseObject(arg1);
            cmd = jsonObject.getString("command");
            to = jsonObject.getString("host");
        } catch (Exception e) {
            to = channel.remoteAddress().toString();
            cmd = arg1;
//            Map map = ExecuteTask.pingInfo(arg1, "127.0.0.1");
//            ExecuteTask.saveInfo(arg1, map, lightSet, false);
        }
//        ConsoleUtil.cleanSet(lightSet);
        //当有用户发送消息的时候，对其他用户发送信息
        if (cmd.length() > 9) {
            for (Channel ch : group) {
                SocketAddress address = ch.remoteAddress();
                String str = address.toString();
                String ip = str.substring(1, str.indexOf(":"));
                if (address != null) {
                    if (ip.equals(to)) {
                        ch.writeAndFlush(cmd);
                    } else {
                        if (!ip.equals("127.0.0.1")) {
                            ch.writeAndFlush(cmd);
                        }
                    }
                }
            }
        }
        logger.warn("[" + to + "] receive :" + cmd);
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
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        logger.warn("[" + ip + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        logger.warn("[" + ip + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        logger.warn("[" + ip + "]" + cause.toString());
        ctx.close().sync();
    }
}
