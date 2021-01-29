package com.weng.netty.test;

import com.weng.netty.Client.RpcClientProxy;
import com.weng.netty.Client.SocketClient;
import com.weng.netty.Server.file.HelloObject;
import com.weng.netty.Server.file.HelloService;
import com.weng.netty.Server.netty_item.HessianSerializer;
import com.weng.netty.Server.netty_item.KryoSerializer;
import com.weng.netty.Server.netty_item.ProtobufSerializer;

/**
 * @author wengyinbing
 * @data 2021/1/27 19:25
 **/
public class SocketTestClient {

    public static void main(String[] args) {
        //RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        //SocketClient client = new SocketClient("127.0.0.1", 9000);
        SocketClient client = new SocketClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}