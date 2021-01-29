package com.weng.netty.Server;

import com.weng.netty.Client.RpcRequest;
import com.weng.netty.Server.stage.RpcException;

/**
 * @author wengyinbing
 * @data 2021/1/21 19:26
 **/
public interface RpcServer {
    void start(int port);
    void start();

    <T> void publishService(Object service, Class<T> serviceClass) throws RpcException;
    <T> void publishService(T service, String serviceName);
}
