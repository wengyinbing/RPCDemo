package com.weng.netty.Server;

import com.weng.netty.Client.RpcRequest;
/**
 * @author wengyinbing
 * @data 2021/1/21 19:26
 **/
public interface RpcServer {
    void start(int port);
}
