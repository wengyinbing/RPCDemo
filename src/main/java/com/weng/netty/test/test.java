package com.weng.netty.test;


import com.weng.netty.Client.RpcRequest;
import com.weng.netty.Server.stage.ResponseCode;



/**
 * @author wengyinbing
 * @data 2021/1/27 21:31
 **/
public class test {
    public static void main(String[] args) {
        System.out.println(ResponseCode.SUCCESS.getCode());
        Integer a = 128;
        Integer b = 128;//-128 ~ 127 在常量池中 其余
        System.out.println(a == b);
        Object obj = new RpcRequest();
        System.out.println(obj);
        System.out.println(obj instanceof RpcRequest);
    }
}
