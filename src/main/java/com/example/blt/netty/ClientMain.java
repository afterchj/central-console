package com.example.blt.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
public class ClientMain {

    private static Logger logger = LoggerFactory.getLogger(ClientMain.class);
    private static String host = "127.0.0.1";
    //    private static String host = "192.168.51.95";
    private static int port = 8001;
    Channel channel = null;

    public static void main(String[] args) throws IOException {
//		new ClientMain("122.112.229.195", 8001).run();
//		new ClientMain("119.3.49.192", 8001).run();
//        new ClientMain("127.0.0.1", 8001).run();
        new ClientMain().run();
    }

    public void run() throws IOException {
        Channel channel = getChannel();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        logger.warn("请输入指令：");
        while (true) {
            String input = reader.readLine();
            if (input != null) {
                if ("quit".equals(input)) {
                    System.exit(0);
                }
                channel.writeAndFlush(input);
            }
        }
    }

    public void sendCron(String str, boolean flag) {
        Channel channel = getChannel();
        //向服务端发送内容
        channel.writeAndFlush(str);
        if (flag) {
            try {
                channel.closeFuture();
            } catch (Exception e1) {
                logger.error("Exception=" + e1);
            }
        }
    }

    public Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        //设置一个worker线程，使用
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker);
        //指定所使用的 NIO 传输 Channel
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientInitialHandler());
        try {
            //使用指定的 端口设置套 接字地址
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            logger.error("InterruptedException=" + e.getMessage());
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e1) {
                e.printStackTrace();
            }
        }
        return channel;
    }
}
