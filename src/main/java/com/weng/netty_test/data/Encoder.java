package com.weng.netty_test.data;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengyinbing
 * @data 2021/1/28 14:58
 **/
public class Encoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    private static final Logger logger = LoggerFactory.getLogger(Encoder.class);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {
        //System.out.println("编码 encode");
        //System.out.println("处理的数据:" + msg.toString());
        logger.info("编码encode");
        logger.info("处理的数据:{}",msg.toString());
        out.writeInt(MAGIC_NUMBER);

        out.writeInt(1);//length
        out.writeInt((int)msg);
    }
}
