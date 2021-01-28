package com.weng.netty_test.server;

import com.weng.netty_test.data.Encoder;
import com.weng.netty_test.data.Decoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import io.netty.handler.logging.LogLevel;

/**
 * @author wengyinbing
 * @data 2021/1/28 14:15
 **/
@AllArgsConstructor
public class myServer {
    private final int port;

    public void start() throws Exception{
        ServerBootstrap sb = new ServerBootstrap();
        EventLoopGroup sg = new NioEventLoopGroup();
        sb.group(sg)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, 256)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new Encoder());
                        pipeline.addLast(new Decoder());
                        pipeline.addLast(new myServerHandler());
                        //pipeline.addLast(new NettyServerHandler());
                    }
                });
        ChannelFuture future = sb.bind(port).sync();
        future.channel().closeFuture().sync();


    }

    public static void main(String[] args) throws Exception {
        int port = 9000;
        System.out.println("start server with port: " + port);
        new myServer(port).start();
    }
}
