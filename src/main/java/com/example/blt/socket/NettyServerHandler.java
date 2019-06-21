package com.example.blt.socket;

import com.example.blt.entity.ConsoleKeys;
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
import java.util.HashMap;
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

    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Set<Map> vaddrSet = new CopyOnWriteArraySet<>();
    private static Set<Map> lmacSet = new CopyOnWriteArraySet<>();


    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String msg) throws Exception {
        Channel channel = arg0.channel();
        channel.writeAndFlush(msg);
        logger.info("[" + channel.remoteAddress().toString() + "] receive:" + msg);
        SocketAddress address = channel.remoteAddress();
        String str = address.toString();
        String ip = str.substring(1, str.indexOf(":"));
        String vaddr = ConsoleKeys.VADDR.getValue();
        String lmac = ConsoleKeys.lMAC.getValue();
        Set list1 = ConsoleUtil.getInfo(lmac);
        Set list2 = ConsoleUtil.getInfo(vaddr);
        if (list1 == null) {
            lmacSet.clear();
        }
        if (list2 == null) {
            vaddrSet.clear();
        }
        if (msg.indexOf("77040F0227") != -1) {
            Map map = ExecuteTask.pingInfo(msg, ip);
            Map params = new HashMap();
            executorService.submit(() -> {
                MapUtil.removeEntries(map, new String[]{"vaddr"});
                vaddrSet.add(map);
                params.put("list", vaddrSet);
                sqlSessionTemplate.selectOne("console.saveUpdate", params);
                ConsoleUtil.saveInfo(vaddr, vaddrSet);
                logger.info("vaddrSize=" + vaddrSet.size());
            });
        } else if (msg.indexOf("77040F01") != -1) {
            Map map = ExecuteTask.pingInfo(msg, ip);
            Map params = new HashMap();
            executorService.submit(() -> {
                MapUtil.removeEntries(map, new String[]{"vaddr"});
                lmacSet.add(map);
                params.put("list", lmacSet);
                sqlSessionTemplate.selectOne("console.saveUpdate", params);
                ConsoleUtil.saveInfo(lmac, lmacSet);
                logger.info("lmacSize=" + lmacSet.size());
            });
        }
    }


    //在建立链接时发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress().toString() + "] " + "online");
    }

    //退出链接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("[" + channel.remoteAddress().toString() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close().sync();
    }
}
