package com.weng.netty.test;

import com.weng.netty.Server.DefaultServiceRegistry;
import com.weng.netty.Server.NettyServer;
import com.weng.netty.Server.ServiceRegistry;
import com.weng.netty.Server.file.HelloService;
import com.weng.netty.Server.file.HelloServiceImpl;

/**
 * @author wengyinbing
 * @data 2021/1/26 23:31
 **/
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
