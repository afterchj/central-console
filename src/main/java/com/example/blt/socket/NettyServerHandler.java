package com.example.blt.socket;

import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    //    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    //    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Set<Map> lightSet = new CopyOnWriteArraySet<>();
    //    private static Set<Map> lmacSet = new CopyOnWriteArraySet<>();


    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String msg) {
        Channel channel = arg0.channel();
        channel.writeAndFlush(msg);
        SocketAddress address = channel.remoteAddress();
//        logger.info("[" + address + "] receive:" + msg);
        String str = address.toString();
        String ip = str.substring(1, str.indexOf(":"));
        ConsoleUtil.cleanSet(lightSet);
        Map map = ExecuteTask.pingInfo(msg, ip);
        ExecuteTask.saveInfo(msg, map, lightSet);

    }


    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress().toString() + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress().toString() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        try {
            ctx.close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
