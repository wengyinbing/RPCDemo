package com.weng.netty.test;

import com.weng.netty.Client.NettyClient;
import com.weng.netty.Client.RpcClient;
import com.weng.netty.Client.RpcClientProxy;
import com.weng.netty.Server.file.HelloObject;
import com.weng.netty.Server.file.HelloService;


/**
 * @author wengyinbing
 * @data 2021/1/26 23:31
 **/
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        //System.out.println(object.toString());
        String res = helloService.hello(object);
        System.out.println(res);

    }
}
