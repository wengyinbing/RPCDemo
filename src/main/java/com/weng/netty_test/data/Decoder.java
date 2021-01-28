package com.weng.netty_test.data;

import com.weng.netty.Server.netty_item.CommonDecoder;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengyinbing
 * @data 2021/1/28 15:09
 **/
public class Decoder extends ReplayingDecoder {
    private static final Logger logger = LoggerFactory.getLogger(Decoder.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       // System.out.println("解码decode");
        logger.info("解码decode");
        int magic = in.readInt();
        if(magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包: {}", magic);

        }
        int length = in.readInt();//协议中传递数据的长度
        int data = in.readInt();
        out.add(data);
        //System.out.println("解码完成");
        logger.info("解码完成");
    }
}
