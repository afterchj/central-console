package com.example.blt.socket;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.netty.ClientMain;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.MapUtil;
import com.example.blt.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器主要的业务逻辑
 *
 * @author hongjian.chen
 * @version time：2018/2/28.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

//    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Set<Map> vaddrSet = new CopyOnWriteArraySet<>();
    private static Set<Map> lmacSet = new CopyOnWriteArraySet<>();
    private static Set<Map> lightSet = new CopyOnWriteArraySet<>();


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
        ExecuteTask.saveInfo(msg, map, lightSet,true);
//        String vaddrKey = ConsoleKeys.VADDR.getValue();
//        String lmacKey = ConsoleKeys.lMAC.getValue();
//        Set lmac = ConsoleUtil.getInfo(lmacKey);
//        Set vaddr = ConsoleUtil.getInfo(vaddrKey);
//        if (lmac == null) {
//            lmacSet.clear();
//        }
//        if (vaddr == null) {
//            vaddrSet.clear();
//        }
//        Map map = ExecuteTask.pingInfo(msg, ip);
//        if (msg.indexOf("77040F01") != -1) {
//            executorService.submit(() -> {
//                MapUtil.removeEntries(map, new String[]{"lmac"});
//                lmacSet.add(map);
//                ConsoleUtil.saveLmac(lmacKey, lmacSet, 80);
//            });
//        } else if (msg.indexOf("77040F0227") != -1) {
//            executorService.submit(() -> {
//                MapUtil.removeEntries(map, new String[]{"vaddr"});
//                vaddrSet.add(map);
//                ConsoleUtil.saveVaddr(vaddrKey, vaddrSet, 30);
//            });
//        } else if (msg.indexOf("77010315") != -1) {
//            JSONObject object = new JSONObject();
//            object.put("host", "all");
//            object.put("command", msg.substring(0, msg.length() - 2));
//            clientMain.sendCron(8001, object.toJSONString(), false);
//        }
    }


    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress().toString() + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
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
