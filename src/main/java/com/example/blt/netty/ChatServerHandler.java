package com.example.blt.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
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
        logger.info("[" + channel.remoteAddress() + "] receive:" + arg1);
        //当有用户发送消息的时候，对其他用户发送信息
        for (Channel ch : group) {
            SocketAddress address = ch.remoteAddress();
            if (address != null) {
                String str = address.toString();
                String ip = str.substring(1, str.indexOf(":"));
                try {
                    JSONObject jsonObject = JSON.parseObject(arg1);
                    String cmd = jsonObject.getString("command");
                    String to = jsonObject.getString("host");
                    if (ip.equals("127.0.0.1") && cmd.length() > 9) {
                        ExecuteTask.parseLocalCmd(cmd, ip);
                        break;
                    }
                    if (ip.equals(to)) {
                        ch.writeAndFlush(cmd);
                        break;
                    } else {
                        if (!ip.equals("127.0.0.1")) {
                            ch.writeAndFlush(cmd);
                        }
                    }
                } catch (Exception e) {
                    int index = arg1.indexOf(":");
                    if (index != -1) {
                        String to = arg1.substring(0, index);
                        String cmd = arg1.substring(index + 1);
                        if (ip.equals(to)) {
                            ch.writeAndFlush(cmd);
                            break;
                        }
                    } else {
                        ConsoleUtil.cleanSet(lightSet);
                        Map map = ExecuteTask.pingInfo(arg1, ip);
                        ExecuteTask.saveInfo(arg1, map, lightSet);
                        if (!ip.equals("127.0.0.1") && arg1.length() > 9) {
                            ch.writeAndFlush(arg1);
                        }
                    }
//                        ExecuteTask.pingInfo(arg1, ip);
                }
            }
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
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        logger.info("[" + ip + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        logger.info("[" + ip + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        logger.info("[" + ip + "]" + cause.toString());
        ctx.close().sync();
    }
}
