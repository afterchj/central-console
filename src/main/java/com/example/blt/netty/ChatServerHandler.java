package com.example.blt.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.Topics;
import com.example.blt.service.ProducerService;
import com.example.blt.task.ExecuteTask;
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

import java.net.SocketAddress;
import java.util.HashMap;
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
        String host = channel.id().toString();
        String master = sqlSessionTemplate.selectOne("console.getHost", host);
        List<String> hosts = null;
        String type = null;
        String cmd = arg1;
        String to = host;
        try {
            JSONObject jsonObject = JSON.parseObject(arg1);
            cmd = jsonObject.getString("command");
            to = jsonObject.getString("host");
            type = jsonObject.getString("type");
        } catch (Exception e) {
            int len = arg1.length();
            if (arg1.indexOf("77050901") != -1) {
                cmd = "77050103";
                String meshId = arg1.substring(8, 24);
                char[] chars = meshId.toCharArray();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < chars.length; i++) {
                    if (i % 2 != 0) {
                        buffer.append(chars[i]);
                    }
                }
                insertOrUpdateHost(channel, buffer.toString(), "");
            }
            if (arg1.indexOf("77050208") != -1) {
                logger.warn("hostId [{}] str [{}]", host, arg1);
                cmd = "77050103";
                Map map = new HashMap();
                String flag = arg1.substring(8, 10);
                map.put("hostId", host);
                map.put("flag", flag);
                updateHost(map, false);
            }
            if (arg1.indexOf("77010F65") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77050304") != -1) {
                cmd = "77050103";
            }
            if (arg1.indexOf("77050506") != -1) {
                cmd = "77050103";
                Map map = new HashMap();
                String temp = StringBuildUtils.sortMac(arg1.substring(8, 12)).replace(":", "");
                int product = Integer.parseInt(temp, 16);
                if (arg1.length() <= 16) return;
                String version = arg1.substring(12, 16);
                map.put("hostId", host);
                map.put("type", product);
                map.put("version", version);
                saveHost(map, false);
            }
            if (arg1.indexOf("77050705") != -1) {
                cmd = "77050106CCCC";//获取固件信息
                if (len >= 48 && len <= 52) {
                    String mac = StringBuildUtils.sortMac(arg1.substring(36, 48));
                    insertOrUpdateHost(channel, "", mac);
                } else {
                    String mac = StringBuildUtils.sortMac(arg1.substring(8, 20));
                    insertOrUpdateHost(channel, "", mac);
                }
            }
            if (host.equals(master)) {
                to = "master";
            }
        }
        if (to.equals("master")) {
            hosts = sqlSessionTemplate.selectList("console.getHostsByGid", host);
            if (hosts.size() == 0) {
                hosts = sqlSessionTemplate.selectList("console.getHosts", type);
            }
        }
//        if (arg1.indexOf("182716324621") != -1) {
//            logger.error("ip [{}] cmd [{}]", to, cmd);
//        }
        int len = cmd.length();
        //当有用户发送消息的时候，对其他用户发送信息
        if (len > 9 && len <= 50) {
            if (cmd.indexOf("77050103") == -1) {
                logger.warn("hostId[{}] hosts[{}] cmd [{}]", host, hosts, cmd);
            }
            StringBuildUtils.parseLocalCmd(cmd, to);
            for (Channel ch : group) {
                SocketAddress address = ch.remoteAddress();
                if (address != null) {
                    String str = address.toString();
                    String ip = str.substring(1, str.indexOf(":"));
                    if (!ip.equals("127.0.0.1")) {
                        String id = ch.id().toString();
                        logger.warn("id[{}] ip [{}]", id, ip);
                        if (to.equals("all")) {
                            ch.writeAndFlush(cmd);
                        } else if (to.equals("master")) {
                            for (String guest : hosts) {
                                if (id.equals(guest)) {
                                    ch.writeAndFlush(cmd);
                                }
                            }
                        } else if (to.equals(id)) {
                            ch.writeAndFlush(cmd);
                        }
                    }
                }
            }
        }
        if (len >= 46) {
            if (arg1.indexOf("CCCC") != -1) {
//                logger.warn("info [{}]", arg1);
//                StringBuildUtils.buildLightInfo(host, arg1);
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
        Channel channel = ctx.channel();
        insertOrUpdateHost(channel, "", "");
        channel.writeAndFlush("77050101CCCC");
        channel.writeAndFlush("77050105CCCC");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        insertOrUpdateHost(channel, "", "");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
        logger.error("socketInactive error [{}]", cause.getMessage());
        ctx.close().sync();
    }

    private void insertOrUpdateHost(Channel channel, String meshId, String mac) {
        logger.warn("hostId [{}] meshId [{}] mac [{}] status [{}]", channel.id(), meshId, mac, channel.isActive());
        Map map = new ConcurrentHashMap();
        String addr = channel.remoteAddress().toString();
        map.put("ip", addr.substring(1, addr.indexOf(":")));
        map.put("hostId", channel.id().toString());
        map.put("status", channel.isActive());
        if (StringUtils.isNotBlank(meshId)) {
            map.put("meshId", meshId);
        }
        if (StringUtils.isNotBlank(mac)) {
            map.put("mac", mac);
        }
        saveHost(map, false);
    }

    public void saveHost(Map map, boolean flag) {
        if (flag) {
            try {
                ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(map));
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.insert("console.saveUpdateHosts", map);
    }

    public void updateHost(Map map, boolean flag) {
        if (flag) {
            try {
                ProducerService.pushMsg(Topics.HOST_UPDATE_TOPIC.getTopic(), JSON.toJSONString(map));
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.insert("console.updateHosts", map);
    }
}
