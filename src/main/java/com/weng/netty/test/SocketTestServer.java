package com.weng.netty.test;

import com.weng.netty.Server.DefaultServiceRegistry;
import com.weng.netty.Server.ServiceRegistry;
import com.weng.netty.Server.SocketServer;
import com.weng.netty.Server.file.HelloService;
import com.weng.netty.Server.file.HelloServiceImpl;

/**
 * @author wengyinbing
 * @data 2021/1/27 19:26
 **/
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        //RpcServer rpcServer = new RpcServer(serviceRegistry);
        //rpcServer.start(9000);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
