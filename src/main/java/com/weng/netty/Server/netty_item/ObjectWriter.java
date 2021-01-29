package com.weng.netty.Server.netty_item;

import com.weng.netty.Client.RpcRequest;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wengyinbing
 * @data 2021/1/29 10:35
 **/
public class ObjectWriter {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    public static void writeObject(OutputStream outputStream, Object object, CommonSerializer serializer) throws IOException {
        outputStream.write(intToBytes(MAGIC_NUMBER));
        byte[] src = intToBytes(MAGIC_NUMBER);
        System.out.println("协议包表头： "+src[0] + src[1] + src[2]+src[3]);
        if (object instanceof RpcRequest) {
            outputStream.write(intToBytes(PackageType.REQUEST_PACK.getCode()));
        } else {
            outputStream.write(intToBytes(PackageType.RESPONSE_PACK.getCode()));
        }
        outputStream.write(intToBytes(serializer.getCode()));
        byte[] bytes = serializer.serialize(object);
        outputStream.write(intToBytes(bytes.length));
        outputStream.write(bytes);
        outputStream.flush();
    }
    private static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }
}
