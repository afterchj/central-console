package com.example.blt.utils;

import com.example.blt.netty.ClientInitialHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by hongjian.chen on 2019/2/15.
 */

public class SocketUtil {

    private static Logger logger = LoggerFactory.getLogger(SocketUtil.class);

    /**
     * @param cmd
     */
    public static void sendCmd(String cmd) {
        Socket client = null;
        PrintWriter pw = null;
        try {
            client = new Socket("192.168.16.103", 8001);
            pw = new PrintWriter(client.getOutputStream(), true);
            pw.write(cmd);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            pw.flush();
            pw.close();
            try {
                client.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public static String sendCmd2(String host, String cmd) {
        Socket client = null;
        PrintWriter pw = null;
        String code;
        try {
//            client = new Socket(host, 8001);
            client = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(8001);
            client.connect(socketAddress);
//            pw = new PrintWriter(client.getOutputStream(), true);
//            pw.write(cmd);
            client.getOutputStream().write(cmd.getBytes());
//            client.close();
            code = "0";
            return code;
        } catch (IOException e) {
            logger.error(e.getMessage());
            code = "1";
            return code;
        }
//        finally {
//            pw.flush();
//            pw.close();
//            try {

//            } catch (IOException e) {
//                logger.error(e.getMessage());
//                code = "1";
//                return code;
//            }
//        }
    }

    public static String sendMsg3(String host, String cmd)  {
        //设置一个worker线程，使用
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker);
        //指定所使用的 NIO 传输 Channel
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientInitialHandler());
        //使用指定的 端口设置套 接字地址
        Channel channel = null;
        String code;
        try {
            channel.id();
            channel = bootstrap.connect(host, 8001).sync().channel();
            //向服务端发送内容
            channel.writeAndFlush(cmd);
            code = "0";
            return code;
        } catch (InterruptedException e) {
            e.printStackTrace();
            code = "1";
            return code;
        }
    }

    public static void main(String[] args) {
        sendCmd2("192.168.16.56","1000");
    }
}