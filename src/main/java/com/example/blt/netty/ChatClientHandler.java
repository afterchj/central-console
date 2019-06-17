package com.example.blt.netty;

import com.example.blt.utils.ConsoleUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(ChatClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
        if (arg1.equals("7701011B66")) {
            ConsoleUtil.persistHosts();
        }
        //客户端主要用来接收服务器发送的消息
        logger.info("from server:" + arg1);
    }

}
