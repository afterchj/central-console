package com.example.blt.socket;

import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.HashMap;
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
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    //保存所有活动的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String msg) throws Exception {
        Channel channel = arg0.channel();
        channel.writeAndFlush(msg);
        logger.info("[" + channel.remoteAddress().toString() + "] receive:" + msg);
        Set<Map> set = new CopyOnWriteArraySet<>();
        //当有用户发送消息的时候，对其他用户发送信息
        for (Channel ch : group) {
            SocketAddress address = ch.remoteAddress();
            if (address != null) {
//                ch.writeAndFlush(arg1);
                String str = address.toString();
                String ip = str.substring(1, str.indexOf(":"));
                Map map = ExecuteTask.pingInfo(msg, ip);
                set.add(map);
            }
        }
        if (set.size() > 0) {
            Map map = new HashMap();
            map.put("list", map);
            sqlSessionTemplate.selectOne("console.saveUpdate", map);
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
        ctx.close().sync();
    }
}
