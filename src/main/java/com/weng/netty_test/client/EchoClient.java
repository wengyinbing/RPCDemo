package com.weng.netty_test.client;



import com.weng.netty_test.data.Decoder;
import com.weng.netty_test.data.Encoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author wengyinbing
 * @data 2021/1/28 13:35
 **/
//@AllArgsConstructor
public class EchoClient {
    private final String host = "localhost";
    private final int port = 9000;
    private  final static  Bootstrap b = new Bootstrap();;
    private static final Logger logger = LoggerFactory.getLogger(EchoClient.class);
    static {
        EventLoopGroup g = new NioEventLoopGroup();

        try{
            b.group(g)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //.remoteAddress(new InetSocketAddress(host,port))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {    //5
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new Decoder())
                                    .addLast(new Encoder())
                            .addLast(new EchoClientHandler());
                            //new EchoClientHandler());
                        }
                    });
            //ChannelFuture f = b.connect().sync();
            //f.channel().closeFuture().sync();
        }
        catch( Exception e){
            System.out.println("连接客户端出错！");
        }
        finally {
            //g.shutdownGracefully().sync();
        }
    }
    public void start(int data) throws Exception{
        ChannelFuture future = b.connect(host, port).sync();
        logger.info("客户端连接到 {} {}",host,port);
        Channel channel = future.channel();
        if(channel != null){
            channel.writeAndFlush(data).addListener(future1 -> {
                if(future1.isSuccess()) {
                    logger.info(String.format("客户端发送数据: %s", data));
                } else {
                    logger.error("发送消息时有错误发生: ", future1.cause());
                }
            });
            channel.closeFuture().sync();
            //后面处理服务端返回的数据。
            AttributeKey<Integer> key = AttributeKey.valueOf("data");
            int redata = channel.attr(key).get();
            logger.info("服务器返回的数据 {}",redata);
        }

    }

    public static void main(String[] args) throws Exception {
        new EchoClient().start(10);
    }

}
