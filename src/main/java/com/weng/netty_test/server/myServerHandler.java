package com.weng.netty_test.server;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengyinbing
 * @data 2021/1/28 15:39
 **/
public class myServerHandler extends SimpleChannelInboundHandler<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(myServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Integer integer) throws Exception {
        logger.info("channelRead0");
        try{logger.info("服务器接收到数据: {}", integer);
        ChannelFuture future = ctx.writeAndFlush(integer + 1234);
            logger.info("服务器接传送给客户端数据: {}", integer+1234);
        future.addListener(ChannelFutureListener.CLOSE);}
        finally {
            ReferenceCountUtil.release(integer);
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete");
        super.channelReadComplete(ctx);
//        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
