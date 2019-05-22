package com.example.blt.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
public class ClientMain {
	private String host;
	private int port;
	private boolean stop = false;

	public ClientMain(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
//		new ClientMain("122.112.229.195", 8001).run();
//		new ClientMain("119.3.49.192", 8001).run();
		new ClientMain("127.0.0.1", 8001).run();
//		new ClientMain("192.168.16.56", 8001).run();
	}

	public void run() throws IOException {
	    //设置一个worker线程，使用
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker);
		//指定所使用的 NIO 传输 Channel
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ClientInitialHandler());
		try {
		    //使用指定的 端口设置套 接字地址
			Channel channel = bootstrap.connect(host, port).sync().channel();
			//向服务端发送内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("请输入指令：");
			while (true) {
				String input = reader.readLine();
				if (input != null) {
					if ("quit".equals(input)) {
						System.exit(1);
					}
					channel.writeAndFlush(input);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
