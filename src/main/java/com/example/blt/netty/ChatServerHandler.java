package com.example.blt.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.utils.SpringUtils;
import com.example.blt.utils.StringBuildUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 服务器主要的业务逻辑
 *
 * @author hongjian.chen
 * @version time：2018/2/28.
 */
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static RedisTemplate redisTemplate = SpringUtils.getRedisTemplate();

    private static Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);
    //保存所有活动的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) {
        Channel channel = arg0.channel();
        String addr = channel.remoteAddress().toString();
        String ip = addr.substring(1, addr.indexOf(":"));
        String host = channel.id().toString();
        String master = sqlSessionTemplate.selectOne("console.getMaster", host);
        String host_id = sqlSessionTemplate.selectOne("console.getHostId", host);
        List<String> hosts = null;
        String type = null;
        String cmd = arg1;
        String to = host;
        try {
            JSONObject jsonObject = JSON.parseObject(arg1);
            cmd = jsonObject.getString("command");
            to = StringUtils.isNotEmpty(jsonObject.getString("host")) ? jsonObject.getString("host") : "";
            host_id = to;
            type = jsonObject.getString("type");
            if (to.equals(master)) {
                to = "master";
            }
        } catch (Exception e) {
//            if (arg1.indexOf("182716324621") != -1) {
//                logger.error("ip [{}] cmd [{}]", to, cmd);
//            }
            if (arg1.indexOf("77050901") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77050107") != -1) {
                cmd = "77050103";
//                sendPoeInfo(arg0);
            }
            if (arg1.indexOf("77050208") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77010F65") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77050304") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77050506") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77050705") != -1) {
                cmd = "77050103";
            }
            if (host.equals(master)) {
                to = "master";
            }
        }
        if ("master".equals(to)) {
            hosts = sqlSessionTemplate.selectList("console.getHostsByGid", host);
            if (hosts.size() == 0) {
                hosts = sqlSessionTemplate.selectList("console.getHosts", type);
            }
        }
        int len = cmd.length();
        //当有用户发送消息的时候，对其他用户发送信息
        if (len > 9 && len <= 50) {
            for (Channel ch : group) {
                SocketAddress address = ch.remoteAddress();
                if (address != null) {
                    String str = address.toString();
                    String tem = str.substring(1, str.indexOf(":"));
                    if (!tem.equals("127.0.0.1")) {
                        String id = ch.id().toString();
                        if ("all".equals(to)) {
                            ch.writeAndFlush(cmd);
                        } else if ("master".equals(to) && cmd.indexOf("77050103") == -1) {
                            for (String guest : hosts) {
                                if (!guest.equals(host)) {
                                    if (id.equals(guest)) {
                                        ch.writeAndFlush(cmd);
                                    }
                                }
                            }
                        } else if (id.equals(to)) {
                            if (StringUtils.isNotEmpty(host_id)) {
                                ch.writeAndFlush(cmd);
                            }
                        }
                    }
                }
            }
            if (cmd.indexOf("77050103") != -1) {
                redisTemplate.opsForValue().set(host, arg1, 50, TimeUnit.SECONDS);
            } else {
                logger.warn("flag [{}] hostId[{}] to [{}] hosts[{}] cmd [{}]", StringUtils.isNotEmpty(host_id), host, to, hosts, cmd);
            }
        }
        if (arg1.length() >= 16) {
            StringBuildUtils.buildLightInfo(ip, host, arg1);
//                ExecuteTask.pingInfo(host, arg1);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        group.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        group.remove(channel);
    }

    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        sendPoeInfo(ctx);
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        logger.error("socketInactive error [{}]", cause.getMessage());
        ctx.close().sync();
    }

    public void sendPoeInfo(ChannelHandlerContext ctx) {
        SocketAddress address = ctx.channel().remoteAddress();
        String str = address.toString();
        String ip = str.substring(1, str.indexOf(":"));
        if (!ip.equals("127.0.0.1")) {
            ctx.writeAndFlush("77050101CCCC");//获取mesh信息
            ctx.writeAndFlush("77050105CCCC");//获取mac信息
            ctx.writeAndFlush("77050106CCCC");//获取ota信息
        }
    }
}
