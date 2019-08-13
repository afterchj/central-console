package com.example.blt.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.Topics;
import com.example.blt.exception.NoTopicException;
import com.example.blt.service.ProducerService;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.SpringUtils;
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

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器主要的业务逻辑
 *
 * @author hongjian.chen
 * @version time：2018/2/28.
 */
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    private static Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);
    //保存所有活动的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) {
        Channel channel = arg0.channel();
        String addr = channel.remoteAddress().toString();
        String host = addr.substring(1, addr.indexOf(":"));
        String temp = sqlSessionTemplate.selectOne("console.getHost");
        String master = StringUtils.isEmpty(temp) ? "127.0.0.1" : temp;
        List<String> hosts = null;
        String cmd = arg1;
        String to;
        try {
            JSONObject jsonObject = JSON.parseObject(arg1);
            cmd = jsonObject.getString("command");
            to = jsonObject.getString("host");
        } catch (Exception e) {
            if (arg1.indexOf("77050901") != -1) {
                String meshId = arg1.substring(arg1.length() - 20, arg1.length() - 4);
                char[] chars = meshId.toCharArray();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < chars.length; i++) {
                    if (i % 2 != 0) {
                        buffer.append(chars[i]);
                    }
                }
//                for(int i=0;i<16;i+=2) {
//                    meshId += String.valueOf(Integer.parseInt(arg1.substring(len - 20, len - 4).substring(i, i+2),16));
//                }
                insertOrUpdateHost(arg0, buffer.toString());
                to = "127.0.0.1";
            } else {
                to = host;
            }
            if (host.equals(master)) {
                to = "master";
            }
        }
        if (to.equals("master")) {
            hosts = sqlSessionTemplate.selectList("console.getHosts");
        }
//        if (arg1.indexOf("182716324621") != -1) {
//            logger.error("ip [{}] cmd [{}]", to, cmd);
//        }
        int len = cmd.length();
        //当有用户发送消息的时候，对其他用户发送信息
        if (len > 9 && len < 43) {
            logger.warn("ip[{}] hosts[{}] cmd [{}]", to, hosts, cmd);
            ExecuteTask.parseLocalCmd(cmd, to);
            for (Channel ch : group) {
                SocketAddress address = ch.remoteAddress();
                if (address != null) {
                    String str = address.toString();
                    String ip = str.substring(1, str.indexOf(":"));
                    if (!ip.equals("127.0.0.1")) {
                        if (to.equals("all")) {
                            ch.writeAndFlush(cmd);
                        } else if (to.equals("master")) {
                            for (String guest : hosts) {
                                if (ip.equals(guest)) {
                                    ch.writeAndFlush(cmd);
                                }
                            }
                        } else if (to.equals(ip)) {
                            ch.writeAndFlush(cmd);
                        }
                    }
                }
            }
        }
        if (len >= 22) {
            if (arg1.indexOf("CCCC") != -1) {
//                StrUtil.buildLightInfo(host, arg1);
                ExecuteTask.pingInfo(host, arg1);
            }
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
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        InetSocketAddress local = (InetSocketAddress)ctx.channel().localAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        String serverIp = local.getAddress().getHostAddress();
        logger.warn("client ip[{}] server ip[{}]",clientIp,serverIp);
        ctx.channel().writeAndFlush("77050101CCCC");
        insertOrUpdateHost(ctx, "");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        ctx.close().sync();
    }

    private void insertOrUpdateHost(ChannelHandlerContext ctx, String meshId) {
        Map map = new ConcurrentHashMap();
        Channel channel = ctx.channel();
        String addr = channel.remoteAddress().toString();
        map.put("ip", addr.substring(1, addr.indexOf(":")));
        map.put("status", channel.isActive());
        if (StringUtils.isNotBlank(meshId)) {
            map.put("meshId", meshId);
        }
        try {
            ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(map));
        } catch (NoTopicException e) {
            sqlSessionTemplate.insert("console.saveUpdateHost", map);
        }
    }
}
