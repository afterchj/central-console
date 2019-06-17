package com.example.blt.socket;

import com.example.blt.entity.ConsoleInfo;
import com.example.blt.entity.HostInfo;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
import com.example.blt.utils.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * Created by hongjian.chen on 2019/2/15.
 */

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MyServerHandler.class);
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().localAddress().toString() + " connected");
    }
    /**
     * @param buf
     * @return
     * @author hongjian.chen
     * TODO  此处用来处理收到的数据中含有中文的时  出现乱码的问题
     * 2019年2月15日 下午3:57:28
     */
    private String getMessage(ByteBuf buf,String ip) {
        String msg;
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            msg = new String(con, "UTF-8");
            ExecuteTask.pingInfo(msg,ip);
//            StrUtil.buildLightInfo(msg,ip);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException：" + e.getMessage());
            return null;
        }
        return msg;
    }

    /**
     * 功能：读取服务器发送过来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 第一种：接收字符串时的处理
        Channel channel = ctx.channel();
        String str = channel.remoteAddress().toString();
        String ip = str.substring(1, str.indexOf(":"));
        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf,ip);
        ctx.writeAndFlush(rev);
        logger.info("receive from 8000 client:" + rev);
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
