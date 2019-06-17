package com.example.blt.socket;

import com.example.blt.netty.ServerIniterHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;

/**
 * Created by hongjian.chen on 2019/2/15.
 */
public class NettyServer {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(NettyServer.class);

    public static void start(int port) {
        try {
            EventLoopGroup acceptor = new NioEventLoopGroup();
            EventLoopGroup worker = new NioEventLoopGroup();
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //设置TCP相关信息
            bootstrap.option(ChannelOption.ALLOW_HALF_CLOSURE, true);
            bootstrap.group(acceptor, worker);//设置循环线程组，前者用于处理客户端连接事件，后者用于处理网络IO
            bootstrap.channel(NioServerSocketChannel.class);//用于构造socketchannel工厂
            bootstrap.childHandler(new MyServerIniterHandler());//为处理accept客户端的channel中的pipeline添加自定义处理函数
            // 服务器绑定端口监听
            final ChannelFuture bind = bootstrap.bind(port).sync();
            logger.warn("端口[" + port + "]绑定成功!");
            Channel channel = bind.sync().channel();
            // 监听服务器关闭监听
            //channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//             退出
//            acceptor.shutdownGracefully();
//            worker.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        start(8000);

    }
}
