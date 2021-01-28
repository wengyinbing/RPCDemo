package com.weng.simple.test;

import com.weng.simple.Client.RpcClientProxy;
import com.weng.simple.Server.HelloObject;
import com.weng.simple.Server.HelloService;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
