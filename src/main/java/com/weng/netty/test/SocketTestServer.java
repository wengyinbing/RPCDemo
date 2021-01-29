package com.weng.netty.test;

import com.weng.netty.Server.SocketServer;
import com.weng.netty.Server.file.HelloService;
import com.weng.netty.Server.file.HelloServiceImpl;
import com.weng.netty.Server.netty_item.HessianSerializer;
import com.weng.netty.Server.netty_item.KryoSerializer;
import com.weng.netty.Server.netty_item.ProtobufSerializer;
import com.weng.netty.Server.stage.RpcException;

/**
 * @author wengyinbing
 * @data 2021/1/27 19:26
 **/
public class SocketTestServer {
    public static void main(String[] args) throws RpcException {
        HelloService helloService = new HelloServiceImpl();
        //ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //serviceRegistry.register(helloService);
        //RpcServer rpcServer = new RpcServer(serviceRegistry);
        //rpcServer.start(9000);
        //SocketServer socketServer = new SocketServer(serviceRegistry);
        //socketServer.start(9000);
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.publishService(helloService, HelloService.class);
    }
}
