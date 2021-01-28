package com.weng.netty_test.client;

import com.weng.simple.Server.RpcResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengyinbing
 * @data 2021/1/28 14:09
 **/
//extends SimpleChannelInboundHandler<int>
public class EchoClientHandler extends SimpleChannelInboundHandler<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(EchoClientHandler.class);
    /*@Override
    public void channelActive(ChannelHandlerContext ctx) {
        //System.out.println("channelactive");
        logger.info("channelActive");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", //2
                CharsetUtil.UTF_8));
    }*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Integer integer) throws Exception {
        try{
        System.out.println("Client received: " + integer);
        AttributeKey<Integer> key = AttributeKey.valueOf("data");
        ctx.channel().attr(key).set(integer);
        ctx.channel().close();}
        finally {
            ReferenceCountUtil.release(integer);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {                    //4
        System.out.println(this.getClass());
        cause.printStackTrace();
        ctx.close();
    }
}
