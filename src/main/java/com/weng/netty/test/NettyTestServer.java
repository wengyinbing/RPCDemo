package com.weng.netty.test;

import com.weng.netty.Server.NettyServer;
import com.weng.netty.Server.file.HelloService;
import com.weng.netty.Server.file.HelloServiceImpl;
import com.weng.netty.Server.netty_item.ProtobufSerializer;
import com.weng.netty.Server.stage.RpcException;

/**
 * @author wengyinbing
 * @data 2021/1/26 23:31
 **/
public class NettyTestServer {
    public static void main(String[] args) throws RpcException {
        HelloService helloService = new HelloServiceImpl();

        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtobufSerializer());
        //server.start(9999);
        server.publishService(helloService, HelloService.class);
    }
}
