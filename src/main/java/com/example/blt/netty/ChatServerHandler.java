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
import org.springframework.stereotype.Component;

import java.net.SocketAddress;

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
//    @Resource
//    private RedisTemplate<String,String> redisTemplate;

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
//        MemCachedClient memCachedClient = new MemCachedClient();
//        redisTemplate= new RedisTemplate<>();
        Channel channel = arg0.channel();
        //当有用户发送消息的时候，对其他用户发送信息
        for (Channel ch : group) {
            SocketAddress address = ch.remoteAddress();
            if (address != null) {
                String str = address.toString();
                String ip = str.substring(1, str.indexOf(":"));
                try {
                    JSONObject jsonObject = JSON.parseObject(arg1);
                    String cmd = jsonObject.getString("cmd");
                    String to = jsonObject.getString("to");
                    logger.info("[" + ip + "/" + channel.id() + "] cmd: " + arg1);
                    if (to.equals(ip)) {
                        ch.writeAndFlush(cmd);
                    } else {
                        ch.writeAndFlush(cmd);
                    }
                } catch (Exception e) {
                    int index = arg1.indexOf(":");
                    if (index != -1) {
                        logger.info("[" + ip + "/" + channel.id() + "] receive cmd:" + arg1);
                        String to = arg1.substring(0, index);
                        String cmd = arg1.substring(index + 1);
                        if (ip.equals(to)) {
                            ch.writeAndFlush(cmd);
                        } else {
                            ch.writeAndFlush(cmd);
                        }
                    } else {
                        logger.info("[" + ip + "/" + channel.id() + "] receive heartbeat:" + arg1);
                        ch.writeAndFlush(arg1);
                    }
                }
            }
        }
//        if ("0".equals(arg1)) {
//            String address = String.valueOf(channel.remoteAddress());
//            address = address.substring(1, address.lastIndexOf(":"));
//            address = "central-console" + address;
//            Jedis jedis = new Jedis("127.0.0.1",6379);
//            jedis.auth("Tp123456");
//            jedis.setex(address,21,arg1);
//            System.out.println(jedis.get(address));
////            redisTemplate.opsForValue().set(address,arg1,210000, TimeUnit.MILLISECONDS);
////            System.out.println(redisTemplate.opsForValue().get(address));
////            memCachedClient.set(address, arg1, new Date(21000));
//        }
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
        logger.info("[" + ctx.channel().remoteAddress() + "]" + cause.toString());
        ctx.close().sync();
    }
}
