package com.example.blt.netty;

import com.example.blt.utils.AddrUtil;
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

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 8001;

    //        private static String host = "192.168.56.1";
//        private static String host = "192.168.16.60";
//        private static String host = "192.168.51.97";
//        private static String host = "119.3.49.192";

    public static void main(String[] args) throws IOException {
        run(AddrUtil.getIp(true), 8001);
//        run("iotsztp.cn", 8001);
    }

    public static void run(String host, int port) throws IOException {
        Channel channel = getChannel(host, port);
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

    public static void sendCron(String str) {
        Channel channel = getChannel(HOST, PORT);
        //向服务端发送内容
        channel.writeAndFlush(str);
        try {
            channel.closeFuture();
        } catch (Exception e1) {
            logger.error("Exception=" + e1);
        }
    }

    public static void sendCron(String... str) {
        Channel channel = getChannel(str[0], PORT);
        //向服务端发送内容
        channel.writeAndFlush(str[1]);
        try {
            channel.closeFuture();
        } catch (Exception e1) {
            logger.error("Exception=" + e1);
        }
    }


    private static Channel getChannel(String host, int port) {
        Channel channel = null;
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
